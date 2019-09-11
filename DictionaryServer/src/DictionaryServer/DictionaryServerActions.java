/** Server Application for COMP90015 Project 1
 *  @author Haonan Chen 930614
 */
package DictionaryServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DictionaryServer.Exceptions.ParseRequestException;


public class DictionaryServerActions implements DictionaryActions {
	
	private JSONObject dictionary;
	
	public DictionaryServerActions(String filePath) throws FileNotFoundException, ParseRequestException {

		  this.dictionary = new JSONObject();
	      JSONParser jsonParser = new JSONParser();
	      try {
	        	File dic = new File(filePath);
				FileReader fileReader = new FileReader(dic);
				Object object = jsonParser.parse(fileReader);
	            JSONObject jsonObject = (JSONObject) object;
				dictionary = jsonObject;
	      }catch(FileNotFoundException e) {
				throw new FileNotFoundException("Please give valid file path: "+ e.getMessage());
	      }catch(IOException f) {
	    	  throw new ParseRequestException("The file may not be a valid json!");
	      }catch(ParseException f) {
	      	f.printStackTrace();
		  }
	}
	

	@Override
	public synchronized JSONObject query(String word)  {
		JSONObject response = new JSONObject();

			if (dictionary.containsKey(word)) {
				response.put("Action", "Query");
				response.put("Status", "Success");
				response.put("Message", word + ':' + dictionary.get(word).toString());
			} else {
				response.put("Action", "Query");
				response.put("Status", "Failure");
				response.put("Message", "Cannot find the word");
			}
	
		return response;
	} 

	@Override
	public synchronized JSONObject remove(String word) {
		JSONObject response = new JSONObject();

		if(dictionary.containsKey(word)) {
			dictionary.remove(word);
			response.put("Action","Remove" );
			response.put("Status","Success" );
			response.put("Message","(" + word +") is removed" );
			return response;
		}else {
			response.put("Action","Remove" );
			response.put("Status", "Failure");
			response.put("Message","Cannot find the word" );
			return response;
		}
	}

	@Override
	public synchronized JSONObject add(String word, List<String> definitions) throws ParseRequestException {
		JSONObject response = new JSONObject();

			try {
				if (dictionary.containsKey(word) || definitions == null) {
					response.put("Action", "Add");
					response.put("Status", "Failure");
					response.put("Message", "Word already exists or No definition is found");
				} else {
					dictionary.put(word, definitions);
					response.put("Action", "Add");
					response.put("Status", "Success");
					response.put("Message", "("+ word + ") is added with meaning: "+ definitions.toString());
				}

			}catch (Exception e) {
				throw new ParseRequestException("Errors happen when parsing the json");
			}
		
		return response;
	}

	public synchronized JSONObject getDictionary() {
		return dictionary;
	}

	public synchronized void setDictionary(JSONObject dictionary) {
		this.dictionary = dictionary;
	}


}
