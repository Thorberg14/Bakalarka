package thorberg.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyPanelKeyListener implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {	
	}

	@Override
	public void keyReleased(KeyEvent e) {	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) 
			( (MyPanel) e.getSource() ).close();	
	}

}
