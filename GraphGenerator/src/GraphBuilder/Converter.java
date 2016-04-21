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
		Logger.logInfo(this, dm.stanica);
	}
	
	public boolean tryToConnect(DataModul dm){
		
		DataModul last = getLast();
		if (last!=null){
			//if (getTime(last,dm).minutes < 0) return false;
			//if (!this.validDistance(dm.vzdialenost - last.vzdialenost) && dm.vzdialenost > 0) Logger.logError(this, "Hidden subroute dont stat at 0!" + routeName + " - " +dm.stanica );
			if (!this.validDistance(dm.vzdialenost - last.vzdialenost)) return false;
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
		
		//if (l.size() <= 0) { Logger.logDebug(this, "empty rotue"); return; }
		int numberOfErrors = findNegativeConnection(l);
		if (numberOfErrors > 0){
			if (numberOfErrors == l.size()-1){
				Logger.logProblem(this, "Reversed route ("+this.routeName+") with size: "+l.size());
				sortRouteByKm(l);
			}
			else {
				int wi = this.getErrorIndex(l);
				Logger.logProblem(this,"Route ("+this.routeName+") with problem at "+l.get(wi).stanica);
				l.remove(wi);
				l = l.subList(0, wi-1);
			}	
		}
		
		numberOfErrors = findNegativeConnection(l);
		if (numberOfErrors > 0) Logger.logError(this, "ROUTE PROBLEMS STILL NOT SOLVED!!");
		convertRoute();
	}
	
	private void sortRouteByKm(List<DataModul> l){
		l.sort(new Comparator<DataModul>(){
			@Override
			public int compare(DataModul d1, DataModul d2) {
				return d1.vzdialenost - d2.vzdialenost;
			}	
		});
	}
	
	private int getErrorIndex(List<DataModul> l){
		for (int i = 1; i<l.size();i++){
			DataModul dm = l.get(i);
			DataModul last = l.get(i-1);
			if (getTime(last,dm).minutes < 0 || !this.validDistance(dm.vzdialenost - last.vzdialenost)){
				return i;
			}
		}
		return -1;
	}
	private int findNegativeConnection(List<DataModul> l){
		int nc = 0;
		DataModul last = null;
		for (DataModul dm : l){
			if (last!=null){
				if (getTime(last,dm).minutes < 0 || !this.validDistance(dm.vzdialenost - last.vzdialenost)){
					nc++;
					//Logger.logDebug(this, "Negative value in: "+last.stanica+" -> "+dm.stanica);
				}
			}
			last = dm;
		}
		return nc;
	}
	
	private int getAvgDistance(List<DataModul> l){
		int sum = 0;
		DataModul last = null;
		for (DataModul dm : l){
			if (last != null)
				sum += (dm.vzdialenost - last.vzdialenost);
			last = dm;
		}
		if (l==null || l.isEmpty())
			return 0;
		return sum / l.size();
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
	
	private boolean validDistance(int act){
		if (act < 0) return false;
		return true;
	}
}
