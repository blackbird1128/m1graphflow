package m1graph2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UndirectedGraf extends Graf{



    /**
     * Build a graf using the values as a representation of an adjency array
     * @param values the values in the adjency array from left to right
     */
    public UndirectedGraf(int ...values)
    {
        graphNodes = new HashSet<>();
        adjEdList = new HashMap<>();
        int currentNode = 1;
        for(int val : values)
        {
            if(val == 0)
            {
                currentNode += 1;
            }
            else
            {
                this.addEdge(currentNode, val);
            }
        }

    }

    /**
     * Return the in-degree of the Node n
     * @param n the Node we want to get the in-degree of
     * @return the in-degree of the Node
     */
    public int inDegree(Node n)
    {
        return (getIncidentEdges(n).size() + 2 * countSelfLoops(n));
    }


    /**
     * Return the out-degree of the Node
     * @param n the Node we want to get the out-degree of
     * @return the out-degree of the Node
     */
    public int outDegree(Node n)
    {

        return (getIncidentEdges(n).size() + 2 * countSelfLoops(n));
    }

    /**
     * Return the out-degree of the Node with the id nodeId
     * @param nodeId the id of the Node we want to get the out-degree of
     * @return the out degree of the node
     */
    public int outDegree(int nodeId)
    {
        return (getIncidentEdges(nodeId).size() + 2 * countSelfLoops(new Node(nodeId)));
    }



    /**
     * Return the degree of a Node
     * @param n the Node we want to get the degree of
     * @return the degree of the Node
     */
    public int degree(Node n)
    {
        return (getIncidentEdges(n).size() + 2 * countSelfLoops(n));
    }

    /**
     * Return the degree of a Node with the id nodeId
     * @param nodeId the id of the Node we want to get the degree of
     * @return the degree of the Node
     */
    public int degree(int nodeId)
    {
        return  (getIncidentEdges(nodeId).size() + 2 * countSelfLoops(new Node(nodeId)));
    }


    /**
     * Return a copy of the undirected graph
     * @return Graf A copy of the undirected graph
     */
    @Override
    protected UndirectedGraf copy()
    {
        UndirectedGraf copy = new UndirectedGraf();
        for(Node n : graphNodes)
        {
            copy.addNode(n);
        }
        for(List<Edge> edges : adjEdList.values())
        {
            for(Edge e : edges)
            {
                copy.addEdge(e);

            }

        }
        return copy;
    }


    /**
     * Remove the Edge going from the Node from to the Node to if it exists,
     * return true if the operation was successful, false otherwise
     * @param from The Node the Edge to remove is starting from
     * @param to The Node the Edge to remove is going to
     * @return true if the Edge was removed, false otherwise
     */
    @Override
    public boolean removeEdge(Node from, Node to)
    {
        boolean result = false;
        if(existsEdge(from, to))
        {
            Edge toRemove = new Edge(from,to);
            if(existsEdge(toRemove))
            {

                for(Node n : graphNodes)
                {
                    if(adjEdList.get(n) != null) {
                        if(adjEdList.get(n).remove(toRemove))
                        {
                            result = true;
                        }
                    }
                }
            }
        }
        if(existsEdge(to, from))
        {
            Edge toRemove = new Edge(to,from);
            if(existsEdge(toRemove))
            {

                for(Node n : graphNodes)
                {
                    if(adjEdList.get(n) != null) {
                        if(adjEdList.get(n).remove(toRemove))
                        {
                            result = result && true;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Remove the Edge going from the Node with the id fromId to the Node with the id toId if it exists,
     * return true if the operation was successful, false otherwise
     * @param fromId the id of the Node the Edge to remove is starting from
     * @param toId the id of the Node the Edge to remove is going to
     * @return true if the Edge was removed, false otherwise
     */
    @Override
    public boolean removeEdge(int fromId, int toId)
    {
        Node from = new Node(fromId);
        Node to = new Node(toId);
        return removeEdge(from,to) && removeEdge(to, from);
    }

    /**
     * Remove the Edge going from the Node from to the Node to with the specified weight if it exists;
     * return true if the operation was successful, false otherwise
     * @param from the Node the Edge to remove is starting from
     * @param to the Node the Edge to remove is going to
     * @param weight the weight of the Edge to remove
     * @return true if the Edge was removed, false otherwise
     */
    @Override
    public boolean removeEdge(Node from, Node to , int weight)
    {
        Edge toRemove1 = new Edge(from,to , weight);
        Edge toRemove2 = new Edge(to, from, weight);
        return removeEdge(toRemove1) && removeEdge(toRemove2);

    }

    @Override
    public List<Edge> getAllEdges()
    {
        ArrayList<Edge> allEdges = new ArrayList<>();
        for(Node n : graphNodes)
        {
            ArrayList<Edge> incidentEdges = (ArrayList<Edge>) getInEdges(n);
            //incidentEdges.addAll(getOutEdges(n));
            allEdges.addAll(incidentEdges);

        }

        HashSet<Edge> toRemove = new HashSet<>();
        for(Edge e : allEdges)
        {
            if(allEdges.contains(e.getSymmetric()) && !toRemove.contains(e) && e.from() != e.to() )
            {
                toRemove.add(e.getSymmetric());
            }
        }

        allEdges.removeAll(toRemove);

        return allEdges;

    }

    /**
     *
     * Get the number of edges in the graph
     * @return the number of edges as an int
     */
    @Override
    public int nbEdges()
    {
        int count = 0;
       for(List<Edge> edges : adjEdList.values())
        {
            count += edges.size();
        }
       for(Node n : getAllNodes())
       {
           count += countSelfLoops(n);
       }
       return  count / 2;
    }



    /**
     * Add an edge (well two in implementation but one in logic) to the graph going from from
     * to to and vice versa
     * @param from the Node from where the Edge is starting
     * @param to the Node the Edge is going to
     */
    public void addEdge(Node from, Node to)
    {

        if(! existsNode(from))
        {
            addNode(from);
        }
        if(!existsNode(to))
        {
            addNode(to);
        }
        Edge newEdgeLeft = new Edge(from, to);
        Edge newEdgeRight = new Edge(to, from);
        if(adjEdList.containsKey(from))
        {
            adjEdList.get(from).add(newEdgeLeft);
        }
        else
        {
            ArrayList<Edge> edgeList = new ArrayList<>();
            edgeList.add(newEdgeLeft);
            adjEdList.put(from , edgeList);
        }

        if(from.getId() == to.getId())
        {
            return; // prevent duplicating self loop
        }

        if(adjEdList.containsKey(to))
        {
            adjEdList.get(to).add(newEdgeRight);
        }
        else
        {
            ArrayList<Edge> edgeList = new ArrayList<>();
            edgeList.add(newEdgeRight);
            adjEdList.put(to , edgeList);

        }

    }

    /**
     *
     * Add an Edge to the graph, starting from the Node from to the Node to and vice versa
     * with a weight, if the starting and/or the ending nodes aren't in the graph,
     * they are created and added
     * @param from the Node the Edge is starting from
     * @param to the Node the Edge is going to
     * @param weight the weight of the Edge
     */
    public void addEdge(Node from, Node to , int weight)
    {
        Edge added = new Edge(from, to , weight);
        addEdge(added);
    }

    /**
     * Add an Edge to the graph starting from the Node with the id fromId to the
     * Node with the id toId and vice versa, if the Node referenced by one or the two ids doesn't exist,
     * they are created and added to the graph
     * @param fromId the id of the Node the Edge is starting from
     * @param toId the id of the Node the Edge is going to
     */
    public void addEdge(int fromId, int toId)
    {
        Node from = new Node(fromId);
        Node to = new Node(toId);
        addEdge(from, to);
    }



    /**
     * Get the out edges of the Node n
     * @param n the Node to get the out edges of
     * @return A list of all the out edges of the Node n
     */
    public List<Edge> getOutEdges(Node n)
    {
        ArrayList<Edge> outEdges = new ArrayList<>();
        if(adjEdList.containsKey(n))
        {
            outEdges = (ArrayList<Edge>) adjEdList.get(n);
        }
        return outEdges;
    }

    /**
     * Get the out edges of the Node associated with the id nodeId
     * @param nodeId the id of the Node to get the out edges of
     * @return A list of all the out edges of the Node n
     */
    public List<Edge> getOutEdges(int nodeId)
    {
        Node n = new Node(nodeId);
        return getOutEdges(n);
    }

    /**
     * Get the in edges of the Node n
     * @param n the Node to get the in edges of
     * @return A list of all the in edges of the Node n
     */
    public List<Edge> getInEdges(Node n)
    {

        ArrayList<Edge> inEdges = new ArrayList<>();
        for(List<Edge> edgeList : adjEdList.values())
        {
            for(Edge e : edgeList)
            {

                if(e.to().equals(n))
                {
                    inEdges.add(e);
                }
            }
        }

        return inEdges;
    }

    /**
     * Get the in edges of the Node associated with the id nodeId
     * @param nodeId the id of the Node to get the in edges of
     * @return A list of all the in edges of the Node with the id nodeId
     */
    @Override
    public List<Edge> getInEdges(int nodeId)
    {
        Node n = new Node(nodeId);
        return getInEdges(n);
    }

    /**
     * Get a list of all the incident edges of the Node n
     * the incident edges are the union of the in and out edges of a Node
     * @param n the Node to get the incident edges of
     * @return a list of the incident edges of the Node n
     */
    @Override
    public List<Edge> getIncidentEdges(Node n)
    {
        ArrayList<Edge> incidentEdges = new ArrayList<>();
        incidentEdges.addAll(getInEdges(n));
        ArrayList<Edge> toRemove = new ArrayList<>();
        for(Edge e : incidentEdges)
        {
            if(incidentEdges.contains(e.getSymmetric()) && !toRemove.contains(e) && e.from() != e.to())
            {
                toRemove.add(e.getSymmetric());
            }
        }
        incidentEdges.removeAll(toRemove);

        return  incidentEdges;
    }

    /**
     * Get a list of all the incident edges of the Node associated with the id nodeId
     * the incident edges are the union of the in and out edges of a Node
     * @param nodeId the id of the Node to get the incident edges of
     * @return a list of the incident edges of the Node n
     */
    @Override
    public List<Edge> getIncidentEdges(int nodeId)
    {
        Node n = new Node(nodeId);
        return getIncidentEdges(n);
    }




    /**
     * Create a string representing the graph in the dot format
     *
     * @return a string representing the graph in the dot format
     */
    @Override
    public String toDotString()
    {
        StringBuilder repr = new StringBuilder();
        HashSet<Edge> added = new HashSet<>();
        repr.append("graph g {\n");
        ArrayList<Node> graphNodeList = new ArrayList<>(graphNodes);
        Collections.sort(graphNodeList);
        for(Node n : graphNodeList) {
            List<Edge> edges = adjEdList.get(n);

            if (edges != null) {
                Collections.sort(edges);
                for (Edge e : edges) {
                    if(( (!added.contains(e.getSymmetric()) )) ||  e.from().getId() == e.to().getId() ) {
                        repr.append(String.format("\t%d -- %d", e.from().getId(), e.to().getId()));
                        added.add(e);
                        if (e.isWeighted()) {
                            repr.append(String.format(" [label=%d, len=%d]", e.getWeight(), e.getWeight()));
                        }
                        repr.append("\n");
                    }
                }

            }
            else
            {
                if(getIncidentEdges(n).size() == 0)
                {
                    repr.append("\t" +n.getId() + "\n");
                }
            }
        }
        repr.append("}");
        return repr.toString();
    }


    /**
     * Get a graph represented in the file in param filename followed by ".gv" and
     * parse the file to get an equivalent graph in return
     * @param filename the name of the file
     * @return a Graf representing the graph describer in "filename".gv
     */
    public static UndirectedGraf fromDotFile(String filename)
    {

        UndirectedGraf g = new UndirectedGraf();
        Path path = Paths.get(filename + ".gv");
        // /(digraph|graph)(?:[^\{])*\{((?:[^\}])*)\}/gm
        BufferedReader reader;
        try
        {
            reader = Files.newBufferedReader(path);
        }
        catch(IOException e )
        {
            System.out.println("error reading the file");
            return null;
        }

        String text = reader.lines().collect(Collectors.joining("\n"));

        Pattern nodesGetter = Pattern.compile("(graph)(?:[^\\{])*\\{((?:[^\\}])*)\\}");
        Matcher m = nodesGetter.matcher(text);

        if(m.groupCount() < 2)
        {
            System.out.println("Error parsing file: can't match a graph or digraph structure");
            return g;
        }
        m.matches();
        String type = m.group(1);
        String nodesAndEdges = m.group(2);

        String[] lines = nodesAndEdges.split("\\r?\\n");

        Pattern lineSplitter = Pattern.compile("\\s*([0-9]+)\\s*(--)\\s*([0-9]+)(.*);*|([0-9]+);*");

        for(String line: lines)
        {
            line = line.trim();
            Matcher lineMatcher = lineSplitter.matcher(line);

            if(lineMatcher.matches()) {
                if (lineMatcher.groupCount() == 5) {

                    if(lineMatcher.group(1) == null) // No match for the second group, we are in the second situation of the regex
                    {
                        String nodeStr = lineMatcher.group(0);
                        int nodeId = Integer.parseInt(nodeStr);
                        g.addNode(nodeId);
                    }
                    else {

                        String fromS = lineMatcher.group(1);
                        String toS = lineMatcher.group(3);
                        String possibleWeight = lineMatcher.group(4);

                        int from = Integer.parseInt(fromS);
                        int to = Integer.parseInt(toS);
                        Integer weight = null;
                        possibleWeight = possibleWeight.trim();
                        Pattern weightPattern = Pattern.compile("\\[\\s*(?:label|len)=(\\d+)\\s*,\\s*(?:label|len)=(\\d+)\\]");
                        Matcher weightMatcher = weightPattern.matcher(possibleWeight);
                        if(weightMatcher.matches())
                        {
                            weight = Integer.parseInt(weightMatcher.group(1));
                        }
                        if(weight != null)
                        {
                            g.addEdge(from,to, weight);
                        }
                        else
                        {
                            g.addEdge(from, to);
                        }
                    }
                }
            }
        }
        return g;
    }

    /**
     * Compute the reverse (G^-1) of the graph
     * An inverse graph G1 is created by inverting every edge (u,v) to (v,u)
     * however an undirected graph is equal to its inverse graph, so we just return a copy here
     * @return A graph representing G1
     */
    public UndirectedGraf getReverse()
    {
        return this.copy();
    }


    /**
     * Get the transitive closure of the graph
     * The transitive closure of a graph consists of flattening the reachability relation between its nodes by adding
     * a direct edge between two nodes u and v each time v is reachable from u (and the edge (u, v) was not already
     * in the graph).
     * @return the graph representing the transitive closure of the starting graph
     */
    public UndirectedGraf getTransitiveClosure()
    {
        UndirectedGraf copy = this.copy();
        copy.removeSelfLoops();
        copy.removeMultiEdges();


        ArrayList<Edge> toAdd = new ArrayList<>();

        for(Node u : copy.graphNodes)
        {
            ArrayList<Node> reachable = new ArrayList<>();
            reachable = (ArrayList<Node>) copy.getReachable(u);
            for(Node s : reachable)
            {
                if(s != u)
                {
                    toAdd.add(new Edge(u,s));
                }
            }
        }

        for(Edge e : toAdd)
        {
            copy.addEdge(e);
        }

        copy.removeMultiEdges();

        return copy;
    }




}
