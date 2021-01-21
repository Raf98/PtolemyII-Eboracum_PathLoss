package eboracum.wsn.network.node;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eboracum.wsn.network.AdHocNetwork;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.NoRoomException;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.util.Time;
import ptolemy.data.BooleanToken;
import ptolemy.data.StringToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.SingletonParameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.data.expr.Variable;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Settable;
import ptolemy.vergil.icon.EditorIcon;
import ptolemy.vergil.kernel.attributes.EllipseAttribute;


public abstract class WirelessNode extends TypedAtomicActor {

	private static final long serialVersionUID = 1L;
	final static String PLATFORMCONFIG = "eboracum/wsn/PlatformConfig.xml";
	private static final String ICONCOLOR = "{0.5, 0.5, 0.5, 1.0}";
 //   private Entity myGateway;
    private Entity myNetwork;
	protected EllipseAttribute _circle;
	protected EllipseAttribute _circle_comm;
    protected WirelessIOPort in;
    protected WirelessIOPort out;
    protected Time timeControler;
    protected Time newTimeControler;
    protected String iconColor = WirelessNode.ICONCOLOR;
    protected EditorIcon node_icon;
    protected Parameter synchronizedRealTime;
    public Parameter battery;
    public Parameter initBattery;
    public Parameter idleEnergyCost;
    //public Parameter communicationEnergyCost;
    public StringParameter gateway;
    public StringParameter network;
    public Parameter commCoverRadius;
    public String receivedMessage;
    public int numberOfReceivedMessages;
    public int numberOfSentMessages;
    public Time timeOfDeath;
    protected Map<String, Double> eventCommCostMap;
   // private boolean flagAlive;
    
    public WirelessNode(CompositeEntity container, String name) throws IllegalActionException, NameDuplicationException {
    	super(container, name);
    	StringParameter commChannelName = new StringParameter(this,"CommChannelName");
    	commChannelName.setExpression("PowerLossChannel");
    	commCoverRadius = new Parameter(this,"CommCoverRadius");
    	commCoverRadius.setExpression("CommCover");
    	network = new StringParameter(this,"Network");
    	network.setExpression("SimpleAdHocNetwork");
    	in = new WirelessIOPort(this, "input", true, false);
    	in.outsideChannel.setExpression("$CommChannelName");
    	out = new WirelessIOPort(this, "output", false, true);
    	out.outsideChannel.setExpression("$CommChannelName");
    	gateway = new StringParameter(this,"Gateway");
    	gateway.setExpression("");
    	Parameter randomize = new Parameter(this,"randomize");
    	randomize.setExpression("false");
    	synchronizedRealTime = new Parameter(this,"SynchronizedRealTime");
    	synchronizedRealTime.setExpression("true");
    	battery = new Parameter(this,"Battery");
    	battery.setExpression("0");
    	initBattery = new Parameter(this,"InitBattery");
    	initBattery.setExpression("GlobalInitBattery");
    	idleEnergyCost = new Parameter(this,"IdleEnergyCost");
    	idleEnergyCost.setExpression("1");
    	this.eventCommCostMap = new HashMap<String, Double>();
  //  	flagAlive = true;
		buildIcon();
    }
    
