package eboracum.pathloss.generators;

public class PaperTestSyncSimGen extends PaperTestDESimGen{

    @Override
    void setEboracumProps() {
        super.setEboracumProps();
        synchronizedRealTime = true;
    }
    
    public void setFileProps() {
        super.setFileProps();
        simulationName = "PaperTestSyncSimulation";
    }

    
    @Override
    public void run() {
        super.run();
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PaperTestSyncSimGen();
        gen.run();
    }
}
