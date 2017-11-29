import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DivisionByZeroException extends Exception{
	public DivisionByZeroException()
	{
		super("Division by Zero!");
	}
}

class NegativeSqrtException extends Exception{
	public NegativeSqrtException()
	{
		super("Negative Sqrt");
	}
}

public class Calculator extends JFrame implements ActionListener{
	JPanel[] row = new JPanel[6]; //How many rows
	JButton[] button = new JButton[29]; //How many buttons
	String[] theButtons = {"7", "8", "9", "+", "+/-", "n!", "1/x", "log",//The buttons
						   "4", "5", "6", "-", "√", "%", "sin", "ln",
						   "1", "2", "3", "*", "C", "cos", "^",
						   "0", ".", "/", "=", "tan", "x^2"};
	Dimension displayDim = new Dimension(100, 20);
	Dimension smallButton = new Dimension(54, 40);
	Dimension longButton = new Dimension(109, 40);
	
	double[] temp = {0, 0};
	String op = "";
	
	JTextArea display = new JTextArea(1,25);
	JTextArea computation = new JTextArea(2,38);
	JScrollPane scroll = new JScrollPane(computation);
		
	Font displayFont = new Font("Helvetica", Font.BOLD, 20);
	Font buttonFont = new Font("Helvetica", Font.PLAIN, 14);
	
	public Calculator(){
		super("Calculator");
		setDesign();
		setSize(450, 300); //window size
		setResizable(false); //makes it so window can't be resized
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(6,8); //6 rows 8 columns
		setLayout(grid); //sets window grid format
		
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER, 1, 1);
		
		for(int i = 0; i < 6; i++)
			row[i] = new JPanel();
		
		row[0].setLayout(f1);
		row[1].setLayout(f1);
		
		for(int i = 2; i < 6; i++)
			row[i].setLayout(f2);
		
		for(int i = 0; i < 29; i++){
			button[i] = new JButton();
			button[i].setText(theButtons[i]);
			button[i].setFont(buttonFont);
			button[i].addActionListener(this);
		}
		
