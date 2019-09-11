/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import DictionaryServer.Exceptions.CloseFailureException;
import DictionaryServer.Exceptions.ParseRequestException;

import java.util.*;

import java.io.*;

public class DictionaryServer implements Runnable{

    private DictionaryServerActions actions;
    private Socket socket;

    public DictionaryServer(DictionaryServerActions actions,Socket socket) {
        this.actions = actions;
        this.socket = socket;

    }

    public void run() {
           try{
               initiate();
           }catch(IOException e){
               e.printStackTrace();
           } catch (CloseFailureException e) {
        	  e.printStackTrace();
		} catch (ParseRequestException e) {
			e.printStackTrace();
		}

    }

    public void initiate() throws IOException, CloseFailureException, ParseRequestException{
        try {
            InputStream is = this.socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(os, "UTF8");
            String request = "";
            while((request = br.readLine()) != null) {
            	
            	System.out.println("Server receives "+ request);
            	
                JSONParser parser = new JSONParser();
                JSONObject requestJson = (JSONObject) parser.parse(request);
                JSONObject result = getResult(requestJson);
                ow.write(result.toString()+'\n');
                ow.flush();
            }
            br.close();
            ow.close();
            os.close();
            ir.close();
            is.close();
        } catch(SocketException e){        
        
        }
        catch(Exception e){
        	throw new ParseRequestException("Errors happen when parsing the request json");

        }finally{
            //close socket
            try {
                if(this.socket!=null) {
                    this.socket.close();
                }

            } catch (IOException e) {
                throw new CloseFailureException("Cannot close the socket");
            }
        }
    }


    public JSONObject getResult(JSONObject requestJson) {
        JSONObject result = null;
        String task = requestJson.get("Task").toString();
        if(task.equals("Query") ){
            result = this.actions.query(requestJson.get("Key").toString());
        }else if(task.equals("Add")) {
            String[] defs = requestJson.get("Value").toString().split(",");
            List<String> definitions = Arrays.asList(defs);
            try {
				result = this.actions.add(requestJson.get("Key").toString(), definitions);
			} catch (ParseRequestException e) {
		
				e.printStackTrace();
			}
        }else if(task.equals("Remove")) {
            result = this.actions.remove(requestJson.get("Key").toString());
        }
        return result;
    }
 


    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DictionaryServerActions getDictionaryService() {
        return this.actions;
    }

    public void setDictionaryServerActions(DictionaryServerActions actions) {
        this.actions = actions;
    }


}