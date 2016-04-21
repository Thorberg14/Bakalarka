package graph;

import java.util.ArrayList;

public class Node {
	
	public String name;
	public int id = 0;
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public Node(String name){
		this.name = name;
	}
	
	public void print(){
		System.out.println("NODE "+id);
		System.out.println("name: "+name);
		for (int i=0;i<edges.size();i++){
			System.out.println("Can go to: "+edges.get(i).getToNodeName());
		}
	}
	
	public int getDegree(){
		return edges.size();
	}
}
