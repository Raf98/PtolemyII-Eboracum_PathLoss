package eboracum.simulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSummarizer {
	
	DecimalFormat df = new DecimalFormat("#.#######");
	//ArrayList<Double> simulationTotalTime = new ArrayList<Double>();
	//ArrayList<Double> numberofSensedEvents = new ArrayList<Double>();
	HashMap<String, ArrayList<Double>> nodeRemainingBattery = new HashMap<String,ArrayList<Double>>();
	HashMap<String, ArrayList<Double>> nodeNumberofSentMessages = new HashMap<String,ArrayList<Double>>();
	HashMap<String, ArrayList<Double>> nodeNumberofEnqueuedEvents = new HashMap<String,ArrayList<Double>>();
	HashMap<String, ArrayList<Double>> nodeNumberofSensoredEvents = new HashMap<String,ArrayList<Double>>();
	HashMap<String, ArrayList<Double>> nodeTimeofDeath = new HashMap<String,ArrayList<Double>>();
	HashMap<String, ArrayList<Double>> eventNumberofTimesSensed = new HashMap<String,ArrayList<Double>>();
	HashMap<String, ArrayList<Double>> eventsByDaySensed = new HashMap<String,ArrayList<Double>>();
	
	public void go(){
		//for (int j = 1; j < 7; j++){
			//String file = "MeshMobileAnt_3/sensor.mobile.controlled.AntControlledDRMobileWSNNode_Mesh100";
			String file = "NodeGrid81_SideSink_EventSpaceDistUniform_Rebuild_sensor.controlled.AntControlledWSNNode";
			for (int i = 0; i < 3; i++){
				//this.collectDataFromFile(file+"_"+i+".csv");
				collectEventsSensedFromFile(file+"_"+i+".csv");
				saveSummaryFile(eventsByDaySensed, file+"eventsByDaySensed.csv");}
			//saveSummaryFile(nodeRemainingBattery, "NodeGrid49_SideSink_EventSpaceDistUniform_NoNetRebuild_EventsVarID"+0+"_nodeRemainingBattery.csv");
			//saveSummaryFile(nodeNumberofSentMessages, "NodeGrid49_SideSink_EventSpaceDistUniform_NoNetRebuild_EventsVarID"+j+"_nodeNumberofSentMessages.csv");
			//saveSummaryFile(nodeNumberofEnqueuedEvents, "NodeGrid49_SideSink_EventSpaceDistUniform_NoNetRebuild_EventsVarID"+j+"_nodeNumberofEnqueuedEvents.csv");
			//saveSummaryFile(nodeNumberofSensoredEvents, file+"_nodeNumberofSensoredEvents.csv");
			//saveSummaryFile(nodeTimeofDeath, file+"_"+j+"_nodeTimeofDeath.csv");}
			//saveSummaryFile(eventNumberofTimesSensed, file+"_eventNumberofTimesSensed.csv");
		//}
	}
	
	public void saveSummaryFile(HashMap<String, ArrayList<Double>> data, String newFile){
		List<String> lines = new ArrayList<String>();
		for (Map.Entry<String, ArrayList<Double>> entry : data.entrySet()) {
			//System.out.println(entry.getKey()+" "+mean(entry.getValue())+" "+Math.sqrt(var(entry.getValue())));
			lines.add(entry.getKey()+";"+df.format(mean(entry.getValue()))+";"+df.format(Math.sqrt(var(entry.getValue()))));
			//System.out.println(entry.getKey());
			//for (int i=0; i < entry.getValue().size(); i++){
			//	System.out.println(df.format(entry.getValue().get(i)));
			//}
		}
		writeToFile(lines,"eboracum/data/"+newFile);
	}
	
	public static double var(ArrayList<Double> a) {
        if (a.size() == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum += (a.get(i) - avg) * (a.get(i) - avg);
        }
        return sum / (a.size() - 1);
    }
	
	public static double mean(ArrayList<Double> a) {
        if (a.size() == 0) return Double.NaN;
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum = sum + a.get(i);
        }
        return sum / a.size();
    }

	public void collectEventsSensedFromFile(String file){
		String csvFile = "eboracum/data/"+file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		boolean resultsFlag = false;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] rline = line.split(cvsSplitBy);
				if (rline[0].equals("Nodes")) break;
				//System.out.println(rline[0]);
				if (resultsFlag){
								//System.out.println(rline[0]+" "+rline[1]);
				
									if (!eventsByDaySensed.containsKey(rline[0])) eventsByDaySensed.put(rline[0], new ArrayList<Double>());
									eventsByDaySensed.get(rline[0]).add(Double.parseDouble(rline[1]));
				}	
				if (rline[0].equals("Number of Sensed Events by the WSN per Day")) resultsFlag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void collectDataFromFile(String file){
		String csvFile = "eboracum/data/"+file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		boolean resultsFlag = false;
		boolean nodesFlag = false;
		boolean eventsFlag = false;
		int count = 0;
		int jumpCount = 999999;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
				String[] rline = line.split(cvsSplitBy);
				//System.out.println(rline[0]);
				if (resultsFlag){
					if (rline[0].equals("Simulation Total Time")) {
						//System.out.println(rline[1]);
					}
					else if (rline[0].equals("Number of Sensed Events by the WSN")) {
						//System.out.println(rline[1]);
					}
					else {
						if (eventsFlag){
							if (rline[0].equals("Class Name")) jumpCount = count+2;
							if (jumpCount < count) {
								if (!eventNumberofTimesSensed.containsKey(rline[1])) eventNumberofTimesSensed.put(rline[1], new ArrayList<Double>());
								eventNumberofTimesSensed.get(rline[1]).add(Double.parseDouble(rline[4]));
								//System.out.println(rline[1]+" "+rline[2]+" "+rline[3]+" "+rline[4]);
							}
						}
						if (rline[0].equals("Nodes")) nodesFlag = true;
						if (nodesFlag){
							if (rline[0].equals("Events")) {
								nodesFlag = false;
								eventsFlag = true;
							}
							else{
								if (rline[0].equals("Class Name")) jumpCount = count;
								if (jumpCount < count) {
									if (!nodeRemainingBattery.containsKey(rline[1])) nodeRemainingBattery.put(rline[1], new ArrayList<Double>());
									nodeRemainingBattery.get(rline[1]).add(Double.parseDouble(rline[2]));
									if (!nodeNumberofSentMessages.containsKey(rline[1])) nodeNumberofSentMessages.put(rline[1], new ArrayList<Double>());
									nodeNumberofSentMessages.get(rline[1]).add(Double.parseDouble(rline[3]));
									if (!nodeNumberofEnqueuedEvents.containsKey(rline[1])) nodeNumberofEnqueuedEvents.put(rline[1], new ArrayList<Double>());
									nodeNumberofEnqueuedEvents.get(rline[1]).add(Double.parseDouble(rline[5]));
									if (!nodeNumberofSensoredEvents.containsKey(rline[1])) nodeNumberofSensoredEvents.put(rline[1], new ArrayList<Double>());
									nodeNumberofSensoredEvents.get(rline[1]).add(Double.parseDouble(rline[6]));
									if (!nodeTimeofDeath.containsKey(rline[1])) nodeTimeofDeath.put(rline[1], new ArrayList<Double>());
									nodeTimeofDeath.get(rline[1]).add(Double.parseDouble(rline[7]));
									//System.out.println(df.format(nodeRemainingBattery.get(nodeRemainingBattery.size()-1))+" ");
									//System.out.print(df.format(nodeNumberofSentMessages.get(nodeNumberofSentMessages.size()-1))+" ");
									//System.out.print(df.format(nodeNumberofEnqueuedEvents.get(nodeNumberofEnqueuedEvents.size()-1))+" ");
									//System.out.print(df.format(nodeNumberofSensoredEvents.get(nodeNumberofSensoredEvents.size()-1))+" ");
									//System.out.println((nodeTimeofDeath));
								}
							}
						}
					}
				}
				if (rline[0].equals("Results")) resultsFlag = true;
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(List<String> lines, String path){
		FileWriter f;
		try {
			f = new FileWriter(new File(path));
			for(String line : lines){
				f.write(line+ "\n");
			}
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		DataSummarizer ds = new DataSummarizer();
		ds.go();
	}

}
