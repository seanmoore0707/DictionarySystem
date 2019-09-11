/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer;
import org.json.simple.JSONObject;

import DictionaryServer.Exceptions.ParseRequestException;

import java.util.*;
public interface DictionaryActions {
	public JSONObject query(String input) throws ParseRequestException;
	public JSONObject remove(String input);
	public JSONObject add(String key,List<String> meaning) throws ParseRequestException;
}
