package eboracum.wsn.network.node.sensor.mobile;

import java.util.Random;

import eboracum.wsn.network.node.sensor.cpu.SimpleFIFOBasedCPU;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;

public class RandomMobileWSNNode extends BasicMobileWSNNode{

	private static final long serialVersionUID = 1L;
	public Parameter jumpRadiusParam;

	public RandomMobileWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		jumpRadiusParam = new Parameter (this, "JumpRadiusParam"); 
		jumpRadiusParam.setExpression("20");
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
		this.cpu = new SimpleFIFOBasedCPU();
	}
	
	public double [] newPosition() {
        double [] _newLocation;
        int inputRandomX, inputRandomY;
        Location locationAttribute = (Location) this.getAttribute("_location");
        _newLocation = locationAttribute.getLocation(); 
        Random RandomGen = new Random();
        int radius = Integer.parseInt(jumpRadiusParam.getExpression());
        inputRandomX = (RandomGen.nextInt(41))-radius; 
        inputRandomY = (RandomGen.nextInt(41))-radius;
        double inputX = new Double (inputRandomX);
        _newLocation[0]+=inputX;
        double inputY = new Double (inputRandomY);
        _newLocation[1]+=inputY;
        return _newLocation;
    }
	
}
