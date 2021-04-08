package eboracum.pathloss;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import eboracum.wsn.network.node.sensor.SimpleWSNNode;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.NameDuplicationException;

public class PathLossWSNNode extends SimpleWSNNode{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    PathLossRectangleAttribute[][] pathLossPoints;

    public PathLossWSNNode(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
    }
    
    double euclideanDistanceBetweenNodeAndPoint(double wsnX, double wsnY, double pX, double pY) {
        
        double angle = Math.atan2((wsnY - pY), (wsnX - pX))* 180 / Math.PI;
        System.out.println("Angle: " + angle); 
        
        double distance = Math.sqrt((wsnX-pX)*(wsnX-pX)+(wsnY-pY)*(wsnY-pY));
        System.out.println("Distance: " + Math.sqrt(wsnX*pX+wsnY*pY));
        
        
        return Math.sqrt(wsnX*pX+wsnY*pY);
    }
    
    @Override
    protected void buildIcon() throws IllegalActionException, NameDuplicationException {
        // TODO Auto-generated method stub
        super.buildIcon();
        
        Location wsnNodeLocation = (Location) this.getAttribute("_location");
        double wsnNodeX = wsnNodeLocation.getLocation()[0];
        double wsnNodeY = wsnNodeLocation.getLocation()[1];
        
        Location circleCommLocation = (Location) this._circle_comm.getAttribute("_location");
        double circleCommX = circleCommLocation.getLocation()[0];
        double circleCommY = circleCommLocation.getLocation()[1];
        double circleCommWidth = Double.parseDouble(this._circle_comm.width.getValueAsString());
        double circleCommHeight = Double.parseDouble(this._circle_comm.height.getValueAsString());

        Shape circleCommShape = new Ellipse2D.Double(circleCommX, circleCommY, circleCommWidth, circleCommHeight);
        double circleCommRadius = Double.parseDouble(this.commCoverRadius.getValueAsString());
        
        
        double initialX = wsnNodeX - circleCommRadius;
        double initialY = wsnNodeY - circleCommRadius;
        int numRowsCols = (int) ((circleCommRadius*2)/circleCommWidth + 1); 
        
        pathLossPoints = new PathLossRectangleAttribute[numRowsCols][numRowsCols];
        
        for(int i = 0; i < numRowsCols; ++i) {
            for(int j = 0; j < numRowsCols; ++j) {
                pathLossPoints[i][j] = new PathLossRectangleAttribute(this.node_icon, "rec(" + i + ", " + j + ")");
                
                pathLossPoints[i][j].x = initialX + j*circleCommWidth;
                pathLossPoints[i][j].y = initialY + j*circleCommHeight;
                pathLossPoints[i][j].width.setToken(Double.toString(circleCommWidth));
                pathLossPoints[i][j].height.setToken(Double.toString(circleCommHeight));
                pathLossPoints[i][j].distanceFromNode = euclideanDistanceBetweenNodeAndPoint(
                        wsnNodeX, wsnNodeY, pathLossPoints[i][j].x, pathLossPoints[i][j].y);
                
                Point2D point = new Point((int)pathLossPoints[i][j].x, (int)pathLossPoints[i][j].y);
                
                if(circleCommShape.contains(point)) {
                  pathLossPoints[i][j].available = true;  
                  pathLossPoints[i][j].fillColor.setToken("{0.0, 1.0, 0.0, 0.05}");

                } else {
                    pathLossPoints[i][j].available = false;  
                    pathLossPoints[i][j].fillColor.setToken("{1.0, 0.0, 0.0, 0.05}");
                }
            }
        }
    }

}
