import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import m1graph2022.*;

import java.util.*;


public class AugmentingPath {


    public static void main(String[] args)  {
            Graf g = Graf.fromDotFile("dots/weightedSimpleGraph");
            System.out.println(g.toDotString());
            System.out.println( AugmentingPath.getAugmentingPath(g.getNode(1), g.getNode(5), g,  AP_ALGORITHM.DFS));
    }

    public static boolean canJoin(Node from, Node to, Graf g, Set<Node> visited){
        boolean canJoin = false;
        if(from.equals(to)){
            return true;
        }
        for (Edge e :
                g.getOutEdges(from)) {
            if (!visited.contains(e.to())) {
                visited.add(e.to());
                canJoin = canJoin(e.to(),to,g,visited);
                visited.remove(e.to());
            }
            if (canJoin){
                return true;
            }
        }
        return false;
    }

    public static Pair<List<Edge>,Integer> getAugmentingPath(Node start, Node end, Graf g, AP_ALGORITHM algorithm )
    {
        switch (algorithm)
        {
            case DFS:
                return getAugmentingPathDFS(start, end, g);
            case GREEDY:
                return getAugmentingPathLookAhead(start,end,g,0);
            case LOOKAHEAD1:
                return getAugmentingPathLookAhead(start,end,g,1);
            case LOOKAHEAD2:
                return getAugmentingPathLookAhead(start,end,g,2);
                default:
                return null;
        }
    }

    public static Pair<List<Edge>,Integer> getAugmentingPath(int idStart, int idEnd, Graf g , AP_ALGORITHM algorithm)
    {
        Node start = new Node(idStart);
        Node end = new Node(idEnd);
        return getAugmentingPath(start, end, g, algorithm);
    }

    //////////
    ////////// DFS algorithm to compute the augmenting path
    //////////
    private static Pair<List<Edge>, Integer> getPathToRecDFS(Node start, Node end, Graf g, List<Edge> currentPath, Set<Node> visited, int currentFlow)
    {
        if (start.equals(end))
        {
            // Return the current path and flow
            return new Pair<List<Edge>, Integer>(currentPath, currentFlow);
        }
        else
        {
            // Mark the current node as visited
            visited.add(start);

            // Create a priority queue for the edges
            PriorityQueue<Edge> edges = new PriorityQueue<>(g.getOutEdges(start));

            // Search for augmenting paths
            while (!edges.isEmpty())
            {
                // Take the edge with the greatest weight
                Edge e = edges.poll();

                // Get the next node
                Node n = e.to();

                // If the next node has not been visited
                if (!visited.contains(n))
                {
                    // Copy the current path and visited array
                    List<Edge> extendedPath = new ArrayList<>(currentPath);
                    Set<Node> visitedC = new HashSet<>(visited);

                    // Add the current edge to the path
                    extendedPath.add(e);

                    // Update the flow with the weight of the edge
                    int newFlow = Math.min(currentFlow, e.getWeight());

                    // Recursively search for paths from the next node
                    Pair<List<Edge>, Integer> path = getPathToRecDFS(n, end, g, extendedPath, visitedC, newFlow);

                    // If a path was found, return it
                    if (path != null)
                    {
                        return path;
                    }
                }
            }

            // If no path was found, return an empty path
            return null;
        }
    }

    private static Pair<List<Edge>,Integer> getAugmentingPathDFS(Node start, Node end , Graf g)
    {
        List<Node> startReachable =  g.getReachable(start);
        if(! startReachable.contains(end))
        {
            return null;
        }
        else
        {
            HashSet<Node> visited = new HashSet<>();
            ArrayList<Edge> currentPath = new ArrayList<>();
            System.out.println("Visited graph : " + g.toDotString());
            return getPathToRecDFS(start, end, g,currentPath,visited, Integer.MAX_VALUE);
        }


    }


    //////////
    ////////// Greedy algorithms to compute the augmenting path
    //////////

    private static Pair<List<Edge>,Integer> getAugmentingPathLookAhead(Node start, Node end , Graf g, int lookahead) {
        Set<Node> visited = new HashSet<>();
        Pair<List<Edge>,Integer> currentPath = new Pair<>(new ArrayList<>(),Integer.MAX_VALUE);
        return getAugmentingPathLookAheadRec(start,end,g,lookahead, visited, currentPath);
    }

    private static Pair<List<Edge>, Integer> getAugmentingPathLookAheadRec(Node start, Node end, Graf g, int lookahead, Set<Node> visited, Pair<List<Edge>, Integer> currentPath) {
        if(start.equals(end)){
            return currentPath;
        }
        if(!canJoin(start,end,g,visited)){
            return null;
        }
        visited.add(start);
        List<Pair<List<Edge>,Integer>> potentialPaths = potentialPaths(start,end,g,visited,lookahead+1);
        visited.remove(start);
        Pair<List<Edge>,Integer> currentBestOption=null;
        for (Pair<List<Edge>, Integer> p:
                potentialPaths){
            if(currentBestOption == null){
                currentBestOption = p;
                continue;
            }
            if(p.second.compareTo(currentBestOption.second)>0){
                currentBestOption = p;
                currentBestOption.second = p.second;
            }
        }
        if(currentBestOption ==null || currentBestOption.second == 0)
            return null;

        currentPath.first.addAll(currentBestOption.first);
        currentPath.second = Math.min(currentPath.second, currentBestOption.second);
        Node last = currentPath.first.get(currentPath.first.size()-1).to();
        for (Edge e :
                currentBestOption.first) {
            visited.add(e.to());
        };
        return getAugmentingPathLookAheadRec(last,end, g,lookahead,visited,currentPath);
    }

    private static List<Pair<List<Edge>, Integer>> potentialPaths(Node start, Node end, Graf g, Set<Node> visited, int distance) {
        List<Pair<List<Edge>, Integer>> paths = new ArrayList<>();
        if(distance==0 || start.equals(end)){
            for (Edge e :
                    g.getOutEdges(start)) {
                Pair<List<Edge>, Integer> p = new Pair<>(new ArrayList<>(),e.getWeight());
                p.first.add(e);
                paths.add(p);
            }
            return paths;
        }
        for (Edge e :
                g.getOutEdges(start)) {
            visited.add(e.to());
            if(!canJoin(e.to(),end,g,visited)) continue;
            for (Pair<List<Edge>,Integer> p : potentialPaths(e.to(),end,g,visited,distance-1)){
                Pair<List<Edge>,Integer> currentPath = new Pair<>(new ArrayList<>(),0);
                currentPath.first.add(e);
                currentPath.second = e.getWeight();
                if(p.second.compareTo(currentPath.second)<0){
                    currentPath.second = p.second;
                }
                currentPath.first.addAll(p.first);
                paths.add(currentPath);
            }
            visited.remove(e.to());
        }
        return paths;
    }


}
