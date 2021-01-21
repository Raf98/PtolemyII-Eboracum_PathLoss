package eboracum.wsn.event;

import eboracum.wsn.type.EventType;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.util.Time;
import ptolemy.data.BooleanToken;
import ptolemy.data.StringToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.SingletonParameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.data.type.BaseType;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Settable;
import ptolemy.vergil.icon.EditorIcon;
import ptolemy.vergil.kernel.attributes.RectangleAttribute;


public abstract class BasicEvent extends TypedAtomicActor {
    // Abstract class representing WSN event actors

    private static final long serialVersionUID = 1L;
    protected RectangleAttribute _rectangle;
    protected WirelessIOPort out;
    protected WirelessIOPort outCentral;
    protected EventType eType;
    // PtolemyII parameters for this actor
    public StringParameter type;
    public StringParameter endType;
    public StringParameter color;
    public Parameter triggerTime;
  //  public Parameter lifetime;
  //  public Parameter period;
    //location is already a Parameter for the actor
    //protected int duration;
    protected Time finishTime;
    public int numberOfProducedEvents;
    public int numberOfSensorProcessedEvents;
    Parameter randomize;
    String message;
        
    public BasicEvent(CompositeEntity container, String name)
                        throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // Define the event node's parameters
        StringParameter sensorChannelName = new StringParameter(this,"SensorChannelName");
    	sensorChannelName.setExpression("LimitedRangeChannel");
        type = new StringParameter(this, "Type");
        type.setExpression("EventType");
        endType = new StringParameter(this, "EndType");
        endType.setExpression("E");
        color = new StringParameter(this, "Color");
        color.setExpression("{0.0, 0.0, 0.9, 1.0}");
        triggerTime = new Parameter(this, "TriggerTime"); 
        triggerTime.setExpression("0");// default event start at 0
        // Define event node's wireless ports.
        out = new WirelessIOPort(this, "output", false, true);
        out.outsideChannel.setExpression("$SensorChannelName"); //"$SensorChannelName"
        out.setTypeEquals(BaseType.STRING);
        outCentral = new WirelessIOPort(this, "output2", false, true);
        outCentral.outsideChannel.setExpression("AtomicWirelessChannel"); //"$SensorChannelName"
        outCentral.setTypeEquals(BaseType.STRING);
        // Create the node's icon to be used on Vergil
        buildIcon();      
    }

    public void initialize() throws IllegalActionException {
        super.initialize();
        this.numberOfProducedEvents = 0;
        this.numberOfSensorProcessedEvents = 0; 
        // Redefine event color according Color parameter
        _rectangle.fillColor.setToken("{0.5, 0.5, 0.5, 1.0}");
        _fireAt(Double.parseDouble(triggerTime.getExpression())); //_fireAt(0);
        message = this.endType.getExpression();
   }
    
   public void fire() throws NoTokenException, IllegalActionException {
        super.fire();
        //type.getExpression();
        out.send(0, new StringToken(message));
        outCentral.send(0, new StringToken(message));
        _rectangle.fillColor.setToken(color.getExpression());
        this.numberOfProducedEvents++;
    }

    public boolean postfire() throws IllegalActionException{
    	//System.out.println((this.getDirector().getAttribute("synchronizeToRealTime")));
    	if (((Parameter)this.getDirector().getAttribute("synchronizeToRealTime")).getExpression().equals("true")){
    		UncolorNode un = new UncolorNode();
    		un.start();
    	}
        return super.postfire();
    }
    
    
    private void buildIcon() throws IllegalActionException, NameDuplicationException {
    	EditorIcon node_icon = new EditorIcon(this, "_icon");
    	_rectangle = new RectangleAttribute(node_icon, "_rectangle");
    	_rectangle.centered.setToken("true");
    	_rectangle.width.setToken("11");
    	_rectangle.height.setToken("11");
    	_rectangle.fillColor.setToken(color.getExpression());
    	_rectangle.lineColor.setToken("{0.0, 0.0, 0.0, 0.0}");
    	node_icon.setPersistent(false);
    	SingletonParameter hide = new SingletonParameter(this, "_hideName");
    	hide.setToken(BooleanToken.TRUE);
    	hide.setVisibility(Settable.EXPERT);
    	(new SingletonParameter(out, "_hide")).setToken(BooleanToken.TRUE);
    	(new SingletonParameter(outCentral, "_hide")).setToken(BooleanToken.TRUE);
    }

    public EventType getType() {
		return eType;
	}

	public void setType(EventType type) {
		this.eType = type;
	}

	public String setLocation(double x, double y) throws IllegalActionException{ 
        double[] p = new double[2];
        CompositeEntity container = (CompositeEntity) this.getContainer();
        p[0] = x;  
        p[1] = y; 
        // save new location onto the MoML       
        return _getLocationSetMoML(container, this, p);
    }
    
    protected String _getLocationSetMoML(CompositeEntity container,
            Entity node, double[] location) throws IllegalActionException {
        // First figure out the name of the class of the _location
        // attribute.  Usually, it is ptolemy.kernel.util.Location,
        // but another possibility is
        // ptolemy.actor.parameters.LocationParameter.
        Attribute locationAttribute = node.getAttribute("_location");
        String className = null;
        if (locationAttribute != null) {
            className = locationAttribute.getClass().getName();
            return "<property name=\"" + node.getName(container)
                    + "._location\" " + "class=\"" + className + "\" value=\"["
                    + location[0] + ", " + location[1] + "]\"/>\n";
        } else {
            // The _location attribute does not exist.
            // FIXME: We could make a new attribute first instead of
            // throwing an exception here.
            throw new IllegalActionException(
                    "The _location attribute does not exist for node = " + node
                            + "with container = " + container);
        } 
    }
    
    protected class UncolorNode extends Thread {
        public UncolorNode() {
        }
        public void run() {
            try {
				Thread.sleep((long) 1000.0);
				if (this.isAlive())
					_rectangle.fillColor.setToken("{0.5, 0.5, 0.5, 1.0}");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
        
}