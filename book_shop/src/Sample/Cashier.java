package Sample;

import static java.lang.String.valueOf;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Cashier implements Initializable {

    @FXML
    private TableView<CartBook> cartTable;

    @FXML
    private TableColumn<CartBook, Integer> ItemNo;

    @FXML
    private TableColumn<CartBook, String> bookTitle;

    @FXML
    private TableColumn<CartBook, Integer> bookQuantity;

    @FXML
    private TableColumn<CartBook, String> bookPrice;

    @FXML
    private TableColumn<CartBook, String> bookPriceTotal;


    @FXML
    private TextField orderID;
    
    @FXML Label txtPt,txtQt;


    @FXML
    private Button backToStore;

    @FXML
    private TextField CusName,CusTeleNumber,TotalCheck,QuantityCheck,purchaseDate,purchaseTime,status;


    @FXML
    private Button purchase;

    @FXML
    private Button change;
    int sum_quantity = 0;
    Double sum_total = 0.00;


//    ObservableList<CartBook> list = FXCollections.observableArrayList(
//           new CartBook("kamal",6,"LKS 200.0","LKS 1200.0")
//   );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ItemNo.setCellValueFactory(new PropertyValueFactory<CartBook, Integer>("itemNo"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<CartBook, String>("bokTitle"));
        bookQuantity.setCellValueFactory(new PropertyValueFactory<CartBook, Integer>("bokQuantity"));
        bookPrice.setCellValueFactory(new PropertyValueFactory<CartBook, String>("bokPrice"));
        bookPriceTotal.setCellValueFactory(new PropertyValueFactory<CartBook, String>("TotalPrice"));

      
    }

    

  
    
    public void confirm(ActionEvent actionEvent) {
    	DatabaseConnection con=new DatabaseConnection();
    	Connection ca =con.getConnection();
    	try {
    		String cName = CusName.getText();
    		int cNum = Integer.parseInt(CusTeleNumber.getText());
    		String query="UPDATE cart_customer "
    				+ "SET  customer_name = ?, customer_tele=?,total_price=?,total_quntity=?,states=?"+
    						"WHERE cart_id=\""+(orderID.getText()+"\"");
    				  PreparedStatement ps1 = ca.prepareStatement(query);
	    	
	       
	        ps1.setString(1,cName);
	        ps1.setInt(2, cNum);
	        ps1.setDouble(3, sum_total);
	        ps1.setDouble(4, sum_quantity);
	        ps1.setString(5,"ORDER IS COMPLETED");
//        ps1.setString(6,purchaseDate.getText());
//        ps1.setString(7,purchaseTime.getText());
        ps1.executeUpdate();
        ca.close();
        status.setText("ORDER IS COMPLETED");
        
    	
    	}
    	catch(NumberFormatException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("INPUT VALID PHONE NUMBER");
            alert.showAndWait();
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ORDER IS COMPLETED");
        alert.setHeaderText(" ORDER ID===**"+orderID.getText()+"**   COMPLETED");
        alert.setContentText("");
        alert.showAndWait();
        }

    

    public void change(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("change.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    public void refresh()  {
      

      try {
      	cartTable.getItems().clear();
      	DatabaseConnection con=new DatabaseConnection();
  		Connection ca =con.getConnection();
          Statement statement = ca.createStatement();

          String get_data = "select * from addCartInfo where cart_id="+orderID.getText()+";";
          ResultSet re1 = statement.executeQuery(get_data);
          //Double full_total = 0.00;
          counter();
          while (re1.next()) {
              int b_no = re1.getInt(1);
              String b_name = re1.getString(2);
              int b_quantity = re1.getInt(3);
              Double b_price = Double.parseDouble(re1.getString(4));
              Double b_total = Double.parseDouble(re1.getString(5));
//              full_total = full_total + b_total;
//              System.out.println(full_total);


              CartBook cartBook = new CartBook(b_no, b_name, b_quantity, b_price, b_total);
              ObservableList<CartBook> cartBooks = cartTable.getItems();
              cartBooks.add(cartBook);
              cartTable.setItems(cartBooks);


          }
          ca.close();
      } catch (SQLException sqlException) {
          sqlException.printStackTrace();
      }


   }
    	public void counter() {
			
		
		    DatabaseConnection con=new DatabaseConnection();
			Connection conn =con.getConnection();
			
			try {
			String conqury=" SELECT SUM(book_tprice),SUM(book_quntity) "
					+ "FROM project_data.addcartinfo WHERE cart_id="+orderID.getText()+";";
			Statement stat=conn.createStatement();
			ResultSet quout=stat.executeQuery(conqury);
			while(quout.next()) {
				sum_quantity=quout.getInt(2);
				sum_total=quout.getDouble(1);
			}
			  conn.close();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		   
		    txtQt.setText(valueOf(sum_quantity));
		    txtPt.setText(valueOf(sum_total));
		    TotalCheck.setText(valueOf(sum_total));
		    QuantityCheck.setText(valueOf(sum_quantity));
		}
    	public void search() {
    		 try { 
    			 clearAll();
    			 cartTable.getItems().clear();
    	        	DatabaseConnection con=new DatabaseConnection();
    	    		Connection ca =con.getConnection();
    	            Statement statement = ca.createStatement();
    	            Statement stat = ca.createStatement();

    	            String get_books_data = "select * from addCartInfo where cart_id="+orderID.getText()+";";
    	            String get_cus_data = "select * from cart_customer where cart_id="+orderID.getText()+";";
    	            ResultSet re1 = statement.executeQuery(get_books_data);
    	            ResultSet reg2 = stat.executeQuery(get_cus_data);
    	            counter();
    	            while (re1.next()) {
    	                int b_no = re1.getInt(1);
    	                String b_name = re1.getString(2);
    	                int b_quantity = re1.getInt(3);
    	                Double b_price = Double.parseDouble(re1.getString(4));
    	                Double b_total = Double.parseDouble(re1.getString(5));
//    	                full_total = full_total + b_total;
//    	                System.out.println(full_total);


    	                CartBook cartBook = new CartBook(b_no, b_name, b_quantity, b_price, b_total);
    	                ObservableList<CartBook> cartBooks = cartTable.getItems();
    	                cartBooks.add(cartBook);
    	                cartTable.setItems(cartBooks);


    	            }
    	            
    	            while (reg2.next()) {
    	            	CusName.setText(reg2.getString("customer_name"));
    	            	CusTeleNumber.setText(reg2.getString("customer_tele"));
    	            	purchaseDate.setText(reg2.getString("purchase_date"));
    	            	purchaseTime.setText(reg2.getString("purchese_time"));
    	            	status.setText(reg2.getString("states"));
    	            }
    	            
    	            ca.close();
    	        } catch (SQLException sqlException) {
    	            sqlException.printStackTrace();
    	        }
    	}
    	public void clearAll() {
            CusName.setText("");
            CusTeleNumber.setText("");
            status.setText("");            
            purchaseDate.setText("");
            purchaseTime.setText("");
            TotalCheck.setText("");
            QuantityCheck.setText("");

        }
    }

