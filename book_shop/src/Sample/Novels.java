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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Novels implements Initializable {

    @FXML
    private GridPane gridNovel;

    @FXML
    private ScrollPane scrollNovel;

    private List<Book> Books = new ArrayList<>();


    private List<Book> getData()  {
        List<Book> Books = new ArrayList<>();
        Book book;
        DatabaseConnection con=new DatabaseConnection();
		Connection conn =con.getConnection();
        try { String conqury="SELECT * from book_data where category='"+"Novels"+"'";
		Statement stat=conn.createStatement();
		ResultSet quout=stat.executeQuery(conqury);
		
		while(quout.next()) {
			book = new Book();
	        book.setName(quout.getString("book_name"));
	        book.setPrice(quout.getDouble("book_price"));
	        book.setImgSrc(quout.getString("book_img_src"));

	        
	        Books.add(book);
	        

				 
			}

		 
		 
		}
       catch(Exception e) {
		System.out.println(e);
	}

        return Books;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Books.addAll(getData());
        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < Books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                book_item_controller book_item_controller = fxmlLoader.getController();
                book_item_controller.setData(Books.get(i));

            if (column == 6) {
                column = 0;
                row++;
            }
            gridNovel.add(anchorPane, column++, row);

            gridNovel.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridNovel.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridNovel.setMaxWidth(Region.USE_PREF_SIZE);

            gridNovel.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridNovel.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridNovel.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(10));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
