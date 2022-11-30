import m1graph2022.Node;

import java.util.HashMap;

public class Flow {


    HashMap<Pair<Node,Node>, Integer> internalRepresentation;

    Flow()
    {
        internalRepresentation = new HashMap<>();
    }

    void addToFlow(Pair<Node,Node> n, int flow)
    {
        internalRepresentation.put(n, flow);
    }

    void addToFlow(Node first, Node second, int flow)
    {
        Pair<Node,Node> n = new Pair<>(first, second);
        internalRepresentation.put(n, flow);
    }

    void addToFlow(int firstId, int secondId, int flow)
    {
        Pair<Node,Node> n = new Pair<>(new Node(firstId),new Node(secondId));
        internalRepresentation.put(n, flow);
    }


}
