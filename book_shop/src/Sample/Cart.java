package Sample;

import javafx.application.Platform;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;
import static java.lang.Double.sum;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public  class Cart implements Initializable {

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
    private TextField cartQuantity,cartTotal;
    
    @FXML Label txtPt,txtQt;


    @FXML
    private Button backToStore;

    @FXML
    private TextField CusName,CusTeleNumber,TotalCheck,QuantityCheck,purchaseDate,purchaseTime;


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

        try {
        	DatabaseConnection con=new DatabaseConnection();
    		Connection ca =con.getConnection();
            Statement statement = ca.createStatement();

            String get_data = "select * from addCartInfo where cart_id="+String.valueOf(top.cart_id)+";";
            ResultSet re1 = statement.executeQuery(get_data);
            //Double full_total = 0.00;
            counter();
            while (re1.next()) {
                int b_no = re1.getInt(1);
                String b_name = re1.getString(2);
                int b_quantity = re1.getInt(3);
                Double b_price = Double.parseDouble(re1.getString(4));
                Double b_total = Double.parseDouble(re1.getString(5));
//                full_total = full_total + b_total;
//                System.out.println(full_total);


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

    public void Date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateToday = sdf.format(new Date());
        purchaseDate.setText(dateToday);
    }

    String timeNow;

    private void timeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeNow = sdf.format(new Date());
            Platform.runLater(() -> {
                purchaseTime.setText(timeNow);
            });

        });
        thread.start();
    }

    public void clearAll() {
        CusName.setText("");
        CusTeleNumber.setText("");
        cartQuantity.setText("");
        cartTotal.setText("");
        purchaseDate.setText("");
        purchaseTime.setText("");
        TotalCheck.setText("");
        QuantityCheck.setText("");

    }


    public void backToStore(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shop.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    
    public void confirm(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bill Confirmation");
        alert.setHeaderText("Confirm the addings");
        alert.setContentText("Do you want to confirm these addings? ");

        if (alert.showAndWait().get() == ButtonType.OK) {
        	QuantityCheck.setText(String.valueOf(sum_quantity));
        	TotalCheck.setText(String.valueOf(sum_total));
        }
        }

    public void purchase(ActionEvent actionEvent) throws IOException {
        
    	if(CusName.getText().isEmpty()|| CusTeleNumber.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required Data");
            alert.showAndWait();}
     
    	else {
        DatabaseConnection con=new DatabaseConnection();
    	Connection ca =con.getConnection();
    	
    	
    	try {
    		String cName = CusName.getText();
    		int cNum = Integer.parseInt(CusTeleNumber.getText());
	    	PreparedStatement ps1 = ca.prepareStatement("insert into cart_customer values(?,?,?,?,?,?,?,?)");
	        ps1.setInt(1, top.cart_id);
	        ps1.setString(2,cName);
	        ps1.setInt(3, cNum);
	        ps1.setDouble(4, sum_total);
        ps1.setDouble(5, sum_quantity);
        ps1.setString(6,purchaseDate.getText());
        ps1.setString(7,purchaseTime.getText());
        ps1.setString(8,"ORDER IS NOT COMPLETED");
        ps1.executeUpdate();
        ca.close();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bill Conformed");
        alert.setHeaderText("YOUR ORDER ID===**"+String.valueOf(top.cart_id)+"**");
        alert.setContentText("Please remember your order id and tell it to the Cashier");
        alert.showAndWait();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("top.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    	
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
    	}
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

          String get_data = "select * from addCartInfo where cart_id="+String.valueOf(top.cart_id)+";";
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
			Date();
		    timeNow();
		
		    DatabaseConnection con=new DatabaseConnection();
			Connection conn =con.getConnection();
			
			try {
			String conqury=" SELECT SUM(book_tprice),SUM(book_quntity) "
					+ "FROM project_data.addcartinfo WHERE cart_id="+top.cart_id+";";
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
		}
    }






