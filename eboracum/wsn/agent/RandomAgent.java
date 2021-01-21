package eboracum.wsn.agent;

import eboracum.wsn.network.node.WirelessNode;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;

public class RandomAgent implements BasicAgent{

	public WirelessNode myNode;
	private double threshold;
	
	public void setNode(ControlledWSNNode myNode){
		this.myNode = myNode;
	}
	
	public RandomAgent(double threshold){
		this.threshold = threshold;
	}
	
	public boolean eventSensed(String tempEvent){
		double rand = Math.random();
		if (rand <= this.threshold) return true;
		else return false;
	}

	public void setNode(ControlledDRMobileWSNNode myNode) {
		this.myNode = myNode;
	}
	
}
