package eboracum.wsn.network.node;

import java.util.ArrayList;
import ptolemy.actor.NoTokenException;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class NetworkMainGateway extends WirelessNode {

	private static final long serialVersionUID = 1L;
	private static final String MAINGATEWAYCOLOR = "{0.0, 0.0, 0.0, 1.0}";
	
	public ArrayList <Integer> detailEventSensoredCounter; 
	public int eventSensoredCounter;
	public int eventSensoredGenCounter;
	private int dayCounter;
	private String lastSensedEvent;

	public NetworkMainGateway(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		this.iconColor = NetworkMainGateway.MAINGATEWAYCOLOR;
        Parameter networked = new Parameter(this,"networked");
        networked.setExpression("true");
		this.eventSensoredCounter = 0;
		this.dayCounter = 1;
	}
	
	public void initialize() throws IllegalActionException{
		super.initialize();
		this.eventSensoredCounter = 0;
		detailEventSensoredCounter = new ArrayList<Integer>();
		//createDataLogFile();
	}

	public void fire() throws NoTokenException, IllegalActionException {
		super.fire();
		battery.setExpression(initBattery.getValueAsString());
		if (this.receivedMessage != null) {
			//System.out.println("Sink pre flt:   >>>>>>> "+this.receivedMessage+" "+lastSensedEvent);
			if (!this.receivedMessage.equals(lastSensedEvent)){
				this.eventSensoredGenCounter++;
				//System.out.println("Sink:   >>>>>>> "+this.eventSensoredCounter);
				if (this.getDirector().getModelTime().getDoubleValue()/(3600*24) > this.dayCounter){
					this.eventSensoredCounter++;
					detailEventSensoredCounter.add(this.eventSensoredCounter);
				//	appendDataReportFile(this.dayCounter+";"+ this.eventSensoredCounter);
					this.dayCounter++;
					this.eventSensoredCounter = 0;
				}
				else
					this.eventSensoredCounter++;
				lastSensedEvent = this.receivedMessage;
			}
		}
	}

	/*
	private void createDataLogFile() {
		File file = new File("york/data/eventSensedLog.csv");  
        try {
			file.createNewFile();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void appendDataReportFile(String line) {
		File file = new File("york/data/eventSensedLog.csv");  
        try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter writer = new BufferedWriter( fw );
			writer.write(line); writer.newLine();
			writer.flush();
			writer.close();
			fw.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
