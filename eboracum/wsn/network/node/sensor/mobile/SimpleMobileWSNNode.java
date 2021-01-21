package eboracum.wsn.network.node.sensor.mobile;

import eboracum.wsn.network.node.sensor.cpu.SimpleFIFOBasedCPU;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;

public class SimpleMobileWSNNode extends BasicMobileWSNNode{

	private static final long serialVersionUID = 1L;
	public Parameter velocityParam;   
    public Parameter directionParam;

    public SimpleMobileWSNNode(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        velocityParam = new Parameter (this, "VelocityParam"); 
        velocityParam.setExpression("10");
        directionParam = new Parameter (this, "DirectionParam"); 
        directionParam.setExpression("0");
    }
    
    public void initialize() throws IllegalActionException {
		super.initialize();
		this.cpu = new SimpleFIFOBasedCPU();
	}
     public void setVelocity(double velocity){
        velocityParam.setExpression(Double.toString(velocity));
     }
     
     public void setDirection(double direction){
        directionParam.setExpression(Double.toString(direction));
     }
        
     public double getVelocity(){
         return Double.parseDouble(velocityParam.getExpression());
     }
     public double getDirection(){
         return Double.parseDouble(directionParam.getExpression());
     }
     
     public double [] newPosition() {
         double [] _newLocation;
         double deltaX, deltaY, directionCorrection;
         Location locationAttribute = (Location) this.getAttribute("_location");
         _newLocation = locationAttribute.getLocation(); 
         directionCorrection =getDirection(); 
         deltaX = format(Math.cos(Math.toRadians(directionCorrection)));
         if (deltaX >=0) { 
             deltaX =  Math.ceil(deltaX)*getVelocity();
         }
         else { 
             deltaX =  Math.floor(deltaX)*getVelocity();
         }
         _newLocation[0] = _newLocation[0] + deltaX;
         deltaY = format(Math.sin(Math.toRadians(directionCorrection))*-1); 
         if (deltaY >=0) { 
             deltaY =  Math.ceil(deltaY)*getVelocity(); }
         else {                                               
             deltaY =  Math.floor(deltaY)*getVelocity();
         }
        _newLocation[1] = _newLocation[1] + deltaY; 
        return _newLocation;
      }
       
     private static double format(double value) {
         return (double)Math.round(value * 1000000) / 1000000;
     }
 
}
