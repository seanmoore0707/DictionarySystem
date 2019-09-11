/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryClient.Exceptions;
@SuppressWarnings("serial")
public class ParseRequestException extends Exception {
	 public ParseRequestException () {

	    }
	        public ParseRequestException(String message) {
	            super(message);
	        }
}
