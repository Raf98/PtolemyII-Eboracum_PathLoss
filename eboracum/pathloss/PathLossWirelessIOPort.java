package eboracum.pathloss;

import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.kernel.ComponentEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class PathLossWirelessIOPort extends WirelessIOPort {
    
    double pathLoss;

    public PathLossWirelessIOPort(ComponentEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
    }
    
   

}
