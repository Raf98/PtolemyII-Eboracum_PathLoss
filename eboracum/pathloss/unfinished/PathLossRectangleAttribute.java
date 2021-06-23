package eboracum.pathloss.unfinished;

import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.NamedObj;
import ptolemy.vergil.kernel.attributes.RectangleAttribute;

public class PathLossRectangleAttribute extends RectangleAttribute{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public double x, y;
    public double freeSpacePathLoss;
    public double hataPathLoss;
    public double distanceFromNode;
    public double frequency;
    public double heightFactor;
    public boolean available;
    
    public PathLossRectangleAttribute(NamedObj container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
        this.heightFactor = 0;
    }
    
    public PathLossRectangleAttribute(NamedObj container, String name, double heightFactor)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
        this.heightFactor = heightFactor;
    }
    
    

}
