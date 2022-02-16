package Sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class changeOrder {

    @FXML
    private TextField editNo;

    @FXML
    private TextField editQuantity;

    @FXML
    private Button editUpdate;

    @FXML
    private TextField deleteNo;

    @FXML
    private Button deleteDelete;

    @FXML
    void deleteDelete(ActionEvent event) {
        try {
        	DatabaseConnection con=new DatabaseConnection();
        	Connection ca =con.getConnection();
            Statement statement = ca.createStatement();

            String entered_ID_del= deleteNo.getText();
            String det_data = "delete from addCartInfo where item_id = "+ entered_ID_del +";";
            PreparedStatement preparedStmt = ca.prepareStatement(det_data);
        	preparedStmt.executeUpdate(); 
        	ca.close();
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("BOOK REMOVED");
            alert.showAndWait();


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

        @FXML
    void editUpdate(ActionEvent event) {
            try {
            	DatabaseConnection con=new DatabaseConnection();
                Connection ca =con.getConnection();
                Statement statement = ca.createStatement();

                String entered_ID_edit= editNo.getText();
                int entered_quantity = parseInt(editQuantity.getText());

                String get_price = "select * from addCartInfo where item_id = '"+ entered_ID_edit +"';";
                ResultSet re0 = statement.executeQuery(get_price);
                re0.next();

                Double b_price = parseDouble(re0.getString(4));
                Double b_total = (b_price) * (double)entered_quantity;

                String get_data = "update addCartInfo set book_quntity = "+ entered_quantity +", book_tprice ="+ b_total +"  where item_id = '"+ entered_ID_edit +"';";
                statement.executeUpdate(get_data);
                ca.close();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("BOOK UPDATED");
                alert.showAndWait();
               

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
    }
//        
}

