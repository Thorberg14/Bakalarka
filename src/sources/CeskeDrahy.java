package sources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import DataCollector.DataWriter;
import util.DataModul;

class CeskeDrahy extends WebSource{
	
	public CeskeDrahy(){
		this.name = "ceske_drahy";
		this.shortName = "cd.cz";
		this.rootUrl = "http://www.cd.cz/spojeni/";
		dw = new DataWriter("DATA\\"+name+"_data.txt");
	}

	@Override
	protected void loadData() throws IOException, InterruptedException {
		BufferedReader in = confirmForm();
		//String s;
		//while ((s = in.readLine()) != null) System.out.println(s);
		parseRouteList(in);
	}

	/////////////////COMMUNICATOR/////////////////
	public BufferedReader confirmOdchodForm(String station) throws IOException{
		URL url = new URL(rootUrl+"/dep.aspx");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
	         
		String urlParameters = "FROM_0t=Babice nad Svitavou"
								+ "&FROM_0h=Babice nad Svitavou%1%342"
								+ "&form-datum=7.2.2016 Ne"
								+ "&form-odjezd=true"
								+ "&dtcheckbox=on"
								+ "&cmdSearch=Vyhledat";
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
					
	    con.getResponseCode();
	        
	    return new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8"));
	}
	
	public BufferedReader confirmForm() throws IOException{
		
		URL url = new URL(rootUrl+"/lines.aspx");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
	         
		//MASK_0t=Ex&
		String urlParameters = "dtcheckbox=on&cmdSearch=Vyhledat";
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
					
	    con.getResponseCode();
	        
	    return new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8"));
	}
	
	
	public BufferedReader getRouteList(String nextUrl) throws IOException{
		
		URL url = new URL(rootUrl+"/lines.aspx");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
	         
		String a[] = nextUrl.split("aspx");
		String urlParameters = a[1];
		urlParameters = urlParameters.replaceAll(";", "&");
		urlParameters = urlParameters.replace("?", "");
			
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
					
	    con.getResponseCode();
	        
	    return new BufferedReader( new InputStreamReader(con.getInputStream(),"UTF-8")); 
	}
	
	public BufferedReader getRouteTable(String url) throws MalformedURLException, IOException{
		return new BufferedReader( new InputStreamReader( (new URL(url)).openStream()) );
	}
	
	////////////////PARSER//////////////////////////////
	
	
	private int MAX_RESULTS = 1000; 
	private int tableCount = 0;
	//TrType
		
	public void parseRouteList(BufferedReader in) throws IOException, InterruptedException{   
		for(int ps=0;ps<MAX_RESULTS;ps++){
			System.out.println("STRANKA: "+ps);
			String lastUrl = "";
			String nextUrl = "";
			
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				//	System.out.println(inputLine);
				if (inputLine.contains("contentboxgreyextra")){ //najde riadok s odkazmi
					String[] a = inputLine.split("href=\"");
					for (int i = 0;i<a.length;i++){
          			
						//System.out.println(a[i]);
						if (a[i].contains(">následující<")){
							String[] t = a[i].split("\"");
							nextUrl = rootUrl+t[0];
						}
							
						if (a[i].startsWith("Route")){
							//System.out.println(a[i]);
							String[] t= a[i].split("\"");
							String resUrl = rootUrl+t[0]; 
							System.out.println(resUrl);
							
							t= a[i].split("\">");
							t = t[1].split("<");
							String routeType = t[0];
							
							parseRouteTable(resUrl,routeType);
							
							delay();
						}  
					}
				} 
			}
			in.close();
			System.out.println("______________________");
			System.out.println(nextUrl);
			if (nextUrl.equals(lastUrl)) return;
			delay();
			in = getRouteList(nextUrl);	
		}
	}
	
	public void parseRouteTable(String u, String routeType) throws IOException{
		tableCount++;
		BufferedReader in = getRouteTable(u);
		
		dw.newRoute(routeType);
		System.out.println("PARSE TABLE ("+tableCount+")");
		
		 String inputLine;
		 boolean x = false;
	        while ((inputLine = in.readLine()) != null){
	        	if (inputLine.contains("contentboxwhite")){
	        		String a[] = inputLine.split("\">");
	        		
	        		for (int i=0;i<a.length;i++){
	        			//String[] label = { "STANICA: ", "PRICHOD: ","ODCHOD: ","POZNAMKA: ","KM: " };
	        			if (a[i].startsWith("<li class")) { break; }
	        			if (a[i].startsWith("Km")) { x = true; continue; }
	        			if (x) {
	        				String res[] = new String[5];
	        				for (int j = 0; j < 5 ; j++){
	        					String t[] = a[i].split("<");
	        					res[j] = t[0];
	        					//System.out.println(label[j]+res);
	        					i++;
	        				}
	        				DataModul dm = new DataModul(routeType,res[0],res[1],res[2],res[3],res[4]);
	        				dw.newStation(dm);
	        				i--; 
	        			}  
	        		}
	        		
	        	}
	        }
	}
	
	public void delay() throws InterruptedException{
		Random r = new Random();
		Thread.sleep(500+r.nextInt(1000));
	}
	
}
