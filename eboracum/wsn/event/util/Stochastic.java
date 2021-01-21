package eboracum.wsn.event.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.util.Time;
import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class Stochastic extends TypedAtomicActor {
	   
	private static final long serialVersionUID = 1L;
	
	public StringParameter triggerTimeHistFile;
	public StringParameter durationHistFile;
    public StringParameter periodHistFile;
    public StringParameter velocityHistFile;
    public StringParameter directionHistFile;
    public StringParameter timeBetweenDirChangesHistFile;
    public StringParameter positionSpectFile;
	private AliasMethod triggerTime;
	private Time newTriggerTimeControler;
	public AliasMethod period;
	public AliasMethod position[];
	public AliasMethod duration;
	public AliasMethod velocity;
	public AliasMethod direction;
	public AliasMethod timeBetweenDirChanges;
	
	public Stochastic(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
		triggerTimeHistFile = new StringParameter (this, "TriggerTimeHistFile"); 
	    triggerTimeHistFile.setExpression("eboracum/wsn/event/util/triggerTimeHist.csv");
	    positionSpectFile = new StringParameter (this, "PositionSpectFile"); 
        positionSpectFile.setExpression("eboracum/wsn/event/util/spectStartPosition.csv");
	    durationHistFile = new StringParameter (this, "DurationHistFile"); 
	    durationHistFile.setExpression("eboracum/wsn/event/util/durationHist.csv");
	    periodHistFile = new StringParameter (this, "PeriodHistFile"); 
	    periodHistFile.setExpression("eboracum/wsn/event/util/periodHist.csv");
	    velocityHistFile = new StringParameter (this, "VelocityHisttFile"); 
        velocityHistFile.setExpression("eboracum/wsn/event/util/velocityHist.csv");
        directionHistFile = new StringParameter (this, "DirectionHistFile"); 
        directionHistFile.setExpression("eboracum/wsn/event/util/directionHist.csv");
        timeBetweenDirChangesHistFile = new StringParameter (this, "TimeBetweenDirChangesHistFile"); 
        timeBetweenDirChangesHistFile.setExpression("eboracum/wsn/event/util/timeBetweenChanges.csv");
        //
        this.triggerTime = loadAliasClassFromHistogram(triggerTimeHistFile.getExpression());
        this.newTriggerTimeControler = Time.NEGATIVE_INFINITY;
	    this.position = loadAliasClassesFromSpectogram(positionSpectFile.getExpression());
	    this.period = loadAliasClassFromHistogram(periodHistFile.getExpression());
	    this.duration = loadAliasClassFromHistogram(durationHistFile.getExpression());
	    this.velocity = loadAliasClassFromHistogram(velocityHistFile.getExpression());
	    this.direction = loadAliasClassFromHistogram(directionHistFile.getExpression());
	    this.timeBetweenDirChanges = loadAliasClassFromHistogram(timeBetweenDirChangesHistFile.getExpression());
	}
	
	public double getTriggerTime(){
		double temp;
		if (this.newTriggerTimeControler.equals(Time.NEGATIVE_INFINITY)){
			temp = this.triggerTime.next();
		}
		else {
			temp = this.triggerTime.next()+this.newTriggerTimeControler.getDoubleValue();
		}
		this.newTriggerTimeControler = this.newTriggerTimeControler.add(temp);		
		return temp;
	}

	public AliasMethod loadAliasClassFromHistogram(String histoFile){
		ArrayList<Double> histo = histogramReader(histoFile);
		return new AliasMethod(normalize(histo));
	}
	
	public AliasMethod[] loadAliasClassesFromSpectogram(String histoFile){
		ArrayList<ArrayList<Double>> spectro = spectogramReader(histoFile);
		ArrayList<Double> tempRouletteWheel = new ArrayList<Double>();
		int indexY = 0;
		for (int i=0; i<spectro.size(); i++){
			tempRouletteWheel.add(0.0);
			for (int j=0; j<spectro.get(i).size(); j++){
				tempRouletteWheel.set(i, spectro.get(i).get(j)+tempRouletteWheel.get(i));
			}
		}
		AliasMethod[] temp = new AliasMethod[2]; 
		temp[1] = new AliasMethod(normalize(tempRouletteWheel));
		temp[0] = new AliasMethod(normalize(spectro.get(indexY)));
		return temp;
	}
	
	private ArrayList<Double> normalize(ArrayList<Double> tempRouletteWheel){
		double sum=0;
		for (Double value : tempRouletteWheel){
			sum += value;
		}
		for (int i =0; i < tempRouletteWheel.size(); i++){
			tempRouletteWheel.set(i, (tempRouletteWheel.get(i)/sum));
		}
		return tempRouletteWheel;
	}
	
	/*private static void printRoulette(ArrayList<Double> rouletteWheel){
		DecimalFormat df = new DecimalFormat("#.#########");
		for (Double value : rouletteWheel){
			System.out.println(df.format(value));
		}
	}*/
	
	private ArrayList<ArrayList<Double>> spectogramReader(String histoFile){
		BufferedReader br = null;
		String line = "";
		ArrayList<ArrayList<Double>> spectro = new ArrayList<ArrayList<Double>>();
		try {
			br = new BufferedReader(new FileReader(histoFile));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(";");
				if (!row[0].equals("")) {
					ArrayList<Double> temp = new ArrayList<Double>();
					for (int i=0; i<row.length; i++){
						if (i > 0) {
							temp.add(Double.parseDouble(row[i])); 
						}
					}
					spectro.add(temp);
				}
			}
			br.close();
			return spectro;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ArrayList<Double> histogramReader(String histoFile){
		BufferedReader br = null;
		String line = "";
		ArrayList<Double> histo = new ArrayList<Double>();
		try {
			br = new BufferedReader(new FileReader(histoFile));
			while ((line = br.readLine()) != null) {
				int binId = Integer.parseInt(line.split(";")[0]);
				double binValue = Double.parseDouble(line.split(";")[1]);
				histo.add(binId,binValue);
			}
			br.close();
			return histo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
