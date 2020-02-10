package serpis.ad.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import serpis.ad.Conexion;
import serpis.ad.DAO.ClienteDAO;
import serpis.ad.DAO.PedidoDAO;
import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Cliente;
import serpis.ad.modelos.Pedido;
import serpis.ad.modelos.PedidoLinea;
import serpis.ad.vistas.PedidosPanel;
import serpis.ad.vistas.TiendaGUI;

public class TiendaController {

	static TiendaGUI tiendaGUI;
	
	static Cliente clienteActual;
	
	static List<Pedido> pedidos;
	
	static List<Articulo> carrito;
	
	static Pedido pedidoPendiente;
	
	public TiendaController(TiendaGUI tiendaGUI) {
		this.tiendaGUI = tiendaGUI;
	}
	
	public static void login(Long clientId) {
		try {
			clienteActual = ClienteDAO.getById(clientId);
			TiendaGUI.getLabelCliente().setText("Bienvenid@, " + clienteActual.getNombre());
			String usuarioTitle = TiendaGUI.getUsuarioTitle();
			TiendaGUI.changeUserCard(usuarioTitle);
			carrito = new ArrayList<Articulo>();
			
		}catch(NullPointerException npe) {
			JOptionPane.showMessageDialog(null, "No existe ningún cliente con este ID");
		}

	}

	public static Cliente getClienteActual() {
		return clienteActual;
	}

	public static void setClienteActual(Cliente clienteActual) {
		TiendaController.clienteActual = clienteActual;
	}
	
	public static void logout() {
		String loginTitle = TiendaGUI.getLoginTitle();
		String artTitle = TiendaGUI.getArtTitle();
		TiendaGUI.changeUserCard(loginTitle);
		TiendaGUI.changePanelesCard(artTitle);
		TiendaController.clienteActual = null;
		TiendaController.pedidos = null;
		TiendaController.carrito = null;
	}
	
	public static void showPedidos() {
		
		if (pedidos == null) {
			pedidos = PedidoDAO.getByCliente(clienteActual);
		}
		
		String pedidosTitle = TiendaGUI.getPedidosTitle();
		TiendaGUI.changePanelesCard(pedidosTitle);
		TiendaGUI.getPedidosPanel().setTableData(pedidos);
	}
	
	public static Pedido getPedidoByIndex(int index) {
		return pedidos.get(index);
	}
	
	public static void showCarrito() {
		String carritoTitle = TiendaGUI.getCarritoTitle();
		TiendaGUI.changePanelesCard(carritoTitle);
		TiendaGUI.getCarritoPanel().setTableData(getPedidoPendiente());
		
	}
	public static void addToCarrito(Articulo articulo) {
		carrito.add(articulo);
	}

	public static List<Articulo> getCarrito() {
		return carrito;
	}

	public static void setCarrito(List<Articulo> carrito) {
		TiendaController.carrito = carrito;
	}
	
	public static void generarPedido() {
		Pedido pedido = new Pedido(clienteActual);
		
		List<PedidoLinea> lineas = new ArrayList<PedidoLinea>();
		
		for (Articulo articulo: carrito) {
			
			boolean articuloDuplicado = false;
			for (PedidoLinea linea: lineas) {
				if (linea.getArticulo().getId() == articulo.getId()) {
					linea.setUnidades(linea.getUnidades().add(new BigDecimal(1)));
				}
				if (!articuloDuplicado)
					articuloDuplicado = true;
				break;
			}
			
			if (!articuloDuplicado) {
				PedidoLinea nuevaLinea = new PedidoLinea(pedido);
				nuevaLinea.setArticulo(articulo);
				nuevaLinea.setUnidades(new BigDecimal(1));
				lineas.add(nuevaLinea);
			}
		}
		
		pedidoPendiente = pedido;
		
	}
	
	public static Pedido getPedidoPendiente() {
		if (pedidoPendiente == null)
			generarPedido();
		return pedidoPendiente;
	}


	public static void confirmarPedido() {
		PedidoDAO.insert(pedidoPendiente);
		vaciarCarrito();
	}
	
	public static void vaciarCarrito() {
		pedidoPendiente = null;
		carrito = new ArrayList<Articulo>();
		TiendaGUI.initCarrito();
	}
	
}
