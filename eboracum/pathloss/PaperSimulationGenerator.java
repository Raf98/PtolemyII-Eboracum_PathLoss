package eboracum.pathloss;

import java.io.IOException;

public class PaperSimulationGenerator extends PathLossSimulationGenerator {
    
    @Override
    void setChannelProps(){
        super.setChannelProps();
        transmitterPower = 2;
        receiverSensivity = -104;
        isPLDOCalculated = true;
        n = 3.5;
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
        synchronizedRealTime = false;
    }
    
    @Override
    void setEventsProps() {
        period = 28800;
        differentEvents = false;
        eventType = "E0";
    }
    
    public void setFileProps() {
        filePath = "eboracum/pathloss/simulations/paper/";
        dataFilesPath = filePath + "data/";
        simulationName = "PaperSimulation";
    }
    
    public void run() {
        setAllProps();
        
        final double[] commCosts = {0.219178, 0.22356164, 0.249863, 0.298082, 0.385753}; 
                
        int initialTxPower = (int)this.transmitterPower;
        for(int i = 0; i < 5; i++) {
            this.transmitterPower = initialTxPower + (i*3);
            this.eventType = "E"+i;
            //this.simulationName = "PaperSimulation" + (int)this.transmitterPower;
            this.dataReportFile = this.dataFilesPath + this.simulationName + (int)this.transmitterPower + ".csv";
            createPrimalEntityInXMLFile(simulationName + (int)this.transmitterPower);
            
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            this.commCost = commCosts[i];
            createDataReportFile();
            addEventToListOfEvents("E" + i, commCosts[i], 0, 1);
            
            setNodesProps();
            setVergilProps();
        }
        
        writeEventsInPlatformConfig();
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PaperSimulationGenerator();
        gen.run();
    }
}
