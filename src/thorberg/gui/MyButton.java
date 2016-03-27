package thorberg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JButton;

public abstract class MyButton extends JButton{
 	private static final long serialVersionUID = 1L;
 	
 	private Image bgImage;
 	
 	public MyButton(String s,int x,int y){
 		super();
 		setFocusable(false);
 		setLayout(null);
 		setLocation(x,y);
 		setSize(100,30);
 		setText(s);
 		addActionListener(ButtonManager.getInstance());
 		setBackground(Color.LIGHT_GRAY);
 	} 
 	
 	public void setBackground(Image i){
 		bgImage = i;
 	}
 	
 	public void paintComponent(Graphics g){
 		if (bgImage!=null) g.drawImage(bgImage,0,0,null);
 		else super.paintComponent(g);
 	}
 	
 	public abstract void onClick();
}
