package serpis.ad.vistas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import serpis.ad.controladores.TiendaController;
import serpis.ad.modelos.Pedido;
import serpis.ad.modelos.PedidoLinea;

public class CarritoPanel extends JPanel {

	DefaultTableModel model;
	JTable tabla;

	public CarritoPanel() {

		setLayout(new BorderLayout(10, 10));

		JLabel labelCarrito = new JLabel("Carrito de compra");
		add(labelCarrito, BorderLayout.NORTH);

		model = new DefaultTableModel();
		tabla = new JTable(model);

		tabla.setFillsViewportHeight(true);
		model.addColumn("Artículo");
		model.addColumn("Precio");
		model.addColumn("Unidades");
		model.addColumn("Importe");
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		// Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);
		
		JButton botonConfirmar = new JButton("Confirmar pedido");
		botonConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TiendaController.confirmarPedido();
					JOptionPane.showMessageDialog(null,
							"Pedido realizado con éxito. ¡Gracias por tu compra!");
				}catch (Exception exc) {
					JOptionPane.showMessageDialog(null,
							"Ha habido un error con tu pedido.");
				}
			}
		});
		
		add(botonConfirmar, BorderLayout.SOUTH);
	}
	
	public void setTableData(Pedido pedido) {
		
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = 0; i < rowCount; i++) {
		    model.removeRow(i);
		}
		
		for (PedidoLinea linea: pedido.getPedidoLineas()) {
			String[] dato = new String[] {
					linea.getArticulo().getNombre(), linea.getPrecio() + " €", linea.getUnidades().toString(), linea.getImporte() + " €"
			};
			model.addRow(dato);
		}
	}
}
