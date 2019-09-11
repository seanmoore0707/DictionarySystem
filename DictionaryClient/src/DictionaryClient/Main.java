/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;



import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	public JFrame frame;
	private JTextField hostText;
	private JTextField portText;
	private JButton connectButton;
	private JButton disconnectButton;
	String host;
	int port;
	private DictionaryClient client = new DictionaryClient();    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Main() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 490, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane background = new JTextPane();
		background.setFont(new Font("Arial", Font.BOLD, 22));
		background.setSelectedTextColor(UIManager.getColor("Button.select"));
		background.setForeground(UIManager.getColor("Button.highlight"));
		background.setBackground(new Color(0, 0, 0));
		background.setText("                   Simple Dictionary System");
		background.setBounds(6, 21, 478, 31);
		frame.getContentPane().add(background);
		
		JLabel hostLabel = new JLabel("Host Address");
		hostLabel.setForeground(new Color(192, 192, 192));
		hostLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		hostLabel.setBounds(54, 98, 103, 26);
		frame.getContentPane().add(hostLabel);
		
		hostText = new JTextField();
		hostText.setBounds(160, 97, 215, 31);
		frame.getContentPane().add(hostText);
		hostText.setColumns(10);
		
		JLabel portLabel = new JLabel("Port Number");
		portLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		portLabel.setForeground(new Color(192, 192, 192));
		portLabel.setBounds(54, 165, 103, 16);
		frame.getContentPane().add(portLabel);
	
		
		portText = new JTextField();
		portText.setBounds(160, 159, 215, 31);
		frame.getContentPane().add(portText);
		portText.setColumns(10);
		
		
		connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));	
		connectButton.addActionListener(new ConnectActionListener());
		connectButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		connectButton.setBounds(182, 272, 117, 29);
		frame.getContentPane().add(connectButton);
		
		
		 setTitle("Dictionary");
	     setSize(500,400);
	     frame.setVisible(true);
	}

	
	private class ConnectActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == connectButton) {
          	  host = hostText.getText();
          	  if(!portText.getText().equals("")) {
          		try{port = Integer.parseInt(portText.getText());}
        	  	  catch (NumberFormatException | NullPointerException nfe) {
        	  		JOptionPane.showConfirmDialog(null, "Please type in a number", "Please type in a number", JOptionPane.YES_NO_OPTION); 
      		  }
          		}
      	  	  
      	  	if(host.equals("") ) {
          	  	JOptionPane.showConfirmDialog(null, "The host address cannot be empty", "The host address cannot be empty", JOptionPane.YES_NO_OPTION);    
      	  	}else if(!host.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}") && !host.equals("localhost")){
          	  	JOptionPane.showConfirmDialog(null, "Invalid ip address", "Invalid ip address", JOptionPane.YES_NO_OPTION);    
    		}else if( portText.getText().equals("")) {
          	  	JOptionPane.showConfirmDialog(null, "The port number cannot be empty", "The port number cannot be empty", JOptionPane.YES_NO_OPTION);    
      	  	}else if(!portText.getText().matches("^[0-9]+$")){
          	  	JOptionPane.showConfirmDialog(null, "Port number must be number", "Port number must be number", JOptionPane.YES_NO_OPTION);  	  	
      	  	}else if(port<0 || port>65535){
          	  	JOptionPane.showConfirmDialog(null, "Port number out of range", "Port number out of range", JOptionPane.YES_NO_OPTION);       		   
    		}else {
    			
    			        	  	
      	  		try {
					client.initiate(host, port);
	      	  		String msg = "The client is running";
	      	     	JOptionPane.showConfirmDialog(null, msg, msg, JOptionPane.YES_NO_OPTION);		
	      	     	frame.setVisible(false);
	      	     	new Dashboard(client);
	      	  		
				} catch (ConnectException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION);
				} catch (UnknownHostException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION);
				} catch (IOException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION);
				}
            	  	
          	  	
    		}
        }
	}}
	
	
	

}
