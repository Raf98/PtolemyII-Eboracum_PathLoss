package eboracum.wsn.network;

import java.util.ArrayList;
import java.util.Iterator;
import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class SimpleAdHocNetwork extends AdHocNetwork{
	
	private static final long serialVersionUID = 1L;

	public SimpleAdHocNetwork(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
	}

	public void buildNetwork(){
    	for(int i=0; i<this.nodes.size(); i++){
        	@SuppressWarnings("unchecked")
			ArrayList<Entity> tempNetworkNode = (ArrayList<Entity>) this.networkedNodes.clone();
        	Iterator<Entity> n = tempNetworkNode.iterator();
        	while (n.hasNext()) {
        		Entity node = (Entity) n.next();
        		try {
					this.defineThisNodeAsGateway(node);
				} catch (IllegalActionException e) {
					e.printStackTrace();
				}
            }
        }
    }

    private void defineThisNodeAsGateway(Entity gatewayNode) throws IllegalActionException {
        Iterator<Entity> n = nodes.iterator();
    	while (n.hasNext()) {
    		Entity node = (Entity) n.next();
            if (calcDistance(node, gatewayNode) <= this.coverRadius && !gatewayNode.equals(node) && 
            		((StringParameter)node.getAttribute("Gateway")).getExpression().equals("")) {
        		((StringParameter)node.getAttribute("Gateway")).setExpression(gatewayNode.getName());
        		this.networkedNodes.add(node);
        		_drawLine(node,gatewayNode, "lineGateway_"+node.getName());
        		//System.out.println(node+" "+gatewayNode+"  "+("lineGateway"+node.getName()));
        	}
        }
    }

}
