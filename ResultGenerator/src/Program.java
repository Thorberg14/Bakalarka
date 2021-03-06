import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Program {
	static String path = "";
	
	public static void main(String[] args) throws IOException {	
		path = args[0];
		System.out.println("Working path changed to: "+path);
		Commander.setWorkingPath(path);
		List<String> gf = getGraphFiles();	
		for (String path : gf){
			Map<String, String> d = getDictionary(path);
			createHTML(d);
		}
		
	}

	static List<String> getGraphFiles() throws IOException{	
		List<String> files = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(path+"\\graphPaths.txt"));
		String s;
		while ((s = br.readLine())!= null){
			files.add(s);	
		}
		br.close();
		return files;	
	}
	
	static Map<String, String> getDictionary(String graphPath) throws IOException{	
		
		Map<String, String> dict = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new FileReader(graphPath+"\\info.txt"));
		String s;
		//GET GRAPH PACKAGE PROPERTIES
		while ((s = br.readLine())!= null){
			String [] a = s.split(" ",2);
			a[1] = a[1].replace("GRAPH_PATH", graphPath);

			dict.put(a[0], a[1]);
		}
		br.close();
		
		if (dict.get("[TITLE]") == null)
			dict.put("[TITLE]","Network");
		
		if (dict.get("[COUNTRY]") == null)
			dict.put("[COUNTRY]","Unknown");
		
		if (dict.get("[COUNTRY_CODE]") == null)
			dict.put("[COUNTRY_CODE]","DEFAULT");
		
		//CALCULATE GRAPH VALUES
		br = new BufferedReader(new FileReader(path+"\\commands.txt"));
		
		while ( (s = br.readLine())!= null){
			for (String key : dict.keySet())
				s = s.replace(key, dict.get(key));
			
			String[] a = s.split(" ");
			String key = a[0];
			String result = Commander.runCommand(Arrays.copyOfRange(a, 1, a.length));
			System.out.println(key+" = "+result);
			if (result!=null)
				dict.put(key, result);
		} 
		br.close();
		return dict;
	}
	
	static void createHTML(Map<String, String> dict) throws IOException{		
					
		String htmlName = dict.get("[TITLE]").replace(" ", "_")+"_"+dict.get("[COUNTRY_CODE]").replace(" ", "_")+".html";
		PrintWriter writer = new PrintWriter(path+"\\HTML\\"+htmlName, "UTF-8");
		
		
		BufferedReader br = new BufferedReader(new FileReader(path+"\\HTML\\template.html"));
		String s = null;
		while ((s = br.readLine())!= null){
			for (String key : dict.keySet()) {
				s = s.replace(key, dict.get(key));
			}
			s = s.replaceAll("\\[(.)*\\]", "N/A");
			System.out.println(s);
			writer.println(s);				
		}
		writer.close();
		br.close();
	}
}
