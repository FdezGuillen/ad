package serpis.ad.vistas;

import java.util.ArrayList;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
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
	static JTable tabla;
	JPanel panelDetalle;
	JScrollPane detallePane;
	JLabel labelId;
	JLabel labelFecha;
	JLabel labelImporte;
	JButton buttonEliminar;
	JTable tablaLineas;
	JScrollPane paneLineas;
	
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
        
		
//		tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
//		ButtonEditor be = new ButtonEditor(new JCheckBox());
//        tabla.getColumn("Acciones").setCellEditor(be);
//        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabla.rowAtPoint(evt.getPoint());
                int col = tabla.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                	setDetailData(tabla.getSelectedRow());
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
        buttonEliminar = new JButton("Eliminar pedido");
        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	removeRow();
            }
        });
        buttonEliminar.setVisible(false);
        
		modelLineas = new DefaultTableModel(); 
		tablaLineas = new JTable(modelLineas); 
        tablaLineas.setFillsViewportHeight(true);

		modelLineas.addColumn("Artículo"); 
		modelLineas.addColumn("Precio");
		modelLineas.addColumn("Unidades"); 
		modelLineas.addColumn("Importe");
        //Create the scroll pane and add the table to it.
        paneLineas = new JScrollPane(tablaLineas);
		paneLineas.setVisible(false);
		panelDetalle.add(labelId);
		panelDetalle.add(labelFecha);
		panelDetalle.add(labelImporte);
		panelDetalle.add(buttonEliminar);
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
		for (int i = rowCount-1; i >= 0; i--) {
		    model.removeRow(i);
		}
		
		for (Pedido pedido: pedidos) {
			String[] dato = new String[] {
					pedido.getId().toString(), pedido.getFecha().toString(), pedido.getImporte().toString(), "Eliminar"
			};
			model.addRow(dato);
		}
		
	}
	
	public void setDetailData(int index) {
		Pedido pedido = TiendaController.getPedidoByIndex(index);
		labelId.setText("Pedido " + pedido.getId());
		labelFecha.setText("Fecha: " + pedido.getFecha());
		labelImporte.setText("Importe total: " + pedido.getImporte());
		buttonEliminar.setVisible(true);
		paneLineas.setVisible(true);
		int rowCount = modelLineas.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount-1; i >= 0; i--) {
		    modelLineas.removeRow(i);
		}
		
		for (PedidoLinea linea: pedido.getPedidoLineas()) {
			String[] dato = new String[] {
					linea.getArticulo().getNombre(), linea.getPrecio() + " €", linea.getUnidades().toString(), linea.getImporte() + " €"
			};
			modelLineas.addRow(dato);
		}
	}
	
	public void emptyDetailData() {
		labelId.setText("");
		labelFecha.setText("");
		labelImporte.setText("");
		buttonEliminar.setVisible(false);

		int rowCount = modelLineas.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount-1; i >= 0; i--) {
		    modelLineas.removeRow(i);
		}
		
		paneLineas.setVisible(false);
		
	}
	
	public static void removeRow() {
		try {
			int index = tabla.getSelectedRow();
        	int res = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este pedido?", 
        			"Eliminar pedido",
        		      JOptionPane.YES_NO_OPTION,
        		      JOptionPane.PLAIN_MESSAGE);
        		      if(res == 0) {
        		    	  
        		    	model.removeRow(index);
       		    	  	TiendaController.eliminarPedido(index);
        		      } 
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
