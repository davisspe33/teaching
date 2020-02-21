package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DrawPanel extends JPanel {
	
	public ArrayList<Shape> shapes;                
	private int shapeCount;                
	private int shapeType;               
	private Shape currentShape;            
	private Color color1;          
	private Color color2;
	private boolean filledShape;           
	private JLabel mouseLocation;          
	private String locationText;      
	private Stroke currentStroke;
	private boolean gradient;
	private float lineWidth;
	private boolean isDashed;
	private float[] dashLength;
	private boolean cleared;
	
	class MouseEventHandler extends MouseAdapter implements MouseMotionListener{
		@Override
		public void mousePressed(MouseEvent event){
			setShapeType(shapeType);
			currentShape.setX1(event.getX());
			currentShape.setY1(event.getY());
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			currentShape.setColor1(color1);
			currentShape.setColor2(color2);
			currentShape.setLineWidth(lineWidth);
			if (isDashed)
				System.out.print("is dashed in the draw panel");
			currentShape.setIsDashed(isDashed);
			currentShape.setDashLength(dashLength);
			locationText = "(" + event.getX() + "," + event.getY() + ")";
			mouseLocation.setText(locationText);
		}
		
		@Override
		public void mouseReleased(MouseEvent event){
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			addShape(currentShape);
			repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent event){
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			if (shapeType != 0){                                   
				if (filledShape)
					currentShape.setIsFilled(true);
			}
			if (gradient)
				currentShape.setGradient(true);
			currentShape.setColor1(color1);
			currentShape.setColor2(color2);
			currentShape.setLineWidth(lineWidth);
			locationText = "(" + event.getX() + "," + event.getY() + ")";
			mouseLocation.setText(locationText);
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent event){
			locationText = "(" + event.getX() + "," + event.getY() + ")";
			mouseLocation.setText(locationText);
		}
	}
	
	public DrawPanel(JLabel mouseLocation){
		
		this.mouseLocation = mouseLocation;
		this.shapeCount = 0;
		this.shapes = new ArrayList<Shape>(); 
		this.shapeType = 0;                          
		this.setShapeType(0);
		this.color1 = Color.BLACK;
		this.color2 = null;
		this.filledShape = false;
		this.lineWidth = 1;
		this.isDashed = false;
		this.dashLength = null;
		this.cleared = false;
		MouseEventHandler mouseListener = new MouseEventHandler();
		this.addMouseListener(mouseListener);
		MouseEventHandler mouseMotionListener = new MouseEventHandler();
		this.addMouseMotionListener(mouseMotionListener);
		setBackground(Color.WHITE);
	}
	
	@Override
	public void paintComponent(Graphics g){          
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		for (Shape shape : shapes){
			shape.draw(g2d);
		}
		if (this.cleared == false){
			if (currentShape != null)
				currentShape.draw(g2d);
		}
		this.cleared = false;
		
	}
	
	public void addShape(Shape shape){
		shapes.add(shape);                 
		shapeCount += 1;
	}
	
	public void setShapeType(int index){
		shapeType = index;
		if (index == 0)
			currentShape = new Line();
		else if (index == 1)
			currentShape = new Oval();
		else if (index == 2)
			currentShape = new Rectangle();
	}
	
	public void setColor1(Color paint){
		this.color1 = paint;
		System.out.print("Color 1 in DrawPanel:" + this.color1);
	}
	
	public void setColor2(Color paint){
		this.color2 = paint;
		System.out.print("Color 2 in DrawPanel:" + this.color2);
	}
	
	public void setStroke(Stroke stroke){
		this.currentStroke = stroke;
	}
	
	public void setFilledShape(boolean filled){
		if (filled)
			filledShape = true;
		else
			filledShape = false;
	}
	
	public Float getLineWidth(){
		return this.lineWidth;
	}
	
	public void setLineWidth(Float width){
		this.lineWidth = width;
	}
	
	public void clearLastShape(){  
		currentShape = null;
		if (shapeCount > 0){
			Shape toBeRemoved = shapes.get(shapeCount - 1);
			shapes.remove(toBeRemoved);
			shapeCount = shapeCount - 1;
			System.out.print(shapes);
			repaint();
		}
		if (shapeCount == 0)
			this.clearDrawing();
	}
	
	public void setGradient(boolean set){
		if (set)
			gradient = true;
		else
			gradient = false;
	}
	
	public void setIsDashed(boolean isDashed){
		if (isDashed)
			this.isDashed = true;
		else
			this.isDashed = false;
	}
	
	public void setDashLength(float[] dashes){
		this.dashLength = dashes;
	}
	
	public void clearDrawing(){    
		shapes.clear();
		shapeCount = 0;
		this.cleared = true;
		repaint();
	}	
}
