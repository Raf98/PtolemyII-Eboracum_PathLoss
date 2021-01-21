package eboracum.wsn.network.node.sensor.mobile.central;

import java.util.ArrayList;
import java.util.Iterator;

import eboracum.wsn.network.AdHocNetwork;
import eboracum.wsn.network.node.sensor.mobile.BasicMobileWSNNode;
import eboracum.wsn.network.node.sensor.mobile.DynamicReorganizedMobileWSNNode;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;

public class MovementRemoteController extends TypedAtomicActor{

	private static final long serialVersionUID = 1L;
	
	public TypedIOPort out;
	public ArrayList<DynamicReorganizedMobileWSNNode> mobileNodes;
	public AdHocNetwork network;
	public Parameter timeBetweenMovents;
	public Parameter squareAreaSize;
	private boolean flagMovement = true;

	public MovementRemoteController(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		timeBetweenMovents = new Parameter(this,"timeBetweenMovents");
		timeBetweenMovents.setExpression("1");
		squareAreaSize = new Parameter(this,"squareAreaSize");
		squareAreaSize.setExpression("1000");
	}
	
	public void initialize() throws IllegalActionException {
	        super.initialize();
	        _fireAt(this.getDirector().getModelStartTime());
	  }

	 public void fire() throws IllegalActionException {
	        super.fire();
	        //System.out.println("fire: "+this.getDirector().getModelTime()+" "+this.getDirector().getModelStartTime());
	        if (this.getDirector().getModelStartTime().equals(this.getDirector().getModelTime())){
	        	this.mobileNodes = new ArrayList<DynamicReorganizedMobileWSNNode>();
	        	try {
					this.findMobileNodesAndNetwork();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	        }
	        else {
	        	this.moveNodes();
	        	//System.out.println(this.getDirector().getModelTime()+" "+this.flagMovement);
	        	//if (this.flagMovement) 
	        		this.network.fire();
	        }
	        if (this.flagMovement) {
	        	//System.out.println("new fire at: "+this.getDirector().getModelTime().add(Double.parseDouble(timeBetweenMovents.getValueAsString())));
	        	_fireAt(this.getDirector().getModelTime().add(Double.parseDouble(timeBetweenMovents.getValueAsString())));
	        }
	       }
	 
	protected void moveNodes() throws NoTokenException, IllegalActionException{
		Iterator<DynamicReorganizedMobileWSNNode> n = this.mobileNodes.iterator();
		while (n.hasNext()) {
			BasicMobileWSNNode node = n.next();
			if (node instanceof eboracum.wsn.network.node.sensor.mobile.DynamicReorganizedMobileWSNNode){
				this.flagMovement = moveDynamicReorganizedWSNNode((DynamicReorganizedMobileWSNNode)node);
			}
			else
				this.flagMovement = node.move();
		}
		if (this.mobileNodes.isEmpty()) this.flagMovement = false; 
	}
	
	private boolean moveDynamicReorganizedWSNNode(DynamicReorganizedMobileWSNNode node) throws NoTokenException, IllegalActionException{
		double radius = Double.parseDouble(node.commCoverRadius.getValueAsString());
	    int newXY[] = getNewXY(getSensorCoverNeighbours(node, radius), node, radius);
	    if (newXY[0]!= 0 || newXY[1] != 0){
	    	node.move(newXY[0], newXY[1]);
	    	return true;
	    }
	    return false;
	    //return true;
	}
	
	private int[] getNewXY(ArrayList<DynamicReorganizedMobileWSNNode> neigbours, DynamicReorganizedMobileWSNNode node, double radius){
		int returnXY[] = new int[2];
		double[] nodeLocation = getNodeLocation(node);
		returnXY[0] = Double.valueOf(nodeLocation[0]).intValue();
		returnXY[1] = Double.valueOf(nodeLocation[1]).intValue();
	    double distance, angle;
	    Iterator<DynamicReorganizedMobileWSNNode> n = getSensorCoverNeighbours(node, radius).iterator();    
		while (n.hasNext()) {
			BasicMobileWSNNode neigbour = (BasicMobileWSNNode) n.next();
			double[] neigbourLocation = getNodeLocation(neigbour);
			distance = calcDistance(neigbour, node);
			angle = calcAngle(neigbour, node);
			double xsign = Math.signum(nodeLocation[0]-neigbourLocation[0]);
			double ysign = Math.signum(nodeLocation[1]-neigbourLocation[1]);
			returnXY[0]+=Double.valueOf((xsign*forceEquation(distance,radius)*Math.cos(angle))).intValue();
			returnXY[1]+=Double.valueOf((ysign*forceEquation(distance,radius)*Math.sin(angle))).intValue();
		}
		double[] borderControl =  borderControl(nodeLocation, radius);
		returnXY[0]+= borderControl[0];
		returnXY[1]+= borderControl[1];
		if (Math.abs(returnXY[0]-nodeLocation[0])>1 || Math.abs(returnXY[1]-nodeLocation[1])>1)
			return returnXY;
		else return new int[]{0,0};
	}
	
	private double[] borderControl(double[] nodeLocation, double radius){
		double dimension = Double.parseDouble(squareAreaSize.getValueAsString());
		double[] returnXY = new double[2];
		returnXY[0]+=((+1)*forceEquation(nodeLocation[0],radius));
		returnXY[0]+=((-1)*forceEquation(dimension-nodeLocation[0],radius));
		returnXY[1]+=(((+1)*forceEquation(nodeLocation[1],radius)));
		returnXY[1]+=(((-1)*forceEquation(dimension-nodeLocation[1],radius)));
		return returnXY;
	}
	
	private double forceEquation(double d, double r){
		double b = 30;
		double f = (r)*Math.exp((-d/b));
		if (f < 0) return 0;
		else return f;
	}
	
	private double calcAngle(Entity node1, Entity node2){
    	Location lnode1 = (Location) node1.getAttribute("_location");
    	Location lnode2 = (Location) node2.getAttribute("_location");
    	double y = Math.abs(Math.abs(lnode1.getLocation()[1])-Math.abs(lnode2.getLocation()[1]));
    	double d = calcDistance(node1, node2);
    	if (d != 0)
    		return Math.asin(y/d);
    	else return 0;
	}	
	
	private double[] getNodeLocation(BasicMobileWSNNode node){
		 double [] location;
	     Location locationAttribute = (Location) node.getAttribute("_location");
	     location = locationAttribute.getLocation();
	     return location;
	}
	
	protected ArrayList<DynamicReorganizedMobileWSNNode> getSensorCoverNeighbours(BasicMobileWSNNode node, double radius){
		ArrayList<DynamicReorganizedMobileWSNNode> nodes = new ArrayList<DynamicReorganizedMobileWSNNode>();
		Iterator<DynamicReorganizedMobileWSNNode> actors = this.mobileNodes.iterator();
		while (actors.hasNext()) {
            Entity actor = (Entity) actors.next();
            if (actor instanceof BasicMobileWSNNode && !node.equals(actor) && calcDistance(actor, node) < radius*2){
           		nodes.add((DynamicReorganizedMobileWSNNode) actor);
           	}
        }
		return nodes;
	}
	
	 protected double calcDistance(Entity node1, Entity node2){
	    	Location lnode1 = (Location) node1.getAttribute("_location");
	    	Location lnode2 = (Location) node2.getAttribute("_location");
	    	double c1 = Math.abs(Math.abs(lnode1.getLocation()[0])-Math.abs(lnode2.getLocation()[0]));
	    	double c2 = Math.abs(Math.abs(lnode1.getLocation()[1])-Math.abs(lnode2.getLocation()[1]));
	    	return Math.sqrt(c1*c1+c2*c2);
	}
	
	 protected void findMobileNodesAndNetwork() throws IllegalActionException, ClassNotFoundException {
	        CompositeActor container = (CompositeActor) getContainer();
	        @SuppressWarnings("rawtypes")
			Iterator actors = container.deepEntityList().iterator();
	        while (actors.hasNext()) {
	            Entity actor = (Entity) actors.next();
	           	if (actor instanceof eboracum.wsn.network.node.sensor.mobile.BasicMobileWSNNode){
	           		if (actor instanceof eboracum.wsn.network.node.sensor.mobile.DynamicReorganizedMobileWSNNode)
	           			((DynamicReorganizedMobileWSNNode)actor).setMyMovementRemoteController(this);
	           		this.mobileNodes.add((DynamicReorganizedMobileWSNNode) actor);
	           	} else {
	           		if (actor instanceof eboracum.wsn.network.AdHocNetwork){
	           			this.network = (AdHocNetwork) actor;
	           		}  	
	           	}
	        }
	 }

	protected void showNodes(ArrayList<BasicMobileWSNNode> nodes){
		Iterator<BasicMobileWSNNode> n = nodes.iterator();
		while (n.hasNext()) {
			Entity node = (Entity) n.next();
			System.out.println(node.getName());
		}
	}
	
	protected void showNodes(){
		Iterator<DynamicReorganizedMobileWSNNode> n = this.mobileNodes.iterator();
		while (n.hasNext()) {
			Entity node = (Entity) n.next();
			System.out.println(node.getName());
		}
	}
	
	
}
