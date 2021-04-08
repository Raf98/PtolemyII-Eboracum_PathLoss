package eboracum.pathloss;

import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.NamedObj;
import ptolemy.vergil.kernel.attributes.RectangleAttribute;

public class PathLossRectangleAttribute extends RectangleAttribute{

    double pathLoss;
    
    
    public PathLossRectangleAttribute(NamedObj container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
    }
    
    

}
