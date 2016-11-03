package dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;


import dao.interfaces.ComprobanteDao;
import dao.interfaces.PedidoDao;
import dao.mysql.MySql_ComprobanteDao;
import dao.mysql.MySql_PedidoDao;


public class MySqlDAOFactory extends DAOFactory {
	
	public static Connection obtenerConexion(){
		Connection conexion=null;

		 try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/bd_gym_4.6.4_data";
			conexion = DriverManager.getConnection(url,"root","root");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 
		return conexion;
	}

	@Override
	public PedidoDao getPedidoDAO() {
		// TODO Auto-generated method stub
		return new MySql_PedidoDao();
	}



	@Override
	public ComprobanteDao getComprobanteDao() {
		// TODO Auto-generated method stub
		return new MySql_ComprobanteDao();
	}
}
