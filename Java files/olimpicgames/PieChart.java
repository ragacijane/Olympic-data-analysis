package olimpicgames;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.*;
import java.util.ArrayList;

import javax.swing.JPanel;


public class PieChart extends JPanel {
	private ArrayList<PieChartData> slices;
	private double totalSize;
	
	public PieChart(ArrayList<PieChartData> s) {
		setBackground(Color.WHITE);
		setBounds(100, 50, 500, 500);
		slices=s;
		totalSize=0;
		for (int i = 0; i < slices.size(); i++)
			totalSize+=slices.get(i).getNum();
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D comp2D = (Graphics2D) g;
		int r = getSize().width - 200;
		int xInset = 100;
		int yInset = 100;
		
		int cenX = xInset + r/2;
		int cenY = yInset + r/2;
		
		comp2D.setColor(getBackground());
		comp2D.fillRect(0, 0, getSize().width, getSize().height);
		comp2D.setColor(Color.lightGray);
		Ellipse2D.Double pie = new Ellipse2D.Double(xInset, yInset, r, r);
		comp2D.fill(pie);
		double start = 0;
		for (int i = 0; i < slices.size(); i++) {
			double extent = slices.get(i).getNum() * 360D / totalSize;
			comp2D.setColor(slices.get(i).getColor());
			Arc2D.Double drawSlice = new Arc2D.Double(xInset, yInset, r, r, start, extent,Arc2D.Double.PIE);
			
			Point2D pe = drawSlice.getEndPoint();
			Point2D ps = drawSlice.getStartPoint();
			double d = pe.distance(ps)/2;
			double polur = r/2;
			double str = Math.sqrt(Math.pow(polur, 2) - Math.pow(d, 2));
			int x = (int)(ps.getX() + pe.getX())/2;
			int y = (int)(ps.getY() + pe.getY())/2;
			int addX = (int) (extent/20);
			int addY = (int) (extent/20) + slices.get(i).getCountry().length()/5;
			
			//System.out.println(extent + slices.get(i).getCountry() + " " + start);
			
			if(extent > 90) {
				if((start + extent) < 180) {
					y -= addY + 15;
				}else {
					y += addY + 15;
				}
			}
			else {
				if(start > 180 && start <= 270) {
					x -= addX + slices.get(i).getCountry().length();;
					y += addY;
				}else if(start > 90 && start <= 180) {
					x -= addX  + slices.get(i).getCountry().length();;
					y -= addY;
				}else if(start <= 90) {
					x += addX;
					y -= addY;
				}else if(start > 270) {
					y += addY;
					x += addX;
				}
			}
			start += extent;
			comp2D.fill(drawSlice);
			comp2D.setColor(Color.BLACK);
			comp2D.drawString(slices.get(i).getCountry(), x, y);
			
		}
	}
	
}

