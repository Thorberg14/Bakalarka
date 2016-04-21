package gui.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GraphBuilder.GraphModel;
import gui.view.Screen;
import sources.*;

public class Controller {
	public static Screen gui;
	
	public static List<WebSource> sources = new ArrayList<WebSource>();
	public static List<String> availableGraphFormats = new ArrayList<String>();
	
	public static void main(String[] args){

		sources.add(new ZelPage(ZelPage.SLOVENSKO));
		sources.add(new ZelPage(ZelPage.CESKO));
		sources.add(new ZelPage(ZelPage.NEMECKO));
		sources.add(new ZelPage(ZelPage.POLSKO));
		sources.add(new ZelPage(ZelPage.BULHARSKO));
		sources.add(new ZelPage(ZelPage.MADARSKO));	
		sources.add(new ZelPage(ZelPage.RAKUSKO));
		sources.add(new ZelPage(ZelPage.RUMUNSKO));
		sources.add(new ZelPage(ZelPage.SVAJCIARSKO));
		
		sources.add(new Test(ZelPage.TEST));
		
		for (WebSource s: sources) s.init();
		
		availableGraphFormats.add(GraphModel.graphNames[0]); //DEFAULT
		availableGraphFormats.add(GraphModel.graphNames[1]); //LOGICAL
		availableGraphFormats.add(GraphModel.graphNames[2]); //PAJEK				
			
		Screen gui = new Screen();
		
		for (WebSource s: sources) gui.sourceSelector.addItem(s.getName());
		for (String t: availableGraphFormats) gui.graphFormatSelector.addItem(t);
		
		gui.sourceSelector.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				if ( sources.get( gui.sourceSelector.getSelectedIndex() ).dataFileExists()){ 
					gui.dataInfo.setText("Data dostupne v subore"); 
					gui.dataReloadCheck.setVisible(true); 
					gui.dataReloadCheck.setSelected(false);
				}
				else { 
					gui.dataInfo.setText("Data je nutne stiahnut z webovej stranky"); 
					gui.dataReloadCheck.setVisible(false); 
					gui.dataReloadCheck.setSelected(true);
				}
			}
			
		});
		
		gui.confirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				WebSource s = sources.get( gui.sourceSelector.getSelectedIndex() );
				try {
					if (gui.dataReloadCheck.isSelected()) s.reloadData();
					GraphModel gm = s.processData();
					gm.printToFile(gui.graphFormatSelector.getSelectedIndex(),s.getName(),gui.orientedType.isSelected());
				} catch (IOException | InterruptedException er) {
					er.printStackTrace();
				}
			}
			
		});
		gui.sourceSelector.setSelectedIndex(0);
	}
	
}
