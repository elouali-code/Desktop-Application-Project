package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Class.Etudiant;
import Class.Module;
import Connection.ConnectionMySQL;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TableColumn;

public class CordiNoteController{
	@FXML
	private TextField Min;
	@FXML
	private TextField Max;
	@FXML
	private Label IDLabel;
	@FXML
	private Label prenomLabel;
	@FXML
	private Button enregistrer;
	@FXML
	private Label nomLabel;
	@FXML
	private Label noteLabel;
	@FXML
	private TableView module;
	@FXML
	private TableColumn nommodule;
	@FXML
	private TableColumn notemodule;
	@FXML
	private TableColumn validation;
	@FXML
	private ChoiceBox modulechoix;
	@FXML
	private TextField notenew;
	@FXML
	private Button precedent;
	@FXML
	private Button suivant;
	@FXML
	private ImageView image;
	private ArrayList<Etudiant> y=new ArrayList<Etudiant>();
	public int i=0;
	// Event Listener on Button.onAction
	@FXML
	public void chercherClicked(ActionEvent event) throws URISyntaxException {
		// TODO Autogenerated
		try {
			float min = Float.parseFloat(Min.getText());
			float max = Float.parseFloat(Max.getText());
			if(min>max) {
				JOptionPane.showMessageDialog(null, "Valeurs invalides ");
			}else if (Min.getText().equals("") || Max.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs ");
			}else {
				i=0;
				minmax();
				etud(i);
			}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valeurs invalides ");
		}
	}
	// Event Listener on Button[#enregistrer].onAction
	@FXML
	public void enregistrer(ActionEvent event) {
		// TODO Autogenerated
		try {
			ConnectionMySQL x = new ConnectionMySQL();
			if(String.valueOf(modulechoix.getValue()).equals("") || notenew.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
			}else if(Float.parseFloat(notenew.getText())>20 || Float.parseFloat(notenew.getText())<0) {
				JOptionPane.showMessageDialog(null, "Valeurs invalides ");
				
			}else {
				x.EnregistrerCordiNote(Integer.parseInt(IDLabel.getText()), String.valueOf(modulechoix.getValue()), Float.parseFloat(notenew.getText()));
				x.calculeGenerale(Integer.parseInt(IDLabel.getText()));
				etud(i);
				notenew.clear();
			}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valeurs invalides");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	// Event Listener on Button[#precedent].onAction
	@FXML
	public void precedent(ActionEvent event) throws URISyntaxException {
		// TODO Autogenerated
		try {
			i-=1;
			etud(i);
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valeurs invalides ");
		}
		
	}
	// Event Listener on Button[#suivant].onAction
	@FXML
	public void suivant(ActionEvent event) throws URISyntaxException {
		// TODO Autogenerate
		try {
			i+=1;
			etud(i);
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valeurs invalides ");
		}
		
	}
	public void minmax() {
		ConnectionMySQL x = new ConnectionMySQL();
		y = x.CordiNote(Float.parseFloat(Min.getText()), Float.parseFloat(Max.getText()));
	}
	public void etud(int i) throws URISyntaxException {
		try {
			ConnectionMySQL x= new ConnectionMySQL();
			if(y.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Aucun �tudiant!");
			}else if(i>y.size())
			{
				i=i-1;
				etud(i);
			}else {
				IDLabel.setText(String.valueOf(y.get(i).getId()));
				nomLabel.setText(String.valueOf(y.get(i).getNom()));
				prenomLabel.setText(String.valueOf(y.get(i).getPrenom()));
				noteLabel.setText(String.valueOf(y.get(i).getNotegenerale()));
				Etudiant z = y.get(i);
				x.CordiNote2(z);
				module.getItems().clear();
				nommodule.setCellValueFactory(new PropertyValueFactory<Module, String>("nommodule"));
				notemodule.setCellValueFactory(new PropertyValueFactory<Module, Float>("notemodule"));
				validation.setCellValueFactory(new PropertyValueFactory<Module, String>("validation"));
				module.setDisable(false);
				module.setItems(z.getModule());
				modulechoix.setDisable(false);
				modulechoix.getItems().clear();
				modulechoix.setValue(null);
				for(int j=0;j<z.getModule().size();j++) {
					modulechoix.getItems().add(z.getModule().get(j).getNommodule());
				}
				notenew.setDisable(false);
				enregistrer.setDisable(false);
				if(i!=y.size()-1) {
					suivant.setDisable(false);
				}else {
					suivant.setDisable(true);
				}
				if(i!=0) {
					precedent.setDisable(false);
				}else {
					precedent.setDisable(true);
				}
				Image img = new Image(getClass().getResource("/ImageEtud/"+String.valueOf(y.get(i).getId())+".jpg").toURI().toString());
				image.setImage(img);
			}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valeurs invalides ");
		}
		
	}
}



