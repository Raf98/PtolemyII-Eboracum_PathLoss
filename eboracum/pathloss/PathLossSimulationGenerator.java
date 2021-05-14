package eboracum.pathloss;

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
    
    protected List<Property> propertiesList;
    protected Map<Entity, List<Property>> entitiesToPropertiesMap;
    protected Map<Property, List<Property>> propertyToPropertiesMap;
    
    protected String defaultSpacing = "    ";
    
    private class Entity{
        public String name, className;
        
        public Entity(String name, String className) {
            this.name = name;
            this.className = className;
        }
    }
    
    private class Property extends Entity{
        public String value;
        
        public Property(String name, String className, String value) {
            super(name, className);
            this.value = value;
        }
    }
    
    void createPrimalEntityInXMLFile(String fileName) {
        xmlFile = new File(filePath + fileName + ".xml");
        
        try {
            xmlFile.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
                
        try {
            writer = new FileWriter(xmlFile.getAbsolutePath(), false);
            
            //DOCTYPE SEMPRE deve estar alinhado ao lado esquerdo do documento, senao provoca erro ao ser aberto o XML
            writer.write("<?xml version=\"1.0\" standalone=\"no\"?>\n" + 
                    "<!DOCTYPE entity PUBLIC \"-//UC Berkeley//DTD MoML 1//EN\"\n" + 
                    "            \"MoML_1.dtd\">\n" + 
                    "        <entity name=\" " + fileName + "\" class=\"ptolemy.actor.TypedCompositeActor\">\n");
            
            
            writer.write("    <property name=\"_createdBy\" class=\"ptolemy.kernel.attributes.VersionAttribute\" value=\"8.0.1_20101021\">\n" + 
                    "    </property>\n" + 
                    "    <property name=\"Wireless Director\" class=\"ptolemy.domains.wireless.kernel.WirelessDirector\">\n" + 
                    "        <property name=\"synchronizeToRealTime\" class=\"ptolemy.data.expr.Parameter\" value=\"true\">\n" + 
                    "        </property>\n" + 
                    "        <property name=\"_location\" class=\"ptolemy.kernel.util.Location\" value=\"[" + defaultComponentsX + ", " + defaultComponentsY + "]\">\n" + 
                    "        </property>\n" + 
                    "    </property>\n");
            defaultComponentsX += 100;
            
            fillPropertiesMap();
            fillProperties();
            fillEntitiesMap();
            
            for(Property prop : propertiesList) {
                createPropertyInXMLFile(writer, prop, defaultSpacing);
            }
            
            for(Map.Entry<Entity, List<Property>> entry : entitiesToPropertiesMap.entrySet()) {
                createEntityInXMLFile(entry.getKey(), entry.getValue());
            }
            
            writer.write("</entity>");
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    protected void fillEntitiesMap() {
        entitiesToPropertiesMap = new HashMap<>();
        List<Property> propertiesLocalList = new ArrayList<>(); 
        
        Entity entity = new Entity(sensorChannelName, "ptolemy.domains.wireless.lib.LimitedRangeChannel");
        propertiesLocalList.add(new Property("defaultProperties", 
                    "ptolemy.data.expr.Parameter",
                    "{range = SensorCover}"));
        propertiesLocalList.add(new Property("_location", 
                "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX +", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;
        
        propertiesLocalList = new ArrayList<>();
        entity = new Entity(commChannelName, commChannelType);
        propertiesLocalList.add(new Property("defaultProperties", 
                    "ptolemy.data.expr.Parameter",
                    "{range = CommCover, power = Infinity, pathloss = 0.0}"));
        propertiesLocalList.add(new Property("_location", 
                "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX + ", " + defaultComponentsY + "]"));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;
        
        propertiesLocalList = new ArrayList<>();
        entity = new Entity(networkName, "eboracum.pathloss.PathLossAdHocNetwork");
        propertiesLocalList.add(new Property("NetworkSinks", 
                    "ptolemy.data.expr.StringParameter",
                    "{" + sinkName + "}"));
        propertiesLocalList.add(new Property("_location", 
                "ptolemy.kernel.util.Location",
                "[" + defaultComponentsX +", " + defaultComponentsY + "]"));        
        entitiesToPropertiesMap.put(entity, propertiesLocalList);
        defaultComponentsX += 200;
        
        propertiesLocalList = new ArrayList<>();
        entity = new Entity(sinkName,
                "eboracum.wsn.network.node.NetworkMainGateway");
        propertiesLocalList.add(new Property("RebuildNetworkWhenGatewayDies", 
                "ptolemy.data.expr.Parameter", "false"));
        propertiesLocalList.add(new Property("_location", 
                "ptolemy.kernel.util.Location",
                "[" + sinkX + ", " + sinkY + "]"));
        propertiesLocalList.add(new Property("CommChannelName", 
                "ptolemy.data.expr.StringParameter",
                commChannelName));
        propertiesLocalList.add(new Property("Network", 
                "ptolemy.data.expr.StringParameter",
                networkName));
        entitiesToPropertiesMap.put(entity, propertiesLocalList);

        createNodesEntities();
    }

    protected void fillProperties() {
        propertiesList = new ArrayList<>();
        
        propertiesList.add(new Property("_windowProperties","ptolemy.actor.gui.WindowPropertiesAttribute", _windowsProperties));
        propertiesList.add(new Property("_vergilSize", "ptolemy.actor.gui.SizeAttribute", _vergilSize));
        propertiesList.add(new Property("_vergilZoomFactor", "ptolemy.data.expr.ExpertParameter",Double.toString(_vergilZoomFactor)));
        propertiesList.add(new Property("_vergilCenter", "ptolemy.data.expr.ExpertParameter", _vergilCenter));
        
        propertiesList.add(new Property("GlobalInitBattery", "ptolemy.data.expr.Parameter", Double.toString(initBattery)));
        propertiesList.add(new Property("SensorCover", "ptolemy.data.expr.Parameter", Double.toString(sensorCover)));
        propertiesList.add(new Property("CommCover", "ptolemy.data.expr.Parameter", Double.toString(commCover)));
    }

    protected void fillPropertiesMap() {
        
    }
    
    protected void createNodesEntities() {
        double currentX= sinkX + nodesDistance, currentY = sinkY;
        Entity entity;
        List<Property> propertiesLocalList;
        
        
        for(int i = 0; i < numberOfNodes; ++i) {
            if(i%nodesOnRow == 0) {
                currentY += nodesDistance;
                currentX = sinkX + nodesDistance;
            }
            entity = new Entity("SimpleWSNNode"+i,
                    "eboracum.wsn.network.node.sensor.SimpleWSNNode");
            propertiesLocalList = new ArrayList<>();
            propertiesLocalList.add(new Property("_location", 
                    "ptolemy.kernel.util.Location",
                    "[" + currentX + ", " + currentY + "]"));
            propertiesLocalList.add(new Property("CommChannelName", 
                    "ptolemy.data.expr.StringParameter",
                    commChannelName));
            propertiesLocalList.add(new Property("Network", 
                    "ptolemy.data.expr.StringParameter",
                    networkName));
            
            
            entitiesToPropertiesMap.put(entity, propertiesLocalList);
            
            currentX += nodesDistance;
        }
    }

    void createEntityInXMLFile(Entity entity, List<Property> properties) {
        try {
            writer.write(defaultSpacing + "<entity name=\"" + entity.name +"\" class=\"" + entity.className +"\">\n");
            
            for(Property prop : properties) {
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
            writer.write(defaultSpacing + "<property name=\"" + property.name +"\" class=\"" + property.className +
                    "\" value=\"" + property.value + "\">\n");
            
            for(Property prop : properties) {
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
            writer.write( initialSpacing + "<property name=\"" + property.name +"\" class=\"" + property.className +
                    "\" value=\"" + property.value + "\">\n");
            
            writer.write(initialSpacing + "</property>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    void setVergilProps() {
        _windowsProperties = "{bounds={0, 0, 1370, 772}, maximized=false}";
        _vergilSize = "[1147, 655]";
        _vergilZoomFactor = 0.5;
        _vergilCenter = "{657.9375, 336.9288194444446}";
        
        defaultComponentsX = -250;
        defaultComponentsY = -200;
    }
    
    void setEboracumProps() {
        commChannelName = "LogDistanceChannel";
        commChannelType = "eboracum.pathloss." + commChannelName;
        sensorChannelName = "LimitedRangeChannel";
        networkName = "PathLossAdHocNetwork";
        sinkName = "NetworkMainGateway";
        sinkX = 50;
        sinkY = 0;
        numberOfNodes = 10;
        nodesOnRow = 3;
        nodesDistance = 200;
        
        initBattery = 500;
        commCover = 300;
        sensorCover = 90;
    }
    
    
    public void run() {
        setVergilProps();
        setEboracumProps();
        
        filePath = "eboracum/pathloss/simulations/";
        createPrimalEntityInXMLFile("PLSimulation");
        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        PathLossSimulationGenerator gen = new PathLossSimulationGenerator();
        gen.run();
    }
    
}
