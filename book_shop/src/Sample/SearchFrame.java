package Sample;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class SearchFrame implements Initializable {
	@FXML
    private GridPane gridSearch;

    @FXML
    private ScrollPane scrollSearch;

    private List<Book> searchBooks = new ArrayList<>();


    private List<Book> getData()  {
        List<Book> searchBooks = new ArrayList<>();
        Book book;
        DatabaseConnection con=new DatabaseConnection();
		Connection conn =con.getConnection();
        try { String conqury="SELECT * from book_data where book_name LIKE '%"+shop.getSerachKey()+"%'"
        		+ " or authour LIKE '%"+shop.getSerachKey()+"%'";
		Statement stat=conn.createStatement();
		ResultSet quout=stat.executeQuery(conqury);
		
		while(quout.next()) {
			book = new Book();
	        book.setName(quout.getString("book_name"));
	        book.setPrice(quout.getDouble("book_price"));
	        book.setImgSrc(quout.getString("book_img_src"));

	        
	        searchBooks.add(book);

				 
			}
		 
		 
		}
       catch(Exception e) {
		System.out.println(e);
	}

        return searchBooks;
//
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBooks.addAll(getData());
        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < searchBooks.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                book_item_controller book_item_controller = fxmlLoader.getController();
                book_item_controller.setData(searchBooks.get(i));

            if (column == 6) {
                column = 0;
                row++;
            }
            gridSearch.add(anchorPane, column++, row);

            gridSearch.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridSearch.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridSearch.setMaxWidth(Region.USE_PREF_SIZE);

            gridSearch.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridSearch.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridSearch.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(10));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
