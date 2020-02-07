package serpis.ad.vistas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import serpis.ad.Conexion;
import serpis.ad.HibernateMain;
import serpis.ad.DAO.ArticuloDAO;
import serpis.ad.controladores.TiendaController;
import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Cliente;

public class TiendaGUI {



	static Conexion con;
	static JFrame frame;
	static JPanel container;
	static JPanel artPanel;
	static JPanel navPanel;
	static TiendaController controller;
	
	// ELEMENTOS PANEL LOGIN
	static JPanel panelLogin;
	static JButton botonLogin;
	
	// ELEMENTOS PANEL USUARIO
	static JPanel panelUsuario;
	static JLabel labelCliente;
	
	// CARD LAYOUT USUARIO
	static JPanel userCards;
	private static final String USUARIO_TITLE = "Usuario";
	private static final String LOGIN_TITLE = "Login";
	
	// CARD LAYOUT PANELES
	static JPanel panelesCards;
	private static final String ART_TITLE = "Artículos";
	private static final String PEDIDOS_TITLE = "Pedidos";
	
	static //PANEL PEDIDOS
	PedidosPanel pedidosPanel;
	
	public TiendaGUI(Conexion con) {
		this.con = con;
		controller = new TiendaController(this);

	}

	public static void initComponents() {
		con.getEntityManager().getTransaction().begin();
		// Método que inicializa y muestra los elementos de la interfaz

		// Crea el marco, configura tamaño y posición
		frame = new JFrame("Tienda ropa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setResizable(false); // El usuario no podrá cambiar de tamaño el frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				con.closeConnection();
				System.exit(0);
			}
		});

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

		// Crea un panel para articulos
		artPanel = new JPanel();
		artPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		artPanel.setLayout(new GridLayout(0, 4));
		// Genera elementos
		List<Articulo> articulos = ArticuloDAO.getAll();
		for (Articulo articulo : articulos) {
			try {
				JPanel itemPanel = new JPanel(new BorderLayout());
				BufferedImage image = getImageFromBytes(articulo.getImagen());
				ImageIcon imgIcon = scaleImage(new ImageIcon(image), 255, 255);
				itemPanel.add(new JLabel(imgIcon), BorderLayout.CENTER);

				JButton buttonComprar = new JButton("Añadir al pedido");
				buttonComprar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null,
								"Para poder comprar tienes que iniciar sesión primero. " + articulo.getNombre());
					}
				});
				itemPanel.setBorder(new TitledBorder(articulo.getNombre()));
				itemPanel.add(buttonComprar, BorderLayout.SOUTH);
				artPanel.add(itemPanel);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JScrollPane scrollPane = new JScrollPane(artPanel);
		scrollPane.setViewportView(artPanel);
		
		pedidosPanel = new PedidosPanel();
		panelesCards = new JPanel(new CardLayout());
		panelesCards.add(scrollPane, ART_TITLE);
        panelesCards.add(pedidosPanel, PEDIDOS_TITLE);
        changePanelesCard(ART_TITLE);
		
		
		

		// Crea un panel para toda la interfaz, con BorderLayout y un margen
		navPanel = new JPanel();
		navPanel.setLayout(new BorderLayout());
		navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Elementos navPanel
		JButton buttonLogo = new JButton("Tienda de ropa");
		buttonLogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanelesCard(ART_TITLE);
			}
		});

		panelLogin = crearPanelLogin();
		panelUsuario = crearPanelUsuario();
		
		userCards = new JPanel(new CardLayout());
		userCards.add(panelLogin, LOGIN_TITLE);
        userCards.add(panelUsuario, USUARIO_TITLE);
        changeUserCard(LOGIN_TITLE);
		
		
		navPanel.add(buttonLogo, BorderLayout.WEST);
		navPanel.add(userCards, BorderLayout.EAST);

		// Agrega componentes al marco
		frame.getContentPane().add(BorderLayout.NORTH, navPanel);
		frame.getContentPane().add(BorderLayout.CENTER, panelesCards);
		frame.setVisible(true);

		con.getEntityManager().getTransaction().commit();

	}

	private static BufferedImage getImageFromBytes(byte[] imageBytes) throws IOException {
		InputStream in = new ByteArrayInputStream(imageBytes);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		return bImageFromConvert;
	}

	public static ImageIcon scaleImage(ImageIcon icon, int w, int h) {
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > w) {
			nw = w;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if (nh > h) {
			nh = h;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
	}

	public static JPanel getPanelLogin() {
		return panelLogin;
	}

	public static void setPanelLogin(JPanel panelLogin) {
		TiendaGUI.panelLogin = panelLogin;
	}
	
	public static JPanel crearPanelLogin() {
		// Creando el panel en la parte inferior y agregando componentes
		JPanel panel = new JPanel(); // el panel no está visible en la salida
		JLabel etiqueta = new JLabel("Introduce tu ID de cliente");
		JTextField tf = new JTextField(10); // acepta hasta 10 caracteres
		JButton enviar = new JButton("Login");
		enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Long id = Long.parseLong(tf.getText());
					TiendaController.login(id);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "ERROR. Introduce un id de cliente válido");
				}

			}
		});
		

		panel.add(etiqueta); // Componentes agregados usando Flow Layout
		panel.add(tf);
		panel.add(enviar);
		return panel;

	}
	
	public static JPanel crearPanelUsuario() {
		// Creando el panel en la parte inferior y agregando componentes
		JPanel panel = new JPanel(); // el panel no está visible en la salida
		labelCliente = new JLabel("Bienvenid@, ");

		JButton botonCarrito = new JButton("Carrito");
		botonCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		JButton botonPedidos = new JButton("Mis pedidos");
		botonPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TiendaController.showPedidos();
			}
		});
		
		JButton botonPrefs = new JButton("Configuración");
		botonPrefs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		JButton botonLogout = new JButton("Logout");
		botonLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TiendaController.logout();
			}
		});
		
		panel.add(labelCliente); // Componentes agregados usando Flow Layout
		panel.add(botonCarrito);
		panel.add(botonPedidos);
		panel.add(botonPrefs);
		panel.add(botonLogout);
		
		return panel;
	}
	
	// CONTROL DE CARD LAYOUTS
    public static void changeUserCard(String card) {
        CardLayout cl = (CardLayout)(userCards.getLayout());
        cl.show(userCards, card);
    }
    
    public static void changePanelesCard(String card) {
        CardLayout cl = (CardLayout)(panelesCards.getLayout());
        cl.show(panelesCards, card);
    }
    
    
	
	//GETTERS Y SETTERS
	public static JLabel getLabelCliente() {
		return labelCliente;
	}

	public static void setLabelCliente(JLabel labelCliente) {
		TiendaGUI.labelCliente = labelCliente;
	}

	public static String getUsuarioTitle() {
		return USUARIO_TITLE;
	}


	public static String getLoginTitle() {
		// TODO Auto-generated method stub
		return LOGIN_TITLE;
	}

	public static String getArtTitle() {
		return ART_TITLE;
	}

	public static String getPedidosTitle() {
		return PEDIDOS_TITLE;
	}

	public static PedidosPanel getPedidosPanel() {
		return pedidosPanel;
	}

	public static void setPedidosPanel(PedidosPanel pedidosPanel) {
		TiendaGUI.pedidosPanel = pedidosPanel;
	}
	
	

}
