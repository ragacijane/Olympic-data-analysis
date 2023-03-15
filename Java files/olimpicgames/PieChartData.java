package olimpicgames;

import java.awt.Color;
import java.util.Random;

public class PieChartData{
	private double num;
	private String country;
	private Color color;
	
	public PieChartData(String x,String y) {
		num = Integer.parseInt(x);
		country = y;
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
		color = Color.getHSBColor(hue, saturation, luminance);
	}
	public double getNum() {return num;}
	public String getCountry() {return country;}

	public Color getColor() {
		return color;
	}
}
