package thorberg.gui;

import java.awt.Desktop;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class MyHTMLField extends JEditorPane{
	private static final long serialVersionUID = 1L;

	public MyHTMLField(int x, int y){
		setLocation(x,y);
		setSize(100,100);
		setContentType("text/html");
		setEditable(false);	
		addHyperlinkListener(new HyperlinkListener(){

			@Override
			public void hyperlinkUpdate(HyperlinkEvent he) {
				if (he.getEventType() != HyperlinkEvent.EventType.ACTIVATED) return;
				Desktop desktop = Desktop.getDesktop();
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			            desktop.browse(he.getURL().toURI());
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			}
			
		});
	}
}
