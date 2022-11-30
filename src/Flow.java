import m1graph2022.Node;

import java.util.HashMap;

public class Flow {


    HashMap<NodePair, Integer> internalRepresentation;

    Flow()
    {
        internalRepresentation = new HashMap<>();
    }

    void addToFlow(NodePair n, int flow)
    {
        internalRepresentation.put(n, flow);
    }

    void addToFlow(Node first, Node second, int flow)
    {
        NodePair n = new NodePair(first, second);
        internalRepresentation.put(n, flow);
    }

    void addToFlow(int firstId, int secondId, int flow)
    {
        NodePair n = new NodePair(firstId, secondId);
        internalRepresentation.put(n, flow);
    }


}
