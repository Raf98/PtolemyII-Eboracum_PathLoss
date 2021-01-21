package eboracum.wsn.agent;

import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;

public interface BasicAgent {
	
	
	boolean eventSensed(String tempEvent);

	public void setNode(ControlledWSNNode myNode);

	public void setNode(ControlledDRMobileWSNNode myNode);

	
}
