package util;

public class Logger {
	public static void logInfo(Object o, String s){
		//System.out.println("INFO: "+s);
	}
	public static void logProblem(Object o, String s){
		System.out.println(o+" -> "+"PROBLEM: "+s);
	}
	public static void logDebug(Object o, String s){
		System.out.println(o+" -> "+"DEBUG: "+s);
	}
	public static void logError(Object o, String s){
		System.out.println(o+" -> "+"ERROR: "+s);
	}
}
