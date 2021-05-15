package eboracum.pathloss;

import ptolemy.data.DoubleToken;
import ptolemy.data.IntToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.Variable;
import ptolemy.data.type.BaseType;
import ptolemy.data.type.RecordType;
import ptolemy.data.type.Type;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class LogDistanceChannel extends FreeSpaceChannel{

    private static final long serialVersionUID = 1L;
    public Parameter pathLossFactorN;
    public Parameter referenceDistanceD0;
    
    public double pathLossFactorNValue;
    public double referenceDistanceD0Value;
    

    public LogDistanceChannel(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        
        this.pathLossFactorNValue = 3;
        this.referenceDistanceD0Value = 1; 
        
        pathLossFactorN = new Parameter(this, "n");
        pathLossFactorN.setTypeEquals(BaseType.DOUBLE);
        pathLossFactorN.setExpression(Double.toString(this.pathLossFactorNValue));
        pathLossFactorN.setToken(new DoubleToken(this.pathLossFactorNValue));
        
        referenceDistanceD0 = new Parameter(this, "d0");
        referenceDistanceD0.setTypeEquals(BaseType.DOUBLE);
        referenceDistanceD0.setExpression(Double.toString(this.referenceDistanceD0Value));
        referenceDistanceD0.setToken(new DoubleToken(this.referenceDistanceD0Value));
                
        calculatedRangeValue = this.calculateRangeLogDistance();
        
        //System.out.println("Range: " + calculatedRangeValue);
        //System.out.println("PathLoss - 300m: " + this.calculatePathLossLogDistance(300));
        
        defaultProperties.setExpression("{range = " + calculatedRangeValue +", power = Infinity, pathloss = 0.0}");

        /*
        // Force the type of the defaultProperties to at least include
        // the range field. This must be done after setting the value
        // above, because the value in the base class is not a subtype
        // of this specified type.
        String[] labels = { "range", "power", "pathloss" };
        Type[] types = { BaseType.DOUBLE, BaseType.DOUBLE,  BaseType.DOUBLE};
        RecordType type = new RecordType(labels, types);

        // Setting an upper bound allows the addition of fields.
        defaultProperties.setTypeAtMost(type);
        */
        
        pathLossFactor
                .setExpression(this.calculatePathLoss(referenceDistanceD0Value) + "+10*n*log10(distance/d0)");
        
    }
    
    @Override
    public void initialize() throws IllegalActionException {
        super.initialize();
        
        calculatedRangeValue = this.calculateRangeLogDistance();
        defaultProperties.setExpression("{range = " + calculatedRangeValue +", power = Infinity, pathloss = 0.0}");

        
        pathLossFactor
        .setExpression(this.getFreeSpacePathLoss(referenceDistanceD0Value) + "+10*n*log10(distance/d0)");
    }
    
    
    int calculateRangeLogDistance() {
        //double maximumPathLoss = 90;
        pathLossFactorNValue = Double.valueOf(pathLossFactorN.getValueAsString());
        referenceDistanceD0Value = Double.valueOf(referenceDistanceD0.getValueAsString());
        
        System.out.println("D0: " + referenceDistanceD0Value);
        System.out.println((this.maximumPathLoss - this.getFreeSpacePathLoss(referenceDistanceD0Value))/(10*pathLossFactorNValue));
        
        double maxDistance = (Math.pow(10, 
                (this.maximumPathLoss - this.getFreeSpacePathLoss(referenceDistanceD0Value))/(10*pathLossFactorNValue)
                + Math.log10(referenceDistanceD0Value)));
        
        System.out.println("MAX DISTANCE - LOG DISTANCE: " + maxDistance);
        
        return (int)maxDistance;
    }
    
    
    int calculatePathLossLogDistance(double distance) {
        double pathLoss = this.getFreeSpacePathLoss(referenceDistanceD0Value) 
                + 10*pathLossFactorNValue*Math.log10(distance/this.referenceDistanceD0Value);
       
        return (int)pathLoss;
    }
    
    int getFreeSpacePathLoss(double distance) {
        return super.calculatePathLoss(distance);
    }

}
