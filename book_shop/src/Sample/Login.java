package Sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class Login {

    @FXML
    private TextField adminName;

    @FXML
    private PasswordField adminPwd;

    @FXML
    private Label errorTxt;

    @FXML
    private Button backBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button newBtn;

    public void validateLogin(){
        String name = adminName.getText();
        String passward = adminPwd.getText();

        DatabaseConnection dataB = new DatabaseConnection();
        Connection con = dataB.getConnection();
        String login_data = "select * from admin where username='"+name+"' AND passward='"+passward+"';";
        try{
            Statement stat=con.createStatement();
            ResultSet re1=stat.executeQuery(login_data);

            if(re1.next()){
                Stage stage = (Stage)loginBtn.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("adminShop.fxml"));
                stage1.setTitle("O S A K A");
                stage1.setScene(new Scene(root,1281,585));
                stage1.show();

            }else {
                errorTxt.setText("Wrong username or password!");
            }

        }catch (Exception NullPointerException){
            NullPointerException.printStackTrace();
        }
    }

    String defKey;
    String key;
    public void newBtn(ActionEvent actionEvent) throws IOException {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("New admin login");
        dialog.setHeaderText("Connecting new admin");
        dialog.setContentText("Please enter the key:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
             defKey = result.get();
        }
        DatabaseConnection dataB = new DatabaseConnection();
        Connection con = dataB.getConnection();
        String login_data = "select * from admin ;";

        try {
            Statement stat = con.createStatement();
            ResultSet re1 = stat.executeQuery(login_data);
            re1.next();
             key = re1.getString(3);

        } catch (Exception NullPointerException) {
            NullPointerException.printStackTrace();
        }
        if (defKey.equals(key)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }else if (defKey.isBlank()) {
            errorTxt.setText("Enter required key!");
        }else {
                errorTxt.setText("NOT valid");
            }
        }



//        Stage stage = (Stage) newBtn.getScene().getWindow();
//        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("DefaltPwCheck.fxml"));
//        stage.setScene(new Scene(fxmlLoader.load()));


    public void loginBtn(ActionEvent actionEvent) {
        if(!adminName.getText().isBlank() && !adminPwd.getText().isBlank()){
            validateLogin();
        }else {
            errorTxt.setText("please enter both name and passward");
        }
    }

    public void backBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)backBtn.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("top.fxml"));
        stage1.setTitle("O S A K A");
        stage1.setScene(new Scene(root,1100,600));
        stage1.show();
    }
}