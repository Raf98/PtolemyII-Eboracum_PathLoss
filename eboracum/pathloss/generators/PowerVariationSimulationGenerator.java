package eboracum.pathloss.generators;

import java.io.IOException;
import java.util.List;

public class PowerVariationSimulationGenerator extends PathLossSimulationGenerator{
    
    @Override
    void setChannelProps(){
        super.setChannelProps();
        transmitterPower = 11;
        receiverSensivity = -137;
        isPLDOCalculated = true;
        n = 4;
        antennaGain = 2.15;
    }
    
    @Override
    void setNodesProps() {
        numberOfNodes = 9;
        nodesOnRow = 3;
        nodesDistance = 1000;
        
        sinkX = 50;
        double numOfNodes = numberOfNodes;
        sinkY = Math.ceil(numOfNodes/nodesOnRow/2)*nodesDistance;
    }
    
    @Override
    public void setFileProps() {
        super.setFileProps();
        filePath = "eboracum/pathloss/simulations/";
        dataFilesPath = filePath + "data/";
        simulationName = "PLPowerSimulation";   
    }
    
    @Override
    void setEventsProps() {
        super.setEventsProps();
        eventType = "E0";
    }
    
    @Override
    public void run() {
        setAllProps();
                
        int initialTxPower = 11;
        for(int i = 0; i < 4; i++) {
            this.transmitterPower = initialTxPower + (i*3);
            dataReportFile = dataFilesPath + simulationName + (int)this.transmitterPower + ".csv";
            createPrimalEntityInXMLFile(simulationName + (int)this.transmitterPower);
            
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            createDataReportFile();
            
            setNodesProps();
            setVergilProps();
        }
        
        for(int i = 0; i < 9; ++i) {
            addEventToListOfEvents("E" + i, commCost, taskID, taskCost);
        }
        
        writeEventsInPlatformConfig();
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PowerVariationSimulationGenerator();
        gen.run();
    }
}
