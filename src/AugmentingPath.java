import m1graph2022.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


enum AP_ALGORITHM
{
    DFS,

}

public class AugmentingPath {


    public static void main(String[] args)  {
            Graf g = Graf.fromDotFile("dots/simpleGraph");
            System.out.println(g.toDotString());
            System.out.println( AugmentingPath.getAugmentingPath(g.getNode(1), g.getNode(5), g,  AP_ALGORITHM.DFS));
    }


    static Pair<List<Node>,Integer> getAugmentingPath(Node start, Node end, Graf g, AP_ALGORITHM algorithm )
    {
        switch (algorithm)
        {
            case DFS:
                return getAugmentingPathDFS(start, end, g);
            default:
                return null;
        }
    }

    static Pair<List<Node>,Integer> getAugmentingPath(int idStart, int idEnd, Graf g , AP_ALGORITHM algorithm)
    {
        Node start = new Node(idStart);
        Node end = new Node(idEnd);
        return getAugmentingPath(start, end, g, algorithm);
    }



    private static Pair<List<Node>,Integer> getPathToRec(Node start, Node end, Graf g, List<Node> currentPath, Set<Node> visited, int currentFlow)
    {
        if(start.equals(end))
        {
            currentPath.add(end);
            return new Pair<List<Node>,Integer>(currentPath,currentFlow);
        }
        else
        {
            //currentPath.add(start);
            visited.add(start);
            for(Node n : g.getSuccessors(start))
            {
                if(!visited.contains(n))
                {
                    ArrayList<Node> extendedPath =  new ArrayList<>(currentPath); // copy cause reference are fucking weird
                    extendedPath.add(n);
                    return getPathToRec(n, end, g , extendedPath,visited, currentFlow);
                }

            }
            return null;
        }

    }

    private static Pair<List<Node>,Integer> getAugmentingPathDFS(Node start, Node end , Graf g)
    {
        List<Node> startReachable =  g.getReachable(start);
        if(! startReachable.contains(end))
        {
            return null;
        }
        else
        {
            HashSet<Node> visited = new HashSet<>();
            return getPathToRec(start, end, g, new ArrayList<>(),visited, 0);
        }


    }


}
