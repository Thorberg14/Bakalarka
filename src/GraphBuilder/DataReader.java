package GraphBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import util.DataModul;
import util.Logger;

public class DataReader {

	public static void read(File file, GraphModel gm) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));		
		String line = null;
		String routeName = null;
		Stack<Converter> stack = new Stack<Converter>();
		Converter con = null;
		
		boolean merging = false; //trying to go back to upper Route
		boolean ultraDepth = false; // currently in non-labeled subroute 
			// (starts if "merging" was not succesfull, ending when whatever labeled route starts
		
		while ( (line = br.readLine() )!=null){
			if (line.startsWith("ROUTE;") || line.startsWith("SUB_ROUTE")){
				if (line.startsWith("ROUTE") && !stack.empty()) {
					Logger.logProblem(DataReader.class, "LAST ROUTE HAS NOT ENDED!! " + routeName );
					while (!stack.empty()) {
						con = stack.pop();
						con.endRoute();
						ultraDepth = false;
						merging = false;
					}
					stack.clear();
				}
				
				if (ultraDepth){
					ultraDepth = false;
					con.endRoute();
					stack.pop();
					con = null;
				}
				
				con = new Converter(gm);
				stack.push(con);
				
				String[] a = line.split(";");
				routeName = a[1].replace(";",""); 
				con.newRoute(routeName);
				continue;
			}
			if (line.startsWith("END")){
				con.endRoute();
				stack.pop();
				if (!stack.isEmpty()){
					con = stack.lastElement();
					merging = true;
				}
				else {
					con = null;
					merging = false;
				}
				continue;
			}
			//ELSE
			
			String a[] = line.split(";");
			DataModul dm = new DataModul(routeName, a[0],a[1],a[2],"",a[3]);
			if (merging) {
				boolean success = con.tryToConnect(dm);
				if (!success){
					Logger.logDebug(DataReader.class,"Hidden subRoute discovered inside "+routeName+"!");
					/*con.endRoute(); */
					stack.push(con = new Converter(gm));
					con.newRoute(routeName);
					con.addData(dm); 
					ultraDepth = true;
				}
				merging = false;
				
			}
			else {
				con.addData(dm);
			}
		}
		br.close();
	}
}
