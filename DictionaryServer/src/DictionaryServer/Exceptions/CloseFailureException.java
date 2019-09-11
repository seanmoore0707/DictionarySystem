/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer.Exceptions;

@SuppressWarnings("serial")
public class CloseFailureException extends Exception {
	  public CloseFailureException () {

	    }
	        public CloseFailureException(String message) {
	            super(message);
	        }
}
