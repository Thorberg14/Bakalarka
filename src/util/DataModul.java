package util;

public class DataModul {
	
	public String stanica;
	public String prichod;
	public String odchod;
	public String poznamka;
	public int vzdialenost;
	public String lineType;
	public DataModul(){
		
	}
	public DataModul(String l,String s,String p,String o,String poz,String vz){
		lineType = l;
		if (s.length() > 1) stanica = s; else stanica = "";
		if (stanica.endsWith(" ")) stanica = s.substring(0, s.length()-1);
		if (stanica.startsWith(" ")) stanica = s.substring(1, s.length());
		stanica = stanica.replace("&amp;", "&");
		
		if (p.length() > 1) prichod = p; else prichod = "";
		if (o.length() > 1) odchod = o; else odchod = "";
		if (poz.length() > 1) poznamka = poz; else poznamka = ""; 
		try {
			vzdialenost = Integer.parseInt(vz);
		}
		catch (NumberFormatException e){
			vzdialenost = 0;	
		}
	}
	public void print(){
		System.out.println("---------------------------");
		System.out.println("LineType: "+lineType);
		System.out.println("Stanica: "+stanica);
		System.out.println("Prichod: "+prichod);
		System.out.println("Odchod: "+odchod);
		System.out.println("Poznamka: "+poznamka);
		System.out.println("Vzdialenost: "+vzdialenost);
		System.out.println("---------------------------");
	}
	
}
