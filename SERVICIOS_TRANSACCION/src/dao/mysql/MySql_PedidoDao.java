package dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import beans.ComprobanteBean;
import beans.DetalleTransaccionBean;
import beans.EmpresaBean;
import beans.PedidoBean;
import beans.TransaccionBean;
import dao.interfaces.PedidoDao;

import dao.factory.MySqlDAOFactory;

public class MySql_PedidoDao extends MySqlDAOFactory implements PedidoDao {


	@Override
	public String generarNumeroTransaccion() throws Exception {
		// TODO Auto-generated method stub
		
		String num = "";
		
		try{
			Connection con=MySqlDAOFactory.obtenerConexion();
			
			Statement stmt=con.createStatement();
			
			String query = "SELECT NUM FROM transaccion ORDER BY NUM DESC LIMIT 1";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				num = rs.getString("NUM");
			}
			
			
		}catch(Exception e){
			System.out.println("ERROR :"+e.getMessage());
		}
		
		return num;
	}

	
	
	//------------------------------------------------------------------BARBIERI----------------------------------------------------------------------------------------------
	@Override
	public int guardarTransaccion(TransaccionBean transaccion) throws Exception {
		// TODO Auto-generated method stub
		
		int  idGenerado = 0;
		
		try{
			Connection con=MySqlDAOFactory.obtenerConexion();
			Statement stmt=con.createStatement();
			
			String query = "INSERT INTO transaccion (ID_USUARIO,IDE,NUM,EST,FEC_ENT,USU_CREA_REGI,FEC_CREA_REGI,ULT_USU_MOD_REGI,FEC_ULT_MOD_REGI) "
					+ "VALUES ("+transaccion.getId_usuario()+",'"+transaccion.getIde()+"','"+transaccion.getNum()+"','"+transaccion.getEst()+"','"+transaccion.getFec_ent()+"','USER',now(),'USER',now()) ;";
			
			System.out.println("QUERY PARA GUARDAR TRANSACCION ----> "+query);
			
			int filas = stmt.executeUpdate(query);
			
			if(filas == 1){
				
				String query2 = "SELECT ID FROM transaccion ORDER BY ID DESC LIMIT 1";
				
				ResultSet rs = stmt.executeQuery(query2);
				
				while(rs.next()){
					
					idGenerado = rs.getInt("ID");	
					
				}
				
			}
			
		}catch(Exception e){
			
			System.out.println("ERROR :"+e.getMessage());
			
		}
		
		return idGenerado;
	}
	
	@Override
	public boolean guardarPedido(PedidoBean pedido) throws Exception {
		// TODO Auto-generated method stub

		boolean flag = false;
		
		try{
			
			Connection con=MySqlDAOFactory.obtenerConexion();
			Statement stmt=con.createStatement();
			
			
			String query = " INSERT INTO pedido (PED_ID,TIP_ENT,TIPO_PAG,DEP_ENT,PRO_ENT,DIS_ENT,DIR_ENT,REF,TEL_REF_1,TEL_REF_2,EST_ENT,CUO,CAR_ENT) "
					+ " VALUES("+pedido.getId()+",'"+pedido.getTipoEntrega()+"','"+pedido.getTipoPago()+"','"+pedido.getDepartamento()+"','"+pedido.getProvincia()+"','"+pedido.getDistrto().replace("Ã", "Ñ")+"','"+pedido.getDireccion()+"','"+pedido.getReferencia()+"','"+pedido.getTelefono1()+"','"+pedido.getTelefono2()+"','P',"+pedido.getCuota()+","+pedido.getCargo_entrega()+");";
			
			System.out.println("QUERY PARA GUARDAR PEDIDO ----> "+query);
			
			int filas = stmt.executeUpdate(query);
			
			if(filas == 1){
				flag = true;
			}
			
		}catch(Exception e){
			System.out.println("ERROR :"+e.getMessage());
		}
		
		
		return flag;
	}

	@Override
	public boolean guardarDetallePedido(DetalleTransaccionBean detalle) throws Exception {
		// TODO Auto-generated method stub
		
		boolean flag  = false;
		
		try{
			
			Connection con=MySqlDAOFactory.obtenerConexion();
			Statement stmt=con.createStatement();
			
			String query = " INSERT INTO detalle_transaccion (VEN_ID,PRO_ID,CAN,IMP) "
					+ " VALUES ("+detalle.getVentaId()+","+detalle.getProductoId()+","+detalle.getCantidad()+","+detalle.getImporte()+")";
			
			System.out.println("QUERY PARA GUARDAR DETALLE ----> "+query);
			
			int filas = stmt.executeUpdate(query);
			
			if(filas == 1){
				flag = true;
			}
			
		}catch(Exception e){
			
			System.out.println("ERROR :"+e.getMessage());
			
		}
		
		return flag;
	}

	
	@Override
	public boolean guardarComprobante(ComprobanteBean comprobante) throws Exception {
		// TODO Auto-generated method stub
		
		boolean flag = false;
		
		try{
			
			Connection con=MySqlDAOFactory.obtenerConexion();
			Statement stmt=con.createStatement();
			
			String query = " INSERT INTO comprobante_pago (VEN_ID ,TIPO ,RUC ,RAZ_SOC ,NUM_COM ,IGV ,FEC_EMI ,FEC_CAN ) "
					+ " VALUES ( "+comprobante.getVen_id()+" , '"+comprobante.getTipo()+"','"+comprobante.getRuc()+"','"+comprobante.getRaz_soc()+"','"+comprobante.getNum_com()+"',"+comprobante.getIgv()+","+comprobante.getFec_emi()+","+comprobante.getFec_can()+" )";
			
			System.out.println("QUERY PARA GUARDAR COMPROBANTE ---> "+query);
			
			int filas = stmt.executeUpdate(query);
			
			if(filas == 1){
				System.out.println("SE GUARDO CON EXITO :DDDDD");
				flag = true;
			}
			
			
		}catch(Exception e){
			
			System.out.println("ERROR :"+e.getMessage());
			
		}
		
		return flag;
	}

	@Override
	public String obtenerUltimoNumeroComprobantexTipo(String tipo) throws Exception {
		// TODO Auto-generated method stub
		
		String num_com = "";
		
		try{
			Connection con=MySqlDAOFactory.obtenerConexion();
			Statement stmt=con.createStatement();
			String query = "";
			//String query = "SELECT NUM_COM FROM comprobante_pago WHERE TIPO = '"+tipo+"' ORDER BY NUM_COM DESC LIMIT 1";
			if(tipo.equalsIgnoreCase("boleta")){
				 query = "SELECT NUM_COM FROM comprobante_pago WHERE TIPO = '"+tipo+"' AND substr(NUM_COM,1,2) = 'BV' ORDER BY NUM_COM DESC LIMIT 1";
			}else{
				 query = "SELECT NUM_COM FROM comprobante_pago WHERE TIPO = '"+tipo+"' AND substr(NUM_COM,1,2) = 'FV' ORDER BY NUM_COM DESC LIMIT 1";
			}
			
			System.out.println("QUERY PARA OBTENER EL ULTIMO NUMERO DE COMPROBANTE ---->"+query);
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.isBeforeFirst()){
				
				if(rs.next()){
					
					num_com = rs.getString("NUM_COM");
					
				}
				
			}
			
			
		}catch(Exception e){
			System.out.println("ERROR :"+e.getMessage());
		}
		
		
		return num_com;
	}

	@Override
	public EmpresaBean buscarEmpresaXUsuario(int id) throws Exception {
		// TODO Auto-generated method stub
		
		EmpresaBean empresa = new EmpresaBean();
		
		try{
			Connection con=MySqlDAOFactory.obtenerConexion();
			Statement stmt=con.createStatement();
			
			String query = "SELECT * FROM empresa WHERE EMP_ID = "+ id;
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				
				empresa.setRuc(rs.getString("RUC"));
				empresa.setRazonSocial(rs.getString("RAZ_SOC"));
				
			}
			
			
		}catch(Exception e){
			System.out.println("ERROR :"+e.getMessage());
		}
		
		
		return empresa;
	}

}
