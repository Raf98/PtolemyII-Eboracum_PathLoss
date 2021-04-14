package eboracum.pathloss;

import ptolemy.data.expr.Parameter;

public class PathLossMethods {
    
    final double LIGHT_SPEED = 299792458;
    
    public double frequency;
    public double frequencyMHz;
    public double transmitterAntennaHeight;
    public double receiverAntennaHeight;
    
    double pathLossPropagationFactor;
    
    public PathLossMethods() {
        
    }
    
    public PathLossMethods(double frequency) {
        this.frequency = frequency;
        this.frequencyMHz = this.frequency/1e6;
    }
    
    public double freeSpace(double frequency, double distance) {
        double wavelength = LIGHT_SPEED/frequency;
        
        return 20*Math.log10(((4*Math.PI*distance)/wavelength));
    }
    
    public double freeSpace(double distance) {
        double wavelength = LIGHT_SPEED/frequency;
                
        return 20*Math.log10(((4*Math.PI*distance)/wavelength));
    }
    
    public int freeSpaceMaximumDistance() { //equation assuming 20dB as maximum power that can be lost
        double wavelength = LIGHT_SPEED/frequency;
        double maximumPathLoss = 20;
        double maxDistance = ((Math.pow(10, maximumPathLoss/20)*wavelength)/(4*Math.PI))*1000;
        
        System.out.println(maxDistance);
        
        return (int)maxDistance;
    }
    /*
     * @maximumPathLoss should be a negative number that represents the maximum path loss 
     * */
    public int freeSpaceMaximumDistance(double maximumPathLoss) {
        double wavelength = LIGHT_SPEED/frequency;
        double maxDistance = ((Math.pow(10, maximumPathLoss/20)*wavelength)/(4*Math.PI))*1000;
        
        
        /*double count;
        for(int i = 0; i < 100; i++) {
            count = (double)i/100;
            System.out.println("PL for " + count + " km: " + this.freeSpace(count)+ " dB");
        }*/
        
        return (int)maxDistance;
    }
    
    public double largeUrbanHata(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor;
        double frequencyMHz = frequency/1e6;
                
         if(frequency > 300e6) {
             receiverAntennaCorrectionFactor = 3.2*Math.log10(Math.pow(11.75*receiverAntennaHeight, 2)) - 4.97;
         } else {
             receiverAntennaCorrectionFactor = 8.29*Math.log10(Math.pow(1.54*receiverAntennaHeight, 2)) - 1.1;
         }
        
        
        return (69.55 + 26.16*Math.log10(frequencyMHz) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    public double largeUrbanHata(double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor;
        
        if(this.frequency > 300e6) {
            receiverAntennaCorrectionFactor = 3.2*Math.pow(Math.log10(11.75*receiverAntennaHeight), 2) - 4.97;
            //System.out.println("Receiver: " + receiverAntennaCorrectionFactor);
        } else {
            receiverAntennaCorrectionFactor = 8.29*Math.pow(Math.log10(1.54*receiverAntennaHeight), 2) - 1.1;
        }
        
        return (69.55 + 26.16*Math.log10(this.frequencyMHz) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance/1000));
        
    }
    
    public double smallMediumUrbanHata(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
       double receiverAntennaCorrectionFactor;
       double frequencyMHz = frequency/1e6;
        
       receiverAntennaCorrectionFactor = (1.1*Math.log10(frequencyMHz) - 0.7)*receiverAntennaHeight 
               - (1.56*Math.log10(frequencyMHz) - 0.8);
        
        return (69.55 + 26.16*Math.log10(frequencyMHz) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    public double smallMediumUrbanHata(double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double receiverAntennaCorrectionFactor = (1.1*Math.log10(this.frequencyMHz) - 0.7)*receiverAntennaHeight 
                - (1.56*Math.log10(this.frequencyMHz) - 0.8);
        
        return (69.55 + 26.16*Math.log10(this.frequencyMHz) - 13.82*Math.log10(transmitterAntennaHeight)
                - receiverAntennaCorrectionFactor + (44.9 - 6.55*Math.log10(transmitterAntennaHeight))
                *Math.log10(distance));
        
    }
    
    public double ruralHata(double frequency, double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        double frequencyMHz = frequency/1e6;
        //if(distance) checar se area eh maior ou menor pra aplicar um metodo ou outro posteriormente
        double urbanHataPathLoss = this.largeUrbanHata(frequency, distance,
                transmitterAntennaHeight, receiverAntennaHeight);
        
        return urbanHataPathLoss - 4.78*Math.pow((Math.log10(frequencyMHz)), 2) + 18.33*Math.log10(frequencyMHz) - 35.94;
    }
    
    public double ruralHata(double distance,
            double transmitterAntennaHeight, 
            double receiverAntennaHeight) {
        //if(distance) checar se area eh maior ou menor pra aplicar um metodo ou outro posteriormente
        double urbanHataPathLoss = this.largeUrbanHata(distance,
                transmitterAntennaHeight, receiverAntennaHeight);
        System.out.println("URBAN: " + urbanHataPathLoss);
        
        return urbanHataPathLoss - 4.78*Math.pow((Math.log10(this.frequencyMHz)), 2) + 18.33*Math.log10(this.frequencyMHz) - 35.94;
    }
}
