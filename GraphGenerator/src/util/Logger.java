package util;

public class Logger {
	public final static int INFO = 0, DEBUG = 1, PROBLEM = 3, ERROR = 4;
	public static int degree = DEBUG;
	
	public static void logInfo(Object o, String s){
		print(INFO,"INFO: "+s);
	}
	public static void logProblem(Object o, String s){
		print(PROBLEM,o+" -> "+"PROBLEM: "+s);
	}
	public static void logDebug(Object o, String s){
		print(DEBUG,o+" -> "+"DEBUG: "+s);
	}
	public static void logError(Object o, String s){
		print(ERROR,o+" -> "+"ERROR: "+s);
	}
	
	protected static void print(int type, String s){
		if (type < degree) return;
		System.out.println(s);		
	}
}
