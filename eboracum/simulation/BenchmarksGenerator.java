package eboracum.simulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eboracum.wsn.event.BasicEvent;
import eboracum.wsn.network.node.WirelessNode;
import ptolemy.actor.Manager;
import ptolemy.actor.TypedCompositeActor;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.Location;
import ptolemy.kernel.util.Workspace;


public abstract class BenchmarksGenerator {

    final static String PLATFORMCONFIG = "eboracum/wsn/PlatformConfig.xml";

    protected Map<WirelessEvent, Integer> wirelessEvents = new HashMap<>();
    protected Map<String, Integer> wirelessNodes = new HashMap<>();
    protected String network;
    protected boolean rebuildNetworkWhenGatewayDies;
    protected int[] scenarioDimensionXY;
    protected double sensorCover;
    protected double commCover;
    protected double initBattery;
    protected int numOfNodes;
    protected boolean nodesRandomizeFlag;
    protected boolean mainGatewayCenteredFlag;
    protected double[][] gridPositions;
    protected boolean synchronizedRealTime;
    protected String wirelessSensorNodesType;
    protected double cpuCost;
    protected double idleCost;

    public BenchmarksGenerator() {
        this.runBenchmarks();
    }

    protected abstract void runBenchmarks();

    /*
	public void run(String simulationIdentification, int round) throws InterruptedException{
		try {
			createDataReportFile(simulationIdentification,round);
			Process p = Runtime.getRuntime().exec("java -Xmx8192m -classpath bin/ ptolemy.vergil.VergilApplication -visualsense -runThenExit eboracum/data/"+simulationIdentification+".xml -DataReportFile \"&quot;eboracum/data/"+simulationIdentification+"_"+round+".csv&quot;\"");
			/*no GUI*/
    //java -classpath . ptolemy.actor.gui.MoMLSimpleApplication eboracum/data/NodeGrid49_SideSink_EventSpaceDistUniform_NoNetRebuild_EventsVarID0.xml
    /*p.waitFor();
} catch (IOException e) {//ThenExit
    e.printStackTrace();
}
}*/


    public void run(String simulationIdentification, int round) throws
    InterruptedException{
        try {  
            createDataReportFile(simulationIdentification,round);
            Process p =
                    Runtime.getRuntime().exec(System.getProperty("user.dir")+"/bin/vergil -visualsense -" +
                            "runThenExit eboracum/data/"+simulationIdentification+".xml -DataReportFile" +
                            "&quot;eboracum/data/"+simulationIdentification+"_"+round+".csv&quot;\"");
            // pode tirar
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            // pode tirar
            String line;
            // pode tirar
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = p.waitFor();
            if (exitVal == 0) {
                System.out.println("### Terminou Simulação! ###");
            } else {
                System.out.println("### Problemas na Simulação! ###");
            }
            // System.exit(0);
            /*no GUI*/
            //java -classpath . ptolemy.actor.gui.MoMLSimpleApplication
            //eboracum/data/NodeGrid49_SideSink_EventSpaceDistUniform_NoNetRebuild_EventsVarID0.xml
        } catch (IOException e) {//ThenExit
            e.printStackTrace();
        }      
    }


    protected void generateGridPosition(int n){
        double divisor = Math.ceil(Math.sqrt(n))+1;
        double fracX = this.scenarioDimensionXY[0]/divisor;
        double fracY = this.scenarioDimensionXY[1]/divisor;
        this.gridPositions = new double[(int) Math.round(divisor*divisor)][2];
        int count = 0;
        double x = fracX;
        for (int i=1; i<divisor; i++){
            double y = fracY;
            for (int j=1; j<divisor; j++){
                gridPositions[count][0]=x;
                gridPositions[count][1]=y;
                count++;
                y += fracY;
            }
            x+= fracX;
        }
    }

    protected void generateRandomPosition(int n){
        double divisor = Math.ceil(Math.sqrt(n))+1;
        this.gridPositions = new double[(int) Math.round(divisor*divisor)][2];
        int count = 0;
        Random RandomGen = new Random();
        for (int i=1; i<divisor; i++){
            for (int j=1; j<divisor; j++){
                gridPositions[count][0]=RandomGen.nextInt(this.scenarioDimensionXY[0]-100)+50;
                gridPositions[count][1]=RandomGen.nextInt(this.scenarioDimensionXY[1]-100)+50;
                count++;
            }
        }
    }

