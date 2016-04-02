package GraphBuilder;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import graph.Edge;
import graph.Node;
import util.DataModul;
import util.Logger;
import util.Time;

public class Converter {
	
	protected String routeName = null;
	protected GraphModel gm = null;
	
	protected List<DataModul> l;
	
	protected final boolean EDGE_ORIENTATION = false;
	
	public Converter(GraphModel gm){
		this.gm = gm; 
	}
	
	public void newRoute(String s) throws FileNotFoundException, UnsupportedEncodingException{
		routeName = s;
		l = new ArrayList<DataModul>();
	}
	
	public void addData(DataModul dm){
		l.add(dm);
	}
	
	public boolean tryToConnect(DataModul dm){
		DataModul last = getLast();
		if (last!=null){
			if (getTime(last,dm).minutes < 0) return false;
			if (dm.vzdialenost - last.vzdialenost  < 0) return false;
		}
		addData(dm);
		return true;	
	}
	
	private void convertRoute(){
		Node lastNode = null;
		
		for (int i = 0;i<l.size();i++){
			DataModul dm = l.get(i);
			
			Node n = new Node(dm.stanica); 
			if ( gm.addNode(n) == false)
				n = gm.getNode(n.name);
			
			if (i > 0){
				//create Edge
				DataModul last = l.get(i-1); 
				Edge e = new Edge(lastNode,n,EDGE_ORIENTATION);		
				e.time.add(getTime(last,dm));
				e.dist.add(new Integer(dm.vzdialenost - last.vzdialenost));				
				e.lineType = dm.lineType;
				e.target = n;
				e.from = lastNode;
				
				gm.addEdge(e);
			}
			
			lastNode = n;
		}
		
	}
			
	public void endRoute(){
		
		if (l.size() <= 0) { Logger.logDebug(this, "empty rotue"); return; }
		int numberOfErrors = findNegativeConnection();
		
		if (numberOfErrors > 0){
			Logger.logDebug(this, "Erors: "+numberOfErrors);
			if (numberOfErrors == l.size()-1)
				Logger.logProblem(this, "Reversed route ("+this.routeName+") with size: "+l.size());
			else Logger.logProblem(this,"Route ("+this.routeName+") with negative values!");
			
			sortRouteByKm();
		}
		
		convertRoute();
	}
	
	private void sortRouteByKm(){
		l.sort(new Comparator<DataModul>(){
			@Override
			public int compare(DataModul d1, DataModul d2) {
				return d1.vzdialenost - d2.vzdialenost;
			}	
		});
	}
	private int findNegativeConnection(){
		int nc = 0;
		DataModul last = null;
		for (DataModul dm : l){
			if (last!=null){
				if (getTime(last,dm).minutes < 0 || dm.vzdialenost - last.vzdialenost  < 0){ 
					nc++;
					Logger.logDebug(this, "Negative value in: "+last.stanica+" -> "+dm.stanica);
				}
			}
			last = dm;
		}
		return nc;
	}
	private Time getTime(DataModul last, DataModul dm){
		Time t;
		if (dm.prichod.length() > 0) t = new Time(dm.prichod);
		else t = new Time(dm.odchod);
		t.minus(new Time(last.odchod));
		if (t.minutes<0) t.minutes+= 24*60; // pridat cely den
		return t;		
	}
	
	private DataModul getLast(){
		if (l == null || l.size() < 1) return null;
		return l.get(l.size()-1);
	}
		
}
