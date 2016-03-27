package sources;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import DataCollector.DataWriter;
import GraphBuilder.DataReader;
import GraphBuilder.GraphModel;

public abstract class WebSource {

	protected String name;
	protected String rootUrl;
	protected String shortName;
	protected DataWriter dw;
	
	protected final String USER_AGENT = "Mozilla/5.0";
	
	public void init(){
		dw = new DataWriter(name);
	}
	
	public boolean dataFileExists(){
		return dw.dataFileExists();
	}
	public String getName(){
		return name;
	}
	public BufferedReader openDataFile() throws FileNotFoundException{
		return new BufferedReader(new FileReader(dw.getDataFile()));	
	}
	
	public void reloadData() throws IOException, InterruptedException{
		loadData();
	}
	
	public GraphModel processData() throws IOException{
		GraphModel gm = new GraphModel();
		DataReader.read(dw.getDataFile(),gm);
		return gm;		
	}
	
	protected abstract void loadData() throws IOException, InterruptedException;
	protected void delay() throws InterruptedException{
		Random r = new Random();
		Thread.sleep(500+r.nextInt(1000));
	}
}
	
