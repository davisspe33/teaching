package Drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;


public abstract class BoundedShape extends Shape {
	
	private boolean isFilled;
	
	public BoundedShape(int x1, int y1, int x2, int y2, Color color1, Color color2, boolean isFilled, Stroke stroke){
		super(x1, y1, x2, y2, color1, color2, stroke);
		this.isFilled = isFilled;
	}
	
	public abstract void draw(Graphics2D g);
	
	public boolean getIsFilled(){
		return this.isFilled;
	}
	
	public void setIsFilled(boolean isFilled){
		this.isFilled = isFilled;
	}
	
	public int getUpperLeftX(){
		return (super.getX1() < super.getX2()) ? super.getX1() : super.getX2();
	}
	
	public int getUpperLeftY(){
		return (super.getY1() < super.getY2()) ? super.getY1() : super.getY2();
	}
	
	public int getWidth(){
		return Math.abs(super.getX1() - super.getX2());
	}
	
	public int getHeight(){
		return Math.abs(super.getY1() - super.getY2());
	}
}
