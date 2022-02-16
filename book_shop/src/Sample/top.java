package Sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class top {
	
	public static  int  cart_id;
    public ImageView cover;
    public ImageView admin;
    @FXML
    private Button SwitchToMain;

    @FXML
    void admin(MouseEvent event) throws IOException {
        Stage stage = (Stage)SwitchToMain.getScene().getWindow();
        stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    
    }

    @FXML
    public void SwitchToMain (ActionEvent event) throws IOException {
    	DatabaseConnection con=new DatabaseConnection();
    	Connection conn =con.getConnection();
    	
    	try {
    	String conqury=" SELECT * FROM addCartInfo WHERE cart_id=(SELECT max(cart_id) FROM addCartInfo);;";
    	Statement stat=conn.createStatement();
    	ResultSet quout=stat.executeQuery(conqury);
    	while(quout.next()) {
    		cart_id=quout.getInt("cart_id");
    		
    	}
    	  conn.close();
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	cart_id=cart_id+1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shop.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1281,654);
        stage.setScene(scene);
        stage.show();
    }
    public static  int cartIDR() {
    	return cart_id;
    }
    

}
