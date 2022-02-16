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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class shop implements Initializable {

    @FXML
    private HBox ContentArea;

    @FXML
    private Button Novels, ShortStories, Fictions, Education, ChildStories, Biography, cart;

    @FXML
    private TextField txtSearch;
    public static String searchKey="";
    @FXML
    private Button home;

    @FXML
    Label onGoingTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("searchingWindow.fxml"));
            ContentArea.getChildren().removeAll();
            ContentArea.getChildren().setAll(root);

        } catch (IOException e) {
           // Logger.getLogger(Shop_Controller.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
    public void Novels(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Novels.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }
    public void ShortStories(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ShortStories.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }
    public void Fictions(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Fictions.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }
    public void Education(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Detective.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }
    public void Biography(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Biography.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }
    public void ChildStories(javafx.event.ActionEvent actionEvent)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ChildStories.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }

    public void cart(ActionEvent actionEvent) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Cart.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);

    }
    @FXML
    void admin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookDB.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    public void setSerachKey() {
    	searchKey=txtSearch.getText();
    }
    public static String getSerachKey() {
    	return searchKey;
    }
    public void search(ActionEvent actionEvent) throws IOException {
    	setSerachKey();
    	Parent root = FXMLLoader.load(getClass().getResource("searchingWindow.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
    }
    public void home (ActionEvent event) throws IOException {
        Stage stage = (Stage)home.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("top.fxml"));
        stage1.setTitle("O S A K A");
        stage1.setScene(new Scene(root,1100,600));
        stage1.show();


    }
    @FXML
    void admin(MouseEvent event) throws IOException {
    	Stage stage = (Stage)home.getScene().getWindow();
        stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    
    }
}

