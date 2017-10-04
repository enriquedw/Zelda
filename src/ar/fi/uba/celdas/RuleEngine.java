package ar.fi.uba.celdas;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class RuleEngine {
	private ArrayList<Rule> rules;
	
	
	RuleEngine(String path) {
		JSONParser parser = new JSONParser();
		rules = new ArrayList<Rule>();
		//Load rules from a json file
		try
	    {
	        Object object = parser.parse(new FileReader(path));
	        
	        JSONArray jsonArray = (JSONArray)object;
	        for(Object n : jsonArray) {
		        ArrayList<String> condiciones = (ArrayList<String>)((JSONObject)n).get("conditions");
		        String accion = (String)((JSONObject)n).get("action"); 
		        Rule rule = new Rule(condiciones, accion);
		        //rule.print();
		        rules.add(rule);
	        }	       
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }		
	}
	
	
	//if the conditions of a rule matches the facts return an action else null
	public String checkRules(ArrayList<String> facts){
	    for(Rule aRule : rules) {
	    	boolean fire = true;
	    	for(String condition : aRule.getConditions()){
	    		if (!facts.contains(condition))
	    			fire = false;
	    	}
	    	if (fire)
	    		return aRule.getAction();
	    }
	    return null;
	}
	
}
