package util;

public class Time {
	public int minutes;
	
	public Time(String s){ //"13:45
		if (s.length() == 0){ minutes = 0; return; }
		String[] a = s.split(":");
		try{
			minutes = Integer.parseInt(a[0]) * 60; //hour
			minutes += Integer.parseInt(a[1]); //minutes	
		}
		catch(Exception e){
			e.printStackTrace();
			minutes = 0;
		}
	}
	public Time(int min){
		minutes = min;
	}
	
	public void minus(Time t){
		minutes = minutes - t.minutes;	
	}
	
	public void print(){
		System.out.println(minutes / 60 +":"+ minutes%60);
	}
}
