package eboracum.wsn.network.node.sensor;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eboracum.wsn.network.node.WirelessNode;
import eboracum.wsn.network.node.sensor.cpu.SensorCPU;
import eboracum.wsn.type.EventType;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.NoRoomException;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.util.Time;
import ptolemy.data.BooleanToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.SingletonParameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.vergil.kernel.attributes.AttributeValueAttribute;
import ptolemy.vergil.kernel.attributes.EllipseAttribute;

public abstract class BasicWirelessSensorNode extends WirelessNode {
	
	private static final long serialVersionUID = 1L;
	final static String PLATFORMCONFIG = "eboracum/wsn/PlatformConfig.xml";
	protected EllipseAttribute _circle_sensor;
	public Time tempLastSensingEventTime;
    protected WirelessIOPort sensoring;
    protected String tempEvent;
    protected Time timeLastCPURun;
    protected SensorCPU cpu;
    protected Map<String, Boolean> eventOrdinaryMap;
    protected EventType eType;
    public StringParameter type;
    public StringParameter eventCostMapping;
    public StringParameter sensedEvents;
    //public Parameter sensoringEnergyCost;
    public Parameter CPUEnergyCost;
    public Parameter sensorCoverRadius;
    public int numberOfSensoredEvents;
    public int numberOfQueuedEvents;
    
    
    public BasicWirelessSensorNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
    	super(container, name);
    	StringParameter sensorChannelName = new StringParameter(this,"SensorChannelName");
    	sensorChannelName.setExpression("LimitedRangeChannel");
    	sensorCoverRadius = new Parameter(this,"SensorCoverRadius");
    	sensorCoverRadius.setExpression("SensorCover");
    	//sensoringEnergyCost = new Parameter(this,"SensoringEnergyCost");
    	//sensoringEnergyCost.setExpression("0");
    	CPUEnergyCost = new Parameter(this,"CPUEnergyCost");
    	CPUEnergyCost.setExpression("0");
    	sensedEvents = new StringParameter(this,"SensedEvents");
    	sensedEvents.setExpression("");
    	sensoring = new WirelessIOPort(this, "inputSensoring", true, false);
    	sensoring.outsideChannel.setExpression("$SensorChannelName");
    	SingletonParameter hide = new SingletonParameter(sensoring, "_hide");
        hide.setToken(BooleanToken.TRUE);
    	this.iconColor = "{0.0, 0.0, 0.5, 1.0}";
    	Parameter networked = new Parameter(this,"networked");
    	networked.setExpression("true");
    	this.eventOrdinaryMap = new HashMap<String, Boolean>();
    	type = new StringParameter(this, "Type");
        type.setExpression("EventType");
    }
    
    public void initialize() throws IllegalActionException {
        super.initialize();
        this.numberOfQueuedEvents = 0;
        this.numberOfSensoredEvents = 0;
        this.timeLastCPURun = Time.NEGATIVE_INFINITY; 
        this.tempLastSensingEventTime = Time.NEGATIVE_INFINITY;
    	_circle_sensor.fillColor.setToken("{0.0, 0.0, 0.5, 0.05}");
    	_circle_sensor.lineColor.setToken("{1.0, 1.0, 1.0, 1.0}");
    	_circle_sensor.width.setToken(Double.toString(Double.parseDouble(sensorCoverRadius.getValueAsString())*2));
    	_circle_sensor.height.setToken(Double.toString(Double.parseDouble(sensorCoverRadius.getValueAsString())*2));
    	this.processEventOrdinaryMap();
	}
    
	public void fire() throws NoTokenException, IllegalActionException {
		super.fire();
		// sensing the Event
		this.sensorNodeAction();
	}
	
	protected void sensorNodeAction() throws NoTokenException, IllegalActionException{
		sensingManager();
		// running CPU (only when the simulation time changes)
		cpuRunManager();
	}
	
	public void sensingManager() throws NoTokenException, IllegalActionException{
		this.tempEvent = null;
		if(this.sensoring.hasToken(0)){
			this.tempEvent = this.sensoring.get(0).toString();
			//System.out.println(this.tempEvent);
			this.tempEvent = tempEvent.substring(1,tempEvent.length()-1);
			ClassLoader classLoader = EventType.class.getClassLoader();
			try {
				@SuppressWarnings("rawtypes")
				Class tn = classLoader.loadClass("eboracum.wsn.type."+this.tempEvent.split("_")[0]);
	           	if (this.getType().getClass().isAssignableFrom(tn)){
	    			//if (Double.parseDouble(this.battery.getValueAsString()) > Double.parseDouble(this.sensoringEnergyCost.getExpression())){
	           			if (this.eventSensedManager(tempEvent)){
	           				/*	this.battery.setExpression(Double.toString(  Double.parseDouble(battery.getValueAsString()) - Double.parseDouble(sensoringEnergyCost.getValueAsString()) ));
    						if (this.synchronizedRealTime.getExpression().equals("false"))
    						this.timeOfDeath = (this.getDirector().getModelTime().add(round((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
    						//_fireAt(round(this.getDirector().getModelTime().getDoubleValue()+(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
	           				 */
	           				if (!this.tempLastSensingEventTime.equals(this.getDirector().getModelTime())){
		    					this.tempLastSensingEventTime = this.getDirector().getModelTime();
		    					this.sensedEvents.setExpression(tempEvent.split("_")[0]);
		    				}
		    				else this.sensedEvents.setExpression(sensedEvents.getExpression()+tempEvent.split("_")[0]);
	           			}
	           			else this.tempEvent = null;
	    			//}
	    			//else this.tempEvent = null;
	           	}
	           	else this.tempEvent = null;
           	}
	        catch (Exception e){
	        	//System.out.println(e);
	        	this.tempEvent = null;
	        }
		}
	}
	
	protected boolean eventSensedManager(String tempEvent) throws NumberFormatException, IllegalActionException{
		//System.out.println(this.getDisplayName()+" "+tempEvent);
		return true;
	}
	
	public void cpuRunManager() throws NoRoomException, IllegalActionException{
			Time tempTimeLastCPURun = Time.NEGATIVE_INFINITY; //temporary variable to help the scheme of battery consumption when not synchronised to the real time
			tempTimeLastCPURun = this.timeLastCPURun; 
			List<Object> runReturn = this.cpu.run(tempEvent,this.getDirector().getModelTime());
			if ((Integer)runReturn.get(0)>0){ // if CPU has process to handle, collect information about this run
				this.timeLastCPURun = this.getDirector().getModelTime(); // last time of CPU run
				this.numberOfQueuedEvents = (Integer)runReturn.get(0); // collect number of event in the CPU waiting to be processed for statistics
			}
			else
				this.numberOfQueuedEvents = 0;
			if ((String)runReturn.get(1) != null){
				// if an event was processed
				if (Double.parseDouble(battery.getValueAsString()) >= ((Double.parseDouble(CPUEnergyCost.getValueAsString())*(this.getDirector().getModelTime().getDoubleValue()-this.newTimeControler.getDoubleValue())))){ // if it has battery yet 
					// deals with the processed event
					battery.setExpression(Double.toString(Double.parseDouble(battery.getValueAsString())-((Double.parseDouble(CPUEnergyCost.getValueAsString())*(this.getDirector().getModelTime().getDoubleValue()-this.newTimeControler.getDoubleValue())))));
					if (this.synchronizedRealTime.getExpression().equals("false"))
						this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
					this.eventDoneManager(runReturn);
				}
			}
			else {
				if (!this.timeControler.equals(this.getDirector().getModelTime())){
					if (((tempTimeLastCPURun != Time.NEGATIVE_INFINITY)) && ((Integer)runReturn.get(0)>0)){
						if (Double.parseDouble(battery.getValueAsString()) >= ((Double.parseDouble(CPUEnergyCost.getValueAsString())*(this.getDirector().getModelTime().getDoubleValue()-this.newTimeControler.getDoubleValue())))){ 
							battery.setExpression(Double.toString(Double.parseDouble(battery.getValueAsString())-((Double.parseDouble(CPUEnergyCost.getValueAsString())*(this.getDirector().getModelTime().getDoubleValue()-this.newTimeControler.getDoubleValue())))));
							if (this.synchronizedRealTime.getExpression().equals("false"))
								this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
						}
					}
				}
			}
			// control the next fire if it is NOT synchronized to the real time
			if ((Time)runReturn.get(2) != Time.NEGATIVE_INFINITY){
				if (this.synchronizedRealTime.getExpression().equals("false")){
						this._fireAt(((Time)runReturn.get(2))); // fire at when the next event processing finishes
				}
			}
			else { 
				this.timeLastCPURun = Time.NEGATIVE_INFINITY;
			}
	}
	
	protected void eventDoneManager(List<Object> runReturn) throws NoRoomException, IllegalActionException{
		// verify if the event must be send through the network (not ordinary)
		//((BasicEvent)this.getEvent(((String)runReturn.get(1)))).numberOfSensorProcessedEvents++;
		if (!this.eventOrdinaryMap.get(((String)runReturn.get(1)).split("_")[0]))	
			// send the event to the gateway
			this.sendMessageToSink(((String)runReturn.get(1)));
		this.numberOfSensoredEvents++; // collect the event for statistics
	}
	
	protected Entity getEvent(String name) throws IllegalActionException {
        CompositeActor container = (CompositeActor) getContainer();
        @SuppressWarnings("rawtypes")
		Iterator actors = container.deepEntityList().iterator();
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            if (node.getName().equals(name)){
            	return node; 
            }
        }
        return null;
    }
	
	public boolean postfire() throws IllegalActionException{
		if (!this.timeControler.equals(this.getDirector().getModelTime())){
			if (tempEvent==null) sensedEvents.setExpression("");
		}
		Boolean flag = super.postfire();
		if (!flag){
			_circle_sensor.fillColor.setToken("{0.0, 0.0, 0.5, 0.0}");
			_circle_sensor.lineColor.setToken("{1.0, 1.0, 1.0, 0.0}");
			sensedEvents.setExpression("");
			return false;
		}
		else return flag;
	}

	private void processEventOrdinaryMap(){
		try {
			File fXmlFile = new File(BasicWirelessSensorNode.PLATFORMCONFIG);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("event");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nEvent = nList.item(i);
				if (nEvent.getNodeType() == Node.ELEMENT_NODE)
					this.eventOrdinaryMap.put(((Element)nEvent).getAttribute("type"), Boolean.valueOf(((Element)nEvent).getAttribute("ordinary")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void buildIcon() throws IllegalActionException, NameDuplicationException {
		super.buildIcon();
		_circle_sensor = new EllipseAttribute(this.node_icon, "_circle_sensor");
		_circle_sensor.centered.setToken("true");
		_circle_sensor.width.setToken("30");
		_circle_sensor.height.setToken("30");
		_circle_sensor.fillColor.setToken("{0.0, 0.0, 0.5, 0.05}");
		_circle_sensor.lineColor.setToken("{1.0, 1.0, 1.0, 1.0}");
		AttributeValueAttribute _param = new AttributeValueAttribute(node_icon,"ParameterValue");
		_param.displayWidth.setToken("20");
		_param.center.setToken("true");
		Location l = new Location(_param,"_location");
		l.setLocation(new double[] {0.0, -21.0});
		_param.attributeName.setExpression("SensedEvents");
	}

	public EventType getType() {
		return eType;
	}

	public void setType(EventType type) {
		this.eType = type;
	}

}