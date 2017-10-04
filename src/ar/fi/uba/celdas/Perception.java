package ar.fi.uba.celdas;
/**
 * @author  Juan Manuel Rodr�guez
 * @date 21/10/2016
 * */

import java.util.ArrayList;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import tools.pathfinder.Node;
import tools.pathfinder.PathFinder;
import core.game.Observation;
import core.game.StateObservation;

public class Perception {

	
	/*Legend:
	 *  w: WALL
		A: Agent A
		+: llave
		X: espada
		2: ara�a
		g: puerta
		.: empty space
	 * 
	 * */
	private char[][] level = null;
	private int sizeWorldWidthInPixels;
	private int sizeWorldHeightInPixels;
	private int levelWidth;
	private int levelHeight;
	private int spriteSizeWidthInPixels;
	private int spriteSizeHeightInPixels;
	
	public Vector2d avatarPosition;
	private Vector2d keyPosition;
	private Vector2d portalPosition;
	public ArrayList<Vector2d> spiderPositions;

	private boolean spiderNear;
	private boolean hasKey;
	
	private ArrayList<String> facts;
	
	public ArrayList<Node> keyPath;
	public ArrayList<Node> portalPath;
	
	public Perception(StateObservation stateObs){
		 	ArrayList<Observation>[][] grid = stateObs.getObservationGrid();
	        ArrayList<Observation> observationList;
	        Observation o;
	        
	        //Configure thr pathfinder utility
	        PathFinder pathf;
	        ArrayList<Integer> list = new ArrayList<>(0);
	        list.add(0); //wall
	        pathf = new PathFinder(list);
	        pathf.run(stateObs);
	        
	        spiderPositions = new ArrayList<Vector2d>();
	        spiderNear = false;
	        this.sizeWorldWidthInPixels= stateObs.getWorldDimension().width;
	        this.sizeWorldHeightInPixels= stateObs.getWorldDimension().height;
	        this.levelWidth = stateObs.getObservationGrid().length;
	        this.levelHeight = stateObs.getObservationGrid()[0].length;
	        this.spriteSizeWidthInPixels =  stateObs.getWorldDimension().width / levelWidth;
	        this.spriteSizeHeightInPixels =  stateObs.getWorldDimension().height / levelHeight;

	        this.level = new char[levelHeight][levelWidth];
	        for(int i=0;i< levelWidth; i++){
	        	for(int j=0;j< levelHeight; j++){
	        		observationList = (grid[i][j]);	        		
	        		if(!observationList.isEmpty()){
	        			o = observationList.get(observationList.size()-1);
	        			String element =  o.category+""+o.itype;
	        			switch (element) {
							case "40": this.level[j][i] = 'w'; break;
							case "44": this.level[j][i] = '+';
								keyPosition = new Vector2d(i,j);
								hasKey = false;
								break;
							case "07": this.level[j][i] = 'A';
								avatarPosition = new Vector2d(i,j);
								break;
							case "311": this.level[j][i] = '2';
								spiderPositions.add(new Vector2d(i,j));
								break;
							case "23": this.level[j][i] = 'g';
								portalPosition = new Vector2d(i,j);
								break;
							case "55": this.level[j][i] = 'X';break;
							default: this.level[j][i] = '?';
								avatarPosition = new Vector2d(i,j);
								hasKey = true;
								break;
								
						}
	        		}else{
	        			 this.level[j][i] = '.';
	        		}
	        	}      	
	        }
	     
	        if (!hasKey)
	        	keyPath = pathf.astar.findPath(new Node(avatarPosition), new Node(keyPosition));
	        else
	        	portalPath = pathf.astar.findPath(new Node(avatarPosition), new Node(portalPosition));
	        
	        for(Vector2d n : spiderPositions)
	        {
	            //System.out.println(n.x + ":" + n.y + ", ");
	            if (ACTIONS.fromVector(n.subtract(avatarPosition)) != ACTIONS.ACTION_NIL) {
	            	spiderNear = true;
	            	//System.out.println("Araña cerca");
	            }
	            	
	        }

	        //Collect the important facts
			facts = new ArrayList<String>();
			if (hasKey)
				facts.add("haskey");
			else
				facts.add("nothaskey");
			
			if (spiderNear)
				facts.add("spidernear");
			else
				facts.add("notspidernear");

	        
	}
	
	public ArrayList<String> getFacts(){
		return facts;
	}
	
	public char getAt(int i, int j){
		return level[i][j];
	}
	
	
	public char[][] getLevel(){
		return level;
	}
	
	public int getSizeWorldWidthInPixels() {
		return sizeWorldWidthInPixels;
	}
	
	public int getSizeWorldHeightInPixels() {
		return sizeWorldHeightInPixels;
	}

	
	public int getLevelWidth() {
		return levelWidth;
	}

	
	public int getLevelHeight() {
		return levelHeight;
	}

	public int getSpriteSizeWidthInPixels() {
		return spriteSizeWidthInPixels;
	}

	
	public int getSpriteSizeHeightInPixels() {
		return spriteSizeHeightInPixels;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("");
		if(level!=null){
			 for(int i=0;i< level.length; i++){
		        	for(int j=0;j<  level[i].length; j++){
		        		sb.append(level[i][j]);
		        	}
		        	sb.append("\n");
			 }
		}
		return sb.toString();
	}
	
}