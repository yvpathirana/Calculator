package Sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddAdmin {

    @FXML
    private TextField newName;

    @FXML
    private TextField newUser;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Label stateText;

    @FXML
    private Button backToLogin;

    @FXML
    private Button addToPanel;

    @FXML
    void addToPanel(ActionEvent event) {

        if(!newName.getText().isEmpty() && !newUser.getText().isBlank() && !newPassword.getText().isBlank()){
        	DatabaseConnection dataB = new DatabaseConnection();
            Connection con = dataB.getConnection();
            try {
                String query = "INSERT into admin values (?,?,?);";
                PreparedStatement preparedStatement= con.prepareStatement(query);
                
                preparedStatement.setString(1,newUser.getText());
                preparedStatement.setString(2,newPassword.getText());
                preparedStatement.setString(3,"07986450");
                preparedStatement.execute();
                stateText.setText("Successfully Update!!");

            }catch(Exception NullPointerException) {
            	System.out.println("nn");
                NullPointerException.printStackTrace();
           }
        }else {
            stateText.setText("Fill all required fields ");
        }
    }

    @FXML
    void backToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) backToLogin.getScene().getWindow();
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("Login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));


    }

}

