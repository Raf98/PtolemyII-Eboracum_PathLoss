package eboracum.pathloss;

import java.io.IOException;
import java.util.List;

public class PowerVariationSimulationGenerator extends PathLossSimulationGenerator{
    
    void setChannelProps(){
        transmitterPower = 11;
        receiverSensivity = -137;
        isPLDOCalculated = true;
        n = 4;
        antennaGain = 2.15;
    }
    
    void setNodesProps() {
        numberOfNodes = 9;
        nodesOnRow = 3;
        nodesDistance = 1000;
        
        sinkX = 50;
        double numOfNodes = numberOfNodes;
        sinkY = Math.ceil(numOfNodes/nodesOnRow/2)*nodesDistance;
    }
    
    public void run() {
        setVergilProps();
        setEboracumProps();
        setNodesProps();
        setChannelProps();
        
        filePath = "eboracum/pathloss/simulations/";
        
        int initialTxPower = 11;
        for(int i = 0; i < 4; i++) {
            this.transmitterPower = initialTxPower + (i*3);
            createPrimalEntityInXMLFile("PLPowerSimulation" + (int)this.transmitterPower);
            
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
        PathLossSimulationGenerator gen = new PowerVariationSimulationGenerator();
        gen.run();
    }
}
