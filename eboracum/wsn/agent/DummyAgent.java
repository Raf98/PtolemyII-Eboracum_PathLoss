package eboracum.wsn.agent;

import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;

public class DummyAgent implements BasicAgent{

	public void setNode(ControlledWSNNode myNode){}
	
	public boolean eventSensed(String tempEvent){
		return true;
	}

	public void setNode(ControlledDRMobileWSNNode myNode) {}
	
}
