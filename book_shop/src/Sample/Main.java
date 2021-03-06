package Sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static final String Currency = "LKS";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("top.fxml"));
        primaryStage.setTitle("O S A K A");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();

      
    }

    public static void main(String[] args) {
        launch(args);
    }
}
