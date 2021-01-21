package eboracum.wsn.network.node.sensor;

import eboracum.wsn.network.node.sensor.cpu.SimpleFIFOBasedCPU;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class SimpleWSNNode extends BasicWirelessSensorNode{

	private static final long serialVersionUID = 1L;

	public SimpleWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
		this.cpu = new SimpleFIFOBasedCPU();
	}

}
