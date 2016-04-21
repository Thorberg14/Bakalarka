package thorberg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

import thorberg.util.TPSCounter;

public class MyFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private MyPanel currSc;
	
	private TPSCounter fpsCounter;
	
    public MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLayout(null);
        getContentPane().setBackground(new Color(200,200,200,255));   
        
        fpsCounter = new TPSCounter();
      }
    
    public void setScreen(MyPanel p){	
    	if (currSc!=null) remove(currSc);
    	currSc=p;
    	add(currSc); 	
    	revalidate();
    }
    
    public void setScreenSize(int x, int y){
    	super.setSize(x,y);
    	int dx = getWidth() - getContentPane().getWidth();
    	int dy = getHeight() - getContentPane().getHeight();
    	super.setSize(this.getWidth()+dx,this.getHeight()+dy);
    }
    
    public Dimension getScreenSize(){
    	return getContentPane().getSize();
    }
       
    public int getCurrentFPS(){
    	return fpsCounter.getCurrentTPS();
    }
    
    public void paint(Graphics g){
    	fpsCounter.tick();   	
    	super.paint(g);
    }
}
