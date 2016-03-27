package sources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import DataCollector.DataWriter;

class CP extends WebSource{

	public CP(){
		this.name = "cestovné_poriadky";
		this.shortName = "cp.sk";
		//this.rootUrl = "http://cp.atlas.sk/vlak/odchody/";
		this.rootUrl = "http://cp.atlas.sk/vlak/spoje/";
		dw = new DataWriter(name+"_data.txt");
	}

	@Override
	protected void loadData() throws IOException, InterruptedException {
		BufferedReader in = confirmForm();
		String s;
		while ( (s = in.readLine()) != null){
			System.out.println(s);
		}
	}
	
	//COMMUNICATOR//
	
public BufferedReader confirmForm() throws IOException{
		
		URL url = new URL("http://www.hzpp.hr/en/timetable");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
	         	         
		String urlParameters = "Location=12&Arrival=false&Departure=true";
		
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
}
