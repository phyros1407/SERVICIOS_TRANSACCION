package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BoletaBean;
import beans.ComprobanteBean;
import beans.DetalleTransaccionBean;
import beans.PedidoBean;
import beans.TransaccionBean;
import dao.interfaces.ComprobanteDao;
import dao.interfaces.PedidoDao;
import dao.factory.DAOFactory;
import util.EnviarBoleta;

/**
 * Servlet implementation class servicios
 */
@WebServlet("/servicios")
public class servicios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servicios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();

		try {

			// USUARIO
			int id_usuario = Integer.parseInt(request
					.getParameter("usuario_generar_pedido"));

			// ARRAYS DE LOS PRODUCTOS SELECCIONADOS
			String ids[] = request.getParameterValues("productosIds");
			String cantidades[] = request
					.getParameterValues("productosCantidad");
			String importes[] = request.getParameterValues("productosImporte");

			String tipo_pago = request.getParameter("tipo_pago");
			String tipo_entrega = request.getParameter("tipo_entrega_pedido");
			String fecha = request.getParameter("fecha_generar_pedido");

			String facturacion = request
					.getParameter("facturacion_generar_pedido");
			System.out.println("EL COMPROBNATE ES --->" + facturacion);
			double rec_ent = Double.parseDouble(request
					.getParameter("cargo_entrega_pedido"));

			String departamento = request
					.getParameter("departamento_entrega_pedido");
			String provincia = request.getParameter("provincia_entrega_pedido");
			String distrito = request.getParameter("distrito_entrega_pedido");
			String direccion = request.getParameter("direccion_entrega_pedido");
			String referencia = request
					.getParameter("referencia_entrega_pedido");

			int cuotas = Integer.parseInt(request
					.getParameter("cuotas_entrega_pedido"));
			System.out.println("CUOTAS -----> " + cuotas);

			DAOFactory dao = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
			PedidoDao pedidodao = dao.getPedidoDAO();

			// GENERAR NUEVO CODIGO PARA TRANSACCION
			String codigoAntiguo = pedidodao.generarNumeroTransaccion();
			System.out.println("CODIGO ANTIGUO -->" + codigoAntiguo);
			String premisa = codigoAntiguo.substring(0, 5);
			int numero = Integer.parseInt(codigoAntiguo.substring(6));
			System.out.println("NUMERO A SUMAR --->" + numero);
			int nuevoNumero = numero + 1;
			System.out.println("NUEVO NUMERO --->" + nuevoNumero);
			String cadena = (String.valueOf(nuevoNumero));
			for (int i = 0; i < 11; i++) {

				if (cadena.length() < 12) {

					cadena = "0" + cadena;

				} else {

					break;

				}

			}
			String codigoNuevo = premisa + cadena;
			System.out.println("NUEVO CODIGO DE TRANSACCION --->" + codigoNuevo
					+ "\n Y SU MEDIDA ES ---->" + codigoNuevo.length());

			// GRABAR TRANSACCION
			TransaccionBean transaccion = new TransaccionBean();
			transaccion.setId_usuario(id_usuario);
			transaccion.setIde("P");// IDENTIFICADOR PEDIDO
			transaccion.setNum(codigoNuevo);
			if (tipo_pago.equalsIgnoreCase("CE")) {
				transaccion.setEst("P");// ESTADO PENDIENTE
			} else {
				transaccion.setEst("C");// ESTADO PENDIENTE
			}

			transaccion.setFec_ent(fecha);

			int idGenerado = pedidodao.guardarTransaccion(transaccion);
			if (idGenerado != 0) {

				System.out
						.println("SE GUARDO DATOS GENERALES DE LA TRANSACCION");
				// GRABAR PEDIDO
				PedidoBean pedido = new PedidoBean();
				pedido.setId(idGenerado);
				if (tipo_entrega.equalsIgnoreCase("local")) {

					pedido.setTipoEntrega("RL");

				}
				if (tipo_entrega.equalsIgnoreCase("casa")) {

					pedido.setTipoEntrega("EC");

				}
				pedido.setTipoPago(tipo_pago);
				pedido.setDepartamento(departamento);
				pedido.setProvincia(provincia);
				pedido.setDistrto(distrito);
				pedido.setDireccion(direccion);
				pedido.setReferencia(referencia);

				if (!(request.getParameter("telefono_entrega_pedido"))
						.equalsIgnoreCase("")) {
					pedido.setTelefono1(request
							.getParameter("telefono_entrega_pedido"));
				} else {
					pedido.setTelefono1("NO CONT.");
				}

				if (!(request.getParameter("telefono_entrega_pedido2"))
						.equalsIgnoreCase("")) {
					pedido.setTelefono2(request
							.getParameter("telefono_entrega_pedido2"));
				} else {
					pedido.setTelefono2("NO CONT.");
				}

				pedido.setEstado("P");
				pedido.setCuota(cuotas);
				pedido.setCargo_entrega(rec_ent);

				if (pedidodao.guardarPedido(pedido)) {
					System.out.println("SE GUARDO DATOS GENERALES DEL PEDIDOs");
					// GRABAR DETALLE_TRANSACCION

					double total = 0;
					for (int i = 0; i < ids.length; i++) {

						DetalleTransaccionBean detalle = new DetalleTransaccionBean();
						detalle.setVentaId(idGenerado);
						detalle.setProductoId(Integer.parseInt(ids[i]));
						detalle.setCantidad(Integer.parseInt(cantidades[i]));
						detalle.setImporte(Double.parseDouble(importes[i]));

						total = total + Double.parseDouble(importes[i]);

						if (pedidodao.guardarDetallePedido(detalle)) {
							System.out
									.println("SE GUARDO EL DETALLE NUMERO --->"
											+ i);
						} else {
							System.out
									.println(" NO SE GUARDO EL DETALLE NUMERO --->"
											+ i);
						}
					}

					System.out
							.println("SE GUARDO DATOS DE LOS DETALLES DEL PEDIDOs");

					// GUARDAR COMPROBANTE
					String preSerie = "";

					ComprobanteBean comprobante = new ComprobanteBean();
					comprobante.setVen_id(idGenerado);
					comprobante.setTipo(facturacion.toUpperCase());

					if (facturacion.equalsIgnoreCase("factura")) {
						comprobante.setRuc(request
								.getParameter("ruc_entrega_pedido"));
						comprobante.setRaz_soc(request
								.getParameter("rs_entrega_pedido"));
						preSerie = "FV";
					} else {
						comprobante.setRuc("");
						comprobante.setRaz_soc("");
						preSerie = "BV";
					}
					
					String antiguoNumeroComprobante = pedidodao
							.obtenerUltimoNumeroComprobantexTipo(facturacion
									.toUpperCase());

					if (antiguoNumeroComprobante.trim().equalsIgnoreCase("")) {

						comprobante.setNum_com(preSerie + "00-00000001");

					} else {
						System.out
								.println("ESTO ES LO QUE DEVUELE EN ANTIGUO COMPROBANTE ----> "
										+ antiguoNumeroComprobante);
						String numeroSerie = antiguoNumeroComprobante
								.substring(0, 5);
						System.out.println("NUMERO DE SERIE ANTIGUO -->"
								+ numeroSerie);
						String numeroComprobante = antiguoNumeroComprobante
								.substring(6);
						System.out.println("NUMERO DE COMPROBANTE ANTIGUO --->"
								+ numeroComprobante);

						if (Integer.parseInt(numeroComprobante) == 99999999) {

							String primeraPartSerie = numeroSerie.substring(0,
									2);
							String segundaPatSerie = numeroSerie
									.substring(3, 4);

							int nuevoNumSerie = Integer
									.parseInt(segundaPatSerie) + 1;

							if (String.valueOf(nuevoNumSerie).length() < 2) {

								comprobante.setNum_com(primeraPartSerie + "0"
										+ nuevoNumSerie + "-00000001");

							} else {

								comprobante.setNum_com(primeraPartSerie
										+ nuevoNumSerie + "-00000001");

							}

						} else {

							int nuevoNumComprobante = Integer
									.parseInt(numeroComprobante) + 1;

							String cadena1 = String
									.valueOf(nuevoNumComprobante);
							for (int i = 0; i < 8; i++) {

								if (cadena1.length() < 8) {
									cadena1 = "0" + cadena1;
								} else {
									break;
								}

							}

							comprobante.setNum_com(numeroSerie + cadena1);

						}
					}

					double igvTotal = total / 1.19;

					comprobante.setIgv(Math.round(igvTotal * 100.00) / 100.00);
					comprobante.setFec_emi("now()");

					if (tipo_pago.equalsIgnoreCase("CE")) {
						comprobante.setFec_can("null");
					} else {
						comprobante.setFec_can("now()");
					}

					if (pedidodao.guardarComprobante(comprobante)) {

						System.out.println("SE GUARDO CORRECTAMENTE EL COMPROBANTE");

						ComprobanteDao comdao = dao.getComprobanteDao();
						ArrayList<BoletaBean> boleta = comdao.buscarComprobanteGenerado(codigoNuevo) ;
						
						String correo = comdao.obtenerCorreo(id_usuario);
						EnviarBoleta email = new EnviarBoleta();
						
						String mensaje = email.armarEmail(boleta);
						email.sendEmail(mensaje,correo);
						
						
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Su pedido ha sido Procesado con exito! Su numero de pedido es  : "+ codigoNuevo + "');");
						out.println("location='ServletGenerarPedido';");
						out.println("localStorage.clear();");
						out.println("$('#cantidadProductos').text(localStorage.length);");
						out.println("</script>");

					} else {

						out.println("<script type=\"text/javascript\">");
						out.println("alert('Su pedido no ha sido Procesado con exito! ');");
						out.println("location='ServletGenerarPedido';");
						out.println("$('#cantidadProductos').text(localStorage.length);");
						out.println("</script>");

					}

				} else {

					out.println("<script type=\"text/javascript\">");
					out.println("alert('Su pedido no ha sido Procesado con exito! ');");
					out.println("location='ServletGenerarPedido';");
					out.println("$('#cantidadProductos').text(localStorage.length);");
					out.println("</script>");

				}

			} else {

				out.println("<script type=\"text/javascript\">");
				out.println("alert('Su pedido no ha sido Procesado con exito! ');");
				out.println("location='ServletGenerarPedido';");
				out.println("$('#cantidadProductos').text(localStorage.length);");
				out.println("</script>");

			}

		} catch (Exception e) {

			System.out.println("ERROR ----> " + e.getMessage());
			response.sendRedirect("home.jsp");

		}
		
	
	}


		
}
