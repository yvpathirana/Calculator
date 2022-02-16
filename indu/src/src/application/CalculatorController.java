package application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CalculatorController {

	@FXML
	private Label expression;
	
	@FXML
	private Label result;
	
	private ArrayList<String> calculation_summary = new ArrayList<>(); 
	
	public void insertNumber(String number) {
		expression.setText(expression.getText()+number);
	}
	
	public void insertOperator(String operator) {
		expression.setText(" "+expression.getText()+" "+operator+" ");
	}
	
	public void insertAnswer(String answer) {
		expression.setText(expression.getText() + answer);
	}
	
	public void clearExpression() {
		expression.setText("");
		result.setText("");
	}
	
	public void deleteLastChar() {
		if(!getExpression().getText().isEmpty()) {
			StringBuilder text = new StringBuilder(getExpression().getText());
			text.deleteCharAt(text.length()-1);
			getExpression().setText(text.toString());
		}
	}
	
	public Label getExpression() {
		return expression;
	}
	
	public void setResult(String newResult) {
		this.result.setText("= " + newResult);
	}
	
	public Label getResult(){
		return result;
	}
	
	public void openSummaryTab() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/SummaryTab.fxml"));
			Parent root = loader.load();
			
			Main.getSummaryStage().setScene(new Scene(root));
			
			SummaryController summaryController = loader.getController();
			summaryController.initializeCalculations(calculation_summary);
			
			Main.getSummaryStage().show();
		}catch (IOException e) {
			 //System.out.println(e);
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
		    e.printStackTrace(new PrintStream(out));
		    String errorMessage = new String(out.toByteArray());
		    
			this.result.setText(errorMessage);
		}
	}
	
	public void addCalculation(String expression, String result) {
		this.calculation_summary.add(expression + " = " + result);
	}
	
	public void onMouseClick(MouseEvent mouseEvent) {
		Button button = (Button)mouseEvent.getSource();
		String buttonText = button.getText();
		
		switch(buttonText) {
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
		case "0":
			insertNumber(buttonText);
			break;
			
		case "/":
		case "×":
		case "+":
		case "-":
			insertOperator(buttonText);
			break;
		
		case "CLEAR":
			clearExpression();
			
		case "=":
			int result = EvaluateString.evaluate(this.getExpression().getText());
			addCalculation(this.getExpression().getText(), String.valueOf(result));		//to call the addcalculation method to save summary of calculations
			setResult(String.valueOf(result));
			break;
			 
		case "ANS":
			insertAnswer(getResult().getText().substring(2)); 	//to begin after the = sign and space=> substring(2)
			break;
			
		case "DELETE":
			deleteLastChar();
			break;
			
		case "HIST":
			openSummaryTab();
		}
	}
}
