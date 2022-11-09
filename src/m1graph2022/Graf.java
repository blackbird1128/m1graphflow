package m1graph2022;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Graf {

    Set<Node> graphNodes;
    Map<Node, List<Edge>> adjEdList;


    public Graf()
    {
        graphNodes = new HashSet<>();
        adjEdList = new HashMap<>();
    }


    /**
     * Build a graf using the values as a representation of an adjency array
     * @param values the values in the adjency array from left to right
     */
    public Graf(int ...values)
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
     * Return a copy of the graph
     * @return Graf A copy of the graph
     */
    protected Graf copy()
    {
        Graf copy = new Graf();
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
     *
     * Return the number of nodes contained in the graph
     *
     * @return A number equals to the number of nodes in the graph
     */
    public int nbNodes()
    {
       return graphNodes.size();
    }

    /**
     *
     * Add a Node to the graph
     * if the Node is already in the graph, it will not be added
     * or replaced
     *
     * @param n The Node to add
     * @return true if the node was added, false otherwise
     */
    public boolean addNode(Node n)
    {
        if(graphNodes.contains(n))
        {
            return false;
        }
        graphNodes.add(n);
        return true;
    }

    /**
     *
     * Add a Node to the graph
     * if the Node is already in the graph, it will not be added
     * or replaced
     *
     * @param nodeId The id of th node to add
     * @return true if the node was added, false otherwise
     */
    public boolean addNode(int nodeId, String name)
    {
        if(graphNodes.contains(nodeId))
        {
            return false;
        }
        graphNodes.add(new Node(nodeId,name));
        return true;
    }

    /**
     * Add a Node to the graph based on its node id
     * if the Node is already in the graph, it will not be added
     * or replaced
     *
     * @param nodeId The nodeId referencing the Node to add
     * @return true if a Node with the same id was added, false otherwise
     */
    public boolean addNode(int nodeId)
    {
        Node n = new Node(nodeId);
        if(graphNodes.contains(n))
        {
            return false;
        }
        graphNodes.add(n);
        return true;
    }

    /**
     *
     * Check if a Node is contained in the graph,
     * if the node is in the graph, it will return true, false otherwise
     *
     * @param n The node to check the presence in the graph
     * @return true if the Node was found in the graph, false otherwise
     */
    public boolean existsNode(Node n)
    {
        return graphNodes.contains(n);
    }

    /**
     * Check if the node with the id nodeId exist in the graph
     * if the node is in the graph, it will return true, false otherwise
     * @param nodeId the id of the Node to check the presence in the graph
     * @return true if a Node with the same id was found in the graph, false otherwise
     */
    public boolean existsNode(int nodeId)
    {
        Node searchedNode = new Node(nodeId);
        return existsNode(searchedNode);

    }

    /**
     *
     * Get the node associated with the id parameter if it's present in the graph,
     * null otherwise
     * @param id the id of the Node to get
     * @return a Node or null if the Node was not found
     */
    public Node getNode(int id)
    {
        if(existsNode(id))
        {
            for ( Node n : graphNodes ) {
                if(n.getId() == id )
                {
                    return n;
                }
            }
        }
        return null;
    }


    /**
     *
     * Get all the nodes in the graph in a List
     *
     * @return an ArrayList containing all the nodes of the graph
     */
    public List<Node> getAllNodes()
    {
        return new ArrayList<>(graphNodes);
    }

    /**
     *
     * Get the largest node id
     * @return the largest node id as an int
     */
    public int largestNodeId()
    {
        Node largest = Collections.max(graphNodes);
        return largest.getId();
    }

    /**
     *
     * Get the smallest node id
     * @return the smallest node id as an int
     */
    public int smallestNodeId()
    {
        Node smallest = Collections.min(graphNodes);
        return smallest.getId();
    }

    /**
     *
     * Get the number of edges in the graph
     * @return the number of edges as an int
     */
    public int nbEdges()
    {
        return getAllEdges().size();
    }

    /**
     *
     * Check if there is an Edge between the Node u and v,
     * return true if there is an Edge between u and v, false
     * otherwise
     * @param u The first Node of the hypothetical Edge
     * @param v The second Node
     * @return true if the Edge exist, false otherwise
     */
    public boolean existsEdge(Node u , Node v)
    {
        Edge lookedEdge = new Edge(u, v);
        for(List<Edge> edges : adjEdList.values())
        {
            if(edges.contains(lookedEdge))
            {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Check if there is an Edge between the Node with id fromId
     * and the Node with the id toId, return true if there is, false
     * otherwise
     * @param fromId the id of the Node where the Edge looked for is starting
     * @param toId the id of the Node where the Edge looked for is ending
     * @return true if the Edge exist, false otherwise
     */
    public boolean existsEdge(int fromId, int toId)
    {
        Node from = new Node(fromId);
        Node to = new Node(toId);
        Edge lookedEdge = new Edge(from, to);
        for(List<Edge> edges : adjEdList.values())
        {
            if(edges.contains(lookedEdge))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the Edge e exist, return true if it exists,
     * false otherwise
     *
     * @param e the edge to Check
     * @return true if the edge exist, false otherwise
     */
    public boolean existsEdge(Edge e) {
        for(List<Edge> edges : adjEdList.values()) {
            if (edges.contains(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Add an Edge in the graph between the Node from and the Node to,
     * if one or both Node doesn't exist they are created and added to the graph
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
        Edge newEdge = new Edge(from, to);
        if(adjEdList.containsKey(from))
        {
            adjEdList.get(from).add(newEdge);
        }
        else
        {
            ArrayList<Edge> edgeList = new ArrayList<>();
            edgeList.add(newEdge);
            adjEdList.put(from , edgeList);
        }

    }

    /**
     * Add the Edge toAdd to the graph, if the starting Node and/or ending Node
     * of the Edge doesn't exist, they are created and added to the graph
     * @param toAdd the Edge to add
     */
    public void addEdge(Edge toAdd)
    {
        Node from = toAdd.from();
        Node to = toAdd.to();
        if(!existsNode(from))
        {
            addNode(from);
        }
        if(!existsNode(to))
        {
            addNode(to);
        }
        if(adjEdList.containsKey(from))
        {
            adjEdList.get(from).add(toAdd);
        }
        else
        {
            ArrayList<Edge> edgeList = new ArrayList<>();
            edgeList.add(toAdd);
            adjEdList.put(from, edgeList);
        }
    }

    /**
     *
     * Add an Edge to the graph, starting from the Node from to the Node to
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
     * Node with the id toId, if the Node referenced by one or the two ids doesn't exist,
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
     * Add an Edge to the graph starting from the Node with id fromId to the
     * Node with id toId with a weight, if the Node referenced by one or the two id doesn't exist,
     * they are created and added to the graph
     *
     * @param fromId the id of the Node the Edge is starting from
     * @param toId the id of the Node the Edge is going to
     * @param weight the weight of the Edge
     */
    public void addEdge(int fromId, int toId, int weight)
    {
        Node from = new Node(fromId);
        Node to = new Node(toId);
        Edge toAdd = new Edge(from, to , weight);
        addEdge(toAdd);
    }

    public List<Edge> getAllEdges()
    {
        List<Edge> allEdges = new ArrayList<>();
        for(List<Edge> edges: adjEdList.values())
        {
            allEdges.addAll(edges);
        }
        return allEdges;
    }

    /**
     * Remove a Node and all the edges associated to it if the Node is present in the graph then return
     * true, do nothing and return false otherwise
     * @param n The Node to remove
     * @return true if the Node was removed, false otherwise
     */
    public boolean removeNode(Node n)
    {

        if(!this.existsNode(n))
        {
            return false;
        }
        ArrayList<Edge> edges = new ArrayList<>(this.getIncidentEdges(n));
        for(int i = 0 ; i < edges.size(); i++)
        {
            this.removeEdge(edges.get(i));
        }
        this.graphNodes.remove(n);
        return true;
    }

    /**
     * Remove the Node with the id nodeId and all the edges associated to if the Node is present in the graph then return
     * true, do nothing and return false otherwise
     * @param nodeId the id of the Node to remove
     * @return true if the Node was removed, false otherwise
     */
    public boolean removeNode(int nodeId)
    {
        Node n = new Node(nodeId);
        return removeNode(n);
    }

    /**
     * Remove the Edge going from the Node from to the Node to if it exists,
     * return true if the operation was successful, false otherwise
     * @param from The Node the Edge to remove is starting from
     * @param to The Node the Edge to remove is going to
     * @return true if the Edge was removed, false otherwise
     */
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
        return result;
    }

    /**
     * Remove the Edge going from the Node with the id fromId to the Node with the id toId if it exists,
     * return true if the operation was successful, false otherwise
     * @param fromId the id of the Node the Edge to remove is starting from
     * @param toId the id of the Node the Edge to remove is going to
     * @return true if the Edge was removed, false otherwise
     */
    public boolean removeEdge(int fromId, int toId)
    {
        Node from = new Node(fromId);
        Node to = new Node(toId);
        return removeEdge(from,to);
    }

    /**
     * Remove the Edge going from the Node from to the Node to with the specified weight if it exists;
     * return true if the operation was successful, false otherwise
     * @param from the Node the Edge to remove is starting from
     * @param to the Node the Edge to remove is going to
     * @param weight the weight of the Edge to remove
     * @return true if the Edge was removed, false otherwise
     */
    public boolean removeEdge(Node from, Node to , int weight)
    {
        Edge toRemove1 = new Edge(from,to , weight);
        return removeEdge(toRemove1);

    }

    /**
     * Return the in-degree of the Node n
     * @param n the Node we want to get the in-degree of
     * @return the in-degree of the Node
     */
    public int inDegree(Node n)
    {
        return getInEdges(n).size();
    }

    /**
     * Return the in-degree of the Node with the id nodeId
     * @param nodeId the id of the Node we want to get the in degree of
     * @return the in-degree of the Node
     */
    public int inDegree(int nodeId)
    {
        Node n = new Node(nodeId);
        return inDegree(n);
    }

    /**
     * Return the out-degree of the Node
     * @param n the Node we want to get the out-degree of
     * @return the out-degree of the Node
     */
    public int outDegree(Node n)
    {
        if(adjEdList.containsKey(n))
        {
            return adjEdList.get(n).size();
        }
        return 0;
    }

    /**
     * Return the out-degree of the Node with the id nodeId
     * @param nodeId the id of the Node we want to get the out-degree of
     * @return the out degree of the node
     */
    public int outDegree(int nodeId)
    {
        Node n = new Node(nodeId);
        return outDegree(n);
    }

    protected int countSelfLoops(Node n )
    {
        int count = 0;
        List<Edge> out = getOutEdges(n);
        for(Edge e : out)
        {
            if(e.from().getId() == e.to().getId())
            {
                count += 1;
            }
        }

        return count;

    }

    /**
     * Return the degree of a Node
     * @param n the Node we want to get the degree of
     * @return the degree of the Node
     */
    public int degree(Node n)
    {
        return getIncidentEdges(n).size() + 2 * countSelfLoops(n);
    }

    /**
     * Return the degree of a Node with the id nodeId
     * @param nodeId the id of the Node we want to get the degree of
     * @return the degree of the Node
     */
    public int degree(int nodeId)
    {
        return  getIncidentEdges(nodeId).size() + 2 * countSelfLoops(new Node(nodeId));
    }

    /**
     * Try to remove the Edge passed as parameter, return true if the
     * operation was successful, false otherwise
     * @param toRemove the Edge to remove
     * @return true if the Edge was removed, false otherwise
     */
    public boolean removeEdge(Edge toRemove)
    {
        boolean result = false;
        if(existsEdge(toRemove))
        {

            for(Node n : graphNodes)
            {
                if(adjEdList.get(n) != null) {
                    adjEdList.get(n).remove(toRemove);
                    result = true;
                }

            }
        }
        return result;

    }


    /**
     * Get all the nodes that are the successors of the Node n
     * A successor is a Node that can be accessed from n using an Edge
     * remove all duplicated successors
     * @param n the Node to get the successors of
     * @return A list of the successors to n
     */
    public List<Node> getSuccessors(Node n)
    {
        HashSet<Node> successorsSet = new HashSet<>();

        ArrayList<Edge> edgeList =  new ArrayList<>();
        if(adjEdList.containsKey(n))
        {
            edgeList = (ArrayList<Edge>) adjEdList.get(n);
        }

        for (Edge nEdge : edgeList ) {
            successorsSet.add(nEdge.to()); // Set prevent duplicate
        }
        return new ArrayList<>(successorsSet);

    }

    /**
     * Get all the nodes that are the successors of the Node n including duplicates
     * @param n The node to get the successors of
     * @return A list of the successors of the Node n, the list may include duplicates at non-predictable positions
     */
    public List<Node> getSuccessorsMulti(Node n)
    {

        ArrayList<Node> successorsSet = new ArrayList<>();

        ArrayList<Edge> edgeList =  new ArrayList<>();
        if(adjEdList.containsKey(n))
        {
            edgeList = (ArrayList<Edge>) adjEdList.get(n);
        }

        for (Edge nEdge : edgeList ) {
            successorsSet.add(nEdge.to());
        }
        return successorsSet;
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
    public List<Edge> getIncidentEdges(Node n)
    {
        ArrayList<Edge> incidentEdges;
        incidentEdges = new ArrayList<>(getOutEdges(n));
        incidentEdges.addAll(getInEdges(n));

        return incidentEdges;
    }

    /**
     * Get a list of all the incident edges of the Node associated with the id nodeId
     * the incident edges are the union of the in and out edges of a Node
     * @param nodeId the id of the Node to get the incident edges of
     * @return a list of the incident edges of the Node n
     */
    public List<Edge> getIncidentEdges(int nodeId)
    {
        Node n = new Node(nodeId);
        return getIncidentEdges(n);
    }

    /**
     * Check if the Node u and v are adjacent
     * two nodes are adjacent if there exist an Edge
     * between u and v and an Edge between v and u
     * @param u The u node
     * @param v the v node
     * @return true if the nodes are adjacent, false otherwise
     */
    public boolean adjacent(Node u, Node v)
    {
        return existsEdge(u, v) || existsEdge(v,u);
    }

    /**
     * Check if the Node with the id fromId and the Node with
     * the id toId are adjacent
     * two nodes are adjacent if there exist an Edge
     * between u and v and an Edge between v and u
     * @param fromId the id of the Node u
     * @param toId the id of the Node v
     * @return true if the nodes are adjacent, false otherwise
     */
    public boolean adjacent(int fromId, int toId)
    {
        return existsEdge(fromId, toId) || existsEdge(toId, fromId);
    }


    /**
     * Compute the reverse (G^-1) of the graph
     * An inverse graph G1 is created by inverting every edge (u,v) to (v,u)
     * @return A graph representing G1
     */
    public Graf getReverse()
    {
        Graf reverse = new Graf();
        for(Node n : adjEdList.keySet())
        {
            reverse.addNode(n);
            ArrayList<Edge> edgeN = (ArrayList<Edge>) getOutEdges(n);
            for(Edge e : edgeN)
            {

                reverse.addEdge(e.getSymmetric());


            }
        }
        return reverse;
    }

    /**
     * Compute the adjacency matrix M of the graph G
     * the adjacency matrix is a matrix of size N*N where N is the
     * number of nodes in the graph each element of the matrix (i,j) represent the number of
     * edges going from i to j
     * Note: the adjency matrix indexing start at 1 witch will cause usual representation starting at 1
     * to be wrapped into arrays of 0
     * @return the adjacency matrix representing the graph
     */
    public int[][] toAdjMatrix()
    {
        int maxSize = Collections.max(graphNodes).getId() +1;
        int[][] mat = new int[maxSize][maxSize];
        for(int i = 0 ; i < maxSize ; i++)
        {
            if(existsNode(i))
            {
                for(Edge  e: getOutEdges(i))
                {
                    mat[i][e.to().getId()]++;
                }
            }
        }
        return mat;

    }

    /**
     * Get a representation of the graph in the successor array formalism
     * the successor array represent the graph by representing each node by their
     * connections to other nodes and separating each nodes by 0
     * If the graph contain a Node with an id 0, return null instead of an ambiguous array;
     * @return an array of int representing the successor array
     */
    public int[] toSuccessorArray()
    {
        int size = 0;
        int maxNode = largestNodeId();
        int minNode = smallestNodeId();

        if(minNode == 0)
        {
            System.out.println("The graph is using node with id 0 making it impossible to \n generate a non ambiguous successor array");
            return null;
        }


        for(int i = 1 ; i <= maxNode; i++)
        {
            ArrayList<Edge> edges = (ArrayList<Edge>) getOutEdges(i);
            size += edges.size() + 1;
        }

        int[] succArray = new int[size];

        int index = 0;


        for(int i = 1 ; i <= maxNode; i++)
        {
            ArrayList<Edge> edges = (ArrayList<Edge>) getOutEdges(i);
            Collections.sort(edges);
            for(Edge e : edges) {
                succArray[index] = e.to().getId();
                index++;
            }
            succArray[index] = 0;
            index++;

        }
        return succArray;
    }

    /**
     * Create a string representing the graph in the dot format
     *
     * @return a String representing the graph in the dot format
     */
    public String toDotString()
    {
        StringBuilder repr = new StringBuilder();
        repr.append("digraph g {\n");
        repr.append("\trankdir=LR\n");
        ArrayList<Node> graphNodeList = new ArrayList<>(graphNodes);
        Collections.sort(graphNodeList);
        for(Node n : graphNodeList) {
            List<Edge> edges = adjEdList.get(n);

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
                if(getIncidentEdges(n).size() == 0)
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
    public void toDotFile(String filename)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filename + ".gv", "UTF-8");
            writer.print(this.toDotString());
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("error writing to dot file");
        }

    }

    /**
     Write the string representing the graph in the dot format into a file
     * the filename is made of the param filename and the extension param extension
     * @param fileName the name of the file
     * @param extension the extension of the file
     */
    public void toDotFile(String fileName, String extension)
    {
        try
        {
            PrintWriter writer = new PrintWriter(fileName + extension, "UTF-8");
            writer.print(this.toDotString());
            writer.close();

        }
        catch(IOException e)
        {
            System.out.println("error writing to dot file");
        }

    }

    /**
     * Get a graph represented in the file in param filename followed by ".gv" and
     * parse the file to get an equivalent graph in return
     * @param filename the name of the file
     * @return a Graf representing the graph describer in "filename".gv
     */
    public static Graf fromDotFile(String filename)
    {

        Graf g = new Graf();
        Path path = Paths.get(filename + ".gv");
        // /(digraph|graph)(?:[^\{])*\{((?:[^\}])*)\}/gm
        BufferedReader reader;
        try
        {
            reader = Files.newBufferedReader(path);
        }
        catch(IOException e)
        {
            System.out.println("error reading the file");
            return null;
        }

        String text = reader.lines().collect(Collectors.joining("\n"));

        Pattern nodesGetter = Pattern.compile("(digraph)(?:[^\\{])*\\{((?:[^\\}])*)\\}");
        Matcher m = nodesGetter.matcher(text);

        if(m.groupCount() < 2)
        {
            System.out.println("Error parsing file: can't match a digraph structure");
            return g;
        }
        m.matches();
        String type = m.group(1);
        String nodesAndEdges = m.group(2);

        String[] lines = nodesAndEdges.split("\\r?\\n");

        Pattern lineSplitter = Pattern.compile("\\s*([0-9]+)\\s*(->)\\s*([0-9]+)(.*);*|([0-9]+);*");

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
     * Remove the self loops in the graph, aka edges (u,u) with u a node
     */
    protected void removeSelfLoops()
    {
        for(List<Edge> edges : adjEdList.values())
        {
            edges.removeIf(Edge::isSelfLoop);
        }
    }


    /**
     * Remove the multi edges in a graph, which are edges that are present multiple time with the same
     * parameters (from, to, weight) if weight is null, only (from, to) is considered
     */
    protected void removeMultiEdges()
    {
        HashSet<Edge> uniqueEdges = new HashSet<>();
        ArrayList<Edge> toRemove = new ArrayList<>();
        for(List<Edge> edges : adjEdList.values())
        {

            for(Edge e : edges)
            {
                Edge eWithoutWeight = new Edge(e.from().getId(), e.to().getId());

                if(!uniqueEdges.add(eWithoutWeight))
                {
                    toRemove.add(e);
                }


            }
        }
        for(Edge e: toRemove)
        {
            removeEdge(e);
        }
    }


    /**
     * Visit a sub part of a graph recursively starting from the Node u
     * and using the set visited to check what are the node already visited
     * return a list of all the visited nodes
     * @param u the node the visit is starting from
     * @param visited already visited nodes are placed in visited
     * @return a list of the visited node in order of visit
     */
    protected List<Node> DFSVisit(Node u,Set<Node> visited)
    {
        ArrayList<Node> visit = new ArrayList<>();
        visited.add(u);
        for(Node v : getSuccessors(u)) {

            if (!visited.contains(v)) {
                visit.add(v);
                visit.addAll(DFSVisit(v, visited));

            }

        }
        return visit;
    }


    /**
     * Return a list representing a DFS traversal of the graph
     * if the graph is made of sub not connected part, they are explored in
     * node number order
     * @return A list representing the visit
     */
    public List<Node> getDFS()
    {
        HashSet<Node> visited = new HashSet<>();
        ArrayList<Node> visit = new ArrayList<>();

        ArrayList<Node> sortedNode = new ArrayList<>(graphNodes);
        Collections.sort(sortedNode);

        for(Node n : sortedNode)
        {
            if(! visited.contains(n))
            {
                visit.add(n);
                visit.addAll(DFSVisit(n, visited));
            }
        }

        return visit;
    }


    /**
     /**
     * Visit a sub part of a graph iteratively starting from the first Node
     * in the Queue q
     * and using the set visited to check what are the node already visited
     * return a list of all the visited nodes
     * @param visited already visited nodes are placed in visited
     * @param q the nodes to visit in order (FIFO)
     * @return a list of the visited node in order of visit
     */
    protected List<Node> BFSVisit( Set<Node> visited, Queue<Node> q)
    {
        ArrayList<Node> visit = new ArrayList<>();
        while(! q.isEmpty())
        {
            Node u = q.poll();
            ArrayList<Node> successors = (ArrayList<Node>) getSuccessors(u);
            Collections.sort(successors);
            visited.add(u);

            for(Node v : successors)
            {

                if(! visited.contains(v))
                {
                     visit.add(v);
                     visited.add(v);
                     q.offer(v);
                }

            }
        }
        return visit;
    }


    /**
     * Return a list representing a BFS traversal of the graph
     * if the graph is made of sub not connected part, they are explored in
     * node number order
     * @return A list representing the visit
     */
    public List<Node> getBFS()
    {
        HashSet<Node> visited = new HashSet<>();
        ArrayList<Node> visit = new ArrayList<>();
        Queue<Node> s = new LinkedList<>();

        ArrayList<Node> sortedNode = new ArrayList<>();
        sortedNode.addAll(graphNodes);
        Collections.sort(sortedNode);

        for(Node n : sortedNode)
        {
            if(! visited.contains(n))
            {
                visit.add(n);
                s.offer(n);
                visit.addAll(BFSVisit(visited, s));
            }
        }
        return visit;

    }


    /**
     * Get a list of all the nodes reachable from the node n
     * (a node v is reachable from u if there is a path from u to v
     * @param n the node to get the reachable nodes of
     * @return a List of all the reachable nodes
     */
    public List<Node> getReachable(Node n)
    {
        HashSet<Node> visited = new HashSet<>();
        return DFSVisit(n , visited);

    }

    /**
     * Get the transitive closure of the graph
     * The transitive closure of a graph consists of flattening the reachability relation between its nodes by adding
     * a direct edge between two nodes u and v each time v is reachable from u (and the edge (u, v) was not already
     * in the graph).
     * @return the graph representing the transitive closure of the starting graph
     */
    public Graf getTransitiveClosure()
    {
        Graf copy = this.copy();
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


    public void addEdge(int idFrom, String t) {
        addEdge(new Edge(idFrom,t));
    }
    public void addEdge(String from, int idTo) {
        addEdge(new Edge(from,idTo));
    }
}
