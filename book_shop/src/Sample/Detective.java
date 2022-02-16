package Sample;

import Model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Detective implements Initializable {

    @FXML
    private GridPane gridDetective;

    @FXML
    private ScrollPane scrollDetective;

    private List<Book> DBooks = new ArrayList<>();


    private List<Book> getData(){
        List<Book> DBooks = new ArrayList<>();
        Book book;
        DatabaseConnection con=new DatabaseConnection();
		Connection conn =con.getConnection();
        try { String conqury="SELECT * from book_data where category='"+"Detective"+"'";
		Statement stat=conn.createStatement();
		ResultSet quout=stat.executeQuery(conqury);
		
		while(quout.next()) {
			book = new Book();
	        book.setName(quout.getString("book_name"));
	        book.setPrice(quout.getDouble("book_price"));
	        book.setImgSrc(quout.getString("book_img_src"));

	        
	        DBooks.add(book);
		}
        }
        catch(Exception e) {
 		System.out.println(e);
 	}


        return DBooks;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBooks.addAll(getData());
        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < DBooks.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                book_item_controller book_item_controller = fxmlLoader.getController();
                book_item_controller.setData(DBooks.get(i));

                if (column == 6) {
                    column = 0;
                    row++;
                }
                gridDetective.add(anchorPane, column++, row);

                gridDetective.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridDetective.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridDetective.setMaxWidth(Region.USE_PREF_SIZE);

                gridDetective.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridDetective.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridDetective.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