    public void initialize() throws IllegalActionException {
        super.initialize();
   //     flagAlive = true;
        this.numberOfReceivedMessages = 0;
        this.numberOfSentMessages = 0;
        timeControler = Time.NEGATIVE_INFINITY;
        newTimeControler = this.getDirector().getModelStartTime();
    	battery.setExpression(initBattery.getValueAsString());
    	this.processEventCommCostMap();
		this.myNetwork = this.getMyNetork();
		if (!((AdHocNetwork)this.myNetwork).nodes.contains(this)) ((AdHocNetwork)this.myNetwork).nodes.add(this);
		_circle_comm.fillColor.setToken("{0.0, 0.0, 0.5, 0.05}");
    	_circle_comm.width.setToken(Double.toString(Double.parseDouble(commCoverRadius.getValueAsString())*2));
		_circle_comm.height.setToken(Double.toString(Double.parseDouble(commCoverRadius.getValueAsString())*2));
		_circle.fillColor.setToken(this.iconColor.substring(0,16)+"1.0}");
		if(this.synchronizedRealTime.getExpression().equals("true")){
			_fireAt(this.getDirector().getModelStartTime());
		}
		else {
			_fireAt((this.getDirector().getModelTime().add(round((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))))));
		}
	}
    
	public void fire() throws NoTokenException, IllegalActionException {
		super.fire();
		// decreases battery when idle
		if (!this.timeControler.equals(this.getDirector().getModelTime())){
			if (this.synchronizedRealTime.getExpression().equals("true") && 
					Double.parseDouble(battery.getValueAsString()) >= Double.parseDouble((this.idleEnergyCost.getExpression())))
				battery.setExpression(Double.toString((Double.parseDouble(battery.getValueAsString())-Double.parseDouble(idleEnergyCost.getValueAsString()))));
			else {
					battery.setExpression(Double.toString((
							Double.parseDouble(battery.getValueAsString())-
							( (Double.parseDouble(idleEnergyCost.getValueAsString())*this.getDirector().getModelTime().subtract(this.newTimeControler).getDoubleValue() )   ))));
				if ((((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))) > 0))
					this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
					//_fireAt((this.getDirector().getModelTime().add(round((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))))));
			}
		}
		// if senses an event
		if(in.hasToken(0)){
			// receives and decide what to do with the message, if it is for this node.
			if (this.receiveMessage(this.in.get(0).toString())) {
				this.sendMessageToSink(this.receivedMessage);
			}
		}
		if (!this.timeControler.equals(this.getDirector().getModelTime())){
			if (this.synchronizedRealTime.getExpression().equals("true")) {
				_fireAt(this.getDirector().getModelTime().add(1));
			}
		}
	}
	
	public boolean postfire() throws IllegalActionException{
		this.receivedMessage = null;
		if (!this.timeControler.equals(this.getDirector().getModelTime())){
			this.timeControler = this.getDirector().getModelTime();
			this.newTimeControler = this.getDirector().getModelTime();
		}
		if (Double.parseDouble(battery.getValueAsString())/Double.parseDouble(initBattery.getValueAsString())>0){
			this.updateIcon(Double.parseDouble(battery.getValueAsString())/Double.parseDouble(initBattery.getValueAsString()));
		}
		if (Double.parseDouble(battery.getValueAsString())<Double.parseDouble((this.idleEnergyCost.getExpression()))){
			//this.flagAlive = false;
			_circle.fillColor.setToken("{0.0, 0.0, 0.0, 0.0}");
			_circle_comm.fillColor.setToken("{0.0, 0.0, 0.0, 0.0}");
			((AdHocNetwork)this.myNetwork).nodes.remove(this);
			((TypedAtomicActor)this.myNetwork).getDirector().fireAtCurrentTime(((TypedAtomicActor)this.myNetwork));
			if (this.synchronizedRealTime.getExpression().equals("true")) {
				this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble((this.idleEnergyCost.getExpression()))))));
			}
			this.battery.setExpression("0");
			return false;
		}
		/*if (gateway.getExpression().equals("")) {
			this.flagAlive = false;
			_circle.fillColor.setToken("{0.5, 0.5, 0.5, 1.0}");
			_circle_comm.fillColor.setToken("{0.0, 0.0, 0.0, 0.0}");
			((AdHocNetwork)this.myNetwork).nodes.remove(this);
			((TypedAtomicActor)this.myNetwork).fire();
			if (this.synchronizedRealTime.getExpression().equals("true")) {
									this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
			}
			return false;
		}
		else {
			if (!gateway.getExpression().equals("END")) {
				this.myGateway = this.getMyGateway();
				if (!((WirelessNode)this.myGateway).flagAlive){				
					_circle.fillColor.setToken("{0.5, 0.5, 0.5, 1.0}");
					_circle_comm.fillColor.setToken("{0.0, 0.0, 0.0, 0.0}");
					((AdHocNetwork)this.myNetwork).nodes.remove(this);
					gateway.setExpression("");
					this.flagAlive = false;
					((TypedAtomicActor)this.myNetwork).fire();
					if (this.synchronizedRealTime.getExpression().equals("true")) {
											this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
					}
					return false; 
				}
			}
		}*/
		return super.postfire();
	}

	public boolean receiveMessage(String tempMessage) throws NumberFormatException, IllegalActionException{
		tempMessage = tempMessage.substring(2, tempMessage.length()-2);
		//double commCost = this.eventCommCostMap.get(tempMessage.split(",")[0].split("=")[1]);
		//if ((Double.parseDouble(battery.getValueAsString()) >= commCost)){
			//battery.setExpression(Double.toString( ( Double.parseDouble(battery.getValueAsString()) - commCost )));
			//_fireAt(this.getDirector().getModelTime().add(round(Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))));
			this.numberOfReceivedMessages++;
			if (this.getName().equals(tempMessage.split(",")[1].split("=")[1])){
				this.receivedMessage = tempMessage.split(",")[0].split("=")[1];
				return true;
			}
			else return false;
		//}
		//else return false;
	}
	
	protected boolean sendMessageToSink(String token) throws NoRoomException, IllegalActionException{
			double commCost = this.eventCommCostMap.get(token.split("_")[0]);
			if ((!gateway.getExpression().equals("")&&!gateway.getExpression().equals("END")) && (Double.parseDouble(battery.getValueAsString()) >= commCost)){
				battery.setExpression(Double.toString( ( Double.parseDouble(battery.getValueAsString()) - commCost )));
				if (this.synchronizedRealTime.getExpression().equals("false"))
					this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
					//_fireAt(this.getDirector().getModelTime().add(round(Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))));
				out.send(0, new StringToken("{event="+token+",gateway="+gateway.getExpression()+"}"));
				this.numberOfSentMessages++;
				return true;
			}
			else
				return false;
	}
	
	public boolean sendMessageToNeighbours(String token, double neighboursCommCost) throws NoRoomException, IllegalActionException{
		double commCost = neighboursCommCost;
		if ((Double.parseDouble(battery.getValueAsString()) >= commCost)){
			battery.setExpression(Double.toString( ( Double.parseDouble(battery.getValueAsString()) - commCost )));
			this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
			//_fireAt(this.getDirector().getModelTime().add(round(Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))));
			out.send(0, new StringToken("{="+token+",gateway=ALL}"));
			//this.numberOfSentMessages++;
			return true;
		}
		else
			return false;
}
	
	protected Entity getMyNetork() throws IllegalActionException {
        CompositeActor container = (CompositeActor) getContainer();
        @SuppressWarnings("rawtypes")
		Iterator actors = container.deepEntityList().iterator();
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            if (node.getName().equals(this.network.getExpression())){
            	return node; 
            }
        }
        return null;
    }
	
	protected Entity getMyGateway() throws IllegalActionException {
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
            if (node.getName().equals(this.gateway.getExpression())){
            	return node; 
            }
        }
        return null;
    }
	
	private void processEventCommCostMap(){
		try {
			File fXmlFile = new File(WirelessNode.PLATFORMCONFIG);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("event");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nEvent = nList.item(i);
				if (nEvent.getNodeType() == Node.ELEMENT_NODE)
					this.eventCommCostMap.put(((Element)nEvent).getAttribute("type"), Double.valueOf(((Element)nEvent).getAttribute("commcost")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateIcon(double colorAlphaAccordingBattery) throws IllegalActionException {
		_circle.fillColor.setToken(this.iconColor.substring(0,16)+colorAlphaAccordingBattery+"}");
	}
	
	protected void buildIcon() throws IllegalActionException, NameDuplicationException {
		node_icon = new EditorIcon(this, "_icon");
		_circle = new EllipseAttribute(node_icon, "_circle");
		_circle.centered.setToken("true");
		_circle.width.setToken("20");
		_circle.height.setToken("20");
		_circle.fillColor.setToken(this.iconColor);
		_circle.lineColor.setToken("{0.0, 0.0, 0.0, 1.0}");
		_circle_comm = new EllipseAttribute(node_icon, "_circle_comm");
		_circle_comm.centered.setToken("true");
		_circle_comm.width.setToken("40");
		_circle_comm.height.setToken("40");
		_circle_comm.fillColor.setToken("{0.0, 0.0, 0.5, 0.02}");
		_circle_comm.lineColor.setToken("{0.0, 0.0, 0.0, 0.02}");
		node_icon.setPersistent(false);
		SingletonParameter hide = new SingletonParameter(this, "_hideName");
		hide.setToken(BooleanToken.TRUE);
		hide.setVisibility(Settable.EXPERT);
		(new SingletonParameter(in, "_hide")).setToken(BooleanToken.TRUE);
		(new SingletonParameter(out, "_hide")).setToken(BooleanToken.TRUE);
	}
	
	public double round(double value) {
		return Math.round(value*10000000.0)/10000000.0;
	}

}