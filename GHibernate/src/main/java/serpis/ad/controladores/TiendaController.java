package serpis.ad.controladores;

import javax.swing.JOptionPane;

import serpis.ad.Conexion;
import serpis.ad.DAO.ClienteDAO;
import serpis.ad.modelos.Cliente;
import serpis.ad.vistas.PedidosPanel;
import serpis.ad.vistas.TiendaGUI;

public class TiendaController {

	static TiendaGUI tiendaGUI;
	
	static Cliente clienteActual;
	
	public TiendaController(TiendaGUI tiendaGUI) {
		this.tiendaGUI = tiendaGUI;
	}
	
	public static void login(Long clientId) {
		try {
			clienteActual = ClienteDAO.getById(clientId);
			TiendaGUI.getLabelCliente().setText("Bienvenid@, " + clienteActual.getNombre());
			String usuarioTitle = TiendaGUI.getUsuarioTitle();
			TiendaGUI.changeUserCard(usuarioTitle);
			
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
	}
	
	public static void showPedidos() {
		String pedidosTitle = TiendaGUI.getPedidosTitle();
		TiendaGUI.changePanelesCard(pedidosTitle);
		TiendaGUI.getPedidosPanel().setTableData(clienteActual);
	}
	
}
