package eboracum.wsn.event;

import java.util.Random;

import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;

public class RandomMobileEvent extends SimpleMobileEvent {
    
   private static final long serialVersionUID = 1L;
   

	public RandomMobileEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
    	Parameter randomize = new Parameter(this,"randomize");
        randomize.setExpression("true");
    }
	
	public void initialize() throws IllegalActionException {
        genTriggerTime();
        //genLifetime();
        //genPeriod();
        super.initialize();
    }
    
	public void genTriggerTime(){
    	double activateTime;
    	//if (this.stocParameterGenerator != null)
//    		activateTime = ((Stochastic)this.stocParameterGenerator).getTriggerTime();
    	//else
    		//activateTime = 0;
    	//activateTime = (int)(Math.random()*(5400000/2));
    	activateTime = (int)(Math.random()*(9000000));
        triggerTime.setExpression(Double.toString(activateTime));     
    }
   
   public double [] calculatePosition() {
        // Recalculate event location (x, y), using its current position plus a random delta (base on the range from -10 until +10)
        double [] _newLocation;
        int inputRandomX, inputRandomY;
        Location locationAttribute = (Location) this.getAttribute("_location");
        _newLocation = locationAttribute.getLocation(); 
        Random RandomGen = new Random();
        inputRandomX = (RandomGen.nextInt(41))-20; 
        inputRandomY = (RandomGen.nextInt(41))-20;
        //System.out.println(inputRandomX+" - "+inputRandomY);
        double inputX = new Double (inputRandomX);
        _newLocation[0]+=inputX;
        double inputY = new Double (inputRandomY);
        _newLocation[1]+=inputY;
        return _newLocation;
    }
 
}
