package olimpicgames;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class MainFrame extends JFrame{
	
	enum screenType{LOADING,MAIN}
	screenType scType=screenType.LOADING;
	int yearIn;
	String strOut;
	
	public MainFrame() {
		super("Olimpic Games");

		setLoading();
		
		this.setBounds(new Rectangle(200,200,450,450));
		addWindowListener(new WindowAdapter() {
			public void WindowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	
	private void populateFrame() {
		this.removeAll();
		this.setRootPane(new JRootPane());
		
		switch(scType) {
		case LOADING:
			drawLoadingScreen();
			break;
		case MAIN:
			drawMainScreen(yearIn,strOut);
			break;
		}
		
		setVisible(true);
		revalidate();
	}

	private void drawMainScreen(int x,String y) {
		this.add(new MainScreen(0,this,y));
		this.setBounds(new Rectangle(200,200,900,800));
	}
	
	private void drawLoadingScreen() {
		this.add(new LoadingScreen(this));
		this.setBounds(new Rectangle(200,200,450,450));
	}
	
	public void setLoading() {
		scType=screenType.LOADING;
		populateFrame();
	}
	
	public void setMain() {
		scType=screenType.MAIN;
		populateFrame();
	}
	
	protected void load(int i) {
		System.loadLibrary("olimpicgamesdll");
		yearIn=i;
		strOut=loadFiles(Integer.toString(i));
		if( strOut.equalsIgnoreCase("None 0!0!0!0!0!0!") )
			showdialog();
		else
			setMain();
	}
	
	public void showdialog() {
		String[] options = new String[2];
		options[0] = new String("Pokusaj ponovo");
		options[1] = new String("Napusti program");
		int confirmed = JOptionPane.showOptionDialog(null, "Nema podataka za datu godinu.",
				"Upozorenje!", 0, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if(confirmed == 1)
			dispose();
		else {
			setLoading();
		}
	}


	private native String loadFiles(String s);
	
	protected native String getCountries(String s);
	
	protected native String getSports(String s);
	
	protected native String getYears(String s);
	
	protected native String updateFiles(String s1,String s2,String s3,String s4,String s);
	
	protected native String xyData(String s1,String s2,String s);
	
	public static void main(String args[]) {
		new MainFrame();
	}
	
}
