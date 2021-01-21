package eboracum.wsn.agent;


import eboracum.wsn.agent.central.GreedyCentralizedLoadBalancer;
import eboracum.wsn.network.node.WirelessNode;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;

public class GreedyPuppetAgent implements BasicAgent{

	public GreedyCentralizedLoadBalancer masterOfPuppets;
	public WirelessNode myNode;	

	public boolean eventSensed(String tempEvent){
		try {
			//System.out.println(myNode.getDirector());
			this.masterOfPuppets.eventSensed(tempEvent,	myNode.getDirector().getModelTime(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setNode(ControlledWSNNode myNode){
		this.myNode = myNode;
	}
	
	public void setNode(ControlledDRMobileWSNNode myNode) {
		this.myNode = myNode;
	}
}
