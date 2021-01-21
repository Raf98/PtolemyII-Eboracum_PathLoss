package eboracum.simulation.benchmarks;

import eboracum.simulation.BenchmarksGenerator;
import eboracum.simulation.util.HistogramSpectrogramFactory;

public class GeneralSimulation extends BenchmarksGenerator {

	protected void runBenchmarks(){
			this.scenarioDimensionXY = new int[]{1000,1000};
			//HistogramSpectrogramFactory.newUniformSpectrogram(this.scenarioDimensionXY[1]-100, this.scenarioDimensionXY[0]-100, "spectStartPosition.csv");
			//simBeePaperConfig("Uniform");
			HistogramSpectrogramFactory.newNormalSpectrogram(this.scenarioDimensionXY[1]-100, this.scenarioDimensionXY[0]-100, "spectStartPosition.csv");
			simBeePaperConfig("Normal");
			//HistogramSpectrogramFactory.newInvertNormalSpectrogram(this.scenarioDimensionXY[1]-100, this.scenarioDimensionXY[0]-100, "spectStartPosition.csv");
			//simBeePaperConfig("InvNormal");
	}
		
	public void simBeePaperConfig(String dist) {
		String simulationIdentification;
		for (int j = 0; j <= 0; j++){
			simulationIdentification = "NodeGrid49_SideSink_EventSpaceDist"+dist+"_NotRebuild"+j;
			this.nodesRandomizeFlag = false;
			this.mainGatewayCenteredFlag = false;
			this.beginSetupBeePaperConfig();
			this.setupBeePaperConfig(j);
			this.endSetupBeePaperConfig(simulationIdentification);
			int numOfRounds = 30;
			for (int i=0; i<numOfRounds; i++) {
				try {
					this.run(simulationIdentification,i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void beginSetupBeePaperConfig(){
		this.initBattery = 5400000/2;
		this.commCover = 160;
		this.sensorCover = 120;
		int numOfNodes = 49;
		if (!nodesRandomizeFlag) generateGridPosition(numOfNodes);
		this.wirelessSensorNodesType = "GeneralType";
		this.cpuCost = 50;
		this.idleCost = 0.3;
		this.wirelessNodes.clear();
		this.wirelessNodes.put("sensor.SimpleWSNNode", numOfNodes);
		//this.wirelessNodes.put("sensor.controled.DummyWSNNode", numOfNodes);
		//this.wirelessNodes.put("sensor.controled.RandomControledWSNNode", numOfNodes);
		//this.wirelessNodes.put("sensor.controled.PSControledWSNNode", numOfNodes);
		//this.wirelessNodes.put("sensor.controled.AntControledWSNNode", numOfNodes);
		//this.wirelessNodes.put("sensor.controled.GreedyWSNNode", numOfNodes);
		this.wirelessEvents.clear();
		//HistogramSpectrogramFactory.newHistogram(359, "directionHist.csv");
		//this.wirelessEvents.put(new WirelessEvent("E0", 0.0018, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "StochasticPeriodicJumperEvent"), 1);
		this.wirelessEvents.put(new WirelessEvent("E0", 0.0018, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "FullyStochasticMobileEvent"), 100);
		//this.wirelessEvents.put(new WirelessEvent("E0", 0.0018, false,"{1.0, 0.0, 0.0, 1.0}", "<task id=\"0\"><cpu name=\"SimpleFIFOBasedCPU\" cost=\"1\"/></task>", "RandomMobileEvent"), 100);
		
	}
	
		private void setupBeePaperConfig(int j){
			 switch (j) {
	        	case 0: HistogramSpectrogramFactory.newPoissonHistogram(120, "periodHist.csv");
	        			break;
	        	/*case 1:	HistogramSpectrogramFactory.newPoissonHistogram(100, "periodHist.csv");
	        			break;
	        	case 2:	HistogramSpectrogramFactory.newPoissonHistogram(1000, "periodHist.csv");
    					break;
	        	case 3:	HistogramSpectrogramFactory.newPoissonHistogram(10000, "periodHist.csv");
						break;
	        	case 4:	HistogramSpectrogramFactory.newUniformHistogram(10, "periodHist.csv");
    					break;
	       		case 5:	HistogramSpectrogramFactory.newUniformHistogram(100, "periodHist.csv");
	        			break;
	       		case 6:	HistogramSpectrogramFactory.newUniformHistogram(1000, "periodHist.csv");
	       				break;
	       		case 7:	HistogramSpectrogramFactory.newUniformHistogram(10000, "periodHist.csv");
	       				break;
	       		case 8:	HistogramSpectrogramFactory.newHistogram(10, "periodHist.csv");
	       				break;
	       		case 9:	HistogramSpectrogramFactory.newHistogram(100, "periodHist.csv");
	       				break;
	       		case 10:HistogramSpectrogramFactory.newHistogram(1000, "periodHist.csv");
	       				break;
	       		case 11:HistogramSpectrogramFactory.newHistogram(10000, "periodHist.csv");
	       				break;*/
			 }
		}
		
		private void endSetupBeePaperConfig(String simulationIdentification){
			generateEventsXML();
			this.network = "SimpleAdHocNetwork";
			this.rebuildNetworkWhenGatewayDies= false;
			this.synchronizedRealTime = false;
			generateModel(simulationIdentification);
		}

		@SuppressWarnings("unused")
		public static void main(String[] args){
			BenchmarksGenerator b = new GeneralSimulation();
		}
		
}
