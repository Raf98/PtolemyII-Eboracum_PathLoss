package eboracum.wsn.network.node.sensor.controlled;

import ptolemy.actor.NoRoomException;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.util.Time;
import ptolemy.data.BooleanToken;
import ptolemy.data.StringToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.SingletonParameter;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.vergil.kernel.attributes.EllipseAttribute;

import eboracum.wsn.agent.PSAgent;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;

public class PSControlledWSNNode extends ControlledWSNNode{

	private static final long serialVersionUID = 1L;
	protected EllipseAttribute _circle_queen;
	public Parameter threshold;
	public Parameter diffCycleTime;
	public Parameter hQN;
	public Parameter thresholdHopCount;
	public Parameter kHopDecay;
	public Parameter decayTime;
	public Parameter kTimeDecay;
	private Time lastDiferentiationTime;
	private Time lastDecayTime;
	protected WirelessIOPort outPS;
	protected WirelessIOPort inPS;
	
	public PSControlledWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		threshold = new Parameter(this,"threshold");
		threshold.setExpression("0.14");
		diffCycleTime = new Parameter(this,"diffCycleTime");
		diffCycleTime.setExpression("14400"); //900
		hQN = new Parameter(this,"hQN");
		hQN.setExpression("50");
		thresholdHopCount = new Parameter(this,"thresholdHopCount");
		thresholdHopCount.setExpression("2");
		kHopDecay = new Parameter(this,"kHopDecay");
		kHopDecay.setExpression("0.25");
		decayTime = new Parameter(this,"decayTime");
		decayTime.setExpression("2000"); //600
		kTimeDecay = new Parameter(this,"kTimeDecay");
		kTimeDecay.setExpression("0.5");
		outPS = new WirelessIOPort(this, "output2", false, true);
	    outPS.outsideChannel.setExpression("PowerLossChannel2");
	    inPS = new WirelessIOPort(this, "input2", true, false);
    	inPS.outsideChannel.setExpression("PowerLossChannel2");
    	try {
			(new SingletonParameter(outPS, "_hide")).setToken(BooleanToken.TRUE);
			(new SingletonParameter(inPS, "_hide")).setToken(BooleanToken.TRUE);
		} catch (NameDuplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
		this.myAgent = new PSAgent();
		this.myAgent.setNode(this);
		this._fireAt(this.getDirector().getModelStartTime());
		_circle_queen.fillColor.setToken("{1.0, 1.0, 1.0, 0.0}");
		_circle_sensor.lineColor.setToken("{1.0, 1.0, 1.0, 0.0}");
		lastDiferentiationTime = Time.NEGATIVE_INFINITY;
		lastDecayTime = Time.NEGATIVE_INFINITY;
	}
	
	public void fire() throws NoTokenException, IllegalActionException {
		super.fire();
		if(inPS.hasToken(0)){
			this.receiveMessage(this.inPS.get(0).toString());
		}
		if ((((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))) > 0)) {	
			if ((this.getDirector().getModelTime()).getDoubleValue()%Double.parseDouble(this.diffCycleTime.getValueAsString())==0){
				if (this.lastDiferentiationTime != this.getDirector().getModelTime()){
					this.lastDiferentiationTime = this.getDirector().getModelTime();
					((PSAgent)this.myAgent).differentiate();
					this._fireAt(this.getDirector().getModelTime().add(Double.parseDouble(diffCycleTime.getValueAsString())));
				}
			}
			if ((this.getDirector().getModelTime()).getDoubleValue()%Double.parseDouble(this.decayTime.getValueAsString())==0){
				if (this.lastDecayTime != this.getDirector().getModelTime()){
					this.lastDecayTime = this.getDirector().getModelTime();
					((PSAgent)this.myAgent).tdecay();
					this._fireAt(this.getDirector().getModelTime().add(Double.parseDouble(decayTime.getValueAsString())));
				}
			}
		}
		else ((PSAgent)this.myAgent).isQueen = false;
		if (((PSAgent)this.myAgent).isQueen) {
			_circle_queen.fillColor.setToken("{1.0, 1.0, 1.0, 1.0}");
			_circle_sensor.lineColor.setToken("{1.0, 1.0, 1.0, 1.0}");
		}
		else {
			_circle_queen.fillColor.setToken("{1.0, 1.0, 1.0, 0.0}");
			_circle_sensor.lineColor.setToken("{1.0, 1.0, 1.0, 0.0}");
		}
	}
	
	public boolean postfire() throws IllegalActionException{
		Boolean flag = super.postfire();
		if (!flag){
			_circle_queen.fillColor.setToken("{1.0, 1.0, 1.0, 0.0}");
			return false;
		}
		else return flag;
	}
	
	public boolean receiveMessage(String tempMessage) throws NumberFormatException, IllegalActionException{
		String message = tempMessage.substring(2, tempMessage.length()-2);
		if (message.split(",")[1].split("=")[1].equals("ALL")){
			((PSAgent)this.myAgent).propagate(tempMessage.split(",")[0]);
			return false;
		}
		else 
			return super.receiveMessage(tempMessage);
	}
	
	public boolean sendMessageToNeighbours(String token, double neighboursCommCost) throws NoRoomException, IllegalActionException{
		double commCost = neighboursCommCost;
		if ((Double.parseDouble(battery.getValueAsString()) >= commCost)){
			battery.setExpression(Double.toString( ( Double.parseDouble(battery.getValueAsString()) - commCost )));
			this.timeOfDeath = (this.getDirector().getModelTime().add(((Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression())))));
			//_fireAt(this.getDirector().getModelTime().add(round(Double.parseDouble(battery.getValueAsString())/Double.parseDouble(idleEnergyCost.getExpression()))));
			outPS.send(0, new StringToken("{="+token+",gateway=ALL}"));
			//this.numberOfSentMessages++;
			return true;
		}
		else
			return false;
	}
	
	protected void buildIcon() throws IllegalActionException, NameDuplicationException {
		super.buildIcon();
		_circle_queen = new EllipseAttribute(this.node_icon, "_circle_queen");
		_circle_queen.centered.setToken("true");
		_circle_queen.width.setToken("15");
		_circle_queen.height.setToken("15");
		_circle_queen.fillColor.setToken("{1.0, 1.0, 1.0, 0.0}");
		_circle_queen.lineColor.setToken("{1.0, 1.0, 1.0, 0.0}");
	}

}
