package sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import util.DataModul;
import util.Logger;


public class ZelPage extends WebSource{
	public static final int SLOVENSKO = 0, CESKO = 1, NEMECKO = 2, POLSKO = 3, BULHARSKO = 4, MADARSKO = 5, RAKUSKO = 6, RUMUNSKO = 7, SVAJCIARSKO = 8, TEST = 9;
	private static final String[] countries = { "slovensko", "ceska-republika","nemecko","polsko","bulharsko","madarsko", "rakousko", "rumunsko", "svycarsko","test"};
	private static final String[] countries_short = { "SVK","CZE","DEU","POL","BGR","HUN","AUT","ROM","CHE","TEST" }; // Nemecko DEU, Svajciarsko CHE
	protected int selectedCountry = 0;
		
	public ZelPage(int country){
		super();
		selectedCountry  = country;
		this.name = "ZelPage_"+countries_short[selectedCountry];
		this.shortName = "zel_page"+"_"+countries_short[selectedCountry];
		this.rootUrl = "http://www.zelpage.cz/";
	}

	@Override
	protected void loadData() throws IOException, InterruptedException {
		
		URL url = new URL(rootUrl+"trate/"+countries[selectedCountry]);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8"));
		parseRouteList(in);
	}
	
	protected void parseRouteList(BufferedReader in) throws IOException, InterruptedException{ 
		List<String> links = new ArrayList<String>();
		String s;
		while ((s = in.readLine()) != null){
			if (s.contains("<a href=\"trate")){
				String[] a = s.split("\"");
				String link = a[1];
				if (link.contains("trat-")) 
					links.add(link);
			}
		}
		for (String link : links ) {
			//Logger.logInfo(link);
			parseRouteTable(rootUrl+link);
			delay();
			//break;
		}
	}
	
	protected void parseRouteTable(String link) throws IOException{
		Logger.logInfo(this, link);
		String trackNumber = link.split("trat-")[1];
		if (!trackIsValid(trackNumber)) return;
		
		dw.newRoute("vlak"+"_"+trackNumber);
		URL url = new URL(link);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8"));
		
		int depth = 0;
		String s;
		while ((s = in.readLine()) != null){
			if (s.contains("<tr onmouseover=")){
				int TABLE_WIDTH = 4;
				String[] table = new String[TABLE_WIDTH];
				
				for (int i = 0;i<TABLE_WIDTH;i++){
					s = in.readLine();
					String[] a = s.split("</t");
					if (a.length > 1) table[i] = a[0];
					else table[i] = "";
				}

				table[TABLE_WIDTH-1] = digUpStationName(table[TABLE_WIDTH-1]);
				Logger.logInfo(this, table[TABLE_WIDTH-1]);
				
				if (table[0].equals("")){
					if (table[1].equals("")){
						Logger.logInfo(this, "Station without km data");
						continue;
					}
					if (depth < 1) { 
							depth = 1; 
							dw.newSubRoute("vlak"+"_"+trackNumber);
					}
					dw.newStation(new DataModul("vlak",table[3],"","",table[2],table[1]));
				}
				else {
					if (depth > 0) { depth--; dw.endRoute(); }
					dw.newStation(new DataModul("vlak",table[3],"","",table[2],table[0]));
				}
			}
		}
		for (int i=0;i<depth+1; i++) dw.endRoute(); //musi sa ukoncit route vo vsetkch vrstvach
	}
	
	protected String digUpStationName(String s){
		int in = 0;
		StringBuilder sb = new StringBuilder();
		List<String> l = new ArrayList<String>();
		
		for (int i =0;i<s.length();i++){
			char c = s.charAt(i);
			if (c == '<') { 
				in++; 
				if (sb.length() > 0) {
					l.add(sb.toString());
					sb.setLength(0);
				}
				continue;
			}
			
			if (c == '>') { in--; continue; }
			if (in == 0) sb.append(c);
			
			
		}
		for (String si : l){
			if (!si.startsWith("&nbsp")) return si;
		}
		return "UNKNOWN";
		/*
		Pattern pattern = Pattern.compile("\\>(.+?)\\<");
		Matcher matcher = pattern.matcher(s);
		if ( matcher.find() ) s = matcher.group();
		s = s.substring(1, s.length()-1);
		s = s.replace("<strong>","");
		return s;
		*/
	}
	
	protected boolean trackIsValid(String track){
		int trackNum = -1;
		try { 
			trackNum = Integer.parseInt(track);
		}
		catch(Exception e){
			//e.printStackTrace();
			Logger.logProblem(this,"Track is not a number "+track);
		}
		if (selectedCountry  == SLOVENSKO && trackNum >= 200) return false;
		if (selectedCountry  == CESKO && trackNum >= 900) return false;
		return true;
	}
}