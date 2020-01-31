package serpis.ad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import serpis.ad.DAO.ArticuloDAO;
import serpis.ad.DAO.CategoriaDAO;
import serpis.ad.DAO.ClienteDAO;
import serpis.ad.DAO.PedidoDAO;
import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Categoria;
import serpis.ad.modelos.Cliente;
import serpis.ad.modelos.Pedido;
import serpis.ad.modelos.PedidoLinea;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateMain {
	static JFrame frame;
	static JPanel artPanel;
	static JPanel navPanel;

	public static void main(String[] args) {

		Conexion con = Conexion.getInstance();
//		Categoria categoria = new Categoria();
//		categoria.setNombre("cat " + LocalDateTime.now());
//		
//		
		CategoriaDAO.setEntityManager(con.getEntityManager());
		ArticuloDAO.setEntityManager(con.getEntityManager());
//		ClienteDAO.setEntityManager(con.getEntityManager());
//		PedidoDAO.setEntityManager(con.getEntityManager());
//		
//		con.getEntityManager().getTransaction().begin();
//		CategoriaDAO.insert(categoria);
//
//		List<Categoria> categorias = CategoriaDAO.getAll();
//		show(categorias);
//		
		System.out.println("********************************************");



		System.out.println("********************************************");
//
//		List<Cliente> clientes = ClienteDAO.getAll();
//		showClientes(clientes);
//
//		PedidoDAO.insert();
//		List<Pedido> pedidos = PedidoDAO.getAll();
//		showPedidos(pedidos);
		
		initComponents();
		con.getEntityManager().getTransaction().commit();
		con.getEntityManager().close();

		con.getEntityManagerFactory().close();

	}
	
	public static void initComponents() {
    	// Método que inicializa y muestra los elementos de la interfaz
    	
        // Crea el marco, configura tamaño y posición
        frame = new JFrame("Tienda ropa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setResizable(false); // El usuario no podrá cambiar de tamaño el frame
        
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);

	    // Crea un panel para toda la interfaz, con BorderLayout y un margen
        artPanel = new JPanel();
        artPanel.setLayout(new GridLayout(0,3));
        artPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Genera elementos
		List<Articulo> articulos = ArticuloDAO.getAll();
		for (Articulo articulo : articulos) {
			try {
				BufferedImage image = getImageFromBytes(articulo.getImagen());
				ImageIcon imgIcon = scaleImage(new ImageIcon(image), 255, 255);
				artPanel.add(new JLabel(imgIcon));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
	    // Crea un panel para toda la interfaz, con BorderLayout y un margen
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Elementos navPanel
        JLabel labelLogo = new JLabel("Tienda de ropa");
        JButton loginButton = new JButton("Iniciar sesión");
        navPanel.add(labelLogo, BorderLayout.WEST);
        navPanel.add(loginButton, BorderLayout.EAST);

        
        // Agrega componentes al marco   
        frame.getContentPane().add(BorderLayout.NORTH, navPanel);
        frame.getContentPane().add(BorderLayout.CENTER, artPanel);
        frame.setVisible(true);
        
    }
	
	   public static ImageIcon scaleImage(ImageIcon icon, int w, int h)
	    {
	        int nw = icon.getIconWidth();
	        int nh = icon.getIconHeight();

	        if(icon.getIconWidth() > w)
	        {
	          nw = w;
	          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
	        }

	        if(nh > h)
	        {
	          nh = h;
	          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
	        }

	        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
	    }

	private static void show(List<Categoria> categorias) {
		for (Categoria categoria : categorias)
			System.out.printf("%3d %s %n", categoria.getId(), categoria.getNombre());
	}

	private static void showArticulo(List<Articulo> articulos) {
		for (Articulo articulo : articulos)
			System.out.printf("%3d %s %n", articulo.getId(), articulo.getNombre());
	}

	private static void showClientes(List<Cliente> clientes) {
		for (Cliente cliente : clientes) {
			System.out.printf("%3d %s %n", cliente.getId(), cliente.getNombre());
		}
	}
	

	private static void showPedidos(List<Pedido> pedidos) {
		for (Pedido pedido : pedidos)
			System.out.printf("%3d %s %s %s %n", pedido.getId(), pedido.getFecha(), pedido.getCliente().getNombre(), pedido.getImporte());		
}
	
	private static BufferedImage getImageFromBytes(byte[] imageBytes) throws IOException {
		InputStream in = new ByteArrayInputStream(imageBytes);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		return bImageFromConvert;
	}
	
	/*
	 * private static void showClientesPedidos(List<Cliente> clientes, List<Pedido>
	 * pedido, List<PedidoLinea> lineaspedido, List<Articulo> articulos) { Pedido
	 * pedidoActual = new Pedido(); long idPedidoActual = -1; double total = 0;
	 * String texto = ""; for (int i = 0; i < lineaspedido.size(); i++) {
	 * System.out.println(lineaspedido.get(i).getId()); if
	 * (lineaspedido.get(i).getIdPedido() == idPedidoActual) {
	 * 
	 * for (int j = 0; j < articulos.size(); j++) { if (articulos.get(j).getId() ==
	 * lineaspedido.get(i).getIdArticulo()) { texto += "\n\t - "
	 * +lineaspedido.get(i).getUnidades() + articulos.get(j).getNombre() +
	 * " | Precio: " + lineaspedido.get(i).getPrecio() + " | Importe: " +
	 * lineaspedido.get(i).getImporte(); total += lineaspedido.get(i).getImporte();
	 * }
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * if (i != 0) { texto += "\n TOTAL: " + total + "\n*********************\n";
	 * System.out.println(texto); }
	 * 
	 * total=0;
	 * 
	 * for (int j=0; j < pedido.size(); j++) { if (lineaspedido.get(i).getIdPedido()
	 * == pedido.get(j).getId()) { pedidoActual = pedido.get(j); idPedidoActual =
	 * pedidoActual.getId(); } }
	 * 
	 * texto = "PEDIDO #" + pedidoActual.getId() + "\n Fecha: " +
	 * pedidoActual.getFecha() + "\n Cliente: " + pedidoActual.getIdCliente(); for
	 * (int j = 0; j < clientes.size(); j++) { if (clientes.get(j).getId() ==
	 * pedidoActual.getIdCliente()) { texto += clientes.get(j).getNombre() + " "; }
	 * }
	 * 
	 * texto += "\n Artículos del pedido:";
	 * 
	 * for (int j = 0; j < articulos.size(); j++) { if (articulos.get(j).getId() ==
	 * lineaspedido.get(i).getIdArticulo()) { texto += "\n\t - "
	 * +lineaspedido.get(i).getUnidades() + articulos.get(j).getNombre() +
	 * " | Precio: " + lineaspedido.get(i).getPrecio() + " | Importe: " +
	 * lineaspedido.get(i).getImporte(); total += lineaspedido.get(i).getImporte();
	 * }
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * }
	 */
}
