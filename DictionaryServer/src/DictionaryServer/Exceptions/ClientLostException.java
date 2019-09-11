/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer.Exceptions;
@SuppressWarnings("serial")
public class ClientLostException extends Exception{
    public ClientLostException() {
    }
    public ClientLostException(String message) {
        super(message);
    }
}