    protected void generateModel(String simulationIdentification){
        Workspace world = new Workspace("World");
        TypedCompositeActor scenario = new TypedCompositeActor(world);
        Manager god;
        try {
            god = new Manager(world, "God");
            scenario.setManager(god);
            File fXmlFile = new File("eboracum/simulation/_base_model.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            // simulation main parameters
            changePropertyAttribute(doc, "GlobalInitBattery", Double.toString(this.initBattery));
            changePropertyAttribute(doc, "SensorCover", Double.toString(this.sensorCover));
            changePropertyAttribute(doc, "CommCover", Double.toString(this.commCover));
            // scenario dimension and position of the main gateway
            changePropertyAttributeOfProperty(doc, "Rectangle", "_location", "[50.0,50.0]");
            changePropertyAttributeOfProperty(doc, "Rectangle", "width", Double.toString(this.scenarioDimensionXY[0]-100));
            changePropertyAttributeOfProperty(doc, "Rectangle", "height", Double.toString(this.scenarioDimensionXY[1]-100));
            changePropertyAttributeOfEntity(doc, "NodeRandomizer", "range", "{{0.0, "+this.scenarioDimensionXY[0]+".0}, {0.0,"+this.scenarioDimensionXY[1]+".0}}");
            // generate Nodes
            int countNodes = 0;
            for (Map.Entry<String, Integer> entry : this.wirelessNodes.entrySet()) {
                for (int i=0; i<entry.getValue(); i++) {
                    @SuppressWarnings("rawtypes")
                    Class c = Class.forName("eboracum.wsn.network.node."+entry.getKey());
                    @SuppressWarnings({ "rawtypes", "unchecked" })
                    Constructor cc = c.getConstructor(new Class[]{CompositeEntity.class,String.class});
                    WirelessNode e = (WirelessNode) cc.newInstance(scenario, "Node"+i);					
                    ((Parameter)e.getAttribute("randomize")).setExpression(Boolean.toString(this.nodesRandomizeFlag));
                    ((Parameter)e.getAttribute("Network")).setExpression(this.network);
                    ((Parameter)e.getAttribute("CPUEnergyCost")).setExpression(String.valueOf(this.cpuCost));
                    ((Parameter)e.getAttribute("IdleEnergyCost")).setExpression(String.valueOf(this.idleCost));
                    ((Parameter)e.getAttribute("SynchronizedRealTime")).setExpression(String.valueOf(this.synchronizedRealTime));
                    setupNodesSpecificParameters(e);
                    if (!this.nodesRandomizeFlag){
                        Location l = new Location(e,"_location");
                        l.setLocation(this.gridPositions[countNodes]);
                    }					
                    ((Parameter)e.getAttribute("Type")).setExpression(this.wirelessSensorNodesType);
                    countNodes++;
                    Node fragmentNode = dBuilder.parse(new ByteArrayInputStream(e.exportMoMLPlain().getBytes())).getDocumentElement();
                    fragmentNode = doc.importNode(fragmentNode, true);
                    doc.getDocumentElement().appendChild(fragmentNode);
                }
            }
            // generate Events
            for (Map.Entry<WirelessEvent, Integer> entry : this.wirelessEvents.entrySet()) {
                for (int i=0; i<entry.getValue(); i++) {
                    @SuppressWarnings("rawtypes")
                    Class c = Class.forName("eboracum.wsn.event."+entry.getKey().getClassName());
                    @SuppressWarnings({ "rawtypes", "unchecked" })
                    Constructor cc = c.getConstructor(new Class[]{CompositeEntity.class,String.class});
                    BasicEvent e = (BasicEvent) cc.newInstance(scenario, entry.getKey().getType()+"_"+i);
                    e.color.setExpression(entry.getKey().getColor());
                    e.type.setExpression(entry.getKey().getType());
                    e.endType.setExpression(entry.getKey().getType());
                    //e.numberOfSensorProcessedEvents += i*1000;
                    //System.out.println(i);
                    Location l = new Location(e,"_location");
                    l.setLocation(new double[]{((this.scenarioDimensionXY[0])/2),((this.scenarioDimensionXY[1])/2)});
                    Node fragmentNode = dBuilder.parse(new ByteArrayInputStream(e.exportMoMLPlain().getBytes())).getDocumentElement();
                    fragmentNode = doc.importNode(fragmentNode, true);
                    doc.getDocumentElement().appendChild(fragmentNode);
                }
            }
            if (this.mainGatewayCenteredFlag)
                changePropertyAttributeOfEntity(doc, "NetworkMainGateway", "_location", "["+((this.scenarioDimensionXY[0])/2)+","+(this.scenarioDimensionXY[1]/2)+"]");
            else
                changePropertyAttributeOfEntity(doc, "NetworkMainGateway", "_location", "["+0+","+(this.scenarioDimensionXY[1]/2)+"]");
            changePropertyAttributeOfEntity(doc, "NetworkMainGateway", "Network", this.network);
            changePropertyAttributeOfEntity(doc, "NetworkMainGateway", "IdleEnergyCost", Double.toString(this.idleCost));
            changePropertyAttributeOfProperty(doc, "Wireless Director", "synchronizeToRealTime", String.valueOf(this.synchronizedRealTime));
            /*
			@SuppressWarnings("rawtypes")
			Class c = Class.forName("eboracum.wsn.network."+this.network);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Constructor cc = c.getConstructor(new Class[]{CompositeEntity.class,String.class});
			AdHocNetwork n = (AdHocNetwork) cc.newInstance(scenario, this.network);
			Location l = new Location(n,"_location");
			l.setLocation(new double[] {-200.0, -80.0});
			//
			Node fragmentNode = dBuilder.parse(new ByteArrayInputStream(n.exportMoMLPlain().getBytes())).getDocumentElement();
			fragmentNode = doc.importNode(fragmentNode, true);
			doc.getDocumentElement().appendChild(fragmentNode);
             */
            changePropertyAttributeOfEntity(doc, "SimpleAdHocNetwork", "RebuildNetwork", Boolean.toString(this.rebuildNetworkWhenGatewayDies));
            //
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DocumentType doctype = doc.getDoctype();
            if(doctype != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            }
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File("eboracum/data/"+simulationIdentification+".xml"));
            transformer.transform(source,file);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    protected void setupNodesSpecificParameters(WirelessNode e){
    }

    private void changePropertyAttributeOfEntity(Document doc, String entityName, String atributteName, String newValue){
        NodeList nList = doc.getElementsByTagName("entity");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nEntity = nList.item(i);
            if (((Element) nEntity).getAttribute("name").equals(entityName)){
                for (int j = 0; j < ((Element)nEntity).getElementsByTagName("property").getLength(); j++) {
                    Node nProperty = (Element)((Element)nEntity).getElementsByTagName("property").item(j);
                    if (((Element) nProperty).getAttribute("name").equals(atributteName))
                        ((Element) nProperty).setAttribute("value", newValue);
                }
            }
        }
    }

    private void changePropertyAttributeOfProperty(Document doc, String propertyName, String atributteName, String newValue){
        NodeList nList = doc.getElementsByTagName("property");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nEntity = nList.item(i);
            if (((Element) nEntity).getAttribute("name").equals(propertyName)){
                for (int j = 0; j < ((Element)nEntity).getElementsByTagName("property").getLength(); j++) {
                    Node nProperty = (Element)((Element)nEntity).getElementsByTagName("property").item(j);
                    if (((Element) nProperty).getAttribute("name").equals(atributteName))
                        ((Element) nProperty).setAttribute("value", newValue);
                }
            }
        }
    }

