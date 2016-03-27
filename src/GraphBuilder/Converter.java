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
	
	protected List<Edge> edges = new ArrayList<Edge>();
	protected List<Boolean> breaks = new ArrayList<Boolean>(); 
	
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
			
			edges.add(e);
			breaks.add(this.subRoutePossibility);
			/*
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
			gm.addEdge(e); */
			
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
		if (edges.size() <= 0) { Logger.logDebug(this, "empty rotue"); return; }
		boolean rev = true;
		for (Edge e : edges) 
			if (e.dist > 0) 
				rev = false;
		if (rev){
			Logger.logDebug(this,"reversed distance route ("+edges.size()+") discovered in "+edges.get(0).lineType);
			for (Edge e : edges) 
				e.dist *= -1;
		}
		
		for (int i = 0; i< edges.size(); i++){
			Edge e = edges.get(i);
			if (e.dist < 0){
				if (breaks.get(i)){
					Logger.logDebug(this, "subroute discovered in "+e.lineType+" ("+e.from.name+")");
				}
				else {
					Logger.logProblem(this, "negative distance in "+e.lineType+" ("+e.from.name+")");
					return;
				}
			}
			else 
				gm.addEdge(e);
		}
		last = null;
		lastNode = null;
	}
	
	public void setSubRoutePos(boolean b){
		this.subRoutePossibility = b;
	}
	
}
