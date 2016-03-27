package gui.view;

import javax.swing.*;

import thorberg.gui.MyComboBox;
import thorberg.gui.MyLabel;

public class Screen extends JFrame{
	
	public MyComboBox sourceSelector;
	public JCheckBox dataReloadCheck;
	public MyLabel dataInfo;
	
	public MyComboBox graphFormatSelector;
	
	public ButtonGroup graphTypeSelector;
	public JRadioButton orientedType;
	public JRadioButton nonOrientedType;

	public JButton confirm;
	
	
	public Screen(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocation(200,100);
        setVisible(true);
        setLayout(null);
        
        //add components//
        
        add(new MyLabel("Zdroj dát: ",50,50));
	     
	    sourceSelector = new MyComboBox(150,50);
	    sourceSelector.setSize(150,30);
	    add(sourceSelector);
	    
	    MyLabel graphLabel = new MyLabel("Grafový formát: ",50,200);
	    add(graphLabel);
	    graphFormatSelector = new MyComboBox(150,200);
	    graphFormatSelector.setSize(150,30);
	    add(graphFormatSelector);
	     
	    dataInfo = new MyLabel("Vyberte zdroj",100,100);
	    dataInfo.setSize(250,30);
	    dataInfo.setVisible(true);
	    add(dataInfo);
			
		dataReloadCheck = new JCheckBox();
		dataReloadCheck.setVisible(false);
		dataReloadCheck.setSelected(false);
		dataReloadCheck.setText("Stiahnut data znova");
		dataReloadCheck.setBackground(getBackground());
		dataReloadCheck.setSize(150,20);
		dataReloadCheck.setLocation(100,140);
		add(dataReloadCheck);
		
		confirm = new JButton();
		confirm.setLocation(120,280);
		confirm.setText("SPRACUJ");
		confirm.setSize(150,30);
		confirm.setVisible(true);
		add(confirm); 
		
		orientedType = new JRadioButton();
		orientedType.setLocation(100,240);
		orientedType.setVisible(true);
		orientedType.setText("Orientovaný");
		orientedType.setSize(100,20);
		orientedType.setBackground(getBackground());
		add(orientedType);
		
		nonOrientedType = new JRadioButton();
		nonOrientedType.setLocation(200,240);
		nonOrientedType.setVisible(true);
		nonOrientedType.setText("Neorientovaný");
		nonOrientedType.setSize(120,20);
		nonOrientedType.setBackground(getBackground());
		nonOrientedType.setSelected(true);
		add(nonOrientedType);
		
		graphTypeSelector = new ButtonGroup();
		graphTypeSelector.add(orientedType);
		graphTypeSelector.add(nonOrientedType);
		     
		//END OF CONPONENTS
		repaint();
		revalidate();
	}
}
