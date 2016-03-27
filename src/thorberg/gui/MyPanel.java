package thorberg.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JLayeredPane;


public class MyPanel extends JLayeredPane{ 
	private Image bgImage;
	
	public final int CENTER = 1;
	private static final long serialVersionUID = 1L;

	public MyPanel(int x,int y){
		super();
		setLocation(x,y);
		setVisible(true);
		setLayout(null);
		setSize(400,400);
		setBackground(new Color(180,180,180,255));
		setOpaque(true);
    }
	
	public void setCloseOnEscape(){
		setFocusable(true);
		addKeyListener(new MyPanelKeyListener());
	}
	
    public void close(){
    	setVisible(false);
    }
    
    public void open(){
    	setVisible(true);
    	requestFocus();
    }
    
    
    @Override
    public void setEnabled(boolean b){
    	super.setEnabled(b);
    	for(Component c : getComponents()) {
    		c.setEnabled(b);
    	}
    }   
    
    public void paintComponent(Graphics g){
    	if (bgImage!=null) g.drawImage(bgImage,0,0,null);
    	else super.paintComponent(g);
    }
    
    public void setBackground(Image i){
    	bgImage = i;
    }
    
    public Point getCenter(){
    	return new Point(getWidth()/2,getHeight()/2);
    }
        
    public Component add(Component c){
    	addToLayer(c,0);
		return c;
    }  
    public void addToLayer(Component c, int i){
    	add(c,new Integer(i));
    }
    public void addToLayerOf(Component adding, Component old){
    	addToLayer(adding,getLayer(old));
    }
    public void addAbove(Component adding, Component old){
    	addToLayer(adding,getLayer(old)+1);
    }
    public void addUnder(Component adding, Component old){
    	addToLayer(adding,getLayer(old)-1);
    }
}
