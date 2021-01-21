package eboracum.wsn.event;

//import ptolemy.data.expr.ChoiceParameter;
import java.util.Iterator;

import eboracum.wsn.event.util.Stochastic;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;

public class StochasticMobileEvent extends SimpleMobileEvent {
    
	private static final long serialVersionUID = 1L;
	
	public StringParameter positionSpectFile; // initial position
    // parameter for event movement
    public StringParameter velocityHistFile;
    public StringParameter directionHistFile;
    public StringParameter timeBetweenDirChangesHistFile;
    public Parameter stochasticTimeBetweenChanges; //(TRUE, FALSE)
    double timeBetweenChanges;
    public Entity stocParameterGenerator;
      
    public StochasticMobileEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        this.getStochastic();
        stochasticTimeBetweenChanges = new Parameter(this, "StochasticTimeBetweenChanges");
        stochasticTimeBetweenChanges.setExpression("TRUE");
    }
    public void setTimeBetweenChanges(int timeBetweenDirChanges){
        this.timeBetweenChanges = timeBetweenDirChanges;
    }
    
    public void initialize() throws IllegalActionException {
        super.initialize();
        //Set event's initial position according to a Spectrogram defined on the positionSpectFile
        ChangeRequest doRandomize;
        doRandomize = new MoMLChangeRequest(this, this.getContainer(), genPosition());
        this.getContainer().requestChange(doRandomize);
        workspace().incrVersion();
        setTimeBetweenChanges(((Stochastic)this.stocParameterGenerator).timeBetweenDirChanges.next());
        setDirection(((Stochastic)this.stocParameterGenerator).direction.next());
    }
    
    public double [] calculatePosition() { 
     // rewrite calculatePosition from the super SimpleMobileEvent using 
     // recalculate event's location (x, y), according to movement parameters (velocity, direction, timeBetweenDirectionChanges) defined by histograms 
        double veloc, dir;
        veloc = ((Stochastic)this.stocParameterGenerator).velocity.next();
        if (timeBetweenChanges == 0){ 
            dir = (double) ((Stochastic)this.stocParameterGenerator).direction.next();
            setDirection(dir);
            // Reinitialise period 
            if (stochasticTimeBetweenChanges.getExpression()=="TRUE"){
            //   read new period value!
            	setTimeBetweenChanges(((Stochastic)this.stocParameterGenerator).timeBetweenDirChanges.next());
           }
        } else timeBetweenChanges--;
        setVelocity(veloc);
        return super.calculatePosition();
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
