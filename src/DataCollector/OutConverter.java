package DataCollector;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import util.DataModul;

public class OutConverter {

	DataWriter dw;
	
	List<DataObject> l = new ArrayList<DataObject>();
	int[] index = new int[5]; 
	int actualDepth = 0;
	
	public OutConverter(DataWriter dw){
		this.dw = dw;
	}
	public void newRoute(String routeType) throws FileNotFoundException, UnsupportedEncodingException{
		dw.newRoute(routeType);
	}
	public void endRoute() throws FileNotFoundException, UnsupportedEncodingException{
		dw.endRoute();
		l.clear();
		for (int i=0;i<5;i++) index[i] = 0;
		actualDepth = 0;
	}
	public void newStation(String[] s) throws FileNotFoundException, UnsupportedEncodingException{
		DataObject o = new DataObject(s);
		l.add(o);
		
		if (actualDepth == 0)
			if (!o.km.equals("") && o.km2.equals("")){
				//pokracuje v rovnakej hlbke
				dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km));
				index[actualDepth] = l.size()-1;
				return;
			}
		if (actualDepth == 0)
			if (o.km.equals("") && !o.km2.equals("")){
				//prechadza do hlbky 
				dw.newSubRoute("vlak");
				dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km2));
				actualDepth = 1;
				index[actualDepth] = l.size()-1;
				return;
			}
		
		if (actualDepth == 1)
			if (!o.km.equals("") && o.km2.equals("")){
				
				
				if (o.name.equals(l.get(index[1]).name) || Integer.parseInt(o.km) < Integer.parseInt(l.get(index[0]).km))
				{ // pada do subroot depth 2
					dw.newSubRoute("vlak");
					dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km));
					actualDepth = 2;
					index[actualDepth] = l.size()-1;
				} 
				else{ // vracia sa do depth 0
					dw.endRoute();
					actualDepth = 0;
					dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km));
					index[actualDepth] = l.size()-1;
				}
				return;
			}
		
		if (actualDepth == 1)
			if (o.km.equals("") && !o.km2.equals("")){
				//pokracuje v rovnakej hlbke
				dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km));
				index[actualDepth] = l.size()-1;
				return;
			}
		
		if (actualDepth == 2)
			if (!o.km.equals("") && o.km2.equals("")){
				//pokracuje v rovnakej hlbke
				dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km));
				index[actualDepth] = l.size()-1;
				return;
			}
		if (actualDepth == 2)
			if (o.km.equals("") && !o.km2.equals("")){
				//vracia sa do hlbky 1
				dw.endRoute();
				actualDepth = 1;
				dw.newStation(new DataModul("vlak",o.name,"","",o.pozn,o.km2));
				index[actualDepth] = l.size()-1;
				return;
			}
					
	}
	
}

class DataObject{
	String km;
	String km2;
	String pozn;
	String name;
	
	public DataObject(String[] s){
		km = s[0];
		km2 = s[1];
		pozn = s[2];
		name = s[3];
		System.out.println(km +","+km2+","+pozn+","+name);
	}
}
