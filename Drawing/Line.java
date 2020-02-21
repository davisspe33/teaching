package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Line extends Shape {
	
	
	public Line(int x1, int y1, int x2, int y2, Color color1, Color color2, Stroke stroke){
		super(x1, y1, x2, y2, color1, color2, stroke);
	}
	
	public Line(){
		super(0, 0, 0, 0, Color.BLACK, null, new BasicStroke());
	}
	
	@Override
	public void setIsFilled(boolean isFilled){
		System.out.print("line object not filled");
	}
	
	public boolean getIsFilled(){
		return false;
	}
			
			
	@Override
	public void draw(Graphics2D g){
		if (super.getColor2() == null)
			g.setPaint(super.getColor1());
		else{
			Color color1p = (Color) super.getColor1();
			Color color2p = (Color) super.getColor2();
			g.setPaint(new GradientPaint(0, 0, color1p, 50, 50, color2p, true));
		}
		
		if (super.getLineWidth() != 1){       
			if (super.getIsDashed()){
				g.setStroke(new BasicStroke(super.getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, super.getDashLength(), 0.0f));
			}
			else{
				BasicStroke stroke = new BasicStroke(super.getLineWidth());
				g.setStroke(stroke);
			}
		}
		else
			if (super.getIsDashed()){
				g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, super.getDashLength(), 0.0f));
			}
			else
				g.setStroke(super.getStroke());
		g.drawLine(super.getX1(), super.getY1(), super.getX2(), super.getY2());  
	}
}
