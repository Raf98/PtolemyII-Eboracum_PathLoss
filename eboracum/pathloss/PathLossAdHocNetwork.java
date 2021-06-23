package eboracum.pathloss;

import java.util.ArrayList;
import java.util.Iterator;

import eboracum.pathloss.unfinished.PathLossMethods;
import eboracum.wsn.network.SimpleAdHocNetwork;
import eboracum.wsn.network.node.WirelessNode;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.data.BooleanToken;
import ptolemy.data.RecordToken;
import ptolemy.data.ScalarToken;
import ptolemy.data.expr.StringParameter;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.domains.wireless.lib.PowerLossChannel;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class PathLossAdHocNetwork extends SimpleAdHocNetwork {
    
    public double maxRange;
    //public double fraunhoferDistance;

    public PathLossAdHocNetwork(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);        
        
        maxRange = 100;
        //findCommunicationCover();
        //changeNodesDefautlRange();
        
        this.commChannelName.setExpression("FreeSpaceChannel");
        PathLossMethods pathLossMethods = new PathLossMethods(800e6);
        this.commCoverRadius.setExpression("100");
        //System.out.println("NETWORK COVER RADIUS: " + this.commCoverRadius.getExpression());
        // TODO Auto-generated constructor stub
    }
    
    public void initialize() throws IllegalActionException {
        super.initialize();
        //this.coverRadius = Double.parseDouble(commCoverRadius.getValueAsString());
        _fireAt(this.getDirector().getModelStartTime());
    }
    
    public void fire() throws IllegalActionException {
        //super.fire();
        if (this.rebuildNetwork.getExpression().equals("true") || this.getDirector().getModelStartTime().equals(this.getDirector().getModelTime())){
                this.networkedNodes = new ArrayList<Entity>();
                this.sinks = new ArrayList<Entity>();
                
                this.findCommunicationCover();
                this.changeNodesDefautlRange();
                
                this.findEntitySinks();
                //System.out.println("BUILD NETWORK CALLED");
                this.buildNetwork();
        }
        if (!this.getDirector().getModelStartTime().equals(this.getDirector().getModelTime())){
                @SuppressWarnings("unchecked")
                ArrayList<Entity> tempNodes = (ArrayList<Entity>) this.nodes.clone();
                Iterator<Entity> n = tempNodes.iterator();
                while (n.hasNext()) {
                        Entity node = (Entity) n.next();
                        ((TypedAtomicActor)node).getDirector().fireAtCurrentTime((TypedAtomicActor)node);
                }
        }
        if (this.nodes.size()<=this.sinks.size()) out.send(0, new BooleanToken("true"));
    }
    
    
    public void findCommunicationCover() {
        CompositeActor container = (CompositeActor) getContainer();
        @SuppressWarnings("rawtypes")
        Iterator actors = container.deepEntityList().iterator();
        
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            
            if(node.getClassName().startsWith("eboracum.pathloss.") && node.getClassName().contains("Channel")) {
            //if(node.getClassName().equals("eboracum.pathloss.FreeSpaceChannel"/*"ptolemy.domains.wireless.lib.PowerLossChannel"*/)) {
                this.commChannelName.setExpression(node.getName());
                //System.out.println("ENTITY - CLASS NAME: " + node.getClassName());
                //System.out.println("ENTITY - NAME: " + node.getName());
                
                FreeSpaceChannel freeSpaceChannel = (FreeSpaceChannel) node;
                //this.fraunhoferDistance = freeSpaceChannel.calculateFraunhoferDistance();
                //PowerLossChannel powerLossChannel = (PowerLossChannel) node;
                
                //System.out.println(freeSpaceChannel.pathLossFactor.getExpression());
                
                ScalarToken maxRangeToken;
                try {
                    maxRangeToken = (ScalarToken)((RecordToken)freeSpaceChannel.defaultProperties.getToken()).get("range");
                    maxRange = maxRangeToken.doubleValue();
                    this.commCoverRadius.setExpression(String.valueOf(maxRange));
                    
                    //System.out.println("max range - free space: " + maxRange);
                } catch (IllegalActionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } 
        }
    }
    
    void changeNodesDefautlRange() {
        CompositeActor container = (CompositeActor) getContainer();
        @SuppressWarnings("rawtypes")
        Iterator actors = container.deepEntityList().iterator();
        
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            
            
            if(node.getClassName().equals("eboracum.wsn.network.node.sensor.SimpleWSNNode") 
                    || node.getClassName().equals("eboracum.wsn.network.node.NetworkMainGateway")) {
                
                WirelessNode wNode = (WirelessNode)node;
                
                wNode.updateNodeCommunicationInfos(this.commChannelName.getExpression(), 
                                                   this.maxRange, 
                                                   this.getName());
                /*
                wNode.commChannelName.setExpression(this.commChannelName.getExpression());
                wNode.commCoverRadius.setExpression(Double.toString(this.maxRange));
                wNode.network.setExpression("PathLossAdHocNetwork");
                
                try {
                    //wNode._circle_comm.fillColor.setToken("{1.0, 0.0, 0.0, 0.2}");
                    wNode.initialize();
                    //wNode._circle_comm.width.setToken(Double.toString(Double.parseDouble(this.commCoverRadius.getValueAsString())*2));
                    //wNode._circle_comm.height.setToken(Double.toString(Double.parseDouble(this.commCoverRadius.getValueAsString())*2));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
               
                //System.out.println("CHANGE NODES :" + node.getFullName());
                }
            }
        
    }
    
   @Override
   protected void defineThisNodeAsGateway(Entity gatewayNode) throws IllegalActionException {
       this.coverRadius = Double.parseDouble(commCoverRadius.getValueAsString());
       
       Iterator<Entity> n = nodes.iterator();
       while (n.hasNext()) {
               Entity node = (Entity) n.next();
               
               //new way to use coverRadius, according to the node
               double nodeCoverRadius, gatewayCoverRadius;
               try {
                   nodeCoverRadius = Double.parseDouble(((WirelessNode) node).commCoverRadius.getExpression());
                   //System.out.println("NODE COVER SIMPLE: " + nodeCoverRadius);
                   gatewayCoverRadius = Double.parseDouble(((WirelessNode) gatewayNode).commCoverRadius.getExpression());
               } catch (NumberFormatException e) {
                   nodeCoverRadius = this.coverRadius;
                   //System.out.println("NODE COVER SIMPLE excep: " + nodeCoverRadius);
               }
               
               //System.out.println("nodecover"+nodeCoverRadius);
               double distanceTR = calcDistance(node, gatewayNode);
               
               if (distanceTR <= nodeCoverRadius /*&& distanceTR > this.fraunhoferDistance*/
                       && !gatewayNode.equals(node) && 
                       ((StringParameter)node.getAttribute("Gateway")).getExpression().equals("")) {
                       ((StringParameter)node.getAttribute("Gateway")).setExpression(gatewayNode.getName());
                       this.networkedNodes.add(node);
                       _drawLine(node,gatewayNode, "lineGateway_"+node.getName());
                       //System.out.println("DRAWING LINE!");
                       //System.out.println(node+" "+gatewayNode+"  "+("lineGateway"+node.getName()));
                       //System.out.println("FRAUNHOFER: " + this.fraunhoferDistance);
                       //System.out.println("DISTANCE TR: " + distanceTR);
               }
       }
   }

}
