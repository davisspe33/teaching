package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public abstract class Shape{
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color1;
	private Color color2;
	private Stroke stroke;
	private boolean gradient;
	private float lineWidth;
	private boolean isDashed;
	private float[] dashLength;
	
	public Shape(int x1, int y1, int x2, int y2, Color color1, Color color2, Stroke stroke){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color1 = color1;
		this.color2 = color2;
		this.stroke = stroke;
		this.lineWidth = 1;
	}
	
	public Shape(){
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.color1 = Color.BLACK;
		this.color2 = null;
		this.stroke = new BasicStroke();
		this.lineWidth = 1;
	}
	public abstract void draw(Graphics2D g);
		
	public abstract void setIsFilled(boolean isFilled);
		
	public boolean getGradient(){
		return this.gradient;
	}
	
	public void setGradient(boolean gradient){
		this.gradient = gradient;
	}
	
	public int getX1(){
		return this.x1;
	}
	
	public void setX1(int x1){
		if (x1 >= 0)
			this.x1 = x1;
		else
			throw new IllegalArgumentException("x1 must be greater than or equal to zero!");
	}
	public int getY1(){
		return this.y1;
	}
	
	public void setY1(int y1){
		if (y1 >= 0)
			this.y1 = y1;
		else
			throw new IllegalArgumentException("y1 must be greater than or equal to zero!");
	}
	
	public int getX2(){
		return this.x2;
	}
	
	public void setX2(int x2){
		if (x2 >= 0)
			this.x2 = x2;
		else
			throw new IllegalArgumentException("x2 must be greater than or equal to zero!");
	}
	
	public int getY2(){
		return this.y2;
	}
	
	public void setY2(int y2){
		if (y2 >= 0)
			this.y2 = y2;
		else
			throw new IllegalArgumentException("y2 must be greater than or equal to zero!");
	}
	
	public Color getColor1(){
		return this.color1;
	}
	
	public Color getColor2(){
		return this.color2;
	}
	
	public Stroke getStroke(){
		return this.stroke;
	}
	
	public void setColor1(Color color){
		this.color1 = color;
	}
	
	public void setColor2(Color color){
		this.color2 = color;
	}
	
	public Float getLineWidth(){
		return this.lineWidth;
	}
	
	public void setLineWidth(Float width){
		this.lineWidth = width;
	}
	
	public boolean getIsDashed(){
		return this.isDashed;
	}
	
	public void setIsDashed(boolean isDashed){
		if (isDashed){
			this.isDashed = true;
			System.out.print("its being set here");
		}
			
		else
			this.isDashed = false;
	}
	
	public void setDashLength(float[] dashes){
		this.dashLength = dashes;
	}
	
	public float[] getDashLength(){
		return this.dashLength;
	}
}