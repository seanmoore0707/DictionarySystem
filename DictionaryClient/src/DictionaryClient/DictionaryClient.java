/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryClient;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import DictionaryClient.Exceptions.AbnormalCommunicationException;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DictionaryClient {
    private Socket socket;
    private OutputStreamWriter osw;
    private BufferedReader br;
    private long time;

    public DictionaryClient() {
    }

    public void initiate(String address,int port) throws ConnectException, UnknownHostException, IOException{
        try{
        	System.out.println("The client is running !");
        	System.out.println(address);
        	System.out.println(port);
            this.socket = new Socket(address,port);
            this.time = System.currentTimeMillis();
        }catch(ConnectException e) {
            throw new ConnectException("Connect failed or timeout, check address and port.");
        }catch (UnknownHostException e){
            throw new UnknownHostException("Unknow host, check the host address.");
        }catch(IOException e){
            e.printStackTrace();
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("The argument of the method is invalid");
        }
        try {
            OutputStream os = socket.getOutputStream();
            this.osw = new OutputStreamWriter(os, "UTF8");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            this.br = new BufferedReader(isr);
        }catch(IOException e) {
        	throw new IOException("Something wrong happens in InputStream or OutputStream");
        }
    }

    public JSONObject request(JSONObject request, int threshod) throws AbnormalCommunicationException, IOException{
        JSONObject response = new JSONObject();
        try {

            if((System.currentTimeMillis() - this.time)<= threshod) {
                this.time = System.currentTimeMillis();
                osw.write(request.toString()+'\n');
                osw.flush();
                String content =br.readLine();
                JSONParser parser = new JSONParser();
                response = (JSONObject) parser.parse(content);
            }else {
                response.put("Timeout", String.valueOf(System.currentTimeMillis() - this.time));
                throw new AbnormalCommunicationException("Request timeout, check the connection");
            }

        }catch(SocketException e){
            response.put("Status","Failure: Connection is lost or Server is down");
            response.put("Action","Terminate the client and restart later");
        }catch (IOException e) {
            response.put("Status","Failure: IO Exception, check input or output streams");
            response.put("Action","Terminate the client and restart later");

        }catch(ParseException e) {
            response.put("Status","Failure: ParseException");
            response.put("Action","Terminate the client and restart later");
        }finally {
            return response;
        }

    }
    
    public void disconnect() throws IOException {
    	try{
    	this.osw.close();
    	this.br.close();
    	this.socket.close();
    	}catch(IOException e) {
    		throw new IOException("Cannot disconnect the client properly");
    	}
    }


}