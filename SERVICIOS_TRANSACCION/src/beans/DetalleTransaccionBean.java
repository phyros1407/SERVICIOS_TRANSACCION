package beans;

public class DetalleTransaccionBean {

	private int idDetalleTransaccion;
	private int ventaId;
	private int productoId;
	private int cantidad;
	private double importe;
	
	
	private String fc;
	
	public String getFc() {
		return fc;
	}
	public void setFc(String fc) {
		this.fc = fc;
	}
	public int getIdDetalleTransaccion() {
		return idDetalleTransaccion;
	}
	public void setIdDetalleTransaccion(int idDetalleTransaccion) {
		this.idDetalleTransaccion = idDetalleTransaccion;
	}
	public int getVentaId() {
		return ventaId;
	}
	public void setVentaId(int ventaId) {
		this.ventaId = ventaId;
	}
	public int getProductoId() {
		return productoId;
	}
	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double impuesto) {
		this.importe = impuesto;
	}
	
	
	
	
	
	
}
