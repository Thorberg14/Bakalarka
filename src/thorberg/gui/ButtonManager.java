package thorberg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonManager implements ActionListener{
	//SINGLETON
	
	private static ButtonManager inst;
	
	public static ButtonManager getInstance(){
		if (inst==null) inst = new ButtonManager();
		return inst;
	}
	
	private ButtonManager(){
	}
	
    @Override
    public void actionPerformed(ActionEvent ae) {
    	((MyButton) ae.getSource()).onClick();
    }
    
}
