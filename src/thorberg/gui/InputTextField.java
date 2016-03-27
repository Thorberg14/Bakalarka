package thorberg.gui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class InputTextField extends JTextField{
	private static final long serialVersionUID = 1L;
	
	String defaultName;
	
	public InputTextField(String name,int x, int y){
		
		defaultName= name;
		setText(defaultName);
		setLocation(x,y);
		setSize(100,20);
		
		addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent fe) {
				if (getText().equals(defaultName)) setText("");
				
			}

			@Override
			public void focusLost(FocusEvent fe) {
				if (getText().equals("")) toDefault();	
			}
		});
				
	}
	
	public void toDefault(){
		setText(defaultName);
		setForeground(Color.BLACK);
	}

}
