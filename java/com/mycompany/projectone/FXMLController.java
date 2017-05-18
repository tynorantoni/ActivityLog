
package com.mycompany.projectone;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

	@FXML
	private ListView list;
	@FXML
	private TextField fieldAct;
	@FXML
	private TextField fieldT;

	private MainApp mainApp;

	public FXMLController() {
	}

	//button add action handler -> button add values into DB and listview
	@FXML
	private void handleButtonAdd(ActionEvent event) {

		try {
			String act = fieldAct.getText();
			int time = Integer.parseInt(fieldT.getText());

			LocalDateTime timestamp = LocalDateTime.now();
			JDBCConn jdbcConn = new JDBCConn();
			list.getItems().add(act + " for " + time + " hours  (at: " + timestamp + ")");

			jdbcConn.insertToDB(act, time);
		} catch (NumberFormatException e) {
			fieldT.setText("!NaN!");
		} catch (SQLException e) {
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());

		}
	}
	//button delete action handler -> button delete values from DB and listview
	@FXML
	private void handleButtonDelete(ActionEvent event) throws IOException, SQLException {
		JDBCConn jdbcConn = new JDBCConn();

		try {
			final int selectedId = list.getSelectionModel().getSelectedIndex();

			list.getItems().remove(selectedId);
			jdbcConn.deletefromDB(selectedId);

		} catch (Exception e) {
			e.getClass();
		}
	}
//method used to fill in listview after application start
	public void refresh() {
		JDBCConn jdbcConn = new JDBCConn();
		try {

			List<String> result = jdbcConn.showFromDB();
			for (String row : result) {
				list.getItems().add(row);
			}
		} catch (SQLException e) {
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
