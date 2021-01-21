package eboracum.wsn.event;

import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;


public class AtomicEvent extends BasicEvent{
    // base concrete class to represent wsn events

	private static final long serialVersionUID = 1L;

	public AtomicEvent(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
    }
   
}
