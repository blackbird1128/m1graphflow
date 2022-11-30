import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;
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



    private static Pair<List<Edge>,Integer> getPathToRec(Node start, Node end, Graf g, List<Edge> currentPath, Set<Node> visited, int currentFlow)
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
                    return getPathToRec(n, end, g , extendedPath,visited, currentFlow);
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
            return getPathToRec(start, end, g,currentPath,visited, Integer.MAX_VALUE);
        }


    }


}
