package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MyWriter {

	private static List<File> l = new ArrayList<File>();

	public static PrintWriter getWriter(File f) throws FileNotFoundException, UnsupportedEncodingException{
		boolean opened = false;
		for (int i=0;i<l.size();i++)
			if (l.get(i).equals(f)) {
				opened = true;  
				break; 
			}
		PrintWriter pw;
		if (opened) {
			pw = new PrintWriter(new FileOutputStream(f,true));
		}
		else {
			f.getParentFile().mkdirs();
			pw = new PrintWriter(f, "UTF-8");
			l.add(f);
		}
	
		return pw;
	}
	
	public static void closeWriter(File f){
		l.remove(f);
		
	}
}
