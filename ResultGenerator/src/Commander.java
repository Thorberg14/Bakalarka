import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commander {
	static private String workingPath = null;
	
	static public void setWorkingPath(String path){
		workingPath = path;
	}

	public static String runCommand(String[] s) throws IOException{
		String command = "";
		for (String st : s) command += st+" "; 
		System.out.println("Executing command: "+command);
		ProcessBuilder builder = new ProcessBuilder(s);
		if (workingPath!=null)
			builder.directory(new File(workingPath));
		builder.redirectError(new File("error.txt"));
	    Process p = builder.start();
	    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	     
	    return r.readLine();
	}
}