		display.setFont(displayFont);
		display.setEditable(false); //input not allowed by keyboard
		display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); //input from left to right
		
		computation.setFont(buttonFont);
		computation.setEditable(false);
		computation.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		//Setting dimensions
		display.setPreferredSize(displayDim);
		
		for(int i = 0; i < 29; i++)
			button[i].setPreferredSize(smallButton);
		button[20].setPreferredSize(longButton);
		button[23].setPreferredSize(longButton);
		button[26].setPreferredSize(longButton);
		
		
		row[0].add(display); //adds display to row 1
		add(row[0]);
		
		row[1].add(scroll); //makes computation display scroll
		add(row[1]);
				
		for(int i = 0; i < 8; i++)
			row[2].add(button[i]);
		add(row[2]);
		
		for(int i = 8; i < 16; i++)
			row[3].add(button[i]);
		add(row[3]);
		
		for(int i = 16; i < 23; i++)
			row[4].add(button[i]);
		add(row[4]);
		
		for(int i = 23; i < 29; i++)
			row[5].add(button[i]);
		add(row[5]);
		
		setVisible(true);
		display.append("0");
		
	}
	
	public final void setDesign(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		if(display.getText().equals("0") && computation.getText().equals(""))
			display.setText("");
		if(display.getText().equals("Can't divide by Zero") ||
		   display.getText().equals("Negative Number"))
			display.setText("");
		if(ae.getSource() == button[0])
	        display.append("7");
	    if(ae.getSource() == button[1])
	        display.append("8");
	    if(ae.getSource() == button[2])
	        display.append("9");
	    if(ae.getSource() == button[3])
	        OperatorPressed("+");
	    if(ae.getSource() == button[4])
	        getPosNeg();
	    if(ae.getSource() == button[5])
	    	getfact();
	    if(ae.getSource() == button[6])
	    	getReciprocal();
	    if(ae.getSource() == button[7])
	    	getLog();
	    if(ae.getSource() == button[8])
	        display.append("4");
	    if(ae.getSource() == button[9])
	        display.append("5");
	    if(ae.getSource() == button[10])
	        display.append("6");
	    if(ae.getSource() == button[11])
	        OperatorPressed("-");
	    if(ae.getSource() == button[12])
	    	getSqrt();
	    if(ae.getSource() == button[13])
	    	OperatorPressed("%");
	    if(ae.getSource() == button[14])
	    	getSin();
	    if(ae.getSource() == button[15])
	    	getLn();
	    if(ae.getSource() == button[16])
	        display.append("1");
	    if(ae.getSource() == button[17])
	        display.append("2");
	    if(ae.getSource() == button[18])
	        display.append("3");
	    if(ae.getSource() == button[19])
	        OperatorPressed("*");
	    if(ae.getSource() == button[20])
	        clear();
	    if(ae.getSource() == button[21])
	    	getCos();
	    if(ae.getSource() == button[22])
	    	OperatorPressed("^");
	    if(ae.getSource() == button[23])
	        display.append("0");
	    if(ae.getSource() == button[24]){
	    	if(display.getText().contains(".")){
	    		System.out.println("Can't Have more than one decimal point");
	    	}
	    	else
	    		display.append(".");
	    }
	    if(ae.getSource() == button[25])
	        OperatorPressed("/");
	    if(ae.getSource() == button[26])
	        getEquals();
	    if(ae.getSource() == button[27])
	    	getTan();
	    if(ae.getSource() == button[28])
		    getSquare();
	}
	
	
	public void clear(){
		try{
			display.setText("0");
			computation.setText("");
			for(int i = 0; i < 2; i++)
				temp[i] = 0;
		} 
		catch(NullPointerException e){ 
			System.out.println(e.getMessage());
		}
	}
	
	public void OperatorPressed(String operator){
		try{
    		//Since I use scrolling text for the computation,
    		//I have to include an or statement checking if the computation
    		//contains an = sign in order to do another calculation with that result.
    		if (temp[0] == 0 && computation.getText().equals("") || computation.getText().contains("="))  
    			temp[0] = Double.parseDouble(display.getText());
    		else{
    			temp[1] = Double.parseDouble(display.getText());
    			temp[0] = calcTemp0(op, temp[0], temp[1]);
    		}
    		
    		switch(operator){
    		case "+":
    			op = "+";
    			break;
    		case "-":
    			op = "-";
    			break;
    		case "*":
    			op = "*";
    			break;
    		case "/":
    			op = "/";
    			break;
    		case "%":
    			op = "%";
    			break;
    		case "^":
    			op = "^";
    			break;
    		default:
    			break;
    		}
    		computation.append(display.getText() + " " + op + " ");
    		display.setText("");
    	}
    	//Prevent user from entering two operators in a row
    	catch (NumberFormatException e){
    		System.out.println("Can't Enter Two Operators");
    	}
    	catch(DivisionByZeroException e){
	    	clear();
	    	display.setText("Can't divide by Zero");
	    }
	}
	
	public void getSqrt(){
		try{
			if (Double.parseDouble(display.getText()) < 0)
				throw new NegativeSqrtException();
			double sqrt = Math.sqrt(Double.parseDouble(display.getText()));
			display.setText(Double.toString(sqrt));
		}
		catch(NegativeSqrtException e){
			clear();
			display.setText("Negative Number");
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getReciprocal(){
		try{
			if (Double.parseDouble(display.getText()) == 0)
				display.setText(Double.toString(0));
			else{
				double rec = 1.0 / Double.parseDouble(display.getText());
				display.setText(Double.toString(rec));
			}
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getSin(){
		try{
			display.setText(Double.toString(Math.sin(Double.parseDouble(display.getText()))));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getCos(){
		try{
			display.setText(Double.toString(Math.cos(Double.parseDouble(display.getText()))));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getTan(){
		try{
			display.setText(Double.toString(Math.tan(Double.parseDouble(display.getText()))));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getLog(){
		try{
			display.setText(Double.toString(Math.log10(Double.parseDouble(display.getText()))));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getLn(){
		try{
			display.setText(Double.toString(Math.log(Double.parseDouble(display.getText()))));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getSquare(){
		try{
			double d = Double.parseDouble(display.getText());
			double sqr = d * d;
			display.setText(Double.toString(sqr));
		}
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getfact(){
		if (display.getText().contains("."))
			System.out.println("Not an Integer\nNot Calculating Factorial");
		else{
			try{
				double n = Double.parseDouble(display.getText());
				int fact = 1;
		        for (int i = 1; i <= n; i++) {
		            fact *= i;
		        }
				display.setText(Double.toString(fact));
			}
			catch(NumberFormatException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	//Switch number between negative and positive
	public void getPosNeg(){
		try{
			double num = Double.parseDouble(display.getText());
			if(num != 0) {
				num = num * (-1);
				display.setText(Double.toString(num));
			}
		} 
		catch(NumberFormatException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getEquals(){
		computation.append(display.getText());
	    double result = 0;
	    //Prevents user from entering an operator then pressing equals
	    try{
	    	temp[1] = Double.parseDouble(display.getText());
	    	
	    	System.out.println(temp[0]);
	    	System.out.println(temp[1]);
	    	System.out.println(op);
	    	
	    	result = calcTemp0(op, temp[0], temp[1]);
	    	display.setText(result + "");
	    	computation.append(" = " + result + "\n");
	    	temp[1] = 0;
	    	temp[0] = 0;
	    	op = "";
	    }
	    catch(DivisionByZeroException e){
	    	clear();
	    	display.setText("Can't divide by Zero");
	    }
	    catch(NumberFormatException e){
	    	System.out.println("Can't Calculate, Missing Second Number");
	    }
	    
	}
	
	//Completes the operation of the past two inputs
	//and Stores them in temp[0] since we only have two variables
	public double calcTemp0(String op, double d1, double d2) throws DivisionByZeroException{
		switch (op){
		case "+":
			return d1 + d2;
		case "-":
			return d1 - d2;
		case "*":
			return d1 * d2;
		case "/":
			if (d2 == 0)
				throw new DivisionByZeroException();
			else
				return d1 / d2;
		case "%":
			return d1 % d2;
		case "^":
			return Math.pow(d1, d2);
		default:
			return 0;
		}
	}
	 

	public static void main(String[] args){
		Calculator C = new Calculator();

	}

}
