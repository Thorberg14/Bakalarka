package gui.view;

import javax.swing.JLabel;

public class MyLabel extends JLabel{
	private static final long serialVersionUID = 1L;

	public MyLabel(String text, int x, int y){
		super();
		setText(text);
		setVisible(true);
		setLocation(x,y);
		setSize(100,30);
	}
}
