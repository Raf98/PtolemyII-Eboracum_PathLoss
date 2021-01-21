package eboracum.wsn.network.node.sensor.controlled;

import eboracum.wsn.agent.DummyAgent;
import eboracum.wsn.network.node.sensor.ControlledWSNNode;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class DummyControlledWSNNode extends ControlledWSNNode{

	private static final long serialVersionUID = 1L;

	public DummyControlledWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		this.myAgent = new DummyAgent();
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
	}

}
