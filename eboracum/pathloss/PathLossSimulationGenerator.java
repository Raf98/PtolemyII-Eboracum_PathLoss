package eboracum.pathloss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PathLossSimulationGenerator {

    protected File xmlFile;
    protected String filePath;
    protected FileWriter writer;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;

    protected String commChannelName;
    protected String commChannelType;
    protected String sensorChannelName;
    protected String networkName;
    protected String sinkName;
    protected double sinkX, sinkY;
    protected int numberOfNodes;
    protected int nodesOnRow;
    protected int nodesDistance;

    protected String _windowsProperties;
    protected String _vergilSize;
    protected double _vergilZoomFactor;
    protected String _vergilCenter;
    protected double defaultComponentsX, defaultComponentsY;

    protected double initBattery;
    protected double sensorCover;
    protected double commCover;
    protected String dataReportFile;

    protected double cpuEnergyCost;
    protected double idleEnergyCost;
    protected boolean synchronizedRealTime;

    protected List<Property> propertiesList;
    protected Map<Entity, List<Property>> entitiesToPropertiesMap;
    protected Map<Property, List<Property>> propertyToPropertiesMap;
    protected List<Relation> relationsList;
    protected List<Link> linksList;

    final protected String defaultSpacing = "    ";

    protected double transmitterPower;
    protected double receiverSensivity;
    protected double antennaGain;
    protected double frequency;
    protected double PLD0Value;
    protected boolean isPLDOCalculated;
    protected double n;

    protected double period;

    protected boolean differentEvents;
    protected String eventType;
    protected double commCost;

    String simulationName;

    protected class Entity {
        public String name, className;

        public Entity(String name, String className) {
            this.name = name;
            this.className = className;
        }
    }

    protected class Property extends Entity {
        public String value;

        public Property(String name, String className, String value) {
            super(name, className);
            this.value = value;
        }
    }

    protected class Relation extends Entity {

        public Relation(String name, String className) {
            super(name, className);
        }
    }

    protected class Link {
        public String port, relation;

        public Link(String port, String relation) {
            this.port = port;
            this.relation = relation;
        }

    }

    void createPrimalEntityInXMLFile(String fileName) {
        xmlFile = new File(filePath + fileName + ".xml");

        try {
            xmlFile.createNewFile();
            //System.out.println(xmlFile.getAbsolutePath());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            writer = new FileWriter(xmlFile.getAbsolutePath(), false);

            //DOCTYPE SEMPRE deve estar alinhado ao lado esquerdo do documento, senao provoca erro ao ser aberto o XML
            writer.write("<?xml version=\"1.0\" standalone=\"no\"?>\n"
                    + "<!DOCTYPE entity PUBLIC \"-//UC Berkeley//DTD MoML 1//EN\"\n" + "            \"MoML_1.dtd\">\n"
                    + "        <entity name=\" " + fileName + "\" class=\"ptolemy.actor.TypedCompositeActor\">\n");

            writer.write(
                    "    <property name=\"_createdBy\" class=\"ptolemy.kernel.attributes.VersionAttribute\" value=\"8.0.1_20101021\">\n"
                            + "    </property>\n"
                            + "    <property name=\"Wireless Director\" class=\"ptolemy.domains.wireless.kernel.WirelessDirector\">\n"
                            + "        <property name=\"synchronizeToRealTime\" class=\"ptolemy.data.expr.Parameter\" value=\""
                            + synchronizedRealTime + "\">\n" + "        </property>\n"
                            + "        <property name=\"_location\" class=\"ptolemy.kernel.util.Location\" value=\"["
                            + defaultComponentsX + ", " + defaultComponentsY + "]\">\n" + "        </property>\n"
                            + "        <property name=\"timeResolution\" class=\"ptolemy.actor.parameters.SharedParameter\" value=\"1E-9\">\n"
                            + "        </property>\n" + "    </property>\n");
            defaultComponentsX += 100;

            fillPropertiesMap();
            fillProperties();
            fillEntitiesMap();
            fillRelations();
            fillLinks();

            for (Property prop : propertiesList) {
                createPropertyInXMLFile(writer, prop, defaultSpacing);
            }

            for (Map.Entry<Entity, List<Property>> entry : entitiesToPropertiesMap.entrySet()) {
                createEntityInXMLFile(entry.getKey(), entry.getValue());
            }
            
            for (Relation rel : relationsList) {
                createRelationInXMLFile(writer, rel, defaultSpacing);
            }
            
            for (Link l : linksList) {
                createLinkInXMLFile(writer, l, defaultSpacing);
            }

            writer.write("</entity>");
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void fillEntitiesMap() {
        entitiesToPropertiesMap = new HashMap<>();
        List<Property> propertiesLocalList = new ArrayList<>();

        Entity entity = new Entity(sensorChannelName, "ptolemy.domains.wireless.lib.LimitedRangeChannel");
        propertiesLocalList
                .add(new Property("defaultProperties", "ptolemy.data.expr.Parameter", "{range = SensorCover}"));
        propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;

        propertiesLocalList = new ArrayList<>();
        entity = new Entity(commChannelName, commChannelType);
        propertiesLocalList.add(new Property("defaultProperties", "ptolemy.data.expr.Parameter",
                "{range = 0.0, power = 0.0, pathloss = 0.0, maxPL = 0.0}"));
        propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        fillChannelProps(propertiesLocalList);
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;

        propertiesLocalList = new ArrayList<>();
        entity = new Entity("EventTypeController", "eboracum.wsn.type.EventTypeController");
        propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;

        propertiesLocalList = new ArrayList<>();
        entity = new Entity(networkName, "eboracum.pathloss.PathLossAdHocNetwork");
        propertiesLocalList
                .add(new Property("NetworkSinks", "ptolemy.data.expr.StringParameter", "{" + sinkName + "}"));
        propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;

        propertiesLocalList = new ArrayList<>();
        entity = new Entity("PLDataReporter", "eboracum.pathloss.simulations.PLDataReporter");
        propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;

        propertiesLocalList = new ArrayList<>();
        entity = new Entity("Stop", "ptolemy.actor.lib.Stop");
        propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;

        propertiesLocalList = new ArrayList<>();
        entity = new Entity(sinkName, "eboracum.wsn.network.node.NetworkMainGateway");
        propertiesLocalList.add(new Property("RebuildNetworkWhenGatewayDies", "ptolemy.data.expr.Parameter", "false"));
        propertiesLocalList
                .add(new Property("_location", "ptolemy.kernel.util.Location", "[" + sinkX + ", " + sinkY + "]"));
        propertiesLocalList.add(new Property("CommChannelName", "ptolemy.data.expr.StringParameter", commChannelName));
        propertiesLocalList.add(new Property("Network", "ptolemy.data.expr.StringParameter", networkName));
        propertiesLocalList
                .add(new Property("IdleEnergyCost", "ptolemy.data.expr.Parameter", Double.toString(idleEnergyCost)));
        propertiesLocalList
                .add(new Property("CPUEnergyCost", "ptolemy.data.expr.Parameter", Double.toString(cpuEnergyCost)));
        propertiesLocalList.add(new Property("SynchronizedRealTime", "ptolemy.data.expr.Parameter",
                Boolean.toString(synchronizedRealTime)));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);

        createNodesAndItsEventsEntities();
    }

    protected void fillProperties() {
        propertiesList = new ArrayList<>();

        propertiesList.add(
                new Property("_windowProperties", "ptolemy.actor.gui.WindowPropertiesAttribute", _windowsProperties));
        propertiesList.add(new Property("_vergilSize", "ptolemy.actor.gui.SizeAttribute", _vergilSize));
        propertiesList.add(new Property("_vergilZoomFactor", "ptolemy.data.expr.ExpertParameter",
                Double.toString(_vergilZoomFactor)));
        propertiesList.add(new Property("_vergilCenter", "ptolemy.data.expr.ExpertParameter", _vergilCenter));

        propertiesList
                .add(new Property("GlobalInitBattery", "ptolemy.data.expr.Parameter", Double.toString(initBattery)));
        propertiesList.add(new Property("SensorCover", "ptolemy.data.expr.Parameter", Double.toString(sensorCover)));
        propertiesList.add(new Property("CommCover", "ptolemy.data.expr.Parameter", Double.toString(commCover)));
        propertiesList.add(new Property("DataReportFile", "ptolemy.data.expr.Parameter", dataReportFile));

    }

    protected void fillPropertiesMap() {

    }

    protected void fillRelations() {
        relationsList = new ArrayList<>();

        relationsList.add(new Relation("relation", "ptolemy.actor.TypedIORelation"));
        relationsList.add(new Relation("relation2", "ptolemy.actor.TypedIORelation"));
    }

    protected void fillLinks() {
        linksList = new ArrayList<>();
        
        linksList.add(new Link(networkName + ".out", "relation"));
        linksList.add(new Link("PLDataReporter.trigger", "relation"));
        linksList.add(new Link("PLDataReporter.out", "relation2"));
        linksList.add(new Link("Stop.input", "relation2"));
    }

    protected void createNodesAndItsEventsEntities() {
        double currentX = sinkX + nodesDistance, currentY = sinkY;
        double distanceFactor = 0;
        double numOfNodes = numberOfNodes;
        double nodesRelation = numOfNodes / nodesOnRow;

        if (Math.ceil(nodesRelation) % 2 == 0) {
            distanceFactor = Math.ceil(nodesRelation) / 2 - 0.5;
            //System.out.println("dist: " + distanceFactor);
            currentY = sinkY - (nodesDistance * distanceFactor);
        } else {
            distanceFactor = Math.floor((nodesRelation) / 2);
            currentY = sinkY - (nodesDistance * distanceFactor);
        }

        //System.out.println(currentY);

        Entity entity;
        List<Property> propertiesLocalList;

        //String eventType = "E";

        for (int i = 0; i < numberOfNodes; ++i) {
            if (i % nodesOnRow == 0 && i != 0) {
                currentY += nodesDistance;
                currentX = sinkX + nodesDistance;
                //System.out.println("i: " + i);
            }

            //System.out.println(currentY);
            entity = new Entity("SimpleWSNNode" + i, "eboracum.wsn.network.node.sensor.SimpleWSNNode");
            propertiesLocalList = new ArrayList<>();
            propertiesLocalList.add(
                    new Property("_location", "ptolemy.kernel.util.Location", "[" + currentX + ", " + currentY + "]"));
            propertiesLocalList
                    .add(new Property("CommChannelName", "ptolemy.data.expr.StringParameter", commChannelName));
            propertiesLocalList.add(new Property("Network", "ptolemy.data.expr.StringParameter", networkName));
            propertiesLocalList.add(
                    new Property("IdleEnergyCost", "ptolemy.data.expr.Parameter", Double.toString(idleEnergyCost)));
            propertiesLocalList
                    .add(new Property("CPUEnergyCost", "ptolemy.data.expr.Parameter", Double.toString(cpuEnergyCost)));
            propertiesLocalList.add(new Property("SynchronizedRealTime", "ptolemy.data.expr.Parameter",
                    Boolean.toString(synchronizedRealTime)));

            entitiesToPropertiesMap.put(entity, propertiesLocalList);

            entity = new Entity("PeriodicEvent" + i, "eboracum.wsn.event.PeriodicEvent");
            propertiesLocalList = new ArrayList<>();
            propertiesLocalList.add(new Property("_location", "ptolemy.kernel.util.Location",
                    "[" + (currentX + this.sensorCover / 2) + ", " + currentY + "]"));

            eventType = differentEvents ? "E" + i : eventType;
            propertiesLocalList.add(new Property("Type", "ptolemy.data.expr.StringParameter", eventType));
            propertiesLocalList.add(new Property("EndType", "ptolemy.data.expr.StringParameter", eventType));
            propertiesLocalList.add(new Property("Period", "ptolemy.data.expr.Parameter", Double.toString(period)));
            entitiesToPropertiesMap.put(entity, propertiesLocalList);

            currentX += nodesDistance;
        }
    }

    void createEntityInXMLFile(Entity entity, List<Property> properties) {
        try {
            writer.write(defaultSpacing + "<entity name=\"" + entity.name + "\" class=\"" + entity.className + "\">\n");

            for (Property prop : properties) {
                createPropertyInXMLFile(writer, prop, defaultSpacing + defaultSpacing);
            }

            writer.write(defaultSpacing + "</entity>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void createPropertyWithPropertiesInXMLFile(Property property, List<Property> properties) {
        try {
            writer.write(defaultSpacing + "<property name=\"" + property.name + "\" class=\"" + property.className
                    + "\" value=\"" + property.value + "\">\n");

            for (Property prop : properties) {
                createPropertyInXMLFile(writer, prop, defaultSpacing + defaultSpacing);
            }

            writer.write(defaultSpacing + "</property>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void createPropertyInXMLFile(FileWriter writer, Property property, String initialSpacing) {
        try {
            writer.write(initialSpacing + "<property name=\"" + property.name + "\" class=\"" + property.className
                    + "\" value=\"" + property.value + "\">\n");

            writer.write(initialSpacing + "</property>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void createRelationInXMLFile(FileWriter writer, Relation relation, String initialSpacing) {
        try {
            writer.write(initialSpacing + "<relation name=\"" + relation.name + "\" class=\"" + relation.className
                    + "\">\n");

            writer.write(initialSpacing + "</relation>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void createLinkInXMLFile(FileWriter writer, Link link, String initialSpacing) {
        try {
            writer.write(initialSpacing + "<link port=\"" + link.port + "\" relation=\"" + link.relation + "\">\n");

            writer.write(initialSpacing + "</link>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void setVergilProps() {
        _windowsProperties = "{bounds={0, 26, 1366, 742}, maximized=false}";
        _vergilSize = "[1161, 608]";
        _vergilZoomFactor = 0.1;
        _vergilCenter = "{3932.0, 2400.0}";

        defaultComponentsX = -1300;
        defaultComponentsY = 0;//-1500;
    }

    void setEboracumProps() {
        commChannelName = "LogDistanceChannel";
        commChannelType = "eboracum.pathloss." + commChannelName;
        sensorChannelName = "LimitedRangeChannel";
        networkName = "PathLossAdHocNetwork";
        sinkName = "NetworkMainGateway";

        initBattery = 10;
        commCover = 300;
        sensorCover = 90;
        dataReportFile = filePath + "PLSimulation_DataReport";

        cpuEnergyCost = 0;
        idleEnergyCost = 1;
        synchronizedRealTime = true;
    }

    void setNodesProps() {
        numberOfNodes = 9;
        nodesOnRow = 3;
        nodesDistance = 1000;

        sinkX = 50;
        double numOfNodes = numberOfNodes;
        sinkY = Math.ceil(numOfNodes / nodesOnRow / 2) * nodesDistance;
    }

    void setEventsProps() {
        period = 2;
        eventType = "E";
        differentEvents = true;
    }

    void setChannelProps() {
        transmitterPower = 2;
        receiverSensivity = -123;
        frequency = 915e6;
        PLD0Value = 50;
        isPLDOCalculated = true;
        antennaGain = 0;
        n = 3;
    }

    void fillChannelProps(List<Property> propertiesLocalList) {
        propertiesLocalList.add(new Property("Transmitter Power(dBm)", "ptolemy.data.expr.Parameter",
                String.valueOf(transmitterPower)));
        propertiesLocalList.add(new Property("Received Power/Sensibility(dBm)", "ptolemy.data.expr.Parameter",
                String.valueOf(receiverSensivity)));
        propertiesLocalList
                .add(new Property("Antenna Gain(dBi)", "ptolemy.data.expr.Parameter", String.valueOf(antennaGain)));
        propertiesLocalList
                .add(new Property("Frequency(Hz)", "ptolemy.data.expr.Parameter", String.valueOf(frequency)));
        propertiesLocalList.add(new Property("PL(d0)", "ptolemy.data.expr.Parameter", String.valueOf(PLD0Value)));
        propertiesLocalList.add(
                new Property("Calculated PL(d0)", "ptolemy.data.expr.Parameter", String.valueOf(isPLDOCalculated)));
        propertiesLocalList.add(new Property("n", "ptolemy.data.expr.Parameter", String.valueOf(n)));
    }

    public void setFileProps() {
        filePath = "eboracum/pathloss/simulations/";
        simulationName = "PLSimulation";
    }

    public void setAllProps() {
        setFileProps();
        setVergilProps();
        setEboracumProps();
        setNodesProps();
        setEventsProps();
        setChannelProps();
    }

    public void run() {
        setAllProps();

        createPrimalEntityInXMLFile(simulationName);
        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        createDataReportFile(simulationName);
    }

    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PathLossSimulationGenerator();
        gen.run();
    }

    protected void createDataReportFile(String simulationName) {

        String newDataDir = filePath + "data/";
        File file = new File(newDataDir);

        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.out.println("Failed to create directory!");
                return;
            }
        }

        file = new File(newDataDir + simulationName + ".csv");

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("Simulation Identification;" + simulationName);
            writer.newLine();
            writer.write("Initial Battery;" + this.initBattery);
            writer.newLine();
            //writer.write("Static Communication Radius;"+this.commCover); writer.newLine();
            writer.write("Sensor Cover Radius;" + this.sensorCover);
            writer.newLine();
            writer.write("Node Idle Energy Cost;" + this.idleEnergyCost);
            writer.newLine();
            writer.write("CPU Processing Energy Cost;" + this.cpuEnergyCost);
            writer.newLine();
            writer.write("Communication Channel Type:" + this.commChannelType);
            writer.newLine();
            writer.write("Transmission Power;" + this.transmitterPower);
            writer.newLine();
            writer.write("Receiver Sensivity;" + this.receiverSensivity);
            writer.newLine();
            writer.write("Antenna Gain;" + this.antennaGain);
            writer.newLine();
            writer.write("Frequency;" + this.frequency);
            writer.write("Summary of Nodes");
            writer.newLine();
            writer.write("Class Name;Number of Instances");
            writer.newLine();
            writer.write("eboracum.wsn.network.node." + "SimpleWSNNode" + ";" + numberOfNodes);
            writer.newLine();
            writer.write("Summary of Events");
            writer.newLine();
            writer.write("Class Name;Type;Number of Instances;");
            writer.newLine();
            writer.write("eboracum.wsn.network.node." + "PeriodicEvent" + ";" + eventType + ";" + numberOfNodes + ";");
            writer.newLine();
            writer.newLine();
            writer.write("Results");
            writer.newLine();
            writer.flush();
            writer.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
