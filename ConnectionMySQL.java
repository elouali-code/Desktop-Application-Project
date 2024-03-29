package Connection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.jdbc.MysqlDataSource;

import Class.AdminProfil;
import Class.CordiProfil;
import Class.Etudiant;
import Class.Matiere;
import Class.Module;
import Class.ProfProfil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class ConnectionMySQL 
{
	public static int idfiliere;
	 public static int idUser;
	 public static int idEtud;
	 private static String servername ="localhost";
	 private static String username ="root";
	 private static String dbname ="projecttest";
	 private static Integer portnumber =3306;
	 private static String password ="@abdo2001";
	 private static String Timezone ="UTC";
	 
	 public static Connection getConnection() throws Exception 
	 {
		 Connection cnx = null;
		 MysqlDataSource datasource = new MysqlDataSource();
		 datasource.setServerName(servername);
		 datasource.setUser(username);
		 datasource.setPassword(password);
		 datasource.setDatabaseName(dbname);
		 datasource.setPortNumber(portnumber);
		 datasource.setServerTimezone(Timezone);
		 
		 try
		 {
			 cnx = datasource.getConnection();
		 }
		 catch (Exception e) 
		 {
			 e.printStackTrace();
		 }
		 
	     return cnx;
	 }
	 
	 public String testConnection(String username, String password) throws Exception {
		 Connection cnx =getConnection();
		 String type = null;
	      String sql = "SELECT  * FROM authentification where identifiant=? and motdepasse=?";
	      try {
				PreparedStatement st=cnx.prepareStatement(sql);
				st.setString(1, username);
				st.setString(2, password);
				ResultSet result=st.executeQuery();
				if(result.next()) {
					type =result.getString("type");
				
					idUser=result.getInt("idauthentification");
					if(type.equals("Coordinateur")) {
						
						PreparedStatement st1=cnx.prepareStatement("select idfiliere from filiere where coordinateur_idcoordinateur=(select idcoordinateur from coordinateur where authentification_idauthentification=?)");
						st1.setInt(1, idUser);
						ResultSet result1=st1.executeQuery();
						while(result1.next()) {
							idfiliere = result1.getInt(1);
						}
					}
				    } else{
				    	return null;
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		return type; 
	 }
	 public ArrayList<ProfProfil> afficherProfil(){
		 try {
			 String nom="",prenom="",matiere="";
			 int id=0;
			 Connection cnx = getConnection();
			 ArrayList<ProfProfil> arl= new ArrayList<ProfProfil>();
			 String sql ="select idprofesseur,nom,prenom,nommatiere from professeur full join matiere on idprofesseur=matiere.professeur_idprofesseur and authentification_idauthentification=?";
			 PreparedStatement st = cnx.prepareStatement(sql);
			 st.setInt(1,idUser);
			 ResultSet rs= st.executeQuery();
			 while(rs.next()) {
				 id=rs.getInt("idprofesseur");
				 nom=rs.getString("nom");
				 prenom=rs.getString("prenom");
				 matiere=rs.getString("nommatiere")+" - "+matiere;
			 }
			 arl.add(new ProfProfil(id,nom,prenom,matiere));
			 return arl;
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	 public ArrayList<CordiProfil> afficherProfilCordi(){
		 try {
			 String nom="",prenom="",fil="";
			 int id=0;
			 Connection cnx = getConnection();
			 ArrayList<CordiProfil> arl= new ArrayList<CordiProfil>();
			 String sql ="select idcoordinateur,nom,prenom,nomfiliere from coordinateur inner join filiere on coordinateur_idcoordinateur=idcoordinateur where authentification_idauthentification=?";
			 PreparedStatement st = cnx.prepareStatement(sql);
			 st.setInt(1,idUser);
			 ResultSet rs= st.executeQuery();
			 while(rs.next()) {
				 id=rs.getInt("idcoordinateur");
				 nom=rs.getString("nom");
				 prenom=rs.getString("prenom");
				 fil=rs.getString("nomfiliere");
				 
			 }
			 arl.add(new CordiProfil(id,nom,prenom,fil));
			 return arl;
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	 public ArrayList<AdminProfil> afficherProfilAdmin(){
		 try {
			 String nom="",prenom="";
			 int id=0;
			 Connection cnx = getConnection();
			 ArrayList<AdminProfil> arl= new ArrayList<AdminProfil>();
			 String sql ="select idadministrateur,nom,prenom from administrateur where authentification_idauthentification=?";
			 PreparedStatement st = cnx.prepareStatement(sql);
			 st.setInt(1,idUser);
			 ResultSet rs= st.executeQuery();
			 while(rs.next()) {
				 id=rs.getInt("idadministrateur");
				 nom=rs.getString("nom");
				 prenom=rs.getString("prenom");
			 }
			 arl.add(new AdminProfil(id,nom,prenom));
			 return arl;
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	 
	

	public List<String> matChoiceBox() throws Exception {
		Connection cnx = getConnection();
		List<String> mat =new ArrayList<String>();
		String sql ="select nommatiere from matiere inner join professeur on professeur.idprofesseur=professeur_idprofesseur and idprofesseur=(select professeur.idprofesseur from professeur where professeur.authentification_idauthentification=?) ";
		PreparedStatement st = cnx.prepareStatement(sql);
		st.setInt(1, idUser);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			mat.add(rs.getString("nommatiere"));
		}
		
		return mat;
	}
	public void insererCoef(float cc,float tp, float exam,String nom) throws Exception {
		if(cc+tp+exam==1) {
			Connection cnx = getConnection();
			String sql="Update matiere set cc=?, tp=?, exam=? where nommatiere=?";
			PreparedStatement st =cnx.prepareStatement(sql);
			st.setFloat(1, cc);
			st.setFloat(2, tp);
			st.setFloat(3, exam);
			st.setString(4, nom);
			st.executeUpdate();
		}else {
			JOptionPane.showMessageDialog(null, "Valeur des coefficients non coh�rantes");
		}
	}
	
	public ObservableList<Etudiant> listeEtudiant2(String nom){
		try {
			Connection cnx = getConnection();
			ObservableList<Etudiant> arl = FXCollections.observableArrayList();
			String sql1 ="select idmatiere from matiere where professeur_idprofesseur=(select idprofesseur from professeur where authentification_idauthentification=?) and nommatiere=?";
			String sql = "select idetudiant,nom,prenom,notetp,notecc,noteexam,nommatiere from note_matiere inner join etudiant on etudiant_idetudiant=etudiant.idetudiant inner join matiere on idmatiere=matiere_idmatiere and matiere_idmatiere=?";			
			PreparedStatement st1=cnx.prepareStatement(sql1);
			st1.setInt(1, idUser);
			st1.setString(2, nom);
			ResultSet rs1=st1.executeQuery();
				while(rs1.next()) {
					PreparedStatement st=cnx.prepareStatement(sql);
					st.setInt(1, rs1.getInt("idmatiere"));
					ResultSet rs=st.executeQuery();
					while(rs.next()) {
						arl.add(new Etudiant(rs.getInt("idetudiant"),rs.getString("nom"),rs.getString("prenom"),rs.getString("nommatiere"),rs.getFloat("notetp"),rs.getFloat("notecc"),rs.getFloat("noteexam")));
					}
					
				}
				return arl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
	}
	public ObservableList<Etudiant> listeEtudiant1(String nom){
		try {
			Connection cnx = getConnection();
			ObservableList<Etudiant> arl = FXCollections.observableArrayList();
			String sql1 ="select idmatiere from matiere where professeur_idprofesseur=(select idprofesseur from professeur where authentification_idauthentification=?) and nommatiere=?";
			String sql = "select idetudiant,nom,prenom,notetp,notecc,noteexam from note_matiere inner join etudiant on etudiant_idetudiant=etudiant.idetudiant and matiere_idmatiere=?";			
			PreparedStatement st1=cnx.prepareStatement(sql1);
			st1.setInt(1, idUser);
			st1.setString(2, nom);
			ResultSet rs1=st1.executeQuery();
				while(rs1.next()) {
					PreparedStatement st=cnx.prepareStatement(sql);
					st.setInt(1, rs1.getInt("idmatiere"));
					ResultSet rs=st.executeQuery();
					while(rs.next()) {
						arl.add(new Etudiant(rs.getInt("idetudiant"),rs.getString("nom"),rs.getString("prenom"),rs.getFloat("notetp"),rs.getFloat("notecc"),rs.getFloat("noteexam")));
					}
					
				}
				return arl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
	}
	public ObservableList<Matiere> listeMatiere(){
		try {
			Connection cnx = getConnection();
			ObservableList<Matiere> arl = FXCollections.observableArrayList();
			String sql ="select idmatiere,nommatiere,cc,tp,exam,nommodule from matiere inner join module_has_matiere on idmatiere=matiere_idmatiere inner join module on idmodule=module_idmodule where professeur_idprofesseur=(select idprofesseur from professeur where authentification_idauthentification=?)";		
			PreparedStatement st=cnx.prepareStatement(sql);
			st.setInt(1, idUser);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				arl.add(new Matiere(rs.getInt("idmatiere"),rs.getString("nommatiere"),rs.getString("nommodule"),rs.getFloat("cc"),rs.getFloat("tp"),rs.getFloat("exam")));
					
			}
				return arl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
		
	}
	public List<String> filChoiceBox() throws Exception {
		Connection cnx = getConnection();
		List<String> fil =new ArrayList<String>();
		String sql ="SELECT * FROM projecttest.filiere;";
		PreparedStatement st = cnx.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			fil.add(rs.getString("nomfiliere"));
		}
		
		return fil;
	}
	public List<String> semChoiceBox() throws Exception {
		Connection cnx = getConnection();
		List<String> sem =new ArrayList<String>();
		String sql ="SELECT * FROM projecttest.semestre inner join filiere_has_semestre on semestre_idsemestre=idsemestre where filiere_idfiliere=?";
		PreparedStatement st = cnx.prepareStatement(sql);
		st.setInt(1, idfiliere);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			sem.add(rs.getString("nomsemestre"));
		}
		
		
		return sem;
	}
	public List<String> semChoiceBoxAdmin() throws Exception {
		Connection cnx = getConnection();
		List<String> sem =new ArrayList<String>();
		String sql ="SELECT * FROM projecttest.semestre";
		PreparedStatement st = cnx.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			sem.add(rs.getString("nomsemestre"));
		}
		return sem;
	}
	
	public ObservableList<Etudiant> listeEtudiantCordi(String semestre){
		try {
			Connection cnx = getConnection();
			ObservableList<Etudiant> arl = FXCollections.observableArrayList();
			String sql = "select idetudiant,nom,prenom,validation, nomsemestre,nomfiliere,notesemestre from etudiant inner join filiere on filiere_idfiliere=idfiliere inner join semestre on idsemestre=semestre_idsemestre where nomsemestre=? and idfiliere=?";			
			PreparedStatement st=cnx.prepareStatement(sql);
			st.setString(1, semestre);
			st.setInt(2, idfiliere);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
					arl.add(new Etudiant(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getFloat(7)));
			}
				return arl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
		
	}	
	public ArrayList<Etudiant> CordiNote(float min,float max){
		
		try {
			Connection cnx = getConnection();
			ArrayList<Etudiant> arl = new ArrayList<Etudiant>();
			String sql ="Select idetudiant,nom,prenom,notegenerale from etudiant where notegenerale between ? and ? and filiere_idfiliere=?";
			PreparedStatement st = cnx.prepareStatement(sql);
			st.setFloat(1, min);
			st.setFloat(2, max);
			st.setInt(3, idfiliere);
		
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				arl.add(new Etudiant(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4),null));
			}
			return arl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
		
	}
	public Etudiant CordiNote2(Etudiant et){
		
		try {
			int i=0;
			Connection cnx = getConnection();
			ObservableList<Module> arl1 = FXCollections.observableArrayList();
			String sql = "SELECT nommodule,notemodule,validation FROM module inner join note_module on idmodule=module_idmodule where etudiant_idetudiant =?";
			PreparedStatement st = cnx.prepareStatement(sql);
			st.setInt(1, et.getId());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				arl1.add(new Module(rs.getString("nommodule"),rs.getFloat("notemodule"),rs.getString("validation")));
			}
			et.setModule(arl1);
			return et;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
		
	}

	public void AjouterEtud(String nom,String prenom,String filiere,String semestre) {
	try {
		String idetud = "Select last_insert_id()";
		Connection cnx = getConnection();
		PreparedStatement st = cnx.prepareStatement("SELECT * FROM filiere inner join filiere_has_semestre on filiere_idfiliere=idfiliere inner join semestre on semestre_idsemestre=idsemestre where nomfiliere=? and nomsemestre=?");
		st.setString(1, filiere);
		st.setString(2, semestre);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			int fil = rs.getInt("idfiliere");
			int sem = rs.getInt("idsemestre");
			PreparedStatement st1 =cnx.prepareStatement("Insert into etudiant(nom,prenom,filiere_idfiliere,semestre_idsemestre) values (?,?,?,?)");
			st1.setString(1, nom);
			st1.setString(2, prenom);
			st1.setInt(3, fil);
			st1.setInt(4, sem);
			st1.executeUpdate();
			PreparedStatement st2 = cnx.prepareStatement(idetud);
			ResultSet rs2 =st2.executeQuery();
			while(rs2.next()) {
				int id=rs2.getInt(1);
				PreparedStatement st3 =cnx.prepareStatement("select idmodule from module where filiere_idfiliere=? and semestre_idsemestre=?");
				st3.setInt(1, fil);
				st3.setInt(2, sem);
				ResultSet rs3 =st3.executeQuery();
				while(rs3.next()) {
					int idmod=rs3.getInt(1);
					PreparedStatement st4 =cnx.prepareStatement("Insert into note_module(etudiant_idetudiant,module_idmodule) values (?,?)");
					st4.setInt(1, id);
					st4.setInt(2, idmod);
					st4.executeUpdate();
					PreparedStatement st5 =cnx.prepareStatement("select matiere_idmatiere from module_has_matiere inner join module on module_idmodule=idmodule where module_idmodule=?");
					st5.setInt(1, idmod);
					ResultSet rs5=st5.executeQuery();
					while(rs5.next()) {
						int idmat=rs5.getInt(1);
						PreparedStatement st6 =cnx.prepareStatement("Insert into note_matiere(etudiant_idetudiant,matiere_idmatiere) values (?,?)");
						st6.setInt(1, id);
						st6.setInt(2, idmat);
						st6.executeUpdate();
					}
					
					
				}
			}
			JOptionPane.showMessageDialog(null, "Etudiant bien ajouter ");
			}else {
				JOptionPane.showMessageDialog(null, "Filiere et Semestre invalides");
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Erreur");
	}
	
	}
	
	public ArrayList<Etudiant> chercherEtud(int id){
		try {
			Connection cnx = getConnection();
			ArrayList<Etudiant> arl=new ArrayList<Etudiant>();
			PreparedStatement st = cnx.prepareStatement("Select * from etudiant where idetudiant=?");
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				int fil = rs.getInt("filiere_idfiliere");
				int sem = rs.getInt("semestre_idsemestre");
				PreparedStatement st1 = cnx.prepareStatement("SELECT * FROM filiere inner join filiere_has_semestre on filiere_idfiliere=idfiliere inner join semestre on semestre_idsemestre=idsemestre where idfiliere=? and idsemestre=?");
				st1.setInt(1, fil);
				st1.setInt(2, sem);
				ResultSet rs1 =st1.executeQuery();
				while(rs1.next()) {
					arl.add(new Etudiant(rs.getString("nom"),rs.getString("prenom"),rs1.getString("nomfiliere"),rs1.getString("nomsemestre")));
				}
			}
			return arl;
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			return null;
		}
	}
	public void SupprimerEtud(int id) {
		try {
			Connection cnx = getConnection();
			PreparedStatement st = cnx.prepareStatement("delete from etudiant where(idetudiant=?)");
			PreparedStatement st1 = cnx.prepareStatement("delete from note_matiere where(etudiant_idetudiant=?)");
			PreparedStatement st2 = cnx.prepareStatement("delete from note_module where(etudiant_idetudiant=?)");
			st.setInt(1, id);
			st1.setInt(1, id);
			st2.setInt(1, id);
			st1.executeUpdate();
			st2.executeUpdate();
			st.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
			
		}
	}
	public void ModifierEtud(int id,String nom,String prenom,String filiere,String semestre) {
		try {
			Connection cnx = getConnection();
			PreparedStatement st = cnx.prepareStatement("SELECT * FROM filiere inner join filiere_has_semestre on filiere_idfiliere=idfiliere inner join semestre on semestre_idsemestre=idsemestre where nomfiliere=? and nomsemestre=?");
			st.setString(1, filiere);
			st.setString(2, semestre);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				int fil = rs.getInt("idfiliere");
				int sem = rs.getInt("idsemestre");
				PreparedStatement st1 =cnx.prepareStatement("update etudiant set nom=?,prenom=?,filiere_idfiliere=?,semestre_idsemestre=? where idetudiant=?");
				st1.setString(1, nom);
				st1.setString(2, prenom);
				st1.setInt(3, fil);
				st1.setInt(4, sem);
				st1.setInt(5, id);
				st1.executeUpdate();
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur");
		}
		
		}
public void pdfs(String semestre) throws Exception{
	 String filiere = null;
	        try {
	           Connection cnx= getConnection();
				String sql = "select idetudiant,nom,prenom,validation,nomfiliere,notesemestre from etudiant inner join filiere on filiere_idfiliere=idfiliere inner join semestre on semestre_idsemestre=idsemestre where idfiliere=? and nomsemestre=?";			
				PreparedStatement st=cnx.prepareStatement(sql);
				st.setInt(1, idfiliere);
				st.setString(2, semestre);
				String sql2 = "select nomfiliere from  filiere  where idfiliere=?";			
				PreparedStatement st2=cnx.prepareStatement(sql2);
				st2.setInt(1, idfiliere);
				ResultSet rs2 =st2.executeQuery();
				if(rs2.next()) {
					filiere=rs2.getString(1);
				}
				ResultSet rs=st.executeQuery();

	                    Document my_pdf = new Document();
	                    PdfWriter.getInstance(my_pdf, new FileOutputStream("C:\\Users\\hp\\Desktop\\PV_note.pdf"));
	                    my_pdf.open();
	                    String imgSrc="src\\ImagePDF\\Log.png";
	                    Image img = Image.getInstance(imgSrc);
	                    my_pdf.add(img);
	                    my_pdf.add( new Paragraph(" "));
	                    Font f = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.BOLD, BaseColor.BLACK);
	                    Chunk c = new Chunk("PV : Filiere: "+filiere+" - Semestre: "+semestre, f);
	                    Paragraph para = new Paragraph(c);
	                    my_pdf.add(para);
	                    my_pdf.add( new Paragraph(" "));
	                    my_pdf.add( new Paragraph(" "));
	                    PdfPTable my_table = new PdfPTable(new float[] {1,5,7,3,3});
	                    PdfPCell table_cell;
	                    table_cell= new PdfPCell(new Phrase("ID"));
	                    BaseColor myColor = WebColors.getRGBColor("#ADD8E6");
	                    table_cell.setBackgroundColor(myColor);
	                    my_table.addCell(table_cell);

	                    table_cell= new PdfPCell(new Phrase("Nom"));
	                    table_cell.setBackgroundColor(myColor);
	                    my_table.addCell(table_cell);
	                    
	                    table_cell= new PdfPCell(new Phrase("prenom"));
	                    table_cell.setBackgroundColor(myColor);
	                    my_table.addCell(table_cell);
	      
	                    table_cell= new PdfPCell(new Phrase("Note "));
	                    table_cell.setBackgroundColor(myColor);
	                    my_table.addCell(table_cell);
	                    
	                    table_cell= new PdfPCell(new Phrase("Validation"));
	                    table_cell.setBackgroundColor(myColor);
	                    my_table.addCell(table_cell);

	                    while (rs.next()) {                
	                                    String id = rs.getString(1);
	                                    table_cell=new PdfPCell(new Phrase(id));
	                                    my_table.addCell(table_cell);
	                                    String nom=rs.getString(2);
	                                    table_cell=new PdfPCell(new Phrase(nom));
	                                    my_table.addCell(table_cell);
	                                    String prenom=rs.getString(3);
	                                    table_cell=new PdfPCell(new Phrase(prenom));
	                                    my_table.addCell(table_cell);	                                 
	                                    String note=rs.getString(6);
	                                    table_cell=new PdfPCell(new Phrase(note));
	                                    my_table.addCell(table_cell);
	                                    String validation=rs.getString(4);
	                                    table_cell=new PdfPCell(new Phrase(validation));
	                                    my_table.addCell(table_cell);
	                                    }
	                    my_pdf.add(my_table);
	                    my_pdf.close();              



	    } catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    } catch (DocumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    }
	}

	
public void EnregistrerCordiNote(int id, String module,Float note) {
	try {
		String val="";
		if(note>=10) {
			val="Valid�";
		}else {
			val="Rattrapage";
		}
		Connection cnx = getConnection();
		PreparedStatement st = cnx.prepareStatement("select idmodule from module where nommodule=?");
		st.setString(1, module);
		ResultSet rs =st.executeQuery();
		if(rs.next()) {
			PreparedStatement st1 =cnx.prepareStatement("update note_module set notemodule=?,validation=? where etudiant_idetudiant=? and module_idmodule=?");
			st1.setFloat(1, note);
			st1.setString(2, val);
			st1.setInt(4, rs.getInt(1));
			st1.setInt(3, id);
			st1.executeUpdate();
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Erreur");
	}
}
public void insererMotpass(String nv,String nv2,String ac) throws Exception {
		Connection cnx = getConnection();
		String sql="select motdepasse from authentification where idauthentification=?";
		String sql1="Update authentification set motdepasse=? where idauthentification=?";
		PreparedStatement st =cnx.prepareStatement(sql);
		st.setInt(1, idUser);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			if(rs.getString(1).equals(ac) && nv.equals(nv2)){
				PreparedStatement st1 =cnx.prepareStatement(sql1);
				st1.setString(1, nv);
				st1.setInt(2, idUser);
				st1.executeUpdate();
				JOptionPane.showMessageDialog(null, "Le mot de passe a �t� bien chang� ");
			}else if(!rs.getString(1).equals(ac)) {
				JOptionPane.showMessageDialog(null, "Verfifiez le mot de passe actuel ");
			}else {
				JOptionPane.showMessageDialog(null, "Les nouveaux mot de passe ne sont pas identiques ");
			}
	
		}
		JOptionPane.showMessageDialog(null, "Erreur");
		
		}
		
public void modifiertp(int id,String matiere,float note) {
	try {
		Connection cnx = getConnection();
		String sql ="Update note_matiere inner join matiere on idmatiere=matiere_idmatiere set notetp=? where etudiant_idetudiant=? and nommatiere=?";
		PreparedStatement st = cnx.prepareStatement(sql);
		st.setFloat(1, note);
		st.setInt(2, id);
		st.setString(3, matiere);
		st.executeUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Erreur");
	}
	
}
public void modifiercc(int id,String matiere,float note) {
	try {
		Connection cnx = getConnection();
		String sql ="Update note_matiere inner join matiere on idmatiere=matiere_idmatiere set notecc=? where etudiant_idetudiant=? and nommatiere=?";
		PreparedStatement st = cnx.prepareStatement(sql);
		st.setFloat(1, note);
		st.setInt(2, id);
		st.setString(3, matiere);
		st.executeUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Erreur");
	}
	
}
	public void modifierexam(int id,String matiere,float note) {
	try {
		Connection cnx = getConnection();
		String sql ="Update note_matiere inner join matiere on idmatiere=matiere_idmatiere set noteexam=? where etudiant_idetudiant=? and nommatiere=?";
		PreparedStatement st = cnx.prepareStatement(sql);
		st.setFloat(1, note);
		st.setInt(2, id);
		st.setString(3, matiere);
		st.executeUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Erreur");
	}
	
}
	public void calculeMatiere(int id,String nom) {
		try {
			float tp=0,cc=0,exam=0,ntp=0,ncc=0,nexam=0,notematiere=0;
			Connection cnx = getConnection();
			PreparedStatement st = cnx.prepareCall("select idmatiere,tp,cc,exam matiere from matiere where nommatiere=?");
			st.setString(1, nom);
			ResultSet rs =st.executeQuery();
			while(rs.next()) {
				int idm = rs.getInt(1);
				tp =rs.getFloat(2);
				cc =rs.getFloat(3);
				exam =rs.getFloat(4);
				PreparedStatement st1 = cnx.prepareCall("select notetp,notecc,noteexam from note_matiere where etudiant_idetudiant=? and matiere_idmatiere=?");
				st1.setInt(1, id);
				st1.setInt(2, idm);
				ResultSet rs1=st1.executeQuery();
				while(rs1.next()) {
					ntp =rs1.getFloat(1);
					ncc =rs1.getFloat(2);
					nexam =rs1.getFloat(3);
					notematiere=(tp*ntp)+(cc*ncc)+(exam*nexam);
					PreparedStatement st2 =cnx.prepareStatement("Update note_matiere set note_matiere=? where etudiant_idetudiant=? and matiere_idmatiere=?");
					st2.setFloat(1, notematiere);
					st2.setInt(2, id);
					st2.setInt(3, idm);
					st2.executeUpdate();
					}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calculeModule(int id,String nom) {
		try {
			int idm=0,idmat=0;
			float notemod=0,sumcoef=0;
			String val="";
			Connection cnx=getConnection();
			PreparedStatement st = cnx.prepareStatement("select idmodule from module inner join module_has_matiere on module_idmodule=idmodule inner join matiere on matiere_idmatiere=idmatiere where nommatiere=?");
			st.setString(1,nom);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				idm=rs.getInt(1);
				PreparedStatement st1 = cnx.prepareStatement("select matiere_idmatiere from module_has_matiere where module_idmodule=?");
				st1.setInt(1, idm);
				ResultSet rs1=st1.executeQuery();
				while(rs1.next()) {
					idmat=rs1.getInt(1);
					PreparedStatement st2 =cnx.prepareStatement("select note_matiere,coef from matiere inner join note_matiere on idmatiere=matiere_idmatiere where idmatiere=? and etudiant_idetudiant=?");
					st2.setInt(1, idmat);
					st2.setInt(2, id);
					ResultSet rs2 =st2.executeQuery();
					while(rs2.next()) {
						notemod=notemod+rs2.getFloat(1)*rs2.getFloat(2);
						sumcoef=sumcoef+rs2.getFloat(2);
					}
				}
				notemod=notemod/sumcoef;
				if(notemod>=10) {
					val="Valid�";
				}else {
					val="Rattrapage";
				}
				PreparedStatement st3=cnx.prepareStatement("Update note_module set notemodule=?,validation=? where etudiant_idetudiant=? and module_idmodule=?");
				st3.setFloat(1, notemod);
				st3.setString(2, val);
				st3.setInt(3, id);
				st3.setInt(4, idm);
				st3.executeUpdate();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void calculeGenerale(int id) {
		try {
			float noteg=0,notes=0;
			int count=0;
			String val="";
			Connection cnx=getConnection();
			String sql="Select notemodule from note_module where etudiant_idetudiant=?";
			PreparedStatement st=cnx.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				noteg=noteg+rs.getFloat(1);
			}
			String sql1="Select count(module_idmodule) as nbremodule from note_module where etudiant_idetudiant=?";
			PreparedStatement st1=cnx.prepareStatement(sql1);
			st1.setInt(1, id);
			ResultSet rs1=st1.executeQuery();
			while(rs1.next()) {
				count=rs1.getInt("nbremodule");
				}
			notes=noteg/count;
			if(notes>=10) {
				val="Valid�";
			}else {
				val="Rattrapage";
			}
			PreparedStatement st2 =cnx.prepareStatement("Update etudiant set notegenerale=?,notesemestre=?,validation=? where idetudiant=? ");
			st2.setFloat(1, noteg);
			st2.setFloat(2, notes);
			st2.setString(3, val);
			st2.setInt(4, id);
			st2.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



