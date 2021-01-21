package eboracum.wsn.event;

import ptolemy.actor.NoTokenException;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;

public class SimpleMobileEvent extends PeriodicEvent{
    // Concrete class to represent mobile events, that means events that change its position during simulation execution 
   
	private static final long serialVersionUID = 1L;
	public Parameter velocityParam;   
    public Parameter directionParam;
    //public Parameter timeBetweenDirChangesParam;
     
   public SimpleMobileEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        velocityParam = new Parameter (this, "VelocityParam"); 
        velocityParam.setExpression("10");
        directionParam = new Parameter (this, "DirectionParam"); 
        directionParam.setExpression("0");
    }
  
    public void fire() throws NoTokenException, IllegalActionException {
    	super.fire();
        // Move event and request for actualisation of event location parameter 
        ChangeRequest doRandomize;
        try {
            doRandomize = new MoMLChangeRequest(this, this.getContainer(), moveEvent());
            this.getContainer().requestChange(doRandomize);
        }  catch (NameDuplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        workspace().incrVersion();
        message = this.getName().split("_")[0]+"_"+this.numberOfProducedEvents;
    	//System.out.println(this.message);
    }
    
    public boolean postfire() throws IllegalActionException{
    	Location locationAttribute = (Location) this.getAttribute("_location");
    	double [] _myLocation;
    	_myLocation = locationAttribute.getLocation(); 
    	//System.out.println(_myLocation[0]+","+_myLocation[1]);
    	if (_myLocation[0] <= -100 || _myLocation[1] <= -100 || _myLocation[0] >= 1100 || _myLocation[1] >= 1100)
        	return false;
    	return super.postfire();
    }
    
    public void setVelocity(double velocity){
       velocityParam.setExpression(Double.toString(velocity));
    }
    
    public void setDirection(double direction){
       directionParam.setExpression(Double.toString(direction));
    }
       
    public double getVelocity(){
        return Double.parseDouble(velocityParam.getExpression());
    }
    public double getDirection(){
        return Double.parseDouble(directionParam.getExpression());
    }
    
    public String moveEvent() throws IllegalActionException, NameDuplicationException{
        // Move event for a new position
        double [] _myLocation = calculatePosition();
        return setLocation (_myLocation[0], _myLocation[1]);// save new location onto the MoML       
    }
    
    public double [] calculatePosition() {
        //recalculate its location (x, y), according to movement parameters (velocity, direction, timeBetweenDirectionChanges) 
        double [] _newLocation;
        double deltaX, deltaY, directionCorrection;
        Location locationAttribute = (Location) this.getAttribute("_location");
        _newLocation = locationAttribute.getLocation(); 
        directionCorrection =getDirection(); 
        deltaX = format(Math.cos(Math.toRadians(directionCorrection)));
        if (deltaX >=0) { 
            deltaX =  Math.ceil(deltaX)*getVelocity();
        }
        else { 
            deltaX =  Math.floor(deltaX)*getVelocity();
        }
        _newLocation[0] = _newLocation[0] + deltaX; // X coordinate 
        deltaY = format(Math.sin(Math.toRadians(directionCorrection))*-1); // Position (0,0) is in the left-top instead of in the middle
        if (deltaY >=0) { 
            deltaY =  Math.ceil(deltaY)*getVelocity(); }
        else {                                               
            deltaY =  Math.floor(deltaY)*getVelocity();
        }
       _newLocation[1] = _newLocation[1] + deltaY; // Y coordinate
       return _newLocation;
     }
    
    private static double format(double value) {
        return (double)Math.round(value * 1000000) / 1000000;
    }
}


