package eboracum.pathloss.generators;

public class PaperTestDESimGen extends PaperSimulationGenerator {

    @Override
    void setEboracumProps() {
        super.setEboracumProps();

        initBattery = 6080;
        cpuEnergyCost = 288;
        idleEnergyCost = 2;
        synchronizedRealTime = false;
    }
    
    @Override
    void setEventsProps() {
        period = 2880;
        differentEvents = false;
        eventType = "E0";
    }
    
    public void setFileProps() {
        filePath = "eboracum/pathloss/simulations/paper/test/";
        dataFilesPath = filePath + "data/";
        simulationName = "PaperTestDESimulation";
    }
    
    @Override
    public void run() {
        super.run();
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PaperTestDESimGen();
        gen.run();
    }
    
}
