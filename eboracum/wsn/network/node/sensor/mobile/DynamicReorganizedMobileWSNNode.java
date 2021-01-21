package eboracum.wsn.network.node.sensor.mobile;

import eboracum.wsn.network.node.sensor.cpu.SimpleFIFOBasedCPU;
import eboracum.wsn.network.node.sensor.mobile.central.MovementRemoteController;
import ptolemy.actor.NoTokenException;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;


public class DynamicReorganizedMobileWSNNode extends BasicMobileWSNNode{

	private static final long serialVersionUID = 1L;
	private Entity myMovementRemoteController;
	
	public DynamicReorganizedMobileWSNNode(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
	}
	
	public void initialize() throws IllegalActionException {
		super.initialize();
		this.cpu = new SimpleFIFOBasedCPU();
	}

	public boolean postfire() throws IllegalActionException{
		if (Double.parseDouble(battery.getValueAsString())<Double.parseDouble((this.idleEnergyCost.getExpression()))){
			((MovementRemoteController)this.myMovementRemoteController).mobileNodes.remove(this);
			((MovementRemoteController)this.myMovementRemoteController).getDirector().fireAtCurrentTime(((TypedAtomicActor)this.myMovementRemoteController));
		}
		return super.postfire();
	}
	
	public void move(int x, int y) throws NoTokenException, IllegalActionException {
        ChangeRequest doRandomize;
        try {
            doRandomize = new MoMLChangeRequest(this, this.getContainer(), moveNode(x, y));
            this.getContainer().requestChange(doRandomize);
        }  catch (NameDuplicationException e) {
            e.printStackTrace();
        }
        workspace().incrVersion();
    }
	
	public String moveNode(int x, int y) throws IllegalActionException, NameDuplicationException{
    	double[] _newLocation = new double[2]; 
	    double inputX = new Double (x);
	    _newLocation[0]= inputX;
	    double inputY = new Double (y);
	    _newLocation[1]= inputY;
        return setLocation(_newLocation[0], _newLocation[1]);      
    }
	
	public void setMyMovementRemoteController(MovementRemoteController m){
		this.myMovementRemoteController = m;
	}

	public double[] newPosition() {
		return null;
	}

}
