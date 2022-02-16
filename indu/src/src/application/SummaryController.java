package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SummaryController {

	@FXML
	private ListView<String> summaryList;
	
	public void initializeCalculations(ArrayList<String> calculation_summary) {
		calculation_summary.forEach((calculation)->{	//use of an arrow function
			summaryList.getItems().add(calculation);
		});
	}
}
