package eboracum.pathloss;

import ptolemy.data.DoubleToken;
import ptolemy.data.RecordToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.Variable;
import ptolemy.data.type.BaseType;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.domains.wireless.kernel.WirelessReceiver;
import ptolemy.domains.wireless.lib.PowerLossChannel;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class FreeSpaceChannel extends PowerLossChannel {
    
    final double LIGHT_SPEED = 299792458;
    
    public Parameter frequency;
    public Variable wavelength;
    
    Parameter transmitterAntennaHeight;
    Parameter receiverAntennaHeight;
    
    
    double transmitterAntennaGain;
    double receiverAntennaGain;
    
    Parameter pathLossFactor;

    public FreeSpaceChannel(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        
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
        // TODO Auto-generated method stub
        return super.transformProperties(properties, source, destination);
        /*double powerPropagationFactorValue = ((DoubleToken) powerPropagationFactor
                .getToken()).doubleValue();*/
    }
}
