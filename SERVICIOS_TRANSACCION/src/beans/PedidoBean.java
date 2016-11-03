package beans;

public class PedidoBean {

	
	private int id;
	private String numPedido;
	private String estado;
	private String fechaEntrega;
	private String fechaCreacion;
	private String direccion;
	private String distrto, provincia, departamento,referencia;
	private String tipoPago;
	private String ProductoId;
	private String productoNombre;
	private double cantidad;
	private String categoriaProducto;
	private int categoriaProductoId;
	private String medidaProducto;
	private double precioUnidad;
	private double pesoUnidad;
	private int dni;
	private String tipoPedido;
	private String nombrePersona;
	private Double impProd;
	private String tipoEntrega;
	private String telefono1,telefono2;
	private int cuota;
	private double cargo_entrega;
	private String correo;
	private String trans;
	
	//AUDITORIA 
	private String usu_crea_regi;
	private String fec_crea_regi;
	private String ult_usu_mod_regi;
	private String fec_ult_mod_regi;
	
	//Factura
	ComprobanteBean comprobanteBean;
	
	
	
	
	
	public ComprobanteBean getComprobanteBean() {
		return comprobanteBean;
	}
	public void setComprobanteBean(ComprobanteBean comprobanteBean) {
		this.comprobanteBean = comprobanteBean;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getTipoEntrega() {
		return tipoEntrega;
	}
	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	public Double getImpProd() {
		return impProd;
	}
	public void setImpProd(Double impProd) {
		this.impProd = impProd;
	}
	public String getProductoId() {
		return ProductoId;
	}
	public void setProductoId(String productoId) {
		ProductoId = productoId;
	}
	public String getProductoNombre() {
		return productoNombre;
	}
	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getCategoriaProducto() {
		return categoriaProducto;
	}
	public void setCategoriaProducto(String categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}
	public int getCategoriaProductoId() {
		return categoriaProductoId;
	}
	public void setCategoriaProductoId(int categoriaProductoId) {
		this.categoriaProductoId = categoriaProductoId;
	}
	public String getMedidaProducto() {
		return medidaProducto;
	}
	public void setMedidaProducto(String medidaProducto) {
		this.medidaProducto = medidaProducto;
	}
	public double getPrecioUnidad() {
		return precioUnidad;
	}
	public void setPrecioUnidad(double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}
	public double getPesoUnidad() {
		return pesoUnidad;
	}
	public void setPesoUnidad(double pesoUnidad) {
		this.pesoUnidad = pesoUnidad;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNumPedido() {
		return numPedido;
	}
	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDistrto() {
		return distrto;
	}
	public void setDistrto(String distrto) {
		this.distrto = distrto;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public int getCuota() {
		return cuota;
	}
	public void setCuota(int cuota) {
		this.cuota = cuota;
	}
	
	
	public double getCargo_entrega() {
		return cargo_entrega;
	}
	public void setCargo_entrega(double cargo_entrega) {
		this.cargo_entrega = cargo_entrega;
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
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTrans() {
		return trans;
	}
	public void setTrans(String trans) {
		this.trans = trans;
	}
	
	
	
	
}
