package Sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class bookDB implements Initializable {

    @FXML
    private AnchorPane bookDB;

    @FXML
    private TextField bookName,imagePath,bookPrice,bookAuthor,bookStock;

    @FXML
    private ComboBox<String> bookType;
 
    @FXML
    private ImageView bookImage;

    @FXML
    private Button browseButton;

    @FXML
    private Label display;

    String selectedType="";
    String bookCheaker="";
    int count1=0;

    private final String[] bType ={"Novels","Short stories","Fictions","Detective","Child stories","Biography"};
    private final ObservableList<String> bList = FXCollections.observableArrayList(bType);

    @FXML
    void bookType(ActionEvent event) {
        selectedType = bookType.getSelectionModel().getSelectedItem().toString();

    }

    @FXML
    void browse(ActionEvent event)  {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            imagePath.setText(selectedFile.getAbsolutePath());
            bookImage.setImage(new Image(selectedFile.toURI().toString()));
        }else{
           
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookType.setItems(bList);

    }
	public void save() {
		String bName = bookName.getText();
		String bAuthour=bookAuthor.getText();
		String bCate=bookType.getValue();
        String dbImg = imagePath.getText();
        String bPrice = bookPrice.getText();
        String bStock=bookStock.getText();
        
        DatabaseConnection con=new DatabaseConnection();
		Connection conn =con.getConnection();
		
       //cheaking the new book name in the sql data base//
        String conqury="SELECT * from book_data where book_name='"+bookName.getText()+"'";
    	try{
    		Statement stat=conn.createStatement();
    	ResultSet quout=stat.executeQuery(conqury);
    	while(quout.next()) {
    		
    		 bookCheaker = quout.getString("book_name");}
    	}
    	catch(Exception e) {
			System.out.println(e);
		}
        
        
		if(bName.isEmpty()|| bPrice.isEmpty()||dbImg.isEmpty()|| selectedType.isEmpty()||bStock.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required Data");
            alert.showAndWait();}
		else if(!bookCheaker.isEmpty()) { //if the new book in the in the sql raise alert// 
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Book already exist");
            alert.showAndWait();
            bookCheaker="";
		}
		
		else{
		
		
	      String query = " insert into book_data (book_id,book_name,"
	      		+ " authour, category, book_img_src,book_price,book_stock)"
	        + " values (?, ?, ?, ?, ?,?,?)";

	     
	     
		try { // create the mysql insert
			 PreparedStatement preparedStmt = conn.prepareStatement(query);
			 Statement statement = conn.createStatement();
			 String row_count1 = "SELECT * FROM book_data WHERE book_id=(SELECT max(book_id) FROM book_data);";
		     ResultSet rs1 = statement.executeQuery(row_count1);
		     while(rs1.next()) {
		    	 count1 = rs1.getInt("book_id");
		     }
		     int auto_id = count1 + 1;
			
			
		      preparedStmt.setInt(1,auto_id); 
		      preparedStmt.setString (2, bName);
		      preparedStmt.setString (3,bAuthour );
		      preparedStmt.setString (4, bCate);
		      preparedStmt.setString (5, dbImg);
		      preparedStmt.setDouble(6, Double.parseDouble(bPrice));
		      preparedStmt.setInt(7, Integer.parseInt(bStock));

		      // execute the preparedstatement
		      preparedStmt.execute();
		     
		      
		      display.setText("Saved Successfully");
			  conn.close();
			  clear();
		}
		catch(NumberFormatException e) {
			   display.setText("ENTER Valied INPUTS");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		}
		
	
		
	}

	
	
public void search() {
	DatabaseConnection con=new DatabaseConnection();
	Connection conn =con.getConnection();
	
	try { // mysql search query
		String conqury="SELECT * from book_data where book_name='"+bookName.getText()+"'";
	Statement stat=conn.createStatement();
	ResultSet quout=stat.executeQuery(conqury);
	while(quout.next()) {
		
		bookAuthor.setText(quout.getString("authour"));
		bookType.setValue(quout.getString("category"));
		bookPrice.setText(quout.getString("book_price"));
		bookStock.setText(quout.getString("book_stock"));
		imagePath.setText(quout.getString("book_img_src"));
		InputStream stream = new FileInputStream(quout.getString("book_img_src"));
        Image image = new Image(stream);
        bookImage.setImage(image);
	}
	  conn.close();
	  display.setText("Searched Successfully");
	}

	catch(Exception e) {
		System.out.println(e);
	}
}
public void update() {
	
	DatabaseConnection con=new DatabaseConnection();
	Connection conn =con.getConnection();
	
	try { //update Query
		String query="UPDATE book_data "
	+ "SET  authour = ?, category=?,book_price=?,book_stock=?,book_img_src=? "+
			"WHERE book_name=\""+(bookName.getText()+"\"");
	  PreparedStatement preparedStmt = conn.prepareStatement(query);
    
      preparedStmt.setString (1, bookAuthor.getText());
      preparedStmt.setString(2, bookType.getValue());
      preparedStmt.setDouble(3, Double.parseDouble(bookPrice.getText()));
      preparedStmt.setInt(4, Integer.parseInt(bookStock.getText()));
      preparedStmt.setString (5, imagePath.getText());
      // execute the preparedstatement
      preparedStmt.execute();
	  conn.close();
	  display.setText("Updated Successfully");
	}
	catch(NumberFormatException e) {
		   display.setText("ENTER Valied INPUTS");
	}
	catch(Exception e) {
		System.out.println(e);
	}
}
public void delete() {
	
	DatabaseConnection con=new DatabaseConnection();
	Connection conn =con.getConnection();
	try {//delete query
		String query="DELETE from book_data where book_name=\""+(bookName.getText()+"\"");
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	preparedStmt.executeUpdate(); 
	conn.close();
	clear();
	display.setText("Deleted Successfully");	
	}
	catch(Exception e) {
		System.out.println(e);
	}
}
public void clear() {
	bookAuthor.setText("");
	bookType.setValue("");
	bookPrice.setText("");
	bookStock.setText("");
	imagePath.setText("");
	bookName.setText("");
}
public void toShop(ActionEvent event)throws IOException {
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
    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}
}


