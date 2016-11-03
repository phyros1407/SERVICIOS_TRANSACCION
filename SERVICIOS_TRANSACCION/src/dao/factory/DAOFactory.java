package dao.factory;


import dao.interfaces.ComprobanteDao;

import dao.interfaces.PedidoDao;

public abstract class DAOFactory {
	
	public static final int MYSQL=1;
	public static final int SQLSERVER=2;
	public static final int ORACLE=3;
	
	

	public abstract PedidoDao getPedidoDAO();
	public abstract ComprobanteDao getComprobanteDao();
	
	
	public static DAOFactory getDaoFactory(int factory){
		
		switch (factory) {
		case MYSQL:
			return new MySqlDAOFactory();
		

		default:
			return null;
		}
		
	}

	
}
