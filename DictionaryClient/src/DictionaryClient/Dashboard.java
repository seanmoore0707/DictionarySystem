/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryClient;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowEvent;

import org.json.simple.JSONObject;

import DictionaryClient.Exceptions.AbnormalCommunicationException;



public class Dashboard extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField responseText;
	private JTextField definitionText;
	private JTextField wordText;
	private JTextField timeText;
	private JButton addButton;
	private DictionaryClient client;
	String word;
	int time;
	String definitions;
	private JButton queryButton;
	private JButton removeButton;
	private JButton disconnectButton;


	/**
	 * Create the frame.
	 */
	public Dashboard(DictionaryClient client) {
		this.client = client;
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.getContentPane().setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("                   Simple Dictionary System");
		textPane.setSelectedTextColor(UIManager.getColor("Button.select"));
		textPane.setForeground(Color.WHITE);
		textPane.setFont(new Font("Arial", Font.BOLD, 22));
		textPane.setBackground(Color.BLACK);
		textPane.setBounds(6, 21, 478, 31);
		frame.getContentPane().add(textPane);
		
		JLabel wordLabel = new JLabel("Word");
		wordLabel.setForeground(Color.LIGHT_GRAY);
		wordLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		wordLabel.setBounds(45, 64, 103, 26);
		frame.getContentPane().add(wordLabel);
		
		wordText = new JTextField();
		wordText.setColumns(10);
		wordText.setBounds(160, 64, 215, 31);
		frame.getContentPane().add(wordText);
		
		JLabel definitionLabel = new JLabel("Definitions");
		definitionLabel.setForeground(Color.LIGHT_GRAY);
		definitionLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		definitionLabel.setBounds(45, 106, 103, 16);
		frame.getContentPane().add(definitionLabel);
		
		definitionText = new JTextField();
		definitionText.setColumns(10);
		definitionText.setBounds(160, 100, 215, 31);
		frame.getContentPane().add(definitionText);
		
		JLabel timeLabel = new JLabel("Threshold");
		timeLabel.setForeground(Color.LIGHT_GRAY);
		timeLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		timeLabel.setBounds(45, 143, 103, 16);
		frame.getContentPane().add(timeLabel);
		
		timeText = new JTextField();
		timeText.setColumns(10);
		timeText.setBounds(160, 137, 215, 31);
		frame.getContentPane().add(timeText);
		
		JLabel responseLabel = new JLabel("Response");
		responseLabel.setForeground(Color.LIGHT_GRAY);
		responseLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		responseLabel.setBounds(45, 174, 103, 16);
		frame.getContentPane().add(responseLabel);
		
		responseText = new JTextField();
		responseText.setColumns(10);
		responseText.setBounds(45, 193, 417, 106);
		frame.getContentPane().add(responseText);
		
		
		
		addButton = new JButton("Add");
		addButton.addActionListener(new AddActionListener());
		addButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		addButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		addButton.setBounds(152, 311, 95, 29);
		frame.getContentPane().add(addButton);
		
		
		
		queryButton = new JButton("Query");
		queryButton.addActionListener(new QueryActionListener());
		queryButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		queryButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		queryButton.setBounds(45, 311, 95, 29);
		frame.getContentPane().add(queryButton);
		
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveActionListener());
		removeButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		removeButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		removeButton.setBounds(255, 311, 95, 29);
		frame.getContentPane().add(removeButton);
		
		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new DisconnectActionListener());
		disconnectButton.setFont(new Font("Arial Hebrew", Font.PLAIN, 13));
		disconnectButton.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		disconnectButton.setBounds(359, 311, 103, 29);
		frame.getContentPane().add(disconnectButton);
		
		
		frame.setBounds(100, 100, 490, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Dictionary");
		frame.setVisible(true);
	}
	
	private class AddActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == addButton) {
             	  word = wordText.getText();
          	  	  definitions = definitionText.getText();
          	  	if(!timeText.getText().equals("")) {
          	  	  time = Integer.parseInt(timeText.getText());}
          	  	  
          	  	if(word.equals("") ) {
              	  	JOptionPane.showConfirmDialog(null, "The word cannot be empty", "The word cannot be empty", JOptionPane.YES_NO_OPTION);    
          	  	}else if(definitions.equals("")){
              	  	JOptionPane.showConfirmDialog(null, "Definition cannot be empty", "Definition cannot be empty", JOptionPane.YES_NO_OPTION);    
        		}else if(!timeText.getText().matches("^[0-9]+$")){
              	  	JOptionPane.showConfirmDialog(null, "Time thread must be number", "Time thread must be number", JOptionPane.YES_NO_OPTION); 
        		}else if(timeText.getText().equals("")){
              	  	JOptionPane.showConfirmDialog(null, "Time thread cannot be empty", "Time thread cannot be empty", JOptionPane.YES_NO_OPTION); 
              	}else {
        				        	  	
          	  		try {
          	  			JSONObject request = new JSONObject();
          	  			request.put("Task","Add");
          	  			request.put("Key",word);
          	  			request.put("Value",definitions);   					
						String response = client.request(request, time).toString();	
						responseText.setText(response);
    	      	  		
    				} catch (ConnectException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				} catch (UnknownHostException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				} catch (IOException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				}catch (AbnormalCommunicationException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
					}
                	  	              	  	
        		}
        	}
    	}
	}
	
	private class QueryActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == queryButton) {
             	  word = wordText.getText();
				if(!timeText.getText().equals("")) {
          	  	  time = Integer.parseInt(timeText.getText());}
          	  	  
          	  	if(word.equals("") ) {
              	  	JOptionPane.showConfirmDialog(null, "The word cannot be empty", "The word cannot be empty", JOptionPane.YES_NO_OPTION);    
          	  	} else if(!timeText.getText().matches("^[0-9]+$")){
              	  	JOptionPane.showConfirmDialog(null, "Time thread must be number", "Time thread must be number", JOptionPane.YES_NO_OPTION); 
        		}else if(timeText.getText().equals("")){
              	  	JOptionPane.showConfirmDialog(null, "Time thread cannot be empty", "Time thread cannot be empty", JOptionPane.YES_NO_OPTION); 
              	}else {    				        	  	
          	  		try {
          	  			JSONObject request = new JSONObject();
          	  			request.put("Task","Query");
          	  			request.put("Key",word); 					
						String response = client.request(request, time).toString();						
						responseText.setText(response);
    	      	  		
    				} catch (ConnectException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				} catch (UnknownHostException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				} catch (IOException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				}catch (AbnormalCommunicationException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
					}
                	  	              	  	
        		}
        	}
    	}
	}
	
	private class RemoveActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == removeButton) {
             	  word = wordText.getText();
				if(!timeText.getText().equals("")) {
          	  	  time = Integer.parseInt(timeText.getText());}
          	  	  
          	  	if(word.equals("") ) {
              	  	JOptionPane.showConfirmDialog(null, "The word cannot be empty", "The word cannot be empty", JOptionPane.YES_NO_OPTION);    
          	  	} else if(!timeText.getText().matches("^[0-9]+$")){
              	  	JOptionPane.showConfirmDialog(null, "Time thread must be number", "Time thread must be number", JOptionPane.YES_NO_OPTION); 
        		}else if(timeText.getText().equals("")){
              	  	JOptionPane.showConfirmDialog(null, "Time thread cannot be empty", "Time thread cannot be empty", JOptionPane.YES_NO_OPTION); 
              	}else {    				        	  	
          	  		try {
          	  			JSONObject request = new JSONObject();
          	  			request.put("Task","Remove");
          	  			request.put("Key",word); 					
						String response = client.request(request, time).toString();						
						responseText.setText(response);
    	      	  		
    				} catch (ConnectException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				} catch (UnknownHostException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				} catch (IOException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
    				}catch (AbnormalCommunicationException e1) {
    					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION); 
					}
                	  	              	  	
        		}
        	}
    	}
	}
	
	private class DisconnectActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == disconnectButton) {
        		try {
					client.disconnect();
					JOptionPane.showConfirmDialog(null, "The client is closed", "The client is closed", JOptionPane.YES_NO_OPTION); 
				} catch (IOException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.YES_NO_OPTION);
				}finally {
					frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
					frame.setVisible(false);
				}
        }
	}
	}
	

}
