package eboracum.wsn.event;


import java.util.Iterator;

import eboracum.wsn.event.util.Stochastic;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;


public class StochasticPeriodicStaticEvent extends PeriodicEvent {
    
	private static final long serialVersionUID = 1L;
	public Entity stocParameterGenerator;
	
    
    public StochasticPeriodicStaticEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        this.getStochastic();
    }

    public void initialize() throws IllegalActionException {
        //Redefine position according to a Spectrogram defined on the positionSpectFile
        genTriggerTime();
        genLifetime();
        genPeriod();
        super.initialize();
        ChangeRequest doRandomize;
        doRandomize = new MoMLChangeRequest(this, this.getContainer(), genPosition());
        this.getContainer().requestChange(doRandomize);
        workspace().incrVersion();   
   }
    
	public void genTriggerTime(){
    	double activateTime;
    	if (this.stocParameterGenerator != null)
    		activateTime = ((Stochastic)this.stocParameterGenerator).getTriggerTime();
    	else
    		activateTime = 0;
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
    		tempPeriod = ((Stochastic)this.stocParameterGenerator).period.next();
    	else
    		tempPeriod = 0;
        period.setExpression(Double.toString(tempPeriod));
    }


    public String genPosition() throws IllegalActionException{
    	int [] location = new int[2];
    	if (this.stocParameterGenerator != null){
    		location[0] = ((Stochastic)this.stocParameterGenerator).position[0].next();
    		location[1] = ((Stochastic)this.stocParameterGenerator).position[1].next();
    	}
    	else{
    		location[0] = 0;
    		location[1] = 0; 
    	}
        return setLocation(location[0], location[1]);        
    }
    
    private void getStochastic() {
    	this.stocParameterGenerator =  null;
		@SuppressWarnings("unchecked")
		Iterator<Entity> actors = ((CompositeEntity)this.getContainer()).deepEntityList().iterator();
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            if (node.getName().equals("Stochastic")){
            	this.stocParameterGenerator =  node;
            }
        }
    }
    
}
