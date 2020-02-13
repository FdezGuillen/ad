package serpis.ad.vistas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import serpis.ad.controladores.TiendaController;

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private int pushedRow;
    
    
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        
        pushedRow = row;
        label = (value == null) ? "" : value.toString();
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
//        	int res = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este pedido?", 
//        			"Eliminar pedido",
//        		      JOptionPane.YES_NO_OPTION,
//        		      JOptionPane.PLAIN_MESSAGE);
//        		      if(res == 0) {
//        		    	  
//        		      PedidosPanel.removeRow(pushedRow);
//       		    	  TiendaController.eliminarPedido(pushedRow);
//        		         
//        		      } 
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}