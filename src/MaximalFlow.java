import m1graph2022.Edge;
import m1graph2022.Graf;
import m1graph2022.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaximalFlow {
    //from graph and Pair<augmenting path, weight> to graph highlighted
    static String graphAndPathDotString(Graf g, Pair<List<Edge>,Integer> pathPair){
        List<Edge> edgeList = pathPair.first;
        StringBuilder repr = new StringBuilder();
        repr.append("digraph g {\n");
        repr.append("\trankdir=LR\n");
        ArrayList<Node> graphNodeList = new ArrayList<>(g.getAllNodes());
        Collections.sort(graphNodeList);
        for(Node n : graphNodeList) {
            List<Edge> edges = g.getOutEdges(n);

            if (edges != null) {
                Collections.sort(edges);
                for (Edge e : edges) {
                    repr.append(String.format("\t%s -> %s", e.from(), e.to()));
                    if (e.isWeighted()) {
                        repr.append(String.format(" [label=%d, len=%d", e.getWeight(), e.getWeight()));
                        if(edgeList.contains(e)){
                            repr.append(", penwidth=3, color=\"blue\"");
                        }
                        repr.append("]");
                    }
                    repr.append("\n");
                }

            }
            else
            {
                if(g.getIncidentEdges(n).size() == 0)
                {
                    repr.append("\t" +n + "\n");
                }
            }
        }
        repr.append("}");
        return repr.toString();
    }

    static String flowDotString(Graf g, Pair<List<Edge>,Integer> pathPair){
        List<Edge> edgeList = pathPair.first;
        int minFlow = pathPair.second;
        StringBuilder repr = new StringBuilder();
        repr.append("digraph g {\n");
        repr.append("\trankdir=LR\n");
        ArrayList<Node> graphNodeList = new ArrayList<>(g.getAllNodes());
        Collections.sort(graphNodeList);
        for(Node n : graphNodeList) {
            List<Edge> edges = g.getOutEdges(n);

            if (edges != null) {
                Collections.sort(edges);
                for (Edge e : edges) {
                    repr.append(String.format("\t%s -> %s", e.from(), e.to()));
                    if (e.isWeighted()) {
                        if(edgeList.contains(e)){
                            repr.append(String.format(" [label=\"%d/%d\", len=%d", minFlow, e.getWeight(), e.getWeight()));
                        }
                        else
                        {
                            repr.append(String.format(" [label=%d, len=%d", e.getWeight(), e.getWeight()));
                        }
                        repr.append("]");
                    }
                    repr.append("\n");
                }

            }
            else
            {
                if(g.getIncidentEdges(n).size() == 0)
                {
                    repr.append("\t" +n + "\n");
                }
            }
        }
        repr.append("}");
        return repr.toString();
    }

    public static Graf grafToResidual(Graf g, Pair<List<Edge>,Integer> pathPair){
        Graf result = g.copy();
        List<Edge> allEdges = result.getAllEdges();
        for (Edge e:allEdges) {
            if (pathPair.first.contains(e)){
                if(e.getWeight() - pathPair.second != 0)
                {
                    e.setWeight(e.getWeight() - pathPair.second);
                }
                else
                {
                    result.removeEdge(e);
                }
                result.updateEdge(e.to(), e.from(),pathPair.second);
            }
        }
        return result;
    }

    public static Graf getMaxFlowGraf(Graf g, AP_ALGORITHM algorithm){
        Graf currentGraf = g.copy();
        Pair<List<Edge>, Integer> p = AugmentingPath.getAugmentingPath(g.getNode("s"),g.getNode("t"),currentGraf,algorithm);
        if(p!=null){
            do {
                System.out.println(graphAndPathDotString(currentGraf,p));
                currentGraf = grafToResidual(currentGraf,p);
                p = AugmentingPath.getAugmentingPath(g.getNode("s"),g.getNode("t"),currentGraf,algorithm);
            }while (p!=null);
            return currentGraf;
        }
        return g;
    }

}
