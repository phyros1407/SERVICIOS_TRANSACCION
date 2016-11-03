package beans;

public class TransaccionBean {

	private int id;
	private int id_usuario;
	private String ide;
	private String num;
	private String est;
	private String fec_ent;
	
	//AUDITORIA
	private String usu_crea_regi;
	private String fec_crea_regi;
	private String ult_usu_mod_regi;
	private String fec_ult_mod_regi;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getIde() {
		return ide;
	}
	public void setIde(String ide) {
		this.ide = ide;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getEst() {
		return est;
	}
	public void setEst(String est) {
		this.est = est;
	}
	public String getFec_ent() {
		return fec_ent;
	}
	public void setFec_ent(String fec_ent) {
		this.fec_ent = fec_ent;
	}
	public String getUsu_crea_regi() {
		return usu_crea_regi;
	}
	public void setUsu_crea_regi(String usu_crea_regi) {
		this.usu_crea_regi = usu_crea_regi;
	}
	public String getFec_crea_regi() {
		return fec_crea_regi;
	}
	public void setFec_crea_regi(String fec_crea_regi) {
		this.fec_crea_regi = fec_crea_regi;
	}
	public String getUlt_usu_mod_regi() {
		return ult_usu_mod_regi;
	}
	public void setUlt_usu_mod_regi(String ult_usu_mod_regi) {
		this.ult_usu_mod_regi = ult_usu_mod_regi;
	}
	public String getFec_ult_mod_regi() {
		return fec_ult_mod_regi;
	}
	public void setFec_ult_mod_regi(String fec_ult_mod_regi) {
		this.fec_ult_mod_regi = fec_ult_mod_regi;
	}
	
	
	
	
}
