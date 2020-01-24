package serpis.ad.vistas;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginGUI {
	
	public static void main(String[] args) {
        // Creando el Marco        
        JFrame frame = new JFrame("Supermercado");       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        frame.setSize(400, 100);        
     
 
        // Creando el panel en la parte inferior y agregando componentes       
        JPanel panel = new JPanel(); // el panel no está visible en la salida      
        JLabel etiqueta = new JLabel("Introduce tu ID de cliente");       
        JTextField tf = new JTextField(10); // acepta hasta 10 caracteres        
        JButton enviar = new JButton("Login");       
        JButton restablecer = new JButton("Restablecer");       
   
        panel.add(etiqueta); // Componentes agregados usando Flow Layout      
        panel.add(tf);       
        panel.add(enviar);                 
 
        // Agregar componentes al marco.      
        frame.getContentPane().add(BorderLayout.CENTER, panel);             
     
        frame.setVisible(true);   
	}

}
