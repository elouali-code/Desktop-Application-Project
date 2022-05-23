package Class;

public class CordiProfil {
	private int id;
	private String nom;
	private String prenom;	
	private String filiere;
	public CordiProfil() {
		
	}
	
	public CordiProfil(int id,String nom, String prenom,String filiere) {
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.filiere=filiere;
	}
	

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CordiProfil [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", filiere=" + filiere + "]";
	}

	
}


