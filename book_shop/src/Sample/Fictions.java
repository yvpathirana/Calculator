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

public class Fictions implements Initializable {

    @FXML
    private GridPane gridFiction;

    @FXML
    private ScrollPane scrollFiction;

    private List<Book> FictionBooks = new ArrayList<>();


    private List<Book> getData(){
        List<Book> FictionBooks = new ArrayList<>();
        Book book;
        DatabaseConnection con=new DatabaseConnection();
		Connection conn =con.getConnection();
        try { String conqury="SELECT * from book_data where category='"+"Fictions"+"'";
		Statement stat=conn.createStatement();
		ResultSet quout=stat.executeQuery(conqury);
		
		while(quout.next()) {
			book = new Book();
	        book.setName(quout.getString("book_name"));
	        book.setPrice(quout.getDouble("book_price"));
	        book.setImgSrc(quout.getString("book_img_src"));

	        
	        FictionBooks.add(book);

				 
			}
		 
		 
		}
       catch(Exception e) {
		System.out.println(e);
	}

//        

        return FictionBooks;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FictionBooks.addAll(getData());
        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < FictionBooks.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                book_item_controller book_item_controller = fxmlLoader.getController();
                book_item_controller.setData(FictionBooks.get(i));

                if (column == 6) {
                    column = 0;
                    row++;
                }
                gridFiction.add(anchorPane, column++, row);

                gridFiction.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridFiction.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridFiction.setMaxWidth(Region.USE_PREF_SIZE);

                gridFiction.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridFiction.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridFiction.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
