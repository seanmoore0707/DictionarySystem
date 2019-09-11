/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer;
import java.util.concurrent.LinkedBlockingQueue;

import DictionaryServer.Exceptions.CloseFailureException;
import DictionaryServer.Exceptions.ClientLostException;

import java.util.*;
import java.io.IOException;
import java.lang.InterruptedException;


// Implement my own thread pool
// The thread pool maintains multiple threads waiting for tasks to be allocated
// for concurrent execution.
public class WorkerPool {
    private int num;
    private Worker[] threads;
    private LinkedBlockingQueue queue; // use LinkedBlockingQue to add and delete a thread
    private ArrayList<DictionaryServer> ds = new ArrayList<DictionaryServer>();
    public WorkerPool(int nThreads) {
        num = nThreads;
        queue = new LinkedBlockingQueue();
        threads = new Worker[num];

        for (int i = 0; i < num; i++) {
            threads[i] = new Worker();
            Thread t = new Thread(threads[i]);
            t.start();
        }

    }

    public void execute(Runnable task) {
    	ds.add((DictionaryServer) task);
        synchronized (queue) {
            queue.add(task); // add a thread into the queue
            queue.notify();  // wake up a queue
        }
    }

    private class Worker implements Runnable {

        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait(); // put queue into wait
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // it thread is wake up, execute here
                    task = (Runnable) queue.poll(); // get task
                }
                try {
                    // Here, the task is runnable, however, it is not declared as a thread explicitly.
                    // Therefore we need to use run() to start the task thread
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void terminate() throws CloseFailureException {
    	if(this.ds.size()>0) {
    	for (DictionaryServer server: ds) {
    		if(ds!=null) {
    	try {
			server.getSocket().close();
		} catch (IOException e) {
			throw new CloseFailureException("Cannot close pool!");
		}
    	}
    		
    	}}
    }
}

