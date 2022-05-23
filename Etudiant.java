package Class;


import javafx.collections.ObservableList;

public class Etudiant {
	private int id;
	private String nom;
	private String prenom;
	private String validation;
	private float tp;
	private float cc;
	private float examen;
	private ObservableList<Module> module;
	private String matiere;
	private String semestre;
	private String filiere;
	private float moyenne;
	private float notegenerale;
	
	public Etudiant() {
		super();
	}

	public Etudiant(int id,String nom, String prenom,float tp, float cc, float examen) {
		super();
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.cc=cc;
		this.tp=tp;
		this.examen=examen;
	}

	public Etudiant(int id,String nom, String prenom,String matiere,float tp, float cc, float examen) {
		super();
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.matiere=matiere;
		this.cc=cc;
		this.tp=tp;
		this.examen=examen;
	}
	
	public Etudiant(int id, String nom, String prenom,String validation,String semestre,String filiere,float moyenne) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.validation=validation;
		this.semestre=semestre;
		this.filiere=filiere;
		this.moyenne=moyenne;
}

	public Etudiant(int id, String nom, String prenom,float notegenerale,ObservableList<Module> module) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.module=module;
		this.notegenerale=notegenerale;
		
}
	public Etudiant(String nom, String prenom,String filiere,String semestre) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.filiere=filiere;
		this.semestre=semestre;
	}
	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public float getTp() {
		return tp;
	}

	public void setTp(float tp) {
		this.tp = tp;
	}

	public float getCc() {
		return cc;
	}

	public void setCc(float cc) {
		this.cc = cc;
	}

	public float getExamen() {
		return examen;
	}

	public void setExamen(float examen) {
		this.examen = examen;
	}
	
	
	public float getNotegenerale() {
		return notegenerale;
	}

	public void setNotegenerale(float notegenerale) {
		this.notegenerale = notegenerale;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	
	public float getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}

	public ObservableList<Module> getModule() {
		return module;
	}

	public void setModule(ObservableList<Module> module) {
		this.module = module;
	}

	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	@Override
	public String toString() {
		return "Etudiant [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", validation=" + validation + ", tp="
				+ tp + ", cc=" + cc + ", examen=" + examen + ", module=" + module + ", matiere=" + matiere
				+ ", semestre=" + semestre + ", filiere=" + filiere + ", moyenne=" + moyenne + ", notegenerale="
				+ notegenerale + "]";
	}

	   
}