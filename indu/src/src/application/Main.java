package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private static Stage summaryStage = null;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml")); // instead of borderpane root add  parent root
			primaryStage.setTitle("CalculatorApp by Yasiru Pathirane");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
			createSummaryStage();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createSummaryStage() {
		summaryStage = new Stage();
		summaryStage.setTitle("Calculation Summary");
		summaryStage.setAlwaysOnTop(true);
		summaryStage.setResizable(false);
		summaryStage.initModality(Modality.APPLICATION_MODAL);
		 
		
	}
	
	public static Stage getSummaryStage() {
		return summaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
