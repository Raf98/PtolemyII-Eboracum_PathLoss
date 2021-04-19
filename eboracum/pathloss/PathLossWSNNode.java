package eboracum.pathloss;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import eboracum.wsn.network.node.sensor.SimpleWSNNode;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;

public class PathLossWSNNode extends SimpleWSNNode{

    private static final long serialVersionUID = 1L;
    PathLossRectangleAttribute[][] pathLossPoints;
    Parameter operationFrequency;
    Parameter antennaHeight;
    PathLossMethods pathLossMethods;

    public PathLossWSNNode(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
        operationFrequency = new Parameter(this,"OperationFrequency");
        operationFrequency.setExpression("800e6");
        antennaHeight = new Parameter(this, "AntennaHeight");
        antennaHeight.setExpression("2");
        
        System.out.println("FREQUENCY: " + operationFrequency.getExpression());
        System.out.println("ANTENNA HEIGHT: " + antennaHeight.getExpression());
        
    }
    
    double euclideanDistanceBetweenNodeAndPoint(double wsnX, double wsnY, double pX, double pY) {
        
        double angle = Math.atan2((wsnY - pY), (wsnX - pX))* 180 / Math.PI;
        //System.out.println("Angle: " + angle); 
        double distance = Math.sqrt((wsnX-pX)*(wsnX-pX)+(wsnY-pY)*(wsnY-pY));
        //System.out.println("Distance: " + distance);
        
        
        return distance;
    }
    
    @Override
    public void initialize() throws IllegalActionException {
        // TODO Auto-generated method stub
        super.initialize();
        
        
    }
    
    @Override
    protected void buildIcon() throws IllegalActionException, NameDuplicationException {
        // TODO Auto-generated method stub
        super.buildIcon();
        
        Location wsnNodeLocation = (Location) this._circle.getAttribute("_location");
        //System.out.println(wsnNodeLocation.getLocation()[1]);
        double wsnNodeX = wsnNodeLocation.getLocation()[0];
        double wsnNodeY = wsnNodeLocation.getLocation()[1];
        double nodeWidth = Double.parseDouble(this._circle.width.getValueAsString());
        double nodeHeight = Double.parseDouble(this._circle.height.getValueAsString());
        
        //redefinir pra descobrir de acordo com o Path Loss free space
        /*Location circleCommLocation = (Location) this._circle_comm.getAttribute("_location");
        System.out.println(circleCommLocation.getLocation()[1]);
        double circleCommX = circleCommLocation.getLocation()[0];
        double circleCommY = circleCommLocation.getLocation()[1];
        double circleCommWidth = Double.parseDouble(this._circle_comm.width.getValueAsString());
        double circleCommHeight = Double.parseDouble(this._circle_comm.height.getValueAsString());
        System.out.println(circleCommWidth);
        System.out.println(circleCommHeight);
        */
        
        pathLossMethods = new PathLossMethods(800e6);
                
        double circleCommRadius = pathLossMethods.freeSpaceMaximumDistance();
        this.commCoverRadius.setExpression(Double.toString(circleCommRadius));
        //System.out.println("DISTANCE: " + circleCommRadius);
        //double circleCommRadius = 200;//Double.parseDouble(this.commCoverRadius.getValueAsString());
        double initialX = wsnNodeX - circleCommRadius;// - nodeWidth;
        double initialY = wsnNodeY - circleCommRadius;// - nodeHeight;
        //System.out.println("INITIAL X: " + initialX);
        //System.out.println("INITIAL Y: " + initialY);
        int numRowsCols = (int) ((circleCommRadius*2)/nodeWidth + 1); 

        Shape circleCommShape = new Ellipse2D.Double(initialX, initialY, circleCommRadius*2, circleCommRadius*2);
        
        
       
        
        pathLossPoints = new PathLossRectangleAttribute[numRowsCols][numRowsCols];
        
        for(int i = 0; i < numRowsCols; ++i) {
            for(int j = 0; j < numRowsCols; ++j) {
                pathLossPoints[i][j] = new PathLossRectangleAttribute(this.node_icon, "rec(" + i + ", " + j + ")");
                
                pathLossPoints[i][j].x = initialX + j*nodeWidth;
                pathLossPoints[i][j].y = initialY + i*nodeHeight;
                pathLossPoints[i][j].width.setToken(Double.toString(nodeWidth));
                pathLossPoints[i][j].height.setToken(Double.toString(nodeHeight));
                pathLossPoints[i][j].distanceFromNode = euclideanDistanceBetweenNodeAndPoint(
                        wsnNodeX, wsnNodeY, pathLossPoints[i][j].x, pathLossPoints[i][j].y);
                
                Point2D point = new Point((int)pathLossPoints[i][j].x, (int)pathLossPoints[i][j].y);
                
                if(circleCommShape.contains(point)) {
                  pathLossPoints[i][j].available = true;  
                  //pathLossPoints[i][j].fillColor.setToken("{0.0, 1.0, 0.0, 0.5}");
                  
                  pathLossPoints[i][j].hataPathLoss = pathLossMethods.ruralHata(pathLossPoints[i][j].distanceFromNode, 
                          2, 2);
                  
                  System.out.println("HATA: " + pathLossPoints[i][j].hataPathLoss);
                  System.out.println("Distance: " + pathLossPoints[i][j].distanceFromNode);
                  
                  if(pathLossPoints[i][j].hataPathLoss < 20) {
                      //pathLossPoints[i][j].fillColor.setToken("{1.0, 1.0, 0.0, 0.7}");
                  }

                } else {
                    pathLossPoints[i][j].available = false;  
                    //pathLossPoints[i][j].fillColor.setToken("{1.0, 0.0, 0.0, 0.0}");
                }
                
                Location l = new Location(pathLossPoints[i][j],"_location");
                l.setLocation(new double[] {pathLossPoints[i][j].x, pathLossPoints[i][j].y});
                pathLossPoints[i][j].lineColor.setToken("{0.0, 0.0, 0.0, 0.0}");
            }
        }
    }

}
