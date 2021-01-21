package eboracum.simulation.benchmarks;

import eboracum.simulation.BenchmarksGenerator;
import eboracum.simulation.util.HistogramSpectrogramFactory;

public class MobileNodesSimulation extends BenchmarksGenerator{

	protected void runBenchmarks(){
		//simConfig(49, "sensor.controlled.AntControlledWSNNode", 160);
		//simConfig(49, "sensor.mobile.controlled.AntControlledDRMobileWSNNode", 160);
		//simConfig(64, "sensor.mobile.controlled.AntControlledDRMobileWSNNode", 160);
		//simConfig(81, "sensor.mobile.controlled.AntControlledDRMobileWSNNode", 160);
		//simConfig(100, "sensor.mobile.controlled.AntControlledDRMobileWSNNode", 160);
		//simConfig(64, "sensor.controlled.PSControlledWSNNode", 160);
		//simConfig(49, "sensor.mobile.controlled.PSControlledDRMobileWSNNode", 160);
		//simConfig(81, "sensor.controlled.PSControlledWSNNode", 160);
		//simConfig(100, "sensor.mobile.controlled.PSControlledDRMobileWSNNode", 160);
		//simConfig(100, "sensor.controlled.PSControlledWSNNode", 160);
		//simConfig(100, "sensor.mobile.controlled.AntControlledDRMobileWSNNode", 160);
		//simConfig(81, "sensor.mobile.DynamicReorganizedMobileWSNNode", 160);
		//simConfig(100, "sensor.mobile.DynamicReorganizedMobileWSNNode", 160);
		//simConfig(49, "sensor.SimpleWSNNode", 160);
		simConfig(49, "sensor.mobile.DynamicReorganizedMobileWSNNode", 160);
		//simConfig(81, "sensor.SimpleWSNNode", 160);
		//simConfig(100, "sensor.SimpleWSNNode", 160);
}

public void simConfig(int size, String algo, int commcover) {
	String simulationIdentification;
	simulationIdentification = algo+"_Random"+size;
	this.scenarioDimensionXY = new int[]{1000,1000};
	HistogramSpectrogramFactory.newUniformSpectrogram(this.scenarioDimensionXY[1]-100, this.scenarioDimensionXY[0]-100, "spectStartPosition.csv");
	this.nodesRandomizeFlag = true;
	this.mainGatewayCenteredFlag = false;
	this.rebuildNetworkWhenGatewayDies= true;
	this.setupSimConfig(simulationIdentification, size, algo, commcover);
	int numOfRounds = 1;
	for (int i=0; i<numOfRounds; i++) {
		try {
			this.run(simulationIdentification,i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

private void setupSimConfig(String simulationIdentification, int size, String algo, int commcover){
	this.initBattery = 5400000/2;
	this.commCover = commcover;
	this.sensorCover = 120;
	int numOfNodes = size;
	if (!nodesRandomizeFlag) generateGridPosition(numOfNodes);
	this.wirelessSensorNodesType = "GeneralType";
	this.cpuCost = 50;
	this.idleCost = 0.3;
	this.wirelessNodes.clear();
	this.wirelessNodes.put(algo, numOfNodes);
	this.wirelessEvents.clear();
	this.wirelessEvents.put(new WirelessEvent("E0", 0.0018, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "StochasticPeriodicJumperEvent"), 1);
	HistogramSpectrogramFactory.newPoissonHistogram(120, "periodHist.csv");
	generateEventsXML();
	this.network = "SimpleAdHocNetwork";
	this.synchronizedRealTime = false;
	generateModel(simulationIdentification);
}

@SuppressWarnings("unused")
public static void main(String[] args){
	BenchmarksGenerator b = new MobileNodesSimulation();
}

}
