package eboracum.wsn.agent.central;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ptolemy.actor.CompositeActor;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.util.Time;
import ptolemy.data.BooleanToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Variable;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import eboracum.wsn.agent.GreedyPuppetAgent;
import eboracum.wsn.network.node.sensor.controlled.GreedyWSNNode;

public class GreedyCentralizedLoadBalancer extends TypedAtomicActor {

	private static final long serialVersionUID = 1L;
	private Map<String, ArrayList<GreedyPuppetAgent>> netNodesEventsMap;
	private Time lastInformedTime = Time.NEGATIVE_INFINITY;
	private WirelessIOPort in;

	public GreedyCentralizedLoadBalancer(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		in = new WirelessIOPort(this, "input", true, false);
    	in.outsideChannel.setExpression("AtomicWirelessChannel");
    	netNodesEventsMap = new HashMap<String, ArrayList<GreedyPuppetAgent>>();
	}
	
	public void eventSensed(String eventSensed, Time time, GreedyPuppetAgent agent) throws InterruptedException{
		//System.out.println(agent+" "+time.toString()+" "+lastRunningTime+ "  "+eventSensed);
		//((TypedAtomicActor)(agent.myNode)).getDirector().
		if (time.equals(lastInformedTime)){
			if (!this.netNodesEventsMap.containsKey(eventSensed))
				this.netNodesEventsMap.put(eventSensed, new ArrayList<GreedyPuppetAgent>());
			this.netNodesEventsMap.get(eventSensed).add(agent);
		}
		else {
			this.netNodesEventsMap.clear();
			this.netNodesEventsMap.put(eventSensed, new ArrayList<GreedyPuppetAgent>());
			this.netNodesEventsMap.get(eventSensed).add(agent);
			lastInformedTime = time;
			//System.out.println(this.netNodesEventsMap.get(eventSensed).get(0));
		}
	}
	
	public void initialize() throws IllegalActionException{
		this.informAgentsAboutItsMaster();
	}
	
	public void fire() throws IllegalActionException {
		String event = this.in.get(0).toString();
		event = event.substring(1,event.length()-1);
		//System.out.println(event);
		//System.out.println(this.netNodesEventsMap);
		if (this.netNodesEventsMap.size()>0 && this.netNodesEventsMap.containsKey(event) && event != null){
			GreedyPuppetAgent agent = getNodeWithGraterBattery((ArrayList<GreedyPuppetAgent>)this.netNodesEventsMap.get(event));
			if (agent != null) {
				((GreedyWSNNode)agent.myNode).definedByMasterEvent = event;
				((TypedAtomicActor)agent.myNode).getDirector().fireAtCurrentTime((TypedAtomicActor)agent.myNode);
			}
        }
		this.netNodesEventsMap.remove(event);
	}
	
	
	private GreedyPuppetAgent getNodeWithGraterBattery(ArrayList<GreedyPuppetAgent> agents){
		Iterator<GreedyPuppetAgent> n = (agents).iterator();
		double maxBattery = 0;
		GreedyPuppetAgent maxBatteryAgent = null;
		while (n.hasNext()) {
			GreedyPuppetAgent agent = (GreedyPuppetAgent) n.next();
			((GreedyWSNNode)agent.myNode).definedByMasterEvent = "VOID";
			if (Double.parseDouble(agent.myNode.battery.getExpression()) > maxBattery) {
				maxBattery = Double.parseDouble(agent.myNode.battery.getExpression());
				maxBatteryAgent = agent;
			}
		}
		return maxBatteryAgent;
		
	}
	
	
	protected void informAgentsAboutItsMaster() throws IllegalActionException {
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
	            //System.out.println(node.getClassName());
	            if (node.getClassName().equals("eboracum.wsn.network.node.sensor.controlled.GreedyWSNNode")){
	            	((GreedyPuppetAgent)((GreedyWSNNode)node).myAgent).masterOfPuppets = this;
	            	//System.out.println(((GreedyPuppetAgent)((GreedyWSNNode)node).myAgent));
	            }
	        }
	    }
	    
	
}
