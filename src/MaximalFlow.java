import m1graph2022.Edge;
import m1graph2022.Graf;
import m1graph2022.Node;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MaximalFlow {


    static String graphToInitialDot(Graf g, int currStep)
    {
        StringBuilder repr = new StringBuilder();
        repr.append("digraph g {\n");
        repr.append("\trankdir=LR\n");
        repr.append("\tlabel=\"(").append(currStep).append(") Flow initial . Value : 0\"\n");
        ArrayList<Node> graphNodeList = new ArrayList<>(g.getAllNodes());
        Collections.sort(graphNodeList);
        for(Node n : graphNodeList) {
            List<Edge> edges = g.getOutEdges(n);

            if (edges != null) {
                Collections.sort(edges);
                for (Edge e : edges) {
                    repr.append(String.format("\t%s -> %s", e.from(), e.to()));
                    if (e.isWeighted()) {
                        repr.append(String.format(" [label=%d, len=%d]", e.getWeight(), e.getWeight()));
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

    /**
     * Write the string representing the graph in the dot format into a file
     * the filename is made of the param filename and the extension ".gv"
     * @param filename the name of the file
     */
    public static void toDotFile(String filename, String content)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filename + ".gv", "UTF-8");
            writer.print(content);
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("error writing to dot file");
        }

    }


    /**
     * Write the string representing the graph in the dot format into a file
     * the filename is made of the param filename and the extension ".gv"
     * @param destination the destination of the file
     */
    public static void toDotFile(File destination, String content)
    {
        try
        {
            PrintWriter writer = new PrintWriter(destination + ".gv", "UTF-8");
            writer.print(content);
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("error writing to dot file");
        }

    }


    //from graph and Pair<augmenting path, weight> to graph highlighted
    static String graphAndPathDotString(Graf g, Pair<List<Edge>,Integer> pathPair, int currStep) {
        ArrayList<Node> augmentingPath = new ArrayList<>();
        List<Edge> edgeList = new ArrayList<>();
        if (pathPair != null) {
            edgeList = pathPair.first;
            for (Edge e : pathPair.first) {
                augmentingPath.add(e.from());

            }
            augmentingPath.add(new Node("t"));
        }
        StringBuilder repr = new StringBuilder();
        repr.append("digraph g {\n");
        repr.append("\trankdir=LR\n");
        String label = "label=\"(" + currStep + ") residual graph.\n";
        if(augmentingPath.size() > 0)
        {
            label += "Augmenting path: " + augmentingPath + ".\n";
            label += "Residual capacity: " + pathPair.second + ".";

        }
        else
        {
            label += "Augmenting path: none.\n";
            label += "Previous flow was maximum";
        }
        label += "\";\n";
        repr.append(label);
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
                        if(pathPair != null && Objects.equals(pathPair.second, e.getWeight()) && edgeList.contains(e))
                        {
                            repr.append(", fontcolor=\"red\" ");
                        }
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

    static String flowDotString(Graf g, Pair<List<Edge>,Integer> pathPair, int currStep, int maxFlow){
        List<Edge> edgeList = pathPair != null ? pathPair.first : new ArrayList<>() ;
        int minFlow = pathPair != null ? pathPair.second : -1;
        StringBuilder repr = new StringBuilder();
        repr.append("digraph g {\n");
        repr.append("\trankdir=LR\n");
        String label = "label=\"(" + currStep + ") Flow induced from residual graph " + Math.max(currStep - 1, 1) + ".";
        label += " Value: " + maxFlow +"\";\n";
        repr.append(label);
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
        do {
            System.out.println(graphAndPathDotString(currentGraf,p, 0));
            currentGraf = grafToResidual(currentGraf,p);
            p = AugmentingPath.getAugmentingPath(g.getNode("s"),g.getNode("t"),currentGraf,algorithm);
            System.out.println(p);
        }while (p!=null);
        return currentGraf;
    }

    public static Graf getMaxFlowAndFiles(Graf g, AP_ALGORITHM algorithm, String outputDirectory)
    {

        File output = new File(outputDirectory);
        if(!output.isDirectory())
        {
            System.out.println("Error: the path you provided is not a directory or doesn't exist");
            return null;
        }
        int currStep = 1;
        String curFile = "flow";

        Graf currentGraf = g.copy();
        String curRepr = graphToInitialDot(currentGraf, currStep);
        toDotFile(output + "/" + curFile + currStep, curRepr);
        System.out.println("treated: " + currentGraf.toDotString());
        int maxFlow = 0;
        Pair<List<Edge>, Integer> p = AugmentingPath.getAugmentingPath(g.getNode("s"),g.getNode("t"),currentGraf,algorithm);
        do{
            maxFlow += p.second;
            curRepr = graphAndPathDotString(currentGraf,p, currStep); // residual
            System.out.println(curRepr);
            curFile = "residGraph";
            toDotFile(output + "/" + curFile + currStep, curRepr);
            curRepr = flowDotString(g, p, currStep, maxFlow);
            curFile = "flow";
            if(currStep != 1) // avoid replacing the init dot file
            {
                toDotFile(output + "/" + curFile + currStep , curRepr);
            }
            currentGraf = grafToResidual(currentGraf,p);
            System.out.println("treated: " + currentGraf.toDotString());
            p = AugmentingPath.getAugmentingPath(g.getNode("s"),g.getNode("t"),currentGraf,algorithm);
            currStep++;
        }while(p!= null);

        curRepr = graphAndPathDotString(currentGraf,p, currStep); // residual
        System.out.println(curRepr);
        curFile = "residGraph";
        toDotFile(output + "/" + curFile + currStep, curRepr);
        curRepr = flowDotString(g, p, currStep, maxFlow);
        curFile = "flow";

        toDotFile(output + "/" + curFile + currStep , curRepr);



        return currentGraf;



    }

}
