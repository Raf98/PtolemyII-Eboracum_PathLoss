package eboracum.wsn.type;

import java.util.Iterator;

import eboracum.wsn.event.BasicEvent;
import eboracum.wsn.network.node.sensor.BasicWirelessSensorNode;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.data.expr.StringParameter;

public class EventTypeController extends TypedAtomicActor {
   
	private static final long serialVersionUID = 1L;
	
    public EventTypeController(CompositeEntity container, String name) throws IllegalActionException, NameDuplicationException{
           super(container, name);
    }

    public void initialize() throws IllegalActionException {
        super.initialize();
        giveTypeToNodesAndEvents();
    }
    
    public void fire() throws IllegalActionException {
        super.fire();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void giveTypeToNodesAndEvents() throws IllegalActionException {
        CompositeActor container = (CompositeActor) getContainer();
		Iterator actors = container.deepEntityList().iterator();
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            ClassLoader classLoader = EventType.class.getClassLoader();
            try {
            	Class bwsn = classLoader.loadClass("eboracum.wsn.network.node.sensor.BasicWirelessSensorNode");
				if (bwsn.isAssignableFrom(node.getClass())) {
		           	Class tn =  classLoader.loadClass("eboracum.wsn.type."+((StringParameter)node.getAttribute("Type")).getExpression());
		           	EventType sn = (EventType) tn.getConstructor().newInstance();
		           	((BasicWirelessSensorNode)node).setType(sn);
				}
				Class be = classLoader.loadClass("eboracum.wsn.event.BasicEvent");
				if (be.isAssignableFrom(node.getClass())){
					Class te =  classLoader.loadClass("eboracum.wsn.type."+((StringParameter)node.getAttribute("Type")).getExpression());
					EventType se = (EventType) te.getConstructor().newInstance();
					((BasicEvent)node).setType(se);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
 
}