package olimpicgames;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class LoadingScreen extends JPanel{
	MainFrame mainFrame;
	
	public LoadingScreen(MainFrame mF) {
		super(new BorderLayout());
		mainFrame=mF;
		populateFrame();
	}
	

	private void populateFrame() {
		
		JPanel topItem= new JPanel (new FlowLayout(FlowLayout.CENTER));
		JPanel leftItem= new JPanel (new FlowLayout(FlowLayout.CENTER));
		JPanel rightItem= new JPanel (new FlowLayout(FlowLayout.CENTER));
		JLabel title= new JLabel("Wellcome!");
		topItem.add(title);
		this.add(topItem, BorderLayout.NORTH);
		
		JPanel centerItem= new JPanel( new GridLayout(6,1,10,10));
		
		JLabel choseType= new JLabel("Please chose type of input");
		JRadioButton groupRadio = new JRadioButton("Group input",true);
		JRadioButton singleRadio = new JRadioButton("Single input");
		JLabel year = new JLabel("Please enter the year");
		TextField inputYear = new TextField();
		JButton loadData = new JButton("Load");
		
		ButtonGroup groupOfRadio=new ButtonGroup();
		groupOfRadio.add(groupRadio);
		groupOfRadio.add(singleRadio);
		year.setEnabled(false);
		inputYear.setEnabled(false);
		
		groupRadio.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				year.setEnabled(false);
				inputYear.setEnabled(false);
			}
				
		});
		singleRadio.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				year.setEnabled(true);
				inputYear.setEnabled(true);
			}
		});
		
		centerItem.add(choseType);
		centerItem.add(groupRadio);
		centerItem.add(singleRadio);
		centerItem.add(year);
		centerItem.add(inputYear);
		centerItem.add(loadData);
		

		loadData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(groupRadio.isSelected())
					mainFrame.load(0);
				else
					mainFrame.load(Integer.parseInt( inputYear.getText()) );
			}

		});
		
		this.add( centerItem , BorderLayout.CENTER);
		this.add( leftItem , BorderLayout.WEST);
		this.add( rightItem , BorderLayout.EAST);
	}
}
