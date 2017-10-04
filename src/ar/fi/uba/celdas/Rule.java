package ar.fi.uba.celdas;

import java.util.ArrayList;

public class Rule {
	private ArrayList<String> conditions;
	private String action;
	
	Rule(ArrayList<String> cond, String act){
		conditions = cond;
		action = act; 
	}
	
	public ArrayList<String> getConditions(){
		return conditions;
	}
	
	public String getAction(){
		return action;
	}
	
	void print() {
        System.out.println("Condiciones:");
		 for(String n : conditions)
	        {
	            System.out.println(n);
	        }
		 System.out.println("Acciones:");
		 System.out.println(action);
	}
	
	
}
