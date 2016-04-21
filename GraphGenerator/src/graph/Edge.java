package graph;

import java.util.ArrayList;

import util.Logger;
import util.Time;

public class Edge {

	public int id = 0;
	public String name;
	public Node target;
	public Node from;
	public String lineType;
	public ArrayList<Integer> dist = new ArrayList<Integer>(); // in km
	public ArrayList<Time> time = new ArrayList<Time>(); // in min
	public boolean oriented;
	
	private final String splitter = "_";
	
	public Edge(Node f, Node t, boolean o){
		this.name = f.name + "_" + t.name;
		from = f;
		target = t;
		oriented = o;
		if (f.name.contains(splitter)) Logger.logProblem(this, "SPLITTER IN NODE NAME!!! - " + f.name);
		if (t.name.contains(splitter)) Logger.logProblem(this, "SPLITTER IN NODE NAME!!! - " + f.name);
	}
	
	public void print(){
		System.out.println("EDGE: "+id);
		System.out.println("name: "+name);
		System.out.println("Km: "+dist.get(0));
		if (time.size()>0) System.out.println("Time: "+time.get(0).minutes);
	}
	
	public String getFromNodeName(){
		String a[] = name.split("_");
		return a[0];
	}
	
	public String getToNodeName(){
		String a[] = name.split("_");
		return a[1];
	}
	
	public String getInvertedName(){
		return invertName(name);
	}
	public Edge getInvertedEdge(){
		return new Edge(target,from,oriented);
	}
	public int getAvgTime(){
		int avg = 0;
		for (int i=0;i<time.size();i++)
			avg+=time.get(i).minutes;
		return (int) ( (avg*1.0) / (time.size()+1) );
	}
	
	public int getMinTime(){
		int min = getMaxTime();
		for (int i=0;i<time.size();i++)
			if (time.get(i).minutes < min)
				min = time.get(i).minutes;
		return min;
	}
	
	public int getMaxTime(){
		int max = -1;
		for (int i=0;i<time.size();i++)
			if (time.get(i).minutes > max)
				max = time.get(i).minutes;
		return max;
	}
	
	public static String invertName(String n){
		String a[] = n.split("_");
		return a[1]+"_"+a[0];
	}
}
