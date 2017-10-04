package ar.fi.uba.celdas;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import tools.pathfinder.Node;
import tools.pathfinder.PathFinder;

public class Agent extends AbstractPlayer{

	private RuleEngine aRuleEngine;
    private PathFinder pathf;

	private ArrayList<Node> keyPath;
	private ArrayList<Node> portalPath;


	
	/**
	 * initialize all variables for the agent
	 * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
	 */
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
		aRuleEngine = new RuleEngine("rules.json");
        
	}
	
	/**
	 * return ACTION_NIL on every call to simulate doNothing player
	 * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
	 * @return 	ACTION_NIL all the time
	 */
	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		
		Perception perception = new Perception(stateObs);
	    //System.out.println(perception);
	    /*
	    for(String fact : perception.getFacts()) {
		    System.out.println(fact);
	    }
	    */
		
	    String action = aRuleEngine.checkRules(perception.getFacts());
	    
	    //System.out.println("Accion seleccionada:" + action);

	    
	    if (action.compareTo("attack") == 0)
	    	return ACTIONS.ACTION_USE;
	    //Use the path from perception to move in the direction of the key
	    if (action.compareTo("findkey") == 0)
	    	return ACTIONS.fromVector(perception.keyPath.get(0).position.subtract(perception.avatarPosition));
	    
	    //Use the path from perception to move in the direction of the door
	    return ACTIONS.fromVector(perception.portalPath.get(0).position.subtract(perception.avatarPosition));
	    
	    /*		
		ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
        int index = (int)(Math.random() * actions.size());
        return  actions.get(index);*/
	}
}
