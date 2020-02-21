package Drawing;

import com.sun.prism.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Drawing extends JFrame{
	
	private JButton undo;
	private JButton clear;
	private JComboBox shapeSelect;
	private JCheckBox filled;
	private JCheckBox useGradient;
	private JCheckBox dashed;
	private JButton firstColor;
	private JButton secondColor;
	private JLabel lineWidthLabel;
	private JTextField lineWidth;
	private JLabel dashLengthLabel;
	private JTextField dashLength;
	private JPanel canvas;
	private JLabel mouseLocation;
	private float width;
	private float[] dashes;
	private int index;  
	       
	public Drawing(){
		super("Java 2D Drawings");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 800));
		index = 0;
		
		JPanel components = new JPanel();     
		components.setLayout(new GridLayout(2, 1));
		
		JPanel firstPanel = new JPanel();                  
		firstPanel.setLayout(new FlowLayout());
		
		JPanel secondPanel = new JPanel();                 
		secondPanel.setLayout(new FlowLayout());
		
		mouseLocation = new JLabel("(0,0)");
		mouseLocation.setLayout(new BorderLayout());
		
		DrawPanel drawPanel = new DrawPanel(mouseLocation);
		drawPanel.setLayout(new BorderLayout());
		drawPanel.setShapeType(index);
		this.add(drawPanel, BorderLayout.CENTER);
		
		undo = new JButton("Undo");
		undo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				drawPanel.clearLastShape();
			}
		});
		firstPanel.add(undo);
		
		clear = new JButton("Clear");
		clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				drawPanel.clearDrawing();
			}
		});
		firstPanel.add(clear);
		
		JLabel shape = new JLabel("Shape: ");
		firstPanel.add(shape);
		
		String[] shapeTypes = {"Line", "Oval", "Rectangle"};
		shapeSelect = new JComboBox(shapeTypes);
		shapeSelect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				index = shapeSelect.getSelectedIndex();
				drawPanel.setShapeType(index);
			}
		});
		firstPanel.add(shapeSelect);
		
		filled = new JCheckBox("Filled");
		filled.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event){
				if (event.getStateChange() == ItemEvent.SELECTED){  
					drawPanel.setFilledShape(true);
				}
				else
					drawPanel.setFilledShape(false);
			}
		});
		firstPanel.add(filled);
		
		useGradient = new JCheckBox("Use Gradient");
		useGradient.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event){
				if (event.getStateChange() == ItemEvent.SELECTED){  
					drawPanel.setGradient(true);
				}
				else
					drawPanel.setGradient(false);
			}
		});
		secondPanel.add(useGradient);
		
		firstColor = new JButton("1st Color...");
		firstColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				Color color1 = JColorChooser.showDialog(drawPanel, "Choose Color 1", Color.WHITE);
				System.out.print("color1: " + color1);
				drawPanel.setColor1(color1);
			}
		});
		secondPanel.add(firstColor);
		
		secondColor = new JButton("2nd Color...");
		secondColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				Color color2 = JColorChooser.showDialog(drawPanel, "Choose Color 2", null);
				System.out.print("color2: " + color2);
				drawPanel.setColor2(color2);
			}
		});
		secondPanel.add(secondColor);
		
		lineWidthLabel = new JLabel("Line Width: ");
		secondPanel.add(lineWidthLabel);
		
		lineWidth = new JTextField("", 2);
		lineWidth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				if (lineWidth.getText() == null)
					drawPanel.setLineWidth(1.0f);
				else{
					String widthText = lineWidth.getText();
					Float width = Float.valueOf(widthText);
					drawPanel.setLineWidth(width);
				}
			}
		});
		secondPanel.add(lineWidth);
		
		dashLengthLabel = new JLabel("Dash Length: ");
		secondPanel.add(dashLengthLabel);
		
		dashLength = new JTextField("", 2);
		dashLength.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				if (dashLength.getText() != null){
					float dashLen = Float.valueOf(dashLength.getText());
					dashes = new float[1];
					dashes[0] = dashLen;
					drawPanel.setDashLength(dashes);
				}
				else
					drawPanel.setDashLength(null);
			}
		});
		secondPanel.add(dashLength);
		
		dashed = new JCheckBox("Dashed");
		dashed.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event){
				if (event.getStateChange() == ItemEvent.SELECTED){  
					drawPanel.setIsDashed(true);
				}
				else{
					drawPanel.setIsDashed(false);
				}
				
			}
		});
		secondPanel.add(dashed);
		
		components.add(firstPanel);
		components.add(secondPanel);
		
		this.add(components, BorderLayout.NORTH);
		this.add(mouseLocation, BorderLayout.SOUTH);
		
		
		setVisible(true);
		firstPanel.setVisible(true);
	}
		
		
	public static void main(String[] args) {
		Drawing drawingApp= new Drawing();
	}
}