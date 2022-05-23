package Controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import Class.ProfProfil;
import Connection.ConnectionMySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProfProfilController implements Initializable {
	@FXML
	private Label nom;
	@FXML
	private Label prenom;
	@FXML
	private Label matiere;
	@FXML
	private ImageView photo;
	@FXML
	private ImageView image;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectionMySQL x = new ConnectionMySQL();
		ArrayList<ProfProfil> y=x.afficherProfil();
		Image img;
		try {
			img = new Image(getClass().getResource("/image_prof/"+String.valueOf(y.get(0).getId())+".jpg").toURI().toString());
			image.setImage(img);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nom.setText(y.get(0).getNom());
		prenom.setText(y.get(0).getPrenom());
		matiere.setText(y.get(0).getMatiere());
	}
}


