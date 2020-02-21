package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

public class Rectangle extends BoundedShape {
	
	public Rectangle(int x1, int y1, int x2, int y2, Color color1, Color color2, boolean isFilled, Stroke stroke){
		super(x1, y1, x2, y2, color1, color2, isFilled, stroke);
	}
	
	public Rectangle(){
		super(0, 0, 0, 0, Color.BLACK, null, false, new BasicStroke());
	}
	
	@Override
	public void draw(Graphics2D g){
		if (super.getGradient()){
			if (super.getColor2() == null)
				g.setPaint(super.getColor1());
			else{
				GradientPaint gradient = new GradientPaint(super.getX1(), super.getY1(), super.getColor1(), super.getX2(), super.getY2(), super.getColor2());
				g.setPaint(gradient);
			}
		}        
		else{
			g.setPaint(super.getColor1());
		}
		if (super.getIsFilled()){
			Rectangle2D rect = new Rectangle2D.Double(super.getUpperLeftX(), super.getUpperLeftY(), super.getWidth(), super.getHeight());
			g.fill(rect);
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
		g.drawRect(super.getUpperLeftX(), super.getUpperLeftY(), super.getWidth(), super.getHeight());
	}
	
}
