/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryClient.Exceptions;

@SuppressWarnings("serial")
public class AbnormalCommunicationException extends Exception {

    public AbnormalCommunicationException () {

    }
        public AbnormalCommunicationException (String message) {
            super(message);
        }
    }

