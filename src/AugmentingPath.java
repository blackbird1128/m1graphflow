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
        visited.add(from);
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
        visited.remove(from);
        return false;
    }

    static boolean blocksTheWay(Node end, List<Edge> path, Graf g, Set<Node> alreadyVisited){
        Set<Node> visited = new HashSet<>(alreadyVisited);
        visited.add(path.get(0).from());
        for (Edge e :
                path) {
            visited.add(e.to());
        }
        return !canJoin(path.get(path.size()-1).to(),end,g,visited);
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

    private static Pair<List<Edge>,Integer> getPathToRecDFS(Node start, Node end, Graf g, List<Edge> currentPath, Set<Node> visited, int currentFlow)
    {
        if(start.equals(end))
        {
            return new Pair<List<Edge>,Integer>(currentPath,currentFlow);
        }
        else
        {
            visited.add(start);
            for(Node n : g.getSuccessors(start))
            {
                if(!visited.contains(n))
                {
                    ArrayList<Edge> extendedPath =  new ArrayList<>(currentPath); // copy cause reference are fucking weird
                    List<Edge> le = g.getEdges(start,n);
                    Edge e = Collections.max(le); // we take the greatest augmenting path
                    extendedPath.add(e);
                    if(e.getWeight() < currentFlow){
                        currentFlow = e.getWeight();
                    }
                    return getPathToRecDFS(n, end, g , extendedPath,visited, currentFlow);
                }

            }
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
        List<Pair<List<Edge>,Integer>> potentialPaths = potentialPaths(start,end,g,new HashSet<>(visited),lookahead);
        Pair<List<Edge>,Integer> currentBestOption=null;
        for (Pair<List<Edge>, Integer> p:
                potentialPaths){
            if (!blocksTheWay(end,p.first,g,visited)){
                if(currentBestOption == null){
                    currentBestOption = p;
                    continue;
                }
                if(p.second.compareTo(currentBestOption.second)>0){
                    currentBestOption = p;
                    currentBestOption.second = p.second;
                }
            }
        }
        if(currentBestOption == null || currentBestOption.second == 0)
            return null;

        currentPath.first.addAll(currentBestOption.first);
        currentPath.second = Math.min(currentPath.second, currentBestOption.second);
        Node last = currentPath.first.get(currentPath.first.size()-1).to();
        for (Edge e :
                currentPath.first) {
            visited.add(e.to());
        };
        return getAugmentingPathLookAheadRec(last,end, g,lookahead,visited,currentPath);
    }

    private static List<Pair<List<Edge>, Integer>> potentialPaths(Node start, Node end, Graf g, Set<Node> visited, int distance) {
        visited.add(start);
        List<Pair<List<Edge>, Integer>> paths = new ArrayList<>();
        if(start.equals(end)){
            List<Pair<List<Edge>, Integer>> ret = new ArrayList<>();
            ret.add(new Pair<>(new ArrayList<>(),Integer.MAX_VALUE));
            visited.remove(start);
            return ret;
        }
        if(distance==0){
            for (Edge e :
                    g.getOutEdges(start)) {
                if(!visited.contains(e.to())) {
                    Pair<List<Edge>, Integer> p = new Pair<>(new ArrayList<>(), e.getWeight());
                    p.first.add(e);
                    paths.add(p);
                }
            }
            visited.remove(start);
            return paths;
        }
        for (Edge e :
                g.getOutEdges(start)) {
            if(!canJoin(e.to(),end,g,visited)) continue;
            for (Pair<List<Edge>,Integer> p : potentialPaths(e.to(),end,g,visited,distance-1)){
                if(!visited.contains(e.to())) {
                    Pair<List<Edge>, Integer> currentPath = new Pair<>(new ArrayList<>(), 0);
                    currentPath.first.add(e);
                    currentPath.second = e.getWeight();
                    if (p.second.compareTo(currentPath.second) < 0) {
                        currentPath.second = p.second;
                    }
                    currentPath.first.addAll(p.first);
                    paths.add(currentPath);
                }
            }
        }
        visited.remove(start);
        return paths;
    }


}
