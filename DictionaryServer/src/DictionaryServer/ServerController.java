/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import DictionaryServer.Exceptions.CloseFailureException;
import DictionaryServer.Exceptions.ParseRequestException;
import DictionaryServer.Exceptions.ClientLostException;


public class ServerController implements Runnable {
    private DictionaryServerActions actions;
    private ServerSocket serverSocket;
    private WorkerPool pool;
    private Boolean running;


    public ServerController(ServerSocket socket, WorkerPool pool, String path) throws FileNotFoundException {
    	try {
			this.actions = new DictionaryServerActions(path);
		} catch (ParseRequestException e) {
			e.printStackTrace();
		}
        this.serverSocket = socket;
        this.pool = pool;
        this.running = true;
    }

    public void run() {
        try {
        	System.out.println("The server is running");
            initiate();
        } catch (ClientLostException e) {
            e.printStackTrace();
        }
    }

    public void initiate() throws ClientLostException {
        while(this.running) {
            try {
                Socket client = serverSocket.accept();
                DictionaryServer server = new DictionaryServer(actions,client);
                pool.execute((Runnable) server);
            }catch(SocketException s) {
            	
            }catch(IOException e) {
            	e.printStackTrace();
            	throw new ClientLostException();
            
            }
        }
    }
    public void terminate() throws ClientLostException, CloseFailureException {
    	this.running = false;
    	try {
    		if(pool!=null) {
    		this.pool.terminate();
    		}
			this.serverSocket.close();
		} catch (IOException e) {
			throw new ClientLostException("Cannot close server controller");
		}catch (CloseFailureException e) {
			throw new CloseFailureException("Socket cannot be closed");
		}
    	
    }
    public ServerSocket getServerSocket() {
    	return this.serverSocket;
    }
    
    public void setRunning(Boolean b) {
    	this.running = b;
    }



}
