package thorberg.gui;

import java.awt.Insets;

public abstract class CloseButton extends MyButton{
	private static final long serialVersionUID = 1L;

	public CloseButton(int x, int y) {
        super("X",x,y);
        setSize(30,30);
        this.setMargin(new Insets(0,0,0,0));      
	}   
	
}
