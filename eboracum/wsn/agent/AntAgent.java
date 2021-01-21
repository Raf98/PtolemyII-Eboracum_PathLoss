package eboracum.wsn.agent;

import ptolemy.kernel.util.IllegalActionException;
import eboracum.wsn.network.node.WirelessNode;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import eboracum.wsn.network.node.sensor.controlled.AntControlledWSNNode;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;
import eboracum.wsn.network.node.sensor.mobile.controlled.AntControlledDRMobileWSNNode;

public class AntAgent implements BasicAgent{
	
	public WirelessNode myNode;
	private Double threshold;
	public int agentsSensed;
	public String tempEvent;
	public int totalNodes;

	public AntAgent(){
		super();
		agentsSensed = 1;
		tempEvent = null;
		this.totalNodes = 1;
	}
	
	public boolean eventSensed(String tempEvent){
	//	if (this.tempEvent == null){
			this.tempEvent = tempEvent;
			this.propagate(tempEvent);
			try {
				this.myNode.getDirector().fireAt(this.myNode, this.myNode.getDirector().getModelTime().add(1));
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
	//	}
		return false;
	}
	
	public String deliberate(String tempEvent){
		Double rand = Math.random();
		double stimulus = 0, rho = 0, ksi = 0;
		if (this.myNode instanceof AntControlledWSNNode){
			stimulus = Double.parseDouble(((AntControlledWSNNode)this.myNode).initStimulus.getValueAsString())-(Double.parseDouble(((AntControlledWSNNode)this.myNode).delta.getValueAsString())*(agentsSensed/((double)this.totalNodes+1.0)));
			rho = Double.parseDouble(((AntControlledWSNNode)this.myNode).ro.getValueAsString());
			ksi = Double.parseDouble(((AntControlledWSNNode)this.myNode).ksi.getValueAsString());
		}
		if (this.myNode instanceof AntControlledDRMobileWSNNode){
			stimulus = Double.parseDouble(((AntControlledDRMobileWSNNode)this.myNode).initStimulus.getValueAsString())-(Double.parseDouble(((AntControlledDRMobileWSNNode)this.myNode).delta.getValueAsString())*(agentsSensed/((double)this.totalNodes+1.0)));
			rho = Double.parseDouble(((AntControlledDRMobileWSNNode)this.myNode).ro.getValueAsString());
			ksi = Double.parseDouble(((AntControlledDRMobileWSNNode)this.myNode).ksi.getValueAsString());
		}
		if (rand < (Math.pow(stimulus,2)/(Math.pow(stimulus,2)+Math.pow(threshold,2)))){
			threshold += rho;
			return tempEvent;
		}
		else {
			threshold -= ksi;
			return null;
		}
	}
	
	public void propagate(String message){
		//System.out.println(this.myNode.getName()+" PROPAGATING "+message);
		try {
			this.myNode.sendMessageToNeighbours(message,  0.0006);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateStimulus(String tempEvent){
		try {
			//System.out.println(this.myNode.getName()+";"+((PSAgent)this).h+";"+((Integer.parseInt(message.split("=")[1].split(";")[0])+1))+";"+(Double.parseDouble(message.split("=")[1].split(";")[1])*Double.parseDouble(((PSControledWSNNode)this.myNode).kHopDecay.getValueAsString())));
			if (this.tempEvent!=null) {
				agentsSensed++;
				if (this.totalNodes > agentsSensed) this.totalNodes = agentsSensed;
			}
			//if (this.myNode.getName().equals("Node5")) System.out.println(this.myNode.getName()+" RECEIVES "+tempEvent+" "+agentsSensed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setNode(ControlledWSNNode myNode){
		this.myNode = myNode;
		threshold = Double.parseDouble(((AntControlledWSNNode)this.myNode).initThreshold.getValueAsString());
	}

	@Override
	public void setNode(ControlledDRMobileWSNNode myNode) {
		this.myNode = myNode;
		threshold = Double.parseDouble(((AntControlledDRMobileWSNNode)this.myNode).initThreshold.getValueAsString());
	}

}
