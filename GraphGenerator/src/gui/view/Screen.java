package gui.view;

import javax.swing.*;

public class Screen extends JFrame{
	
	public JComboBox<String> sourceSelector;
	public JCheckBox dataReloadCheck;
	public MyLabel dataInfo;
	public MyLabel progressInfo;
	
	public JComboBox<String> graphFormatSelector;
	
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
	     
	    sourceSelector = new JComboBox<String>();
	    sourceSelector.setLocation(150,30);
	    sourceSelector.setSize(150,30);
	    add(sourceSelector);
	    
	    MyLabel graphLabel = new MyLabel("Grafový formát: ",50,200);
	    add(graphLabel);
	    graphFormatSelector = new JComboBox<String>();
	    graphFormatSelector.setLocation(150,200);
	    graphFormatSelector.setSize(150,30);
	    add(graphFormatSelector);
	     
	    dataInfo = new MyLabel("Vyberte zdroj",100,100);
	    dataInfo.setSize(250,30);
	    dataInfo.setVisible(true);
	    add(dataInfo);
	    
	    progressInfo = new MyLabel("",100,160);
	    progressInfo.setSize(250,30);
	    progressInfo.setVisible(false);
	    add(progressInfo);
			
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
