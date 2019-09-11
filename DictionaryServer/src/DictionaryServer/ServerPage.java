/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;


import DictionaryServer.Exceptions.ClientLostException;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ServerPage  extends JFrame {
	private JPanel contentPane;
	private JFrame frame;
	private JTextField portText;
	private JButton terminateButton;
	private JLabel poolLabel;
	private JTextField statusText;
	private JTextField poolText;
	private JLabel statusLabel;
	private ServerController server;
	private JLabel filePathLabel;
	private JTextField filePathText;
	private JButton connectButton;
	String filepath;
	int port;
	int poolSize;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerPage window = new ServerPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerPage() {
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
			
		JLabel portLabel = new JLabel("Port Number");
		portLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		portLabel.setForeground(new Color(192, 192, 192));
		portLabel.setBounds(53, 70, 103, 16);
		frame.getContentPane().add(portLabel);
		
		portText = new JTextField();
		portText.setBounds(186, 64, 278, 31);
		frame.getContentPane().add(portText);
		portText.setColumns(10);
				
		statusLabel = new JLabel("Status");
		statusLabel.setForeground(Color.LIGHT_GRAY);
		statusLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		statusLabel.setBounds(53, 221, 103, 16);
		frame.getContentPane().add(statusLabel);
		
		statusText = new JTextField();
		statusText.setColumns(10);
		statusText.setBounds(124, 215, 340, 72);
		frame.getContentPane().add(statusText);
		
		poolLabel = new JLabel("Pool Size");
		poolLabel.setForeground(Color.LIGHT_GRAY);
		poolLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		poolLabel.setBounds(53, 112, 103, 16);
		frame.getContentPane().add(poolLabel);
		
		poolText = new JTextField();
		poolText.setColumns(10);
		poolText.setBounds(186, 112, 278, 31);
		frame.getContentPane().add(poolText);
		
		filePathLabel = new JLabel("File Path");
		filePathLabel.setForeground(Color.LIGHT_GRAY);
		filePathLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		filePathLabel.setBounds(53, 166, 103, 16);
		frame.getContentPane().add(filePathLabel);
		
		filePathText = new JTextField();
		filePathText.setColumns(10);
		filePathText.setBounds(186, 160, 278, 31);
		frame.getContentPane().add(filePathText);
		
		terminateButton = new JButton("Terminate");
		terminateButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		terminateButton.addActionListener(new TerminateActionListener());
		terminateButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		terminateButton.setBounds(285, 299, 117, 29);
		frame.getContentPane().add(terminateButton);
		
		
		connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		connectButton.addActionListener(new ConnectActionListener());
		connectButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		connectButton.setBounds(124, 299, 117, 29);
		frame.getContentPane().add(connectButton);
		
		
	}
	
	private class ConnectActionListener implements ActionListener{
		
    	public void actionPerformed(ActionEvent e) {
    		 		
        	if(e.getSource() == connectButton) {
        		  filepath = filePathText.getText();
        		  if(!portText.getText().equals("")) {
          	  	  try{port = Integer.parseInt(portText.getText());}
          	  	  catch (NumberFormatException | NullPointerException nfe) {
          	  		JOptionPane.showConfirmDialog(null, "Please type in a number", "Please type in a number", JOptionPane.YES_NO_OPTION); 
        		  }}
        		  if(!poolText.getText().equals("")) {
        			  try{  poolSize = Integer.parseInt(poolText.getText());} 
        			  catch (NumberFormatException | NullPointerException nfe) {
                	  		JOptionPane.showConfirmDialog(null, "Please type in a number", "Please type in a number", JOptionPane.YES_NO_OPTION); 
              		  }   
        		  }
          	  	  
          	  	  
          	  	if(filepath.equals("") ) {
              	  	JOptionPane.showConfirmDialog(null, "The file path cannot be empty", "The file path cannot be empty", JOptionPane.YES_NO_OPTION);    
          	  	}else if(!filepath.matches("\\/([A-z0-9-_+]+\\/)*([A-z0-9]+\\.(json))")){
              	  	JOptionPane.showConfirmDialog(null, "Invalid file path or the file is not json", "Invalid file path or the file is not json", JOptionPane.YES_NO_OPTION);    
        		}else if(portText.getText().equals("")) {
              	  	JOptionPane.showConfirmDialog(null, "The port number cannot be empty", "The port number cannot be empty", JOptionPane.YES_NO_OPTION);    
          	  	}else if(!portText.getText().matches("^[0-9]+$")){
              	  	JOptionPane.showConfirmDialog(null, "Port number must be number", "Port number must be number", JOptionPane.YES_NO_OPTION);  	  	
          	  	}else if(port<0 || port>65535){
              	  	JOptionPane.showConfirmDialog(null, "Port number out of range", "Port number out of range", JOptionPane.YES_NO_OPTION);       		   
        		}else if(poolText.getText().equals("")) {
              	  	JOptionPane.showConfirmDialog(null, "The worker pool size cannot be empty", "The worker pool size cannot be empty", JOptionPane.YES_NO_OPTION);    
          	  	}else if(!portText.getText().matches("^[0-9]+$")){
              	  	JOptionPane.showConfirmDialog(null, "Worker pool size must be number", "Worker pool size must be number", JOptionPane.YES_NO_OPTION);  	  	
          	  	}else {
        			
        		try {
        			
        			 WorkerPool pool = new WorkerPool(poolSize);
        		     ServerSocket s = new ServerSocket(port);
        		    
        		     
        		     ServerController sc = new ServerController(s, pool, filepath);
        		     server = sc;
        		   
        		     new Thread(sc).start();
        		     
					JOptionPane.showConfirmDialog(null, "The server is running", "The server is running", JOptionPane.YES_NO_OPTION); 
					statusText.setText("The server is running");
					
				} catch (Exception e1) {
					
					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION);
					statusText.setText(e1.getMessage());
				}
        }
	}
	}}
	
	private class TerminateActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == terminateButton) {
        		
        		try {
        			if (server!=null)
					{
        			server.terminate();
					JOptionPane.showConfirmDialog(null, "The server is closed", "The server is closed", JOptionPane.YES_NO_OPTION); }
        			else {
        				JOptionPane.showConfirmDialog(null, "You don't even have a server :)", "You don't even have a server :)", JOptionPane.YES_NO_OPTION); 
        			}
				} catch (Exception e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION);
				}
        		finally {
					frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
					frame.setVisible(false);
				}
        }
	}
	}
	

	
}
	
