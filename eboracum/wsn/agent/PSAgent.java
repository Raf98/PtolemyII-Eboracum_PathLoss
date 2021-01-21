package eboracum.wsn.agent;

import ptolemy.actor.NoRoomException;
import ptolemy.kernel.util.IllegalActionException;
import eboracum.wsn.network.node.WirelessNode;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import eboracum.wsn.network.node.sensor.controlled.PSControlledWSNNode;
import eboracum.wsn.network.node.sensor.mobile.ControlledDRMobileWSNNode;
import eboracum.wsn.network.node.sensor.mobile.controlled.PSControlledDRMobileWSNNode;

public class PSAgent implements BasicAgent{
	
	public WirelessNode myNode;
	public boolean isQueen;
	public boolean willStopBeQueen;
	public double h;

	public PSAgent(){
		super();
		this.isQueen = false;
	}
	
	public boolean eventSensed(String tempEvent){
		if (isQueen) return true;
		else return false;
	}
	
	public void setNode(ControlledWSNNode myNode){
		this.myNode = myNode;
		this.h = Double.parseDouble(((PSControlledWSNNode)this.myNode).hQN.getValueAsString());
		this.willStopBeQueen= false; 
	}

	public void setNode(ControlledDRMobileWSNNode myNode) {
		this.myNode = myNode;
		this.h = Double.parseDouble(((PSControlledDRMobileWSNNode)this.myNode).hQN.getValueAsString());
		this.willStopBeQueen= false; 
	}
	
	public void differentiate() throws NoRoomException, IllegalActionException{
		double thr = 0; 
		String hqn = "";
		if (this.myNode instanceof PSControlledWSNNode){
			thr = Double.parseDouble(((PSControlledWSNNode)this.myNode).threshold.getValueAsString());
			hqn = (((PSControlledWSNNode)this.myNode).hQN).getExpression();
		}
		if (this.myNode instanceof PSControlledDRMobileWSNNode){
			thr = Double.parseDouble(((PSControlledDRMobileWSNNode)this.myNode).threshold.getValueAsString());
			hqn = (((PSControlledDRMobileWSNNode)this.myNode).hQN).getExpression();
		}
		if (h < thr){
			this.isQueen = true;
			this.willStopBeQueen = false;
			this.myNode.sendMessageToNeighbours("0;"+ hqn, 0.0006);
		}
		else {
			if (willStopBeQueen) {
				this.isQueen = false;
				this.willStopBeQueen = false;
			}
			//if ((this.myNode.getDirector().getModelTime().getDoubleValue()/3)%Double.parseDouble(((PSControledWSNNode)this.myNode).diffCycleTime.getValueAsString())!=0) 
			else this.willStopBeQueen = true;
		}
	}
	
	public void propagate(String message) throws NoRoomException, NumberFormatException, IllegalActionException{
		int htr = 0;
		double khd = 0;
		if (this.myNode instanceof PSControlledWSNNode){
			htr = Integer.parseInt(((PSControlledWSNNode)this.myNode).thresholdHopCount.getValueAsString());
			khd = Double.parseDouble(((PSControlledWSNNode)this.myNode).kHopDecay.getValueAsString());
		}
		if (this.myNode instanceof PSControlledDRMobileWSNNode){
			htr = Integer.parseInt(((PSControlledDRMobileWSNNode)this.myNode).thresholdHopCount.getValueAsString());
			khd = Double.parseDouble(((PSControlledDRMobileWSNNode)this.myNode).kHopDecay.getValueAsString());
		}
		if (Integer.parseInt(message.split("=")[1].split(";")[0]) < htr){
			//if (!this.isQueen) 
				h += Double.parseDouble(message.split("=")[1].split(";")[1]);
			try {
				this.myNode.sendMessageToNeighbours((Integer.parseInt(message.split("=")[1].split(";")[0])+1)+";"+(Double.parseDouble(message.split("=")[1].split(";")[1])*khd), 0.0006);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tdecay(){
		if (!this.isQueen || (this.isQueen && this.willStopBeQueen)) {
			//|| ((this.myNode.getDirector().getModelTime().getDoubleValue()/3)%Double.parseDouble(((PSControledWSNNode)this.myNode).diffCycleTime.getValueAsString())==0 || (this.myNode.getDirector().getModelTime().getDoubleValue()/5)%Double.parseDouble(((PSControledWSNNode)this.myNode).diffCycleTime.getValueAsString())==0))
			//if ( this.myNode.getName().equals("Node2"))
				//System.out.println("Decay: "+this.myNode.getName()+";"+((PSControledWSNNode)this.myNode).kTimeDecay.getValueAsString());
			if (this.myNode instanceof PSControlledWSNNode)
				h *= Double.parseDouble(((PSControlledWSNNode)this.myNode).kTimeDecay.getValueAsString());
			if (this.myNode instanceof PSControlledDRMobileWSNNode)
				h *= Double.parseDouble(((PSControlledDRMobileWSNNode)this.myNode).kTimeDecay.getValueAsString());
			//System.out.println(this.myNode.getDisplayName()+ "  "+ h+"  "+this.isQueen);
		}
	}

}
