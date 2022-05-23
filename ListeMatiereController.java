package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Class.Etudiant;
import Class.Matiere;
import Connection.ConnectionMySQL;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class ListeMatiereController implements Initializable {
	@FXML
	private TableView<Matiere> TableMatiere;
	@FXML
	private TableColumn<Matiere, Integer> idmatiere;
	@FXML
	private TableColumn<Matiere, String > nommatiere;
	@FXML
	private TableColumn<Matiere, String > nommodule;
	@FXML
	private TableColumn<Matiere, Float> cc;
	@FXML
	private TableColumn<Matiere, Float> tp;
	@FXML
	private TableColumn<Matiere, Float> exam;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		TableMatiere.getItems().clear();
		ConnectionMySQL x= new ConnectionMySQL();
		ObservableList<Matiere> y = x.listeMatiere();
		idmatiere. setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("id"));
		nommatiere.setCellValueFactory(new PropertyValueFactory<Matiere, String >("nomMatiere"));
		nommodule.setCellValueFactory(new PropertyValueFactory<Matiere, String >("nomModule"));
		tp.setCellValueFactory(new PropertyValueFactory<Matiere, Float>("tp"));
		cc.setCellValueFactory(new PropertyValueFactory<Matiere, Float>("cc"));
		exam.setCellValueFactory(new PropertyValueFactory<Matiere, Float>("exam"));
		TableMatiere.setItems(y);
	}
	

}


