package eboracum.simulation;

import java.text.DecimalFormat;
import java.util.Iterator;

import eboracum.wsn.event.BasicEvent;
import eboracum.wsn.network.node.NetworkMainGateway;
import eboracum.wsn.network.node.sensor.BasicWirelessSensorNode;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.BooleanToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class DataReporter extends TypedAtomicActor {
   
	private static final long serialVersionUID = 1L;
	public TypedIOPort trigger;
	public TypedIOPort out;
	public Parameter simulationReportFile;
    
    public DataReporter(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        simulationReportFile = new Parameter(this, "SimulationReportFile");
        simulationReportFile.setExpression("DataReportFile");
        out = new TypedIOPort(this, "out", false, true);
        out.setTypeEquals(BaseType.BOOLEAN);
        trigger = new TypedIOPort(this, "trigger", true, false);
        trigger.setTypeEquals(BaseType.BOOLEAN);
    }

    @SuppressWarnings("unchecked")
	public void fire() throws IllegalActionException {
        super.fire();
        DecimalFormat df = new DecimalFormat("#.#######");
        boolean firstEventFlag = true;
        boolean firstNodeFlag = true;
        if (trigger.hasToken(0)){
        	CompositeActor container = (CompositeActor) getContainer();
        	@SuppressWarnings("rawtypes")
        	Iterator actors = container.deepEntityList().iterator();
          	String fileReport = simulationReportFile.getValueAsString().substring(1,simulationReportFile.getValueAsString().length()-1);
          	BenchmarksGenerator.appendDataReportFile(fileReport,"Simulation Total Time;"+df.format((this.getDirector().getModelTime().getDoubleValue())));
        	while (actors.hasNext()) {
        		Entity node = (Entity) actors.next();
        		if (node.getClassName().equals("eboracum.wsn.network.node.NetworkMainGateway")){
        			BenchmarksGenerator.appendDataReportFile(fileReport,"Total Number of Sensed Events by the WSN;"+((NetworkMainGateway)node).eventSensoredGenCounter);
        			BenchmarksGenerator.appendDataReportFile(fileReport,"Number of Sensed Events by the WSN per Day");
        			Iterator<Integer> n = ((NetworkMainGateway)node).detailEventSensoredCounter.iterator();
        			int i = 1;
                	while (n.hasNext()) {
                		Integer value = (Integer) n.next();
                		BenchmarksGenerator.appendDataReportFile(fileReport,i+";"+value);
                		//System.out.println(i+";"+value);
                		i++;
                	}
                	BenchmarksGenerator.appendDataReportFile(fileReport,i+";"+((NetworkMainGateway)node).eventSensoredCounter);
                	
        		}
        		ClassLoader classLoader = DataReporter.class.getClassLoader();
                try {
                	@SuppressWarnings("rawtypes")
					Class bwsn = classLoader.loadClass("eboracum.wsn.network.node.sensor.BasicWirelessSensorNode");
                	if (bwsn.isAssignableFrom(node.getClass())){
                		if (firstNodeFlag) {
                			BenchmarksGenerator.appendDataReportFile(fileReport,"Nodes\nClass Name; Name; Remaining Battery;Number of Received Messages;Number of Sent Messages;Number of Enqueued Events; Number of Sensored Events; Time of Death");
                			firstNodeFlag = false;
                		}
                		//System.out.println(node);
    					BenchmarksGenerator.appendDataReportFile(fileReport,node.getClassName()+";"+
                		node.getName()+";"+
    					df.format(Double.parseDouble(((Parameter)node.getAttribute("Battery")).getExpression()))+";"+
                		((BasicWirelessSensorNode)node).numberOfReceivedMessages+";"+
    					((BasicWirelessSensorNode)node).numberOfSentMessages+";"+
                		((BasicWirelessSensorNode)node).numberOfQueuedEvents+";"+
    					((BasicWirelessSensorNode)node).numberOfSensoredEvents+";"+
                		df.format(((BasicWirelessSensorNode)node).timeOfDeath.getDoubleValue()));
                	}
                	
                	@SuppressWarnings("rawtypes")
					Class be = classLoader.loadClass("eboracum.wsn.event.BasicEvent");
    				if (be.isAssignableFrom(node.getClass())){ 
                		if (firstEventFlag) {
                			BenchmarksGenerator.appendDataReportFile(fileReport,"Events\nClass Name; Name; Type; Number of Produced Events; Number of Times Sensed");
                			firstEventFlag = false;
                		}
                		BenchmarksGenerator.appendDataReportFile(fileReport,node.getClassName()+";"+node.getName()+";"+((Parameter)node.getAttribute("Type")).getExpression()+";"+((BasicEvent)node).numberOfProducedEvents+";"+((BasicEvent)node).numberOfSensorProcessedEvents);
                	}
                } catch (Exception e) {
    				e.printStackTrace();  
    			}
        	}
        	out.send(0, new BooleanToken("true"));
        }
    }
    
    public boolean postfire(){
    	return false;
    }
    
 }
