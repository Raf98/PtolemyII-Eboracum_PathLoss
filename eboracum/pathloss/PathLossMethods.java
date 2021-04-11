package eboracum.pathloss;

import ptolemy.data.expr.Parameter;

public class PathLossMethods {
    
    final double LIGHT_SPEED = 299792458;
    
    public double frequency;
    public double transmitterAntennaHeight;
    public double receiverAntennaHeight;
    
    double pathLossPropagationFactor;
    
    public PathLossMethods() {
        
    }
    
    public PathLossMethods(double frequency) {
        this.frequency = frequency;
    }
    
    public double freeSpace(double frequency, double distance) {
        double wavelength = LIGHT_SPEED/frequency;
        
        return 20*Math.log10((wavelength/(4*Math.PI*distance)));
    }
    
    public double freeSpace(double distance) {
        double wavelength = LIGHT_SPEED/frequency;
                
        return 20*Math.log10((wavelength/(4*Math.PI*distance)));
    }
    
    public int freeSpaceMaximumDistance() { //equation assuming 20dB as maximum power that can be lost
        double wavelength = LIGHT_SPEED/frequency;
        double maximumPathLoss = -20;
        double maxDistance = (wavelength/(4*Math.PI*Math.pow(10, maximumPathLoss/20)))*1000;
        
        return (int)maxDistance;
    }
    /*
     * @maximumPathLoss should be a negative number that represents the maximum path loss 
     * */
    public int freeSpaceMaximumDistance(double maximumPathLoss) {
        double wavelength = LIGHT_SPEED/frequency;
        double maxDistance = (wavelength/(4*Math.PI*Math.pow(10, maximumPathLoss/20)))*1000;
        
        return (int)maxDistance;
    }
    
    public double largeUrbanHata(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor = (1.1*Math.log10(frequency) - 0.7)*receiverAntennaHeight 
                - (1.56*Math.log10(frequency) - 0.8);
        
        return (69.55 + 26.16*Math.log10(frequency) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    public double largeUrbanHata(double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor = (1.1*Math.log10(this.frequency) - 0.7)*receiverAntennaHeight 
                - (1.56*Math.log10(this.frequency) - 0.8);
        
        return (69.55 + 26.16*Math.log10(this.frequency) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    public double smallMediumUrbanHata(double frequency, double distance,
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
    
    public double smallMediumUrbanHata(double distance,
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
    
    public double ruralHata(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        //if(distance) checar se area eh maior ou menor pra aplicar um metodo ou outro posteriormente
        double urbanHataPathLoss = this.largeUrbanHata(frequency, distance,
                transmitterAntennaHeight, receiverAntennaHeight);
        
        return urbanHataPathLoss - 4.78*Math.pow((Math.log10(frequency)), 2) + 18.33*Math.log10(frequency) - 35.94;
    }
    
    public double ruralHata(double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        //if(distance) checar se area eh maior ou menor pra aplicar um metodo ou outro posteriormente
        double urbanHataPathLoss = this.largeUrbanHata(distance,
                transmitterAntennaHeight, receiverAntennaHeight);
        
        return urbanHataPathLoss - 4.78*Math.pow((Math.log10(this.frequency)), 2) + 18.33*Math.log10(this.frequency) - 35.94;
    }
}
