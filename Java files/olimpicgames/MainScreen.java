package olimpicgames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import testpiechart.PieChartPanel;

public class MainScreen extends JPanel {
	MainFrame mainFrame;
	String baseMetrics;
	ArrayList<PieChartData> pieChartList = new ArrayList<PieChartData>();
	ArrayList<String> summerData = new ArrayList<String>();
	ArrayList<String> winterData = new ArrayList<String>();
	ArrayList<String> yearData = new ArrayList<String>();
	
	
	public MainScreen(int x, MainFrame mf, String y) {
		super(new GridLayout(1,2,10,10));
		mainFrame=mf;
		baseMetrics=y;
		populateFrame();
	}

	private void populateFrame() {
		JPanel topItem = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel leftItem= new JPanel (new FlowLayout(FlowLayout.CENTER));
		JPanel rightItem= new JPanel (new FlowLayout(FlowLayout.CENTER));
		JPanel bottomItem= new JPanel (new  GridLayout(2,5,10,10));
		JLabel title = new JLabel("Data about Olimpic Games");
		JPanel xyfilter= new JPanel (new  GridLayout(2,4,10,10));
		JPanel xypanel = new JPanel(new BorderLayout());
		
		JPanel dataItem = new JPanel(new BorderLayout());
		JPanel chartsItem = new JPanel(new GridLayout(2,1,10,10));
		
		topItem.add(title);
		dataItem.add(topItem, BorderLayout.NORTH);
		dataItem.add( leftItem , BorderLayout.WEST);
		dataItem.add( rightItem , BorderLayout.EAST);
		
		JPanel centerItem= new JPanel( new GridLayout(4,2,10,10));
		
		JLabel numPart= new JLabel("Number of all participans:");
		JLabel numPartInput=new JLabel();
		centerItem.add(numPart);
		centerItem.add(numPartInput);
		
		JLabel numDisc= new JLabel("Number of all disciplins:");
		JLabel numDiscInput=new JLabel();
		centerItem.add(numDisc);
		centerItem.add(numDiscInput);
		
		JLabel avgHeight= new JLabel("Averge height of all participans");
		JLabel avgHeightInpu=new JLabel();
		centerItem.add(avgHeight);
		centerItem.add(avgHeightInpu);
		
		JLabel avgWeight= new JLabel("Averge weight of all participans");
		JLabel avgWeightInpu=new JLabel();
		centerItem.add(avgWeight);
		centerItem.add(avgWeightInpu);
		
		Pattern p = Pattern.compile("^(.*)!(.*)!(.*)!(.*)!(.*)!$");
		Matcher m = p.matcher(baseMetrics);
		if(m.matches()) {
			numPartInput.setText(m.group(2));
			numDiscInput.setText(m.group(3));
			avgHeightInpu.setText(m.group(4));
			avgWeightInpu.setText(m.group(5));
		}
		
	//sport
		JLabel sportLabel = new JLabel("Chose sport:");
		Vector<String> sportOptions = new Vector<String>();
		sportOptions.add("none");
		String inputstr=mainFrame.getSports("none");
		String str="";
		p = Pattern.compile("(.*)!(.*)!");
		m = p.matcher(inputstr);
		if(m.matches())
			str=m.group(2);
		int n= Integer.parseInt(str);
		p = Pattern.compile("^(.*?)!(.*)!");
		for(int i=0;i < n ; i++ ) {
			m = p.matcher(inputstr);
			if(m.matches())
				sportOptions.add(m.group(1));
			inputstr=inputstr.substring(inputstr.indexOf('!')+1);
		}
		JComboBox<String> sportComboBox = new JComboBox<>(sportOptions);
		
	//year
		JLabel yearLabel = new JLabel("Chose year:");
		Vector<String> yearOptions = new Vector<String>();
		yearOptions.add("none");
		inputstr=mainFrame.getYears("none");
		p = Pattern.compile("(.*)!(.*)!");
		m = p.matcher(inputstr);
		if(m.matches())
			str=m.group(2);
		n= Integer.parseInt(str);
		p = Pattern.compile("^(.*?)!(.*)!");
		for(int i=0;i < n ; i++ ) {
			m = p.matcher(inputstr);
			if(m.matches())
				yearOptions.add(m.group(1));
			inputstr=inputstr.substring(inputstr.indexOf('!')+1);
		}
		JComboBox<String> yearComboBox = new JComboBox<>(yearOptions);
	//type
		JLabel typeLabel = new JLabel("Chose type:");
		Vector<String> typeOptions = new Vector<String>();
		typeOptions.add("none");
		typeOptions.add("Individual");
		typeOptions.add("Team");
		JComboBox<String> typeComboBox = new JComboBox<>(typeOptions);
	//medals
		JLabel medalLabel = new JLabel("Chose medal:");
		Vector<String> medalOptions = new Vector<String>();

		medalOptions.add("none");
		medalOptions.add("Gold");
		medalOptions.add("Silver");
		medalOptions.add("Bronze");
		JComboBox<String> medalComboBox = new JComboBox<>(medalOptions);
		
		JLabel filterLabel = new JLabel("  ");
		JButton filterData = new JButton("Filter");
		filterData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sportFilter,yearFilter,typeFilter,medalFilter,inputstr;

				sportFilter = sportComboBox.getItemAt(sportComboBox.getSelectedIndex());
				yearFilter = yearComboBox.getItemAt(yearComboBox.getSelectedIndex());
				typeFilter = typeComboBox.getItemAt(typeComboBox.getSelectedIndex());
				medalFilter = medalComboBox.getItemAt(medalComboBox.getSelectedIndex());
				
				if(sportComboBox.getSelectedIndex() != 0 
						|| yearComboBox.getSelectedIndex() != 0 
								|| typeComboBox.getSelectedIndex() != 0 
										|| medalComboBox.getSelectedIndex() != 0)

					inputstr=mainFrame.updateFiles(sportFilter, yearFilter, typeFilter, medalFilter,"none");
				else
					inputstr=baseMetrics;
				Pattern p = Pattern.compile("^(.*)!(.*)!(.*)!(.*)!(.*)!$");
				Matcher m = p.matcher(inputstr);
				if(m.matches()) {
					numPartInput.setText(m.group(2));
					numDiscInput.setText(m.group(3));
					avgHeightInpu.setText(m.group(4));
					avgWeightInpu.setText(m.group(5));
					pieChartList.add(new PieChartData(m.group(2),"Sum"));
				}
				pieChartList = getPieChartData(inputstr);
				chartsItem.removeAll();
			    chartsItem.add(new PieChart(pieChartList));
				xypanel.add(xyfilter, BorderLayout.NORTH);
			    xypanel.add(new XYGraph(summerData,winterData,yearData,310,310),BorderLayout.CENTER);
			    chartsItem.add(xypanel);
			    revalidate();
			}
		});
		//Charts
		pieChartList = getPieChartData(baseMetrics);
		
		chartsItem.add(new PieChart(pieChartList));
		
		//xyYear
		JLabel xyYearLabelStart = new JLabel("Chose start year:");
		JLabel xyYearLabelEnd = new JLabel("Chose end year:");
		JComboBox<String> xyYearComboBoxStart = new JComboBox<>(yearOptions);
		JComboBox<String> xyYearComboBoxEnd = new JComboBox<>(yearOptions);
		
		//xy data Type
		JLabel dataTypeLabel = new JLabel("Chose data:");
		Vector<String> dataTypeOptions = new Vector<String>();

		dataTypeOptions.add("none");
		dataTypeOptions.add("Disciplins");
		dataTypeOptions.add("Height");
		dataTypeOptions.add("Weight");
		JComboBox<String> dataTypeComboBox = new JComboBox<>(dataTypeOptions);
		
		//show graph button
		JLabel showLabel = new JLabel(" ");
		JButton showXYGraphButton = new JButton("Show");
		showXYGraphButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String startYear,endYear,typeFilter,inputstr,str="";

				startYear = xyYearComboBoxStart.getItemAt(xyYearComboBoxStart.getSelectedIndex());
				endYear = xyYearComboBoxEnd.getItemAt(xyYearComboBoxEnd.getSelectedIndex());
				typeFilter = String.valueOf( dataTypeComboBox.getSelectedIndex() );
				inputstr = mainFrame.xyData(startYear, endYear, typeFilter);
				summerData.clear();
				winterData.clear();
				yearData.clear();
				//summer
				Pattern p = Pattern.compile("^(.*?)!(.*)");
				Matcher m = p.matcher(inputstr);
				if(m.matches())
					str=m.group(1);
				int n= Integer.parseInt(str);
				inputstr=inputstr.substring(inputstr.indexOf('!')+1);
				for(int i=0;i < n ; i++ ) {
					m = p.matcher(inputstr);
					if(m.matches()) {
							summerData.add(m.group(1));
						}
						else
							System.out.println("Failed!");
					inputstr=inputstr.substring(inputstr.indexOf('!')+1);
				}
				//winter
				m = p.matcher(inputstr);
				if(m.matches())
					str=m.group(1);
				n= Integer.parseInt(str);
				inputstr=inputstr.substring(inputstr.indexOf('!')+1);
				for(int i=0;i < n ; i++ ) {
					m = p.matcher(inputstr);
					if(m.matches()) {
							winterData.add(m.group(1));
						}
						else
							System.out.println("Failed!");
					inputstr=inputstr.substring(inputstr.indexOf('!')+1);
				}
				//years
				int startY=Integer.parseInt(startYear);
				int endY = Integer.parseInt(endYear);
				for(String Y:yearOptions) {
					int curY;
					if(Y == "none")
						curY = 0;
					else
						curY= Integer.parseInt(Y);
					if(startY <= curY && endY >= curY)
						yearData.add(Y);
				}
				xypanel.add(new XYGraph(summerData,winterData,yearData,310,310),BorderLayout.CENTER);
				revalidate();
			}});
		
		//final
		sportComboBox.setSelectedIndex(0);
		yearComboBox.setSelectedIndex(0);
		typeComboBox.setSelectedIndex(0);
		medalComboBox.setSelectedIndex(0);
		
		bottomItem.add(sportLabel);
		bottomItem.add(yearLabel);
		bottomItem.add(typeLabel);
		bottomItem.add(medalLabel);
		bottomItem.add(filterLabel);
		bottomItem.add(sportComboBox);
		bottomItem.add(yearComboBox);
		bottomItem.add(typeComboBox);
		bottomItem.add(medalComboBox);
		bottomItem.add(filterData);

		dataItem.add(bottomItem, BorderLayout.SOUTH);
		dataItem.add(centerItem, BorderLayout.CENTER);
		
		xyfilter.add(xyYearLabelStart);
		xyfilter.add(xyYearLabelEnd);
		xyfilter.add(dataTypeLabel);
		xyfilter.add(showLabel);
		xyfilter.add(xyYearComboBoxStart);
		xyfilter.add(xyYearComboBoxEnd);
		xyfilter.add(dataTypeComboBox);
		xyfilter.add(showXYGraphButton);
		
		xypanel.add(xyfilter, BorderLayout.NORTH);
		chartsItem.add(xypanel);
		
		this.add(dataItem);
		this.add(chartsItem);
	}
	
	public ArrayList<PieChartData> getPieChartData(String inputstr){
		ArrayList<PieChartData> temp = new ArrayList<PieChartData>();
		String str="";
		Pattern p = Pattern.compile("^(.*)!(.*)!(.*)!(.*)!(.*)!(.*)!$");
		Matcher m = p.matcher(inputstr);
		if(m.matches())
			str=m.group(2);
		int n= Integer.parseInt(str);
		pieChartList.add(new PieChartData(m.group(3),"Sum"));
		p = Pattern.compile("^(.*?)!(.*)!");
		for(int i=0;i < n ; i++ ) {
			m = p.matcher(inputstr);
			if(m.matches()) {
				Pattern p1 = Pattern.compile("^(.*) (.*)");
				Matcher m1 = p1.matcher(m.group(1));
				if(m1.matches()) 
				temp.add(new PieChartData(m1.group(2),m1.group(1)));
				else
					System.out.println("Failed!");
			}
			inputstr=inputstr.substring(inputstr.indexOf('!')+1);
		}
		return temp;
	}
}