    private void changePropertyAttribute(Document doc, String atributteName, String newValue){
        NodeList nList = doc.getElementsByTagName("property");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nProperty = nList.item(i);
            if (((Element) nProperty).getAttribute("name").equals(atributteName)){
                ((Element) nProperty).setAttribute("value", newValue);
            }
        }
    }

    protected void generateEventsXML(){
        List<String> linesApplicationLoadXML = new ArrayList<String>();
        linesApplicationLoadXML.add("<load>");
        for (Map.Entry<WirelessEvent, Integer> entry : this.wirelessEvents.entrySet()) {
            linesApplicationLoadXML.add("<event type=\""+entry.getKey().type+"\" ordinary=\""+entry.getKey().ordinary+"\" commcost=\""+entry.getKey().commCost+"\">");
            linesApplicationLoadXML.add(entry.getKey().taskXML);
            linesApplicationLoadXML.add("</event>");
        }
        linesApplicationLoadXML.add("</load>");
        writeToFile(linesApplicationLoadXML, BenchmarksGenerator.PLATFORMCONFIG);
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

    private void createDataReportFile(String simulationIdentification, int round) {
        File file = new File("eboracum/data/"+simulationIdentification+"_"+round+".csv");  
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter( fw );
            writer.write("Simulation Identification;"+simulationIdentification); writer.newLine();
            writer.write("Round;"+round); writer.newLine();
            writer.write("Initial Battery;"+this.initBattery); writer.newLine();
            writer.write("Communication Radius;"+this.commCover); writer.newLine();
            writer.write("Sensor Cover Radius;"+this.sensorCover); writer.newLine();
            writer.write("Node Idle Energy Cost;"+this.idleCost); writer.newLine();
            writer.write("CPU Processing Energy Cost;"+this.cpuCost); writer.newLine();
            writer.write("Summary of Nodes"); writer.newLine();
            writer.write("Class Name;Number of Instances"); writer.newLine();
            for (Map.Entry<String, Integer> entry : this.wirelessNodes.entrySet()) {
                writer.write("eboracum.wsn.network.node."+entry.getKey()+";"+entry.getValue()); writer.newLine();
            }
            writer.write("Summary of Events"); writer.newLine();
            writer.write("Class Name;Type;Number of Instances;Tasks Cost Graph; Comm Energy Cost"); writer.newLine();
            for (Map.Entry<WirelessEvent, Integer> entry : this.wirelessEvents.entrySet()) {
                writer.write("eboracum.wsn.network.node."+entry.getKey().getClassName()+";"+entry.getKey().getType()+";"+entry.getValue()+";"+entry.getKey().taskXML+";"+entry.getKey().commCost); writer.newLine();
            }
            writer.newLine();
            writer.write("Results"); writer.newLine();
            writer.flush();
            writer.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendDataReportFile(String filename, String line) {
        File file = new File(filename);  
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

    protected class WirelessEvent{
        private String type;
        private boolean ordinary;
        private String color;
        private String taskXML;
        private String className;
        private double commCost;

        public WirelessEvent(String type, double commCost, boolean ordinary, String color, String taskXML, String className){
            this.type = type;
            this.ordinary = ordinary;
            this.color = color;
            this.taskXML = taskXML;
            this.className = className;
            this.commCost = commCost;
        }

        public String getType() {
            return type;
        }

        public String getColor() {
            return color;
        }

        public String getClassName() {
            return className;
        }

    }

    public static void changeParametersForGA(double initThreshold, double initStimulus, double delta, double alpha, double ksi, double ro, String xmlFile, int numOfNodes, int iter){
        Workspace world = new Workspace("World");
        TypedCompositeActor scenario = new TypedCompositeActor(world);
        Manager god;
        try {
            god = new Manager(world, "God");
            scenario.setManager(god);
            File fXmlFile = new File(xmlFile+".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            for (int i=0; i<numOfNodes; i++){
                staticChangePropertyAttributeOfEntity(doc, "Node"+i, "initThreshold", Double.toString(initThreshold));
                staticChangePropertyAttributeOfEntity(doc, "Node"+i, "initStimulus", Double.toString(initStimulus));
                staticChangePropertyAttributeOfEntity(doc, "Node"+i, "delta", Double.toString(delta));
                staticChangePropertyAttributeOfEntity(doc, "Node"+i, "alpha", Double.toString(alpha));
                staticChangePropertyAttributeOfEntity(doc, "Node"+i, "ksi", Double.toString(ksi));
                staticChangePropertyAttributeOfEntity(doc, "Node"+i, "ro", Double.toString(ro));	
            }
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DocumentType doctype = doc.getDoctype();
            if(doctype != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            }
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File(xmlFile+"_"+iter+".xml"));
            transformer.transform(source,file);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void staticChangePropertyAttributeOfEntity(Document doc, String entityName, String atributteName, String newValue){
        NodeList nList = doc.getElementsByTagName("entity");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nEntity = nList.item(i);
            if (((Element) nEntity).getAttribute("name").equals(entityName)){
                for (int j = 0; j < ((Element)nEntity).getElementsByTagName("property").getLength(); j++) {
                    Node nProperty = (Element)((Element)nEntity).getElementsByTagName("property").item(j);
                    if (((Element) nProperty).getAttribute("name").equals(atributteName))
                        ((Element) nProperty).setAttribute("value", newValue);
                }
            }
        }
    }



}
