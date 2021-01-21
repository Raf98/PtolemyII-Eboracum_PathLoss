package eboracum.wsn.event;


import java.util.Iterator;

import eboracum.wsn.event.util.Stochastic;
import ptolemy.actor.NoTokenException;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;


public class StochasticPeriodicJumperEvent extends PeriodicEvent {
    
	private static final long serialVersionUID = 1L;
	public Entity stocParameterGenerator;
	
    
    public StochasticPeriodicJumperEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        this.getStochastic();
    }

    public void initialize() throws IllegalActionException {
        //Redefine position according to a Spectrogram defined on the positionSpectFile
        genPeriod();
        super.initialize();
        ChangeRequest doRandomize;
        doRandomize = new MoMLChangeRequest(this, this.getContainer(), genPosition());
        this.getContainer().requestChange(doRandomize);
        workspace().incrVersion();
   }
    
    public void fire() throws NoTokenException, IllegalActionException {
    	//if (numberOfProducedEvents%1000.0 == 0.0) System.out.println(numberOfProducedEvents+";"+System.nanoTime());
        //Redefine position according to a Spectrogram defined on the positionSpectFile
    	message = this.getName().split("_")[0]+"_"+this.numberOfProducedEvents;
        //System.out.println(this.message);
        super.fire();
        
    }

    public boolean postfire() throws IllegalActionException{
    	ChangeRequest doRandomize;
        doRandomize = new MoMLChangeRequest(this, this.getContainer(), genPosition());
		this.getContainer().requestChange(doRandomize);
        workspace().incrVersion();
        genPeriod();
      	return super.postfire();
    }
    
    public void genPeriod(){
    	double tempPeriod;
    	if (this.stocParameterGenerator != null)
    		tempPeriod = ((Stochastic)this.stocParameterGenerator).period.next()+1;
    	else
    		tempPeriod = 1;
        period.setExpression(Double.toString(tempPeriod));
    }


    public String genPosition() throws IllegalActionException{
    	int [] location = new int[2];
    	if (this.stocParameterGenerator != null){
    		location[0] = ((Stochastic)this.stocParameterGenerator).position[0].next()+50;
    		location[1] = ((Stochastic)this.stocParameterGenerator).position[1].next()+50;
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
