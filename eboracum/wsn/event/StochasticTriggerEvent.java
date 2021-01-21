package eboracum.wsn.event;

import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class StochasticTriggerEvent extends TriggerEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StochasticTriggerEvent(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		// TODO Auto-generated constructor stub
	}
	
	public void genTimeTriggerInterval(){
		
	}
	
	public void genNumberOfTriggeredEvents(){
		
	}
	
	 public void genLifetime(){
	 //       int duration;
	  //      if (this.stocParameterGenerator != null)
	   //     	duration = ((Stochastic)this.stocParameterGenerator).duration.next();
	   // 	else
	   // 		duration = 0;
	    //    lifetime.setExpression(Integer.toString(duration));
	 }

}
