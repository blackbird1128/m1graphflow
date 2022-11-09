package m1graph2022;

import java.util.HashSet;
import java.util.Objects;

/*




 */



public class Edge implements Comparable<Edge> {

    Node from;
    Node to;
    Integer weight;

    public Edge(Node _from, Node _to)
    {
        from = _from;
        to = _to;
        weight = null;
    }

    public Edge(Node _from, Node _to, int _weight)
    {
        from = _from;
        to = _to;
        weight = _weight;
    }

    public Edge(int idFrom, int idTo)
    {
        from = new Node(idFrom);
        to =  new Node(idTo);
    }

    public Edge(String fromLabel, String toLabel) {
        this.from = new Node(fromLabel);
        this.to = new Node(toLabel);
    }

    public Edge(Node from, String toLabel) {
        this.from = from;
        this.to = new Node(toLabel);
    }

    public Edge(String fromLabel, Node to) {
        this.from = new Node(fromLabel);
        this.to = to;
    }

    public Edge(int idFrom, String toLabel) {
        this.from = new Node(idFrom);
        this.to = new Node(toLabel);
    }

    public Edge(String fromLabel, int idTo) {
        this.from = new Node(fromLabel);
        this.to = new Node(idTo);
    }


    public Node from()
    {
        return from;
    }

    public Node to()
    {
        return to;
    }


    public Edge getSymmetric()
    {
        return new Edge(to, from);
    }

    public boolean isSelfLoop()
    {
        return to == from;
    }

    protected boolean containsSameNodes(Edge other)
    {
        HashSet<Integer> containedNodes = new HashSet<Integer>();
        containedNodes.add(from().getId());
        containedNodes.add(to().getId());
        return (containedNodes.contains(other.from().getId()) && containedNodes.contains(other.to().getId()));
    }


    public String toString()
    {
        String repr =  this.from().getId() + "->" + this.to().getId();
        if(this.isWeighted())
        {
            repr += " weight=" + this.getWeight();
        }
        return repr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return from.getId() == edge.from.getId() && to.getId() == edge.to().getId() && Objects.equals(weight, edge.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from.id, to.id, weight);
    }

    boolean isWeighted()
    {
        return ! (weight == null);
    }

    public Integer getWeight()
    {
        return weight;
    }

    @Override
    public int compareTo(Edge o) {
        if(this.from().equals(o.from()))
        {
            if(this.to().equals(o.to()))
            {
                if(this.getWeight() == null && o.getWeight() == null)
                {
                    return 0;
                }
                else if( this.getWeight() == null || o.getWeight() == null)
                {
                    // uh, so one have a weight and the other not , so are they equals ?
                    // Not equal ?
                    return -1;
                }
                return this.weight - o.getWeight();
            }
            else
            {
                return this.to().getId() - this.to().getId();
            }

        }
        else
        {
            return this.from().getId() - this.from().getId();
        }

    }


}
