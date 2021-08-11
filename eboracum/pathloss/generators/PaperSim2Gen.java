package eboracum.pathloss.generators;

public class PaperSim2Gen extends PaperSimulationGenerator{
    
    @Override
    void setEboracumProps() {
        super.setEboracumProps();

        initBattery = 25200000;
        commCover = 300;
        sensorCover = 70;
        cpuEnergyCost = 420;
        idleEnergyCost = 20;
        synchronizedRealTime = false;
    }
    
    public void setFileProps() {
        filePath = "eboracum/pathloss/simulations/paper/";
        dataFilesPath = filePath + "data/";
        simulationName = "PaperSim2_";
    }
    
    @Override
    public void fillCommCosts() {
        super.fillCommCosts();
        commCosts[0] = 282.74;
        commCosts[1] = 288.39429;
        commCosts[2] = 322.32303;
        commCosts[3] = 384.52572;
        commCosts[4] = 479.62152;
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PaperSim2Gen();
        gen.run();
    }
    
}
