package eboracum.pathloss;

import ptolemy.data.RecordToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.domains.wireless.kernel.WirelessReceiver;
import ptolemy.domains.wireless.lib.PowerLossChannel;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class PathLossChannel extends PowerLossChannel {
    
    final double LIGHT_SPEED = 299792458;
    
    Parameter frequency;
    Parameter transmitterAntennaHeight;
    Parameter receiverAntennaHeight;
    double transmitterAntennaGain;
    double receiverAntennaGain;
    
    double pathLossPropagationFactor;

    public PathLossChannel(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected double _distanceBetween(WirelessIOPort port1, WirelessIOPort port2) throws IllegalActionException {
        // TODO Auto-generated method stub
        return super._distanceBetween(port1, port2);
    }
    
    @Override
    protected void _transmitTo(Token token, WirelessIOPort sender, WirelessReceiver receiver, RecordToken properties)
            throws IllegalActionException {
        // TODO Auto-generated method stub
        super._transmitTo(token, sender, receiver, properties);
        //this.distance = _distanceBetween(port1, port2);
    }
    
    protected double freeSpacePathLoss(double frequency, double distance) {
        double wavelength = LIGHT_SPEED/frequency;
        
        return Math.log10(Math.pow((wavelength/(4*Math.PI*distance)), 2));
    }
    
    protected double largeUrbanHataPathLoss(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor = (1.1*Math.log10(frequency) - 0.7)*receiverAntennaHeight 
                - (1.56*Math.log10(frequency) - 0.8);
        
        return (69.55 + 26.16*Math.log10(frequency) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    protected double smallMediumUrbanHataPathLoss(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor;
        
        if(frequency > 300e6) {
            receiverAntennaCorrectionFactor = 3.2*Math.log10(Math.pow(11.75*receiverAntennaHeight, 2)) - 4.97;
        } else {
            receiverAntennaCorrectionFactor = 8.29*Math.log10(Math.pow(1.54*receiverAntennaHeight, 2)) - 1.1;
        }
        
        return (69.55 + 26.16*Math.log10(frequency) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    protected double ruralHataPathLoss(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        //if(distance) checar se area eh maior ou menor pra aplicar um metodo ou outro posteriormente
        double urbanHataPathLoss = this.largeUrbanHataPathLoss(frequency, distance,
                transmitterAntennaHeight, receiverAntennaHeight);
        
        return urbanHataPathLoss - 4.78*Math.pow((Math.log10(frequency)), 2) + 18.33*Math.log10(frequency) - 35.94;
    }

}
