package Class;

public class Matiere {
	private int id;
	private String nomMatiere;
	private String nomModule;
	private float cc;
	private float tp;
	private float exam;
	
	public Matiere() {
		
	}
	public Matiere(int id, String nomMatiere, String nomModule, float cc, float tp, float exam) {
		super();
		this.id = id;
		this.nomMatiere = nomMatiere;
		this.nomModule = nomModule;
		this.cc = cc;
		this.tp = tp;
		this.exam = exam;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomMatiere() {
		return nomMatiere;
	}
	public void setNomMatiere(String nomMatiere) {
		this.nomMatiere = nomMatiere;
	}
	public String getNomModule() {
		return nomModule;
	}
	public void setNomModule(String nomModule) {
		this.nomModule = nomModule;
	}
	public float getCc() {
		return cc;
	}
	public void setCc(float cc) {
		this.cc = cc;
	}
	public float getTp() {
		return tp;
	}
	public void setTp(float tp) {
		this.tp = tp;
	}
	public float getExam() {
		return exam;
	}
	public void setExam(float exam) {
		this.exam = exam;
	}
	@Override
	public String toString() {
		return "Matiere [id=" + id + ", nomMatiere=" + nomMatiere + ", nomModule=" + nomModule + ", cc=" + cc + ", tp="
				+ tp + ", exam=" + exam + "]";
	}
	
}

