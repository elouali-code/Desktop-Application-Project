package Class;

public class Module {
	private String nommodule;
	private float notemodule;
	private String validation;
	public Module() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Module(String nommodule, float notemodule, String validation) {
		super();
		this.nommodule = nommodule;
		this.notemodule = notemodule;
		this.validation = validation;
	}
	public String getNommodule() {
		return nommodule;
	}
	public void setNommodule(String nommodule) {
		this.nommodule = nommodule;
	}
	public float getNotemodule() {
		return notemodule;
	}
	public void setNotemodule(float notemodule) {
		this.notemodule = notemodule;
	}
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	@Override
	public String toString() {
		return "Module [nommodule=" + nommodule + ", notemodule=" + notemodule + ", validation=" + validation + "]";
	}
	
}


