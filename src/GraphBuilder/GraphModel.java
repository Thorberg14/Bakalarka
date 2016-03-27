package GraphBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;

import graph.Edge;
import graph.Node;
import util.Logger;
import util.MyWriter;

public class GraphModel {
	
	public static final int DEFAULT = 0, LOGICAL = 1, PAJEK = 2;
	public static String[] graphNames = { "Default", "Logical", "Pajek" };
	
	protected boolean oriented = false;
	protected boolean multigraph = true;
	
	protected Hashtable<String, Node> nodes = new Hashtable<String, Node>();
	protected Hashtable<String, Edge> edges = new Hashtable<String, Edge>();
	
	protected ArrayList<Node> nodesA = new ArrayList<Node>();
	protected ArrayList<Edge> edgesA = new ArrayList<Edge>();
	
	protected int nodeId = 0;
	protected int edgeId = 0;
	
	public boolean addNode(Node n){
		if ( nodes.get(n.name)==null ){
			n.id = ++nodeId;
			nodes.put(n.name, n);
			nodesA.add(n);
			return true;
		}
		return false;
	}
	
	public Node getNode(String n){
		return nodes.get(n);
	}
	
	private Edge isInTheGraph(Edge e){
		Edge edgeInGraph = edges.get(e.name);
		if (edgeInGraph==null && e.oriented) 
			edgeInGraph = edges.get(e.getInvertedName());
		return edgeInGraph;
	}
	public boolean addEdge(Edge e){
		if (e.oriented && !oriented) {
			Logger.logError(this, "oriented edge To Not oriented graph!");
			return false;
		}
		
		Edge old = isInTheGraph(e);		
		if ( old != null) { //edge is already in graph
			//if (old.dist != e.dist && e.dist > 0) Logger.logProblem(this, "Diffrent distance for the same Edge: " + e.name);
			//if (!old.lineType.equals(e.lineType)) Logger.logProblem(this, "Diffrent lineType for the same Edge");
			old.dist.add(e.dist.get(0));
			old.time.add(e.time.get(0));
			return false;
		}
		//ELSE (not in the graph yet)
		if (!e.oriented && oriented) { 
			e.oriented = true; 
			addEdgeToGraph(e); 
			addEdgeToGraph(e.getInvertedEdge());
		}
		else addEdgeToGraph(e);
		
		return true;
	}
	
	private void addEdgeToGraph(Edge e){

		e.id = ++edgeId;
		edges.put(e.name, e);
		edgesA.add(e);
		
		e.from.edges.add(e);
		if (!e.oriented) e.target.edges.add(e);
	}

	/*public Edge getEdge(String n){
		
		if (ORIENTED) return edges.get(n);
		//ELSE
		Edge e = edges.get(n);
		if (e!= null) return e;
		//ELSE
		n = Edge.invertName(n);
		e = edges.get(n);
		return e;
	}*/
	
	
	public void printToFile(int type,String sourceName,boolean oriented) throws FileNotFoundException, UnsupportedEncodingException{
		switch (type){
		case DEFAULT: printToFileAsDefault(sourceName,oriented); break;
		case LOGICAL: printToFileAsLogical(sourceName,oriented); break;
		case PAJEK: printToFileAsPajek(sourceName,oriented); break;
		default: printToFileAsDefault(sourceName,oriented); break;
		}
	}
	public void printToFileAsLogical(String sourceName,boolean oriented) throws FileNotFoundException, UnsupportedEncodingException{
		File nodesF = new File("GRAPHS\\"+sourceName+"\\"+"logical_nodes.txt");
		PrintWriter writer = MyWriter.getWriter(nodesF);
		
		for (int i=0;i<nodesA.size();i++){
			writer.println(nodesA.get(i).id +" "+nodesA.get(i).name);
			Logger.logInfo(this, nodesA.get(i).id +" "+nodesA.get(i).name);
		}
		writer.close();
		
		File edgesF = new File("GRAPHS\\"+sourceName+"\\"+"logical_edges.txt");
		writer = MyWriter.getWriter(edgesF);
		
		for (int i=0;i<nodesA.size();i++){
			Node tmp = nodesA.get(i);
			writer.println("NODE: "+tmp.id);
			for (int j=0;j<tmp.edges.size();j++){
				Edge e = tmp.edges.get(j);
				writer.println("TO: "+ e.target.id + " by " + e.dist.get(0) + "km");
				writer.print("TIME_COST: ");
				for (int k=0;k<e.time.size();k++){
					if (k!=0) writer.print(" ");
					writer.print(e.time.get(k).minutes);
				}
				writer.println();
			}
			writer.println("--------------");
		}
		writer.close();
		
		MyWriter.closeWriter(nodesF);
		MyWriter.closeWriter(edgesF);
	}
	
	public void printToFileAsDefault(String sourceName,boolean oriented) throws FileNotFoundException, UnsupportedEncodingException{
		File nodesF = new File("GRAPHS\\"+sourceName+"\\"+"default_nodes.txt");
		PrintWriter writer = MyWriter.getWriter(nodesF);
		
		for (int i=0;i<nodesA.size();i++){
			writer.println(nodesA.get(i).id +" "+nodesA.get(i).name);
			Logger.logInfo(this, nodesA.get(i).id +" "+nodesA.get(i).name);
		}
		writer.close();
		
		File edgesF = new File("GRAPHS\\"+sourceName+"\\"+"default_edges.txt");
		writer = MyWriter.getWriter(edgesF);

		for (Edge tmp : edgesA){
			writer.print(tmp.from.id+" "+tmp.target.id);		
			writer.print(" "+tmp.dist.get(0));
			for (int j=0;j<tmp.time.size();j++)
				writer.print(" "+tmp.time.get(j).minutes);
			writer.println();
			if (oriented){
				writer.print(tmp.target.id+" "+tmp.from.id);		
				writer.print(" "+tmp.dist.get(0));
				for (int j=0;j<tmp.time.size();j++)
					writer.print(" "+tmp.time.get(j).minutes);
				writer.println();
			}
		}
		writer.close();
		
		MyWriter.closeWriter(nodesF);
		MyWriter.closeWriter(edgesF);
	}
	
	public void printToFileAsPajek(String sourceName,boolean oriented) throws FileNotFoundException, UnsupportedEncodingException{
		File f = new File("GRAPHS\\"+sourceName+"\\"+"pajek.txt");
		PrintWriter writer = MyWriter.getWriter(f);
		writer.println("*Vertices "+nodesA.size());
		for (int i=0;i<nodesA.size();i++){
			writer.println(nodesA.get(i).id +" "+nodesA.get(i).name);
			Logger.logInfo(this, nodesA.get(i).id +" "+"\""+nodesA.get(i).name+"\"");
		}
		
		if (oriented) writer.println("*Arcs");
		else writer.println("*Edges");
		for (Edge tmp: edgesA){
			writer.print(tmp.from.id+" "+tmp.target.id);
			writer.print(" "+tmp.dist.get(0));
			writer.println();
			if (oriented){
				writer.print(tmp.target.id+" "+tmp.from.id);
				writer.print(" "+tmp.dist.get(0));
				writer.println();
			}
		}
		writer.close();
		
		MyWriter.closeWriter(f);
		
		int index = 0;
		for (int i=0;i<nodesA.size();i++){ 
			if (nodesA.get(i).getDegree() > nodesA.get(index).getDegree())
				index = i;
		}
		Logger.logInfo(this, "degree winner: "+nodesA.get(index).name+" - "+nodesA.get(index).getDegree());
	}
}
