package eboracum.wsn.event;

import java.util.ArrayList;

import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class TriggerEvent extends AtomicEvent{

	public ArrayList<BasicEvent> triggeredEvents  = new ArrayList<BasicEvent>();
    public double timeOfExtintion;
    public double timeTriggerInterval;
    public double numberOfTriggeredEvents;
	
	public TriggerEvent(CompositeEntity container, String name) throws IllegalActionException, NameDuplicationException {
		super(container, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String genRandomPosition() {
		return "";
 //   	int [] location = new int[2];
  //  	if (this.stocParameterGenerator != null){
   // 		location[0] = ((Stochastic)this.stocParameterGenerator).position[0].next()+50;
//    		location[1] = ((Stochastic)this.stocParameterGenerator).position[1].next()+50;
//    	}
 //   	else{
 //   		location[0] = 0;
 //   		location[1] = 0; 
 //   	}
 //       return setLocation(location[0], location[1]);        
    }

}
