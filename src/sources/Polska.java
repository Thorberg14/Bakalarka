package sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import DataCollector.DataWriter;

class Polska extends WebSource{
	
	public Polska(){
		this.name = "rozklad.pkp";
		this.shortName = "rozklad";
		this.rootUrl = "http://rozklad-pkp.pl/en/ts";
		dw = new DataWriter(name+"_data.txt");
	}

	@Override
	protected void loadData() throws IOException, InterruptedException {	
		BufferedReader in = confirmForm("RJ");
		//String s;
		//while ((s = in.readLine()) != null) System.out.println(s);
		parseRouteList(in);
	}
	
	public BufferedReader confirmForm(String arg) throws IOException{
		
		URL url = new URL(rootUrl+"?trainname="+arg);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/*con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8"); */	        
	    return new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8"));
	}
	
	
	public void parseRouteList(BufferedReader in) throws IOException, InterruptedException{ 
		List<String> suitable = new ArrayList<String>();
		String s;
		System.out.println("AAA");
		while ((s = in.readLine()) != null){
			String[] x = s.split("a href=\"");
			if (x.length > 1)
				if (x[1].contains("trainlink")){
					suitable.add(x[1].split("\" ")[0]);
					break;
				}
		}
		System.out.println("BBB");
		for (String i:suitable){
			System.out.println(i);
			System.out.println("\n\n\n");
			parseRouteTable(i);
		}
	}
	public void parseRouteTable(String link) throws IOException{
		URL url = new URL(link);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8"));
		String s;
		int tableLine = 0;
		int tableCell = 0;
		String[][] table = new String[100][100];
		while ((s = in.readLine()) != null){
			if (s.startsWith("<tr class=")) { tableLine++; tableCell = 0; continue;}
			if (tableLine == 0) continue;
			if (s.equals("<td>")) { tableCell++; continue; }
			if (s.equals("</tbody>")) break;
			if (s.equals("</td>")) continue;
			if (s.equals("<tr>")) continue;
			//System.out.println("["+tableLine+"]"+"["+tableCell+"] - -  "+s);
			if (s.startsWith("<a href")){
				String res = s.split("\">")[1];
				table[tableLine][tableCell] = res;
			}
			else {
				if (s.startsWith("<")) continue;
				table[tableLine][tableCell] = s;	
			}
		//	System.out.println(s);
		}
		for (int i =0;i<tableLine;i++){
			for (int j=0;j<tableCell;j++)
				System.out.println(table[i][j]);
			System.out.println("_________________");
		}
	}
}