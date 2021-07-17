package eboracum.pathloss;

import java.io.IOException;

public class PaperSimulationGenerator extends PathLossSimulationGenerator {
    
    @Override
    void setChannelProps(){
        transmitterPower = 2;
        receiverSensivity = -137;
        isPLDOCalculated = true;
        n = 4;
        antennaGain = 2.15;
    }
    
    @Override
    void setNodesProps() {
        numberOfNodes = 36;
        nodesOnRow = 6;
        nodesDistance = 150;
        
        sinkX = 50;
        double numOfNodes = numberOfNodes;
        sinkY = Math.ceil(numOfNodes/nodesOnRow/2)*nodesDistance;
    }
    
    @Override
    void setEboracumProps() {
        super.setEboracumProps();

        initBattery = 25200000;
        commCover = 300;
        sensorCover = 70;
        cpuEnergyCost = 2880;
        idleEnergyCost = 20;
    }
    
    @Override
    void setEventsProps() {
        period = 1440;
    }
    
    @Override
    void setFlags() {
        differentEvents = false;
    }
    
    public void run() {
        setAllProps();
        
        filePath = "eboracum/pathloss/simulations/paper/";
        
        int initialTxPower = (int)this.transmitterPower;
        for(int i = 0; i < 5; i++) {
            this.transmitterPower = initialTxPower + (i*3);
            createPrimalEntityInXMLFile("PaperSimulation" + (int)this.transmitterPower);
            
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            setNodesProps();
            setVergilProps();
        }
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PaperSimulationGenerator();
        gen.run();
    }
}
