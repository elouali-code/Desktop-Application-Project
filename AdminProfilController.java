package Controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Class.AdminProfil;
import Class.CordiProfil;
import Connection.ConnectionMySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminProfilController implements Initializable {
	@FXML
	private Label nom;
	@FXML
	private Label prenom;
	@FXML
	private ImageView photo;
	@FXML
	private ImageView image;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectionMySQL x = new ConnectionMySQL();
		ArrayList<AdminProfil> y=x.afficherProfilAdmin();
		Image img;
		try {
			img = new Image(getClass().getResource("/image_admin/"+String.valueOf(y.get(0).getId())+".jpg").toURI().toString());
			image.setImage(img);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nom.setText(y.get(0).getNom());
		prenom.setText(y.get(0).getPrenom());
	}

}


