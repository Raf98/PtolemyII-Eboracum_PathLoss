package eboracum.pathloss;

import eboracum.simulation.BenchmarksGenerator;
import eboracum.simulation.util.HistogramSpectrogramFactory;

public class BenchmarkSimulationTest extends BenchmarksGenerator{

        protected void runBenchmarks(){
                setupBasicConfig("test");
                try {
                        this.run("test",0);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
}

private void setupBasicConfig(String simulationIdentification){
        this.scenarioDimensionXY = new int[]{1000,1000};
        HistogramSpectrogramFactory.newInvertNormalSpectrogram(this.scenarioDimensionXY[0]-100, this.scenarioDimensionXY[0]-100, "spectStartPosition.csv");
        this.initBattery = 10;
        HistogramSpectrogramFactory.newHistogram(120, "triggerTimeHist.csv");
        this.commCover = 160;
        this.sensorCover = 120;
        int numOfNodes = 49;
        this.cpuCost = 50;
        this.idleCost = 1;
        this.nodesRandomizeFlag = false;
        if (!nodesRandomizeFlag) generateGridPosition(numOfNodes);
        this.mainGatewayCenteredFlag = true;
        this.wirelessSensorNodesType = "GeneralType";
        this.wirelessNodes.put("sensor.SimpleWSNNode", numOfNodes);
        this.wirelessEvents.put(new WirelessEvent("E", 0.0018, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E0", 0.219178, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E1", 0.22356164, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"2\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E2", 0.249863, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E3", 0.298082, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"2\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E4", 0.385753, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E5", 1.0, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"2\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E6", 1.0, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E7", 1.0, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"2\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E8", 1.0, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "PeriodicEvent"), 20);
        this.wirelessEvents.put(new WirelessEvent("E9", 1.0, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"2\"/></task>", "PeriodicEvent"), 20);
        generateEventsXML();
        this.network = "SimpleAdHocNetwork";
        this.rebuildNetworkWhenGatewayDies= false;
        this.synchronizedRealTime = true;
        //System.out.println(System.getProperty("user.dir"));
        generateModel(simulationIdentification);
        //System.out.println("AQUI");
}

@SuppressWarnings("unused")
public static void main(String[] args){
        BenchmarksGenerator b = new BenchmarkSimulationTest();
}

}
