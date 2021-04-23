package eboracum.pathloss;

import java.util.ArrayList;
import java.util.Iterator;

import eboracum.wsn.network.SimpleAdHocNetwork;
import eboracum.wsn.network.node.WirelessNode;
import ptolemy.actor.CompositeActor;
import ptolemy.data.RecordToken;
import ptolemy.data.ScalarToken;
import ptolemy.domains.wireless.kernel.WirelessIOPort;
import ptolemy.domains.wireless.lib.PowerLossChannel;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class PathLossAdHocNetwork extends SimpleAdHocNetwork {

    public PathLossAdHocNetwork(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        this.commChannelName.setExpression("FreeSpaceChannel");
        PathLossMethods pathLossMethods = new PathLossMethods(800e6);
        this.commCoverRadius.setExpression(Double.toString(pathLossMethods.freeSpaceMaximumDistance(70)));
        System.out.println("NETWORK COVER RADIUS: " + this.commCoverRadius.getExpression());
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void findEntitySinks() throws IllegalActionException {
        // TODO Auto-generated method stub
        super.findEntitySinks();
    }
    
    @Override
    public void buildNetwork() {
        // TODO Auto-generated method stub
        //super.buildNetwork();
        for(int i=0; i<this.nodes.size(); i++){
            @SuppressWarnings("unchecked")
                    ArrayList<Entity> tempNetworkNode = (ArrayList<Entity>) this.networkedNodes.clone();
            Iterator<Entity> n = tempNetworkNode.iterator();
            while (n.hasNext()) {
                    Entity node = (Entity) n.next();
                    System.out.println("ENTITY: " + node.getClassName());
                    ((WirelessNode)node).commChannelName.setExpression(this.commChannelName.getExpression());
                    
                    try {
                                    this.defineThisNodeAsGateway(node);
                            } catch (IllegalActionException e) {
                                    e.printStackTrace();
                            }
        }
    }
    }
    
    public void findCommunicationCover() {
        CompositeActor container = (CompositeActor) getContainer();
        @SuppressWarnings("rawtypes")
        Iterator actors = container.deepEntityList().iterator();
        
        while (actors.hasNext()) {
            Entity node = (Entity) actors.next();
            
            if(node.getClassName().equals("eboracum.pathloss.FreeSpaceChannel"/*"ptolemy.domains.wireless.lib.PowerLossChannel"*/)) {
                this.commChannelName.setExpression(node.getName());
                System.out.println("ENTITY - CLASS NAME: " + node.getClassName());
                System.out.println("ENTITY - NAME: " + node.getName());
                
                FreeSpaceChannel freeSpaceChannel = (FreeSpaceChannel) node;
                PowerLossChannel powerLossChannel = (PowerLossChannel) node;
                
                System.out.println(freeSpaceChannel.pathLossFactor.getExpression());
                
                ScalarToken maxRangeToken;
                try {
                    maxRangeToken = (ScalarToken)((RecordToken)powerLossChannel.defaultProperties.getToken()).get("range");
                    double maxRange = maxRangeToken.doubleValue();
                    
                    System.out.println("max range - free space: " + maxRange);
                } catch (IllegalActionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
