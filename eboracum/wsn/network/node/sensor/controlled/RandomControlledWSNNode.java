package eboracum.wsn.network.node.sensor.controlled;

import eboracum.wsn.agent.RandomAgent;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class RandomControlledWSNNode extends ControlledWSNNode{

	private static final long serialVersionUID = 1L;
	public Parameter Threshold;

	public RandomControlledWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		Threshold = new Parameter(this,"Threshold");
		Threshold.setExpression("0.50");
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
		this.myAgent = new RandomAgent(Double.parseDouble(Threshold.getValueAsString()));
	}

}
