package serpis.ad.vistas;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import serpis.ad.DAO.PedidoDAO;
import serpis.ad.controladores.TiendaController;
import serpis.ad.modelos.Cliente;
import serpis.ad.modelos.Pedido;

public class PedidosPanel extends JPanel{
	
	JLabel labelTitulo;
	DefaultTableModel model;
	JTable tabla;
	// constructor
	public PedidosPanel() {

		setLayout(new BorderLayout(10, 10)); // Configura el tipo de layout de JPanel como BorderLayout

		labelTitulo = new JLabel("Mis pedidos");
		// Añade elementos al JPanel
		add(labelTitulo, BorderLayout.NORTH);
		
		
		
		model = new DefaultTableModel(); 
		tabla = new JTable(model); 
        tabla.setFillsViewportHeight(true);
		model.addColumn("ID"); 
		model.addColumn("Fecha");
		model.addColumn("Importe"); 
	
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tabla);

        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.CENTER);
	
	}
	
	public PedidosPanel getPanel() {
		return this;
	}
	
	public void setTableData(Cliente cliente) {
		
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = 0; i < rowCount; i++) {
		    model.removeRow(i);
		}
		
		List<Pedido> pedidos = PedidoDAO.getByCliente(cliente);
		for (Pedido pedido: pedidos) {
			String[] dato = new String[] {
					pedido.getId().toString(), pedido.getFecha().toString(), pedido.getImporte().toString()
			};
			model.addRow(dato);
		}
	}

}
