package eboracum.wsn.network.node.sensor.mobile.controlled;

import eboracum.wsn.agent.AntAgent;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.util.Time;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class AntControlledDRMobileWSNNode extends ControlledDRMobileWSNNode{

	private static final long serialVersionUID = 1L;
	public Parameter initThreshold;
	public Parameter initStimulus;
	public Parameter alpha;
	public Parameter delta;
	public Parameter ro;
	public Parameter ksi;
	//public Parameter totalNodes;
	private Time lastSensorTime;

	public AntControlledDRMobileWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		initThreshold = new Parameter(this,"initThreshold");
		initThreshold.setExpression("1");
		initStimulus = new Parameter(this,"initStimulus");
		initStimulus.setExpression("20");
		ksi = new Parameter(this,"ksi");
		ksi.setExpression("0.0007");
		ro = new Parameter(this,"ro");
		ro.setExpression("0.001");
		//alpha = new Parameter(this,"alpha");
		//alpha.setExpression("1");
		delta = new Parameter(this,"delta");
		delta.setExpression("20");
		//totalNodes = new Parameter(this,"totalNodes");
		//totalNodes.setExpression("4");
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
		this.myAgent = new AntAgent();
		this.myAgent.setNode(this);
		lastSensorTime = Time.NEGATIVE_INFINITY;
	}
	
	protected void sensorNodeAction() throws NoTokenException, IllegalActionException{
		if (((AntAgent)this.myAgent).tempEvent != null){
			if (lastSensorTime != this.getDirector().getModelTime() && lastSensorTime != Time.NEGATIVE_INFINITY)
			{
			//this.tempEvent = ((AntAgent)this.myAgent).tempEvent;
				this.tempEvent = ((AntAgent)this.myAgent).deliberate(((AntAgent)this.myAgent).tempEvent);
				//if (tempEvent != null) 
				//	System.out.println(this.getDirector().getModelTime()+" -> "+this.getName()+" - "+tempEvent+"   |   "+((AntAgent)this.myAgent).agentsSensed);
				((AntAgent)this.myAgent).tempEvent = null;
				((AntAgent)this.myAgent).agentsSensed=1;
				if (this.tempEvent != null) {
					if (!this.tempLastSensingEventTime.equals(this.getDirector().getModelTime())){
						this.tempLastSensingEventTime = this.getDirector().getModelTime();
						this.sensedEvents.setExpression(tempEvent.split("_")[0]);
					}
					else this.sensedEvents.setExpression(sensedEvents.getExpression()+tempEvent.split("_")[0]);
				}
			}
		}
		else {
			sensingManager();
			lastSensorTime = this.getDirector().getModelTime();
			//System.out.println(this.getDirector().getModelTime()+" -> "+this.getName()+" "+this.tempEvent);
		}
		//System.out.println(this.getDirector().getModelTime()+" -> "+this.getName()+" - "+tempEvent);
		cpuRunManager();
	}
	
	public boolean receiveMessage(String tempMessage) throws NumberFormatException, IllegalActionException{
		String message = tempMessage.substring(2, tempMessage.length()-2);
		if (message.split(",")[1].split("=")[1].equals("ALL")){
			((AntAgent)this.myAgent).updateStimulus(tempMessage.split(",")[0].split("=")[1]);
			return false;
		}
		else 
			return super.receiveMessage(tempMessage);
	}
	

}
