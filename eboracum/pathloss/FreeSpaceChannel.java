package eboracum.pathloss;

import ptolemy.data.DoubleToken;
import ptolemy.data.RecordToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.Variable;
import ptolemy.data.type.BaseType;
import ptolemy.data.type.RecordType;
import ptolemy.data.type.Type;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.domains.wireless.kernel.WirelessReceiver;
import ptolemy.domains.wireless.lib.PowerLossChannel;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class FreeSpaceChannel extends PowerLossChannel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    final double LIGHT_SPEED = 299792458;
    
    public Parameter frequency;
    public double frequencyValue;
    public Variable wavelength;
    
    Parameter transmitterAntennaHeight;
    Parameter receiverAntennaHeight;
    
    
    double transmitterAntennaGain;
    double receiverAntennaGain;
    
    public Parameter pathLossFactor;

    public FreeSpaceChannel(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        
        this.frequencyValue = 800e6;
        
        PathLossMethods pathLossMethods = new PathLossMethods(frequencyValue);
        double rangeValue = pathLossMethods.freeSpaceMaximumDistance();
        
        defaultProperties.setExpression("{range = " + rangeValue +", power = Infinity, pathloss = 0}");

        // Force the type of the defaultProperties to at least include
        // the range field. This must be done after setting the value
        // above, because the value in the base class is not a subtype
        // of this specified type.
        String[] labels = { "range", "power", "pathloss" };
        Type[] types = { BaseType.DOUBLE, BaseType.DOUBLE,  BaseType.DOUBLE};
        RecordType type = new RecordType(labels, types);

        // Setting an upper bound allows the addition of fields.
        defaultProperties.setTypeAtMost(type);
        
        frequency = new Parameter(this, "frequency");
        pathLossFactor.setTypeEquals(BaseType.DOUBLE);
        pathLossFactor.setExpression("800e6");
        pathLossFactor.setToken(new DoubleToken(800e6));
        
        wavelength = new Variable(this, "wavelength");
        double wl = LIGHT_SPEED/Double.valueOf(frequency.getValueAsString());
        wavelength.setExpression(Double.toString(wl));
        
        pathLossFactor = new Parameter(this, "pathLossFactor");
        pathLossFactor.setTypeEquals(BaseType.DOUBLE);
        pathLossFactor
                .setExpression("wavelength / ((4 * PI * distance)*(4 * PI * distance))");
    }
    
    @Override
    public RecordToken transformProperties(RecordToken properties, WirelessIOPort source, WirelessIOPort destination)
            throws IllegalActionException {

        RecordToken merged = super.transformProperties(properties, source,
                destination);
        
        double pathLossFactorValue = ((DoubleToken) this.pathLossFactor
        .getToken()).doubleValue();
        
        System.out.println("Free Space Path Loss: " + pathLossFactorValue + " dB");
        
        String[] names = { "pathloss" };
        Token[] values = { new DoubleToken(pathLossFactorValue) };
        RecordToken newPathLoss = new RecordToken(names, values);

        // Merge the calculated path loss into the merged token.
        RecordToken result = RecordToken.merge(newPathLoss, merged);

        
        return result;
    }
}