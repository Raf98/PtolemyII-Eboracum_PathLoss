package eboracum.wsn.event;


import java.util.Iterator;

import eboracum.wsn.event.util.Stochastic;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;

public class StochasticStaticEvent extends AtomicEvent {
    
	private static final long serialVersionUID = 1L;
	public Entity stocParameterGenerator;
	
    public StochasticStaticEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        this.getStochastic();
    }

    public void initialize() throws IllegalActionException {
        genTriggerTime();
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
