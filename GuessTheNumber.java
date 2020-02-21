import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import static java.lang.Math.abs;

public class GuessTheNumber extends JFrame{
	
	private JTextField input;
	private JLabel response;
	private JButton button;
	private JLabel label1;
	private JLabel label2;
	
	private int guess;
	private int targetNumber;
	private int previousGuess;
	
	public int newNumber(){
		Random rand = new Random();
		return rand.nextInt(1000) + 1;
	}
	
	public GuessTheNumber(){
		
		super("Guess The Number Game");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500,500));
		
		guess=1001;
		targetNumber=newNumber();
		System.out.println(targetNumber);
		label1 = new JLabel("I have a number between 1 and 1000. Can you guess my number?");
		label2 = new JLabel("Please enter your first guess.");
		button = new JButton("Play Again");
		response = new JLabel();
		input = new JTextField(4);
		
		add(label1);
		add(label2);
		add(input);
		add(response);
		add(button);
		
		input.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					previousGuess = guess;
					guess = Integer.parseInt(input.getText());
					if (abs(guess - targetNumber) > abs(previousGuess - targetNumber)){
						// Getting Colder
						getContentPane().setBackground(Color.BLUE);
					}
					else{
						// Getting Warmer
						getContentPane().setBackground(Color.RED);
					}
					if (guess < targetNumber){
						response.setText("Too Low");
					} 
					else if (guess > targetNumber){
						response.setText("Too High");
					}
					
					if (guess == targetNumber){
						response.setText("Correct!");
						getContentPane().setBackground(Color.GREEN);
						input.setEditable(false);
					}
				}
		});
		
		button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event){
					input.setText(null);
					response.setText(null);
					targetNumber = newNumber();
					System.out.println(targetNumber);
					guess = 1001;
					getContentPane().setBackground(Color.LIGHT_GRAY);
					input.setEditable(true);
				}
		});
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		GuessTheNumber game = new GuessTheNumber();
	}
}