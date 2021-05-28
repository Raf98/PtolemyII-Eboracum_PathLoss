package eboracum.pathloss;

import ptolemy.data.BooleanToken;
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
    
    public Parameter pathLossD0;
    public double pathLossD0Value;
    public Parameter isPLD0Calculated;
    public boolean isPLD0CalculatedValue;
    

    public LogDistanceChannel(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        
        this.pathLossFactorNValue = 3;
        this.referenceDistanceD0Value = 1; 
        
        this.isPLD0CalculatedValue = false;
        this.pathLossD0Value = 65;
        
        pathLossFactorN = new Parameter(this, "n");
        pathLossFactorN.setTypeEquals(BaseType.DOUBLE);
        pathLossFactorN.setExpression(Double.toString(this.pathLossFactorNValue));
        pathLossFactorN.setToken(new DoubleToken(this.pathLossFactorNValue));
        
        referenceDistanceD0 = new Parameter(this, "d0");
        referenceDistanceD0.setTypeEquals(BaseType.DOUBLE);
        referenceDistanceD0.setExpression(Double.toString(this.referenceDistanceD0Value));
        referenceDistanceD0.setToken(new DoubleToken(this.referenceDistanceD0Value));
                
        pathLossD0 = new Parameter(this, "PL(d0)");
        pathLossD0.setTypeEquals(BaseType.DOUBLE);
        pathLossD0.setExpression(Double.toString(this.pathLossD0Value));
        pathLossD0.setToken(new DoubleToken(this.pathLossD0Value));
        
        isPLD0Calculated = new Parameter(this, "Calculated PL(d0)");
        isPLD0Calculated.setTypeEquals(BaseType.BOOLEAN);
        isPLD0Calculated.setExpression(Boolean.toString(this.isPLD0CalculatedValue));
        isPLD0Calculated.setToken(new BooleanToken(this.isPLD0CalculatedValue));
        
        calculatedRangeValue = this.calculateRangeLogDistance();
        
        //System.out.println("Range: " + calculatedRangeValue);
        //System.out.println("PathLoss - 300m: " + this.calculatePathLossLogDistance(300));
        
        defaultProperties.setExpression("{range = " + this.calculatedRangeValue +", power = Infinity, "
                + "pathloss = 0.0, maxPL = " + this.maximumPathLoss + "}");
        
        pathLossFactor
                .setExpression(this.calculatePathLoss(referenceDistanceD0Value) + "+10*n*log10(distance/d0)");
        
    }
    
    @Override
    public void initialize() throws IllegalActionException {
        super.initialize();
        
        this.maximumPathLoss = this.calculateMaxPathLoss();
        this.calculatedRangeValue = this.calculateRangeLogDistance();
        
        defaultProperties.setExpression("{range = " + this.calculatedRangeValue +", power = Infinity, "
                + "pathloss = 0.0, maxPL = " + this.maximumPathLoss + "}");
        
        double PLD0 = checkPLD0();
        
        pathLossFactor
        .setExpression(PLD0 + "+10*n*log10(distance/d0)");
        
        //System.out.println("PATH LOG: " + calculatePathLossLogDistance(1165.9144011798323));
    }
    
    
   int calculateRangeLogDistance() {
        pathLossFactorNValue = Double.valueOf(pathLossFactorN.getValueAsString());
        referenceDistanceD0Value = Double.valueOf(referenceDistanceD0.getValueAsString());
        
        //System.out.println("D0: " + referenceDistanceD0Value);
        //System.out.println((this.maximumPathLoss - this.getFreeSpacePathLoss(referenceDistanceD0Value))/(10*pathLossFactorNValue));
        
        double PLD0 = checkPLD0();
        
        double maxDistance = (Math.pow(10, (this.maximumPathLoss - PLD0)/(10*pathLossFactorNValue)
                + Math.log10(referenceDistanceD0Value)));
        
       // System.out.println("MAX DISTANCE - LOG DISTANCE: " + maxDistance);
        
        return (int)maxDistance;
    }
    
    int calculatePathLossLogDistance(double distance) {
        pathLossFactorNValue = Double.valueOf(pathLossFactorN.getValueAsString());
        referenceDistanceD0Value = Double.valueOf(referenceDistanceD0.getValueAsString());
        
        double PLD0 = checkPLD0();
        
        double pathLoss = PLD0 + 10*pathLossFactorNValue*Math.log10(distance/this.referenceDistanceD0Value);
       
        return (int)pathLoss;
    }
    
    int getFreeSpacePathLoss(double distance) {
        return super.calculatePathLoss(distance);
    }
    
    double checkPLD0() {
        isPLD0CalculatedValue = Boolean.valueOf(isPLD0Calculated.getValueAsString());
        pathLossD0Value = Double.valueOf(pathLossD0.getValueAsString());
        
        double PLD0;
        if(!Boolean.valueOf(isPLD0Calculated.getValueAsString())) {
            PLD0 = pathLossD0Value;
        } else {
            PLD0 = this.getFreeSpacePathLoss(referenceDistanceD0Value);
        }
        
        return PLD0;
    }

}
