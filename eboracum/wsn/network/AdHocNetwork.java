package eboracum.wsn.network;

import java.util.ArrayList;
import java.util.Iterator;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.BooleanToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.data.expr.Variable;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;

public abstract class AdHocNetwork extends TypedAtomicActor {
   
	private static final long serialVersionUID = 1L;
	public StringParameter networkSinks;
    public Parameter commCoverRadius;
    public ArrayList<Entity> networkedNodes;
    public ArrayList<Entity> nodes = new ArrayList<Entity>();
    public ArrayList<Entity> sinks = new ArrayList<Entity>();
    public Parameter rebuildNetwork;
    protected double coverRadius;
    protected int numNodesToBeNetoworked;
    public TypedIOPort out;
    
    public AdHocNetwork(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        networkSinks = new StringParameter(this, "NetworkSinks");
        networkSinks.setExpression("");
        StringParameter commChannelName = new StringParameter(this,"CommChannelName");
    	commChannelName.setExpression("PowerLossChannel");
    	commCoverRadius = new Parameter(this,"CommCoverRadius");
    	commCoverRadius.setExpression("CommCover");
    	rebuildNetwork = new Parameter(this,"RebuildNetwork");
    	rebuildNetwork.setExpression("false");
    	out = new TypedIOPort(this, "out", false, true);
        out.setTypeEquals(BaseType.BOOLEAN);
    }

    public void initialize() throws IllegalActionException {
        super.initialize();
        this.coverRadius = Double.parseDouble(commCoverRadius.getValueAsString());
        _fireAt(this.getDirector().getModelStartTime());
    }
    
    public void fire() throws IllegalActionException {
        super.fire();
        if (this.rebuildNetwork.getExpression().equals("true") || this.getDirector().getModelStartTime().equals(this.getDirector().getModelTime())){
        	this.networkedNodes = new ArrayList<Entity>();
        	this.sinks = new ArrayList<Entity>();
        	this.findEntitySinks();
        	this.buildNetwork();
        }
        if (!this.getDirector().getModelStartTime().equals(this.getDirector().getModelTime())){
        	@SuppressWarnings("unchecked")
        	ArrayList<Entity> tempNodes = (ArrayList<Entity>) this.nodes.clone();
        	Iterator<Entity> n = tempNodes.iterator();
        	while (n.hasNext()) {
        		Entity node = (Entity) n.next();
        		((TypedAtomicActor)node).getDirector().fireAtCurrentTime((TypedAtomicActor)node);
        	}
        }
      	if (this.nodes.size()<=this.sinks.size()) out.send(0, new BooleanToken("true"));
    }

    public abstract void buildNetwork();
    
    protected void showNetwork(){
    	Iterator<Entity> n = this.networkedNodes.iterator();
        while (n.hasNext()) {
        	Entity node = (Entity) n.next();
        	System.out.println(node.getName()+" "+((StringParameter)node.getAttribute("Gateway")).getExpression());
        }
    }
    
    protected void showNodes(){
    	Iterator<Entity> n = this.nodes.iterator();
        while (n.hasNext()) {
        	Entity node = (Entity) n.next();
        	System.out.println(node.getName());
        }
    }
    
    protected double calcDistance(Entity node1, Entity node2){
    	Location lnode1 = (Location) node1.getAttribute("_location");
    	Location lnode2 = (Location) node2.getAttribute("_location");
    	double c1 = Math.abs(Math.abs(lnode1.getLocation()[0])-Math.abs(lnode2.getLocation()[0]));
    	double c2 = Math.abs(Math.abs(lnode1.getLocation()[1])-Math.abs(lnode2.getLocation()[1]));
    	return Math.sqrt(c1*c1+c2*c2);
    }
    
    protected void findEntitySinks() throws IllegalActionException {
        CompositeActor container = (CompositeActor) getContainer();
        @SuppressWarnings("rawtypes")
		Iterator actors = container.deepEntityList().iterator();
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            // Skip actors that are not properly marked.
            Attribute mark = node.getAttribute("networked");
            if (!(mark instanceof Variable)) {
                continue;
            }
            Token markValue = ((Variable) mark).getToken();
            if (!(markValue instanceof BooleanToken)) {
                continue;
            }
            if (!((BooleanToken) markValue).booleanValue()) {
                continue;
            }
           	((StringParameter)node.getAttribute("Gateway")).setExpression("");
           	if (node.getContainer().getAttribute("lineGateway_"+node.getName()) != null)
           		_removeLine("lineGateway_"+node.getName());
           	String[] tempSinks = (networkSinks.getExpression()).substring(1,networkSinks.getExpression().length()-1).split(",");
           	for (int i=0; i < tempSinks.length; i++){
           		//System.out.println(tempSinks[i]);
           		if (node.getName().equals(tempSinks[i])){
           			((StringParameter)node.getAttribute("Gateway")).setExpression("END");
           			this.networkedNodes.add(node);
           			this.sinks.add(node);
           		}
           	}
        }
    }
    
    protected void _drawLine(Entity sender, Entity destination,
            final String lineName) {
    	Location senderLocation = (Location) sender.getAttribute("_location");
    	Location destinationLocation = (Location) destination.getAttribute("_location");
        double x = (destinationLocation.getLocation())[0]
                - (senderLocation.getLocation())[0];
        double y = (destinationLocation.getLocation())[1]
                - (senderLocation.getLocation())[1];
        String moml = new String("<property name=\""
                + lineName
                + "\" class=\"ptolemy.vergil.kernel.attributes.LineAttribute\">"
                + senderLocation.exportMoML() + "<property name=\"x\" value=\""
                + x + "\"/>" + "<property name=\"y\" value=\"" + y + "\"/>"
                + "</property>");
        ChangeRequest request = new MoMLChangeRequest(this, getContainer(),
                moml);
        this.getContainer().requestChange(request);
        workspace().incrVersion();
    }
    
    protected void _removeLine(String lineName) {
        String moml = "<deleteProperty name=\""+ lineName + "\"/>";
        ChangeRequest request = new MoMLChangeRequest(this, getContainer(), moml);
        request.execute();
    }
    
}
