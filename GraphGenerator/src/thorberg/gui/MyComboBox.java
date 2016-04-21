package thorberg.gui;

import java.awt.Color;

import javax.swing.JComboBox;

public class MyComboBox extends JComboBox<String>{
	private static final long serialVersionUID = 1L;

	private boolean lock = false;
	
	public MyComboBox(int x, int y){
		setVisible(true);
		setLocation(x,y);
		setSize(80,20);
     //	setBackground(Color.LIGHT_GRAY);
     	setFocusable(false);
	}
	
	public void setLock(boolean b){
		lock = b;
	}
	
	public boolean isLocked(){
		return lock;
	}
	
}
