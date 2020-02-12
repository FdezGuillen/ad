package serpis.ad.vistas;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import serpis.ad.DAO.PedidoDAO;
import serpis.ad.controladores.TiendaController;
import serpis.ad.modelos.Cliente;
import serpis.ad.modelos.Pedido;
import serpis.ad.modelos.PedidoLinea;

public class PedidosPanel extends JPanel{
	
	JLabel labelTitulo;
	static DefaultTableModel model;
	DefaultTableModel modelLineas;
	JTable tabla;
	JPanel panelDetalle;
	JScrollPane detallePane;
	JLabel labelId;
	JLabel labelFecha;
	JLabel labelImporte;
	JTable tablaLineas;
	// constructor
	
	 Object[][] data = new Object[][] {
         {1, "John", 40.0, false },
         {2, "Rambo", 70.0, false },
         {3, "Zorro", 60.0, true },
     };
     
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
		model.addColumn("Acciones");
        
		
		tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
		ButtonEditor be = new ButtonEditor(new JCheckBox());
        tabla.getColumn("Acciones").setCellEditor(be);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabla.rowAtPoint(evt.getPoint());
                int col = tabla.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                	setDetailData(row);
                }
            }
        });
	
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tabla);

        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.WEST);
        
        // Crea un textArea para el chat, dentro de un panel para que se pueda utilizar con un scroll
        panelDetalle = new JPanel();
        panelDetalle.setLayout(new BoxLayout(panelDetalle, BoxLayout.Y_AXIS));
        labelId = new JLabel();
        labelFecha = new JLabel();
        labelImporte = new JLabel();
        
		modelLineas = new DefaultTableModel(); 
		tablaLineas = new JTable(modelLineas); 
        tablaLineas.setFillsViewportHeight(true);
		modelLineas.addColumn("Artículo"); 
		modelLineas.addColumn("Precio");
		modelLineas.addColumn("Unidades"); 
		modelLineas.addColumn("Importe");
        //Create the scroll pane and add the table to it.
        JScrollPane paneLineas = new JScrollPane(tablaLineas);
		
		panelDetalle.add(labelId);
		panelDetalle.add(labelFecha);
		panelDetalle.add(labelImporte);
		panelDetalle.add(paneLineas);
        detallePane = new JScrollPane(panelDetalle);
        add(detallePane, BorderLayout.CENTER);
        
	
	}
	
	public PedidosPanel getPanel() {
		return this;
	}
	
	public void setTableData(List<Pedido> pedidos) {
		
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = 0; i < rowCount; i++) {
		    model.removeRow(i);
		}
		
		for (Pedido pedido: pedidos) {
			String[] dato = new String[] {
					pedido.getId().toString(), pedido.getFecha().toString(), pedido.getImporte().toString(), "Eliminar"
			};
			model.addRow(dato);
		}
		
		model.fireTableDataChanged();
		tabla.repaint();
			

	}
	
	public void setDetailData(int index) {
		Pedido pedido = TiendaController.getPedidoByIndex(index);
		labelId.setText("Pedido " + pedido.getId());
		labelFecha.setText("Fecha: " + pedido.getFecha());
		labelImporte.setText("Importe total: " + pedido.getImporte());
		
		int rowCount = modelLineas.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = 0; i < rowCount; i++) {
		    modelLineas.removeRow(i);
		}
		
		for (PedidoLinea linea: pedido.getPedidoLineas()) {
			String[] dato = new String[] {
					linea.getArticulo().getNombre(), linea.getPrecio() + " €", linea.getUnidades().toString(), linea.getImporte() + " €"
			};
			modelLineas.addRow(dato);
		}
	}
	
	public static void removeRow(int index) {
		model.removeRow(index);
	}

}
