package eboracum.pathloss;


import java.math.BigDecimal;
import java.math.RoundingMode;

import ptolemy.data.DoubleToken;
import ptolemy.data.RecordToken;
import ptolemy.data.ScalarToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.Variable;
import ptolemy.data.type.BaseType;
import ptolemy.data.type.RecordType;
import ptolemy.data.type.Type;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.domains.wireless.lib.PowerLossChannel;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class FreeSpaceChannel extends PowerLossChannel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public final double LIGHT_SPEED = 299792458;
    
    public Parameter frequency;
    public double frequencyValue;
    public Variable wavelength;
    public double wavelengthValue;
    public double calculatedRangeValue;
    
    public double maximumPathLoss;
    
    public Parameter pathLossFactor;
    
    public double maxAntennaDimension;
    
    
    public Parameter transmitterPower;
    public double transmitterPowerValue;
    public Parameter receivedPower;
    public double receivedPowerValue;
    
    public Parameter antennaGain;
    public double transmitterAntennaGainValue;
    public double receiverAntennaGainValue;
    

    public FreeSpaceChannel(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        
        this.frequencyValue = 915e6;
        //this.maximumPathLoss = 120;
        this.maxAntennaDimension = 0.15;
        
        this.transmitterPowerValue = 0;
        this.receivedPowerValue = -123;
        
        this.transmitterAntennaGainValue = 0;
        this.receiverAntennaGainValue = this.transmitterAntennaGainValue;
        
        frequency = new Parameter(this, "Frequency(Hz)");
        frequency.setTypeEquals(BaseType.DOUBLE);
        frequency.setExpression(Double.toString(this.frequencyValue));
        frequency.setToken(new DoubleToken(this.frequencyValue));
        
        wavelength = new Variable(this, "wavelength");
        wavelengthValue = LIGHT_SPEED/Double.valueOf(frequency.getValueAsString());
        wavelength.setExpression(Double.toString(wavelengthValue));
        
        
        transmitterPower = new Parameter(this, "Transmitter Power(dBm)");
        transmitterPower.setTypeEquals(BaseType.DOUBLE);
        transmitterPower.setExpression(Double.toString(this.transmitterPowerValue));
        transmitterPower.setToken(new DoubleToken(this.transmitterPowerValue));
        
        receivedPower = new Parameter(this, "Received Power/Sensibility(dBm)");
        receivedPower.setTypeEquals(BaseType.DOUBLE);
        receivedPower.setExpression(Double.toString(this.receivedPowerValue));
        receivedPower.setToken(new DoubleToken(this.receivedPowerValue));
        
        antennaGain = new Parameter(this, "Antenna Gain(dBi)");
        antennaGain.setTypeEquals(BaseType.DOUBLE);
        antennaGain.setExpression(Double.toString(this.transmitterAntennaGainValue));
        antennaGain.setToken(new DoubleToken(this.transmitterAntennaGainValue));
        
        this.maximumPathLoss = calculateMaxPathLoss();
        this.calculatedRangeValue = this.calculateRange();
        
        //System.out.println("PathLoss - 300m: " + this.calculatePathLoss(300));
        
        //defaultProperties.setExpression("{range = " + this.calculatedRangeValue +", power = "+ changeTxPowerToW() +", "
        //        + "pathloss = " + this.maximumPathLoss + ", maxPL = " + this.maximumPathLoss + "}");
        
        defaultProperties.setExpression("{range = " + this.calculatedRangeValue +", power = "+ changeTxPowerToW() +", "
                + "pathloss = " + this.maximumPathLoss + "}");

        // Force the type of the defaultProperties to at least include
        // the range field. This must be done after setting the value
        // above, because the value in the base class is not a subtype
        // of this specified type.
        String[] labels = { "range", "power", "pathloss"};// , "maxPL"};
        Type[] types = { BaseType.DOUBLE, BaseType.DOUBLE,  BaseType.DOUBLE};//, BaseType.DOUBLE};
        RecordType type = new RecordType(labels, types);

        // Setting an upper bound allows the addition of fields.
        defaultProperties.setTypeAtMost(type);
        
        pathLossFactor = new Parameter(this, "pathLossFactor");
        pathLossFactor.setTypeEquals(BaseType.DOUBLE);
        pathLossFactor
                .setExpression("20*log10((4 * PI * distance)/wavelength)");
        /*"wavelength / ((4 * PI * distance)*(4 * PI * distance))"*/
    }
    
    @Override
    public void initialize() throws IllegalActionException {
        // TODO Auto-generated method stub
        super.initialize();
        
        this.maximumPathLoss = calculateMaxPathLoss();
        this.calculatedRangeValue = calculateRange();
        
        //System.out.println("PathLoss - 300m: " + calculatePathLoss(300));
        
        //defaultProperties.setExpression("{range = " + this.calculatedRangeValue +", power = "+ changeTxPowerToW() +", "
        //        + "pathloss = " + this.maximumPathLoss + ", maxPL = " + this.maximumPathLoss + "}");
        
        defaultProperties.setExpression("{range = " + this.calculatedRangeValue +", power = "+ changeTxPowerToW() +", "
                + "pathloss = " + this.maximumPathLoss + "}");

    }
    
    @Override
    public RecordToken transformProperties(RecordToken properties, WirelessIOPort source, WirelessIOPort destination)
            throws IllegalActionException {

        RecordToken merged = super.transformProperties(properties, source,
                destination);
        
        double pathLossFactorValue = ((DoubleToken) this.pathLossFactor
        .getToken()).doubleValue();
        
        //System.out.println("Free Space Path Loss: " + pathLossFactorValue + " dB");
        
        String[] names = { "pathloss" };
        Token[] values = { new DoubleToken(pathLossFactorValue) };
        RecordToken newPathLoss = new RecordToken(names, values);

        // Merge the calculated path loss into the merged token.
        RecordToken result = RecordToken.merge(newPathLoss, merged);

        
        return result;
    }
    
    @Override
    protected boolean _isInRange(WirelessIOPort source, WirelessIOPort destination, RecordToken properties)
            throws IllegalActionException {
        double range = Double.POSITIVE_INFINITY;
        boolean rangeIsSet = false;
        

        if (properties != null) {
            Token field = properties.get("range");

            if (field instanceof ScalarToken) {
                // NOTE: This may throw a NotConvertibleException, if,
                // example, a Complex or a Long is given.
                range = ((ScalarToken) field).doubleValue();
                rangeIsSet = true;
                
              //alteraçao para debug
                //System.out.println("isInRange called");
                //System.out.println("Range: " + range);
            }
        }

        if (!rangeIsSet) {
            // Type constraints in the constructor make the casts safe.
            RecordToken defaultPropertiesValue = (RecordToken) defaultProperties
                    .getToken();

            // Type of the field must be convertible to double, but
            // need not actually be a double.
            ScalarToken field = (ScalarToken) defaultPropertiesValue
                    .get("range");
            range = field.doubleValue();
            
            /*
            System.out.println("isInRange called - default");
            System.out.println("Range: " + range);
            System.out.println("Source: " + source.getFullName());
            System.out.println("Destination: " + destination.getFullName());
            */
        }
        
        double distanceBetweenTR = _distanceBetween(source, destination);
        boolean result = (distanceBetweenTR <= range && distanceBetweenTR > this.calculateFraunhoferDistance());
        
        //System.out.println("Distance: " + _distanceBetween(source, destination));
        //System.out.println("isInRange called - END");
        
        //System.out.println("Fraunhofer distance: " + this.calculateFraunhoferDistance());
        //System.out.println("Distance: " + distanceBetweenTR);
        //System.out.println("Is In Range? " + result);

        // Whether a port is in range depends on the
        // transmit properties of this sender, so we set up
        // a listener to be notified of any changes in those
        // properties.  Note that we need to do this even if the
        // properties argument to this method is null because while
        // a port may specify no properties now, it may later acquire
        // properties.
        if (source.getOutsideChannel() == this) {
            source.outsideTransmitProperties.addValueListener(this);
        } else {
            source.insideTransmitProperties.addValueListener(this);
        }

        return result;
    }
    
    
    double calculateRange() {
        //double maximumPathLoss = 80;
        frequencyValue = Double.valueOf(frequency.getValueAsString());
        wavelengthValue = LIGHT_SPEED/frequencyValue;
        
        double maxDistance = ((Math.pow(10, maximumPathLoss/20)*wavelengthValue)/(4*Math.PI));//*1000;
        
        //System.out.println("MAX DISTANCE - FS: " + maxDistance);
        
        return roundDouble(maxDistance, 2);
    }
    
    //distance in meters
    double calculatePathLoss(double distance) {
        wavelengthValue = LIGHT_SPEED/Double.valueOf(frequency.getValueAsString());
        //System.err.println(wavelengthValue);
        double pathLoss = 20*Math.log10((4 * Math.PI * distance)/wavelengthValue);   
        
        return  roundDouble(pathLoss, 2);
    }
    
    double calculateFraunhoferDistance() {
        double fraunhoferDistance = (2*Math.pow(this.maxAntennaDimension, 2))/this.wavelengthValue;
        //System.out.println("Fraunhofer: " + fraunhoferDistance);
        return fraunhoferDistance;
    }
    
    double calculateMaxPathLoss() {
        this.transmitterPowerValue = Double.valueOf(transmitterPower.getValueAsString());
        this.receivedPowerValue = Double.valueOf(receivedPower.getValueAsString());
        this.transmitterAntennaGainValue = this.receiverAntennaGainValue = Double.valueOf(antennaGain.getValueAsString());
        
        double maximumPathLoss = this.transmitterPowerValue + this.transmitterAntennaGainValue + 
                                    this.receiverAntennaGainValue - this.receivedPowerValue;
        //System.out.println("MAX PATH LOSS: " + maximumPathLoss);
        return maximumPathLoss;
    }
    
    double changeTxPowerToW() {
        double txPowerW = Math.pow(10,((this.transmitterPowerValue - 30)/10));
        return roundDouble(txPowerW, 4); 
    }
    
    double roundDouble(double num, int decimalPoint) {
        BigDecimal bd = new BigDecimal(num).setScale(decimalPoint, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
