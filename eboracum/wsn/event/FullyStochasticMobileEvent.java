package eboracum.wsn.event;

import eboracum.wsn.event.util.Stochastic;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class FullyStochasticMobileEvent extends StochasticMobileEvent {
    
	private static final long serialVersionUID = 1L;
    
	public FullyStochasticMobileEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
	}
	
	public void setPeriod(int timeBetweenDirChanges){
        this.timeBetweenChanges = timeBetweenDirChanges;
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
//    		activateTime = 0;
    	activateTime = (int)(Math.random()*(9000000));
        triggerTime.setExpression(Double.toString(activateTime));     
    }
    
    public void genLifetime(){
        int duration;
        if (this.stocParameterGenerator != null)
        	duration = ((Stochastic)this.stocParameterGenerator).duration.next();
    	else
    		duration = 0;
        lifetime.setExpression(Integer.toString(duration));
    }

    public void genPeriod(){
    	double tempPeriod;
    	if (this.stocParameterGenerator != null)
    		tempPeriod = ((Stochastic)this.stocParameterGenerator).duration.next();
    	else
    		tempPeriod = 0;
        period.setExpression(Double.toString(tempPeriod));
    }

}
