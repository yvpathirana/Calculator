package Sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class adminShop implements Initializable {

    @FXML
    private Button dashboard;

    @FXML
    private Button cashierLine;

    @FXML
    private Button customerInfo;

    @FXML
    private Button salesInfo;

    @FXML
    private Button updateItems;

    @FXML
    private Button aboutUs;

    @FXML
    private HBox ContentArea;

    @FXML
    private Label mainTopic;

    @FXML
    void aboutUs(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("aboutUs.fxml"));
//        ContentArea.getChildren().removeAll();
//        ContentArea.getChildren().setAll(root);
    }

    @FXML
    void cashierLine(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("cashier.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }

    @FXML
    void customerInfo(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("customerAdmin.fxml"));
//        ContentArea.getChildren().removeAll();
//        ContentArea.getChildren().setAll(root);
    }

    @FXML
    void dashboard(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
//        ContentArea.getChildren().removeAll();
//        ContentArea.getChildren().setAll(root);
    }

    @FXML
    void salesInfo(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("salesAdmin.fxml"));
//        ContentArea.getChildren().removeAll();
//        ContentArea.getChildren().setAll(root);
    }

    @FXML
    void updateItems(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("bookDB.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("cashier.fxml"));
            ContentArea.getChildren().removeAll();
            ContentArea.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void backToStore(ActionEvent actionEvent) throws IOException {
    	DatabaseConnection con=new DatabaseConnection();
    	Connection conn =con.getConnection();
    	
    	try {
    	String conqury=" SELECT * FROM addCartInfo WHERE cart_id=(SELECT max(cart_id) FROM addCartInfo);;";
    	Statement stat=conn.createStatement();
    	ResultSet quout=stat.executeQuery(conqury);
    	while(quout.next()) {
    		top.cart_id=quout.getInt("cart_id");
    		
    	}
    	  conn.close();
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	top.cart_id=top.cart_id+1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shop.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}

