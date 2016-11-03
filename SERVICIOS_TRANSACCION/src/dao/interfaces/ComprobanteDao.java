package dao.interfaces;

import java.util.ArrayList;

import beans.BoletaBean;

public interface ComprobanteDao {

	public ArrayList<BoletaBean> buscarComprobanteGenerado(String numeroTransaccion);
	public String obtenerCorreo(int id);
	
}
