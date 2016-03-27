package GraphBuilder;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import graph.Edge;
import graph.Node;
import util.DataModul;
import util.Logger;
import util.Time;

public class Converter {
	
	protected List<DataModul> memory = new ArrayList<DataModul>();
	
	protected DataModul last = null; 
	protected Node lastNode = null;
	protected String routeName = null;
	protected GraphModel gm = null;
	
	protected boolean subRoutePossibility = false;
	protected boolean ignoreRoute = false;
	
	protected final boolean EDGE_ORIENTATION = false;
	
	public Converter(GraphModel gm){
		this.gm = gm; 
	}
	public void convert(DataModul dm){
		if (this.ignoreRoute) return;
		
		Node n = new Node(dm.stanica); 
		if ( gm.addNode(n) == false)
			n = gm.getNode(n.name);
		
		if (last!=null){
			
			Edge e = new Edge(lastNode,n,EDGE_ORIENTATION);		
			e.time.add(getTime(last,dm));
			e.dist = dm.vzdialenost - last.vzdialenost;				
			e.lineType = dm.lineType;
			e.target = n;
			e.from = lastNode;
			if (e.dist < 0){
				if (this.subRoutePossibility){
					Logger.logDebug(this, "subroute discovered in "+dm.lineType+" ("+dm.stanica+")");
				}
				else {
					Logger.logProblem(this, "negative distance in "+dm.lineType+" ("+dm.stanica+")");
					ignoreRoute = true;
				}
			}
			else
			gm.addEdge(e);
		}
		
		last = dm;	
		lastNode = n;
		this.subRoutePossibility = false;
		
	}
	
	private Time getTime(DataModul last, DataModul dm){
		Time t;
		if (dm.prichod.length() > 0) t = new Time(dm.prichod);
		else t = new Time(dm.odchod);
		t.minus(new Time(last.odchod));
		if (t.minutes<0) t.minutes+= 24*60; // pridat cely den
		return t;		
	}
	
	public void newRoute(String s) throws FileNotFoundException, UnsupportedEncodingException{
		routeName = s;
		last = null;
		lastNode = null;
	}
	
	public void endRoute(){
		last = null;
		lastNode = null;
	}
	
	public void setSubRoutePos(boolean b){
		this.subRoutePossibility = b;
	}
	
}
