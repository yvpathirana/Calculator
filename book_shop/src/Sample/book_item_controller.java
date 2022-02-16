package Sample;

import Model.Book;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

public class book_item_controller implements Initializable {
    @FXML
    private Label nameLabel, priceLabel, priceType;

    @FXML
    private ImageView bookImg;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Button addToCart;

    int currentValue;
    int count2=0;

    private Book book;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setData(Book book) {
        this.book = book;
        nameLabel.setText(book.getName());
        priceLabel.setText(book.getPrice().toString());
        priceType.setText(Main.Currency);
        try {
        	 InputStream stream = new FileInputStream(book.getImgSrc());
             Image image = new Image(stream);
            bookImg.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);

        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = spinner.getValue();

                //mylabel.setText(Integer.toString(currentValue));
            }
        });
    }


    public void addToCart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cart Confirmation");
        alert.setHeaderText(nameLabel.getText()+" Adding book to cart");
        alert.setContentText("Do you want to add this book to cart? ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            String bTitle = nameLabel.getText();
            Double bPrice = parseDouble(priceLabel.getText());
            int bQuantity = spinner.getValue();
//          Image bImg = bookImg.getImage();

            Double bKTotal = (bPrice) * (double) bQuantity;
            String BPrice = Main.Currency + String.valueOf(bPrice);
            String BKTotal = Main.Currency + String.valueOf(bKTotal);

            try {
            	DatabaseConnection con=new DatabaseConnection();
        		Connection ca =con.getConnection();
                Statement statement = ca.createStatement();

                String row_count2 = "SELECT * FROM addCartInfo WHERE item_id=(SELECT max(item_id) FROM addCartInfo);;";
                ResultSet rs2 = statement.executeQuery(row_count2);
                while(rs2.next()) {
                	 count2 = rs2.getInt("item_id");
                	}
                
                int id2 = count2 + 1;
                PreparedStatement ps1 = ca.prepareStatement("insert into addCartInfo values(?,?,?,?,?,?)");
                ps1.setString(1, String.valueOf(id2));
                ps1.setString(2,bTitle);
                ps1.setInt(3, bQuantity);
                ps1.setDouble(4, bPrice);
                ps1.setDouble(5, bKTotal);
                ps1.setInt(6, top.cartIDR());
                ps1.executeUpdate();
                ca.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
