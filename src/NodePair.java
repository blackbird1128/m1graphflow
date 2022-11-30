import m1graph2022.Node;

import java.util.Objects;

public class NodePair {

    Node firstNode;
    Node secondNode;


    NodePair(Node f, Node s)
    {
        firstNode = f;
        secondNode = s;
    }

    NodePair(int idF, int idS)
    {
        firstNode = new Node(idF);
        secondNode = new Node(idS);

    }

    NodePair(String first, String second)
    {
        firstNode = new Node(first);
        secondNode = new Node(second);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodePair nodePair = (NodePair) o;
        return firstNode.equals(nodePair.firstNode) && secondNode.equals(nodePair.secondNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNode, secondNode);
    }
}
