package eboracum.wsn.network.node.sensor.mobile;

import eboracum.wsn.network.node.sensor.BasicWirelessSensorNode;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.ChangeRequest;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.moml.MoMLChangeRequest;

public abstract class BasicMobileWSNNode extends BasicWirelessSensorNode{

	private static final long serialVersionUID = 1L;

    public BasicMobileWSNNode(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        this.iconColor = "{0.1, 0.8, 0.5, 1.0}";
    }
    
    public boolean move() throws IllegalActionException{
        ChangeRequest doRandomize;
        try {
            doRandomize = new MoMLChangeRequest(this, this.getContainer(), moveNode());
            this.getContainer().requestChange(doRandomize);
        }  catch (NameDuplicationException e) {
            e.printStackTrace();
        }
        workspace().incrVersion();
        return true;
    }
     
     public String moveNode() throws IllegalActionException, NameDuplicationException{
         double [] _myLocation = newPosition();
         return setLocation(_myLocation[0], _myLocation[1]);      
     }
     
     public abstract double [] newPosition();
     
     public String setLocation(double x, double y) throws IllegalActionException{ 
         double[] p = new double[2];
         CompositeEntity container = (CompositeEntity) this.getContainer();
         p[0] = x;  
         p[1] = y; 
         return _getLocationSetMoML(container, this, p);
     }
     
     protected String _getLocationSetMoML(CompositeEntity container,
         Entity node, double[] location) throws IllegalActionException {
         Attribute locationAttribute = node.getAttribute("_location");
         String className = null;
         if (locationAttribute != null) {
             className = locationAttribute.getClass().getName();
             return "<property name=\"" + node.getName(container)
                     + "._location\" " + "class=\"" + className + "\" value=\"["
                     + location[0] + ", " + location[1] + "]\"/>\n";
         } else {
             throw new IllegalActionException(
                     "The _location attribute does not exist for node = " + node
                             + "with container = " + container);
         } 
     }
}
