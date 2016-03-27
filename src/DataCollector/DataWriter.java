package DataCollector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import util.DataModul;
import util.MyWriter;

public class DataWriter {

	protected File dataFile;
	
	public DataWriter(String sourceName){
		dataFile = new File("DATA\\"+sourceName+".txt");
	}
	
	public boolean dataFileExists(){
		if (dataFile.exists() && dataFile.isFile()) return true;
		else return false;
	}
	
	public File getDataFile(){
		return dataFile;
	}
	public void newRoute(String routeType) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter pw = MyWriter.getWriter(dataFile);
		pw.println("ROUTE;"+routeType+";");
        pw.close();	
	}
	
	public void newSubRoute(String routeType) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter pw = MyWriter.getWriter(dataFile);
		pw.println("SUB_ROUTE;"+routeType+";");
        pw.close();	
	}
	
	public void endRoute() throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter pw = MyWriter.getWriter(dataFile);
		pw.println("END_ROUTE;");
        pw.close();	
	}
	
	public void newStation(DataModul dm) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = MyWriter.getWriter(dataFile);
		writer.print(dm.stanica+";");
		writer.print(dm.prichod+";");
		writer.print(dm.odchod+";");
		writer.print(dm.vzdialenost+";");
		writer.println();
		writer.close();
		//Logger.logInfo(dm.stanica);
	}
}
