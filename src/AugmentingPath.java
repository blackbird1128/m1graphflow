import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import m1graph2022.*;

import java.util.*;


public class AugmentingPath {


    public static void main(String[] args)  {
            Graf g = Graf.fromDotFile("dots/weightedSimpleGraph");
            System.out.println(g.toDotString());
            System.out.println( AugmentingPath.getAugmentingPath(g.getNode(1), g.getNode(5), g,  AP_ALGORITHM.DFS));
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

            // If no path was found, return an empty path and 0 flow
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

    private static Pair<List<Edge>,Integer> getAugmentingPathLookAheadRec(Node start, Node end, Graf g, int lookahead, Set<Node> visited, Pair<List<Edge>,Integer> currentPath){
        if (!visited.contains(start)){
            visited.add(start);
        }
        if(start.equals(end)){
            return currentPath;
        }
        List<Pair<List<Edge>,Integer>> listOfCurrentPartialPaths = allPartialPaths(start,end,g, lookahead, visited);
        if(listOfCurrentPartialPaths.size()==0){
            int last = currentPath.first.size()-1;
            Node from = currentPath.first.get(last).from();
            currentPath.first.remove(last);
            int max = 0;
            for (Edge e : currentPath.first) {
                max = Math.max(e.getWeight(),max);
            }
            currentPath.second = max;
            return getAugmentingPathLookAhead(from,end,g,lookahead);
        }


        Pair<List<Edge>, Integer> tokeep = new Pair<>(new ArrayList<>(), 0 );
        for (Pair<List<Edge>, Integer> pair : listOfCurrentPartialPaths) {
            if(tokeep.second.compareTo(pair.second)<0){
                tokeep = pair;
            }
        }
        currentPath.first.addAll(tokeep.first);
        currentPath.second = Math.min(currentPath.second, tokeep.second);
        for (Edge e :
                tokeep.first) {
            visited.add(e.from());
        }
        return getAugmentingPathLookAheadRec(
                tokeep.first.get(tokeep.first.size()-1).to(),
                end,
                g,
                lookahead,
                visited,
                currentPath
                );
    }

    private static List<Pair<List<Edge>, Integer>> allPartialPaths(Node start, Node end, Graf g, int lookahead, Set<Node> visited) {
        ArrayList<Pair<List<Edge>,Integer>> partialPaths = new ArrayList();
        if (lookahead == 0 || start.equals(end)){
            for (Edge e : g.getOutEdges(start)) {
                if (!visited.contains(e.to())){
                    Pair<List<Edge>,Integer> p = new Pair<>(new ArrayList<>(),e.getWeight());
                    p.first.add(e);
                    partialPaths.add(p);
                }
            }
        }else{

            for (Edge e :
                    g.getOutEdges(start)) {
                if (!visited.contains(e.to())){
                    visited.add(e.to());
                    List<Pair<List<Edge>,Integer>> cpp = allPartialPaths(e.to(),end,g,lookahead-1,visited); //cpp = Current Partial Paths
                    visited.remove(e.to());
                    for (Pair<List<Edge>,Integer> pair : cpp) {
                        List<Edge> path = new ArrayList<>();
                        path.add(e);
                        path.addAll(pair.first);
                        Integer min = e.getWeight().compareTo(pair.second) < 0 ? e.getWeight(): pair.second;
                        partialPaths.add(new Pair<>(path,min));
                    }
                }
            }
        }
        
        return partialPaths;
    }
}
