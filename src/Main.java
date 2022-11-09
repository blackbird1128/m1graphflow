
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import m1graph2022.*;

public class Main {

    public static void main(String[] args)  {
        // write your code here

        System.out.println("************ PART 1. UNWEIGTED DIRECTED GRAPHS ***********************");
        System.out.println("\n>>>>>>>> SIMPLE GRAPH >>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>> Creating the subject example graph in G");
        Graf g = new Graf(2, 4, 0, 0, 6, 0, 2, 3, 5, 8, 0, 0, 4, 7, 0, 3, 0, 7, 0);
        System.out.println(">>>> Graph information");
        System.out.println(">> DOT representation\n"+g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        System.out.println(">> Nodes: ");
        List<Node> nodes = g.getAllNodes();
        Collections.sort(nodes);
        for (Node n: nodes)
            System.out.println("Node "+n+": degree "+g.degree(n)+" (in: "+g.inDegree(n)+"/ out: "+g.outDegree(n)+")");

        List<Edge> edges;
        System.out.println(">> Edges: ");
        System.out.println("---------------------------");
        System.out.println("Out-edges per node");
        for (Node n: nodes) {
            edges = g.getOutEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("In-edges per node");
        for (Node n: nodes) {
            edges = g.getInEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        /////////////////////////////////////////////////////

        System.out.println("\n>>>>>>>> creating isolated node 12");
        if (!g.addNode(12))
            System.out.println("Error: failed to create node 12");
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println("\n>>>>>>>> Removing node 3");
        g.removeNode(3);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println(">> Edges: ");
        System.out.println("---------------------------");
        System.out.println("Out-edges per node");
        for (Node n: nodes) {
            edges = g.getOutEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("In-edges per node");
        for (Node n: nodes) {
            edges = g.getInEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("\n>>>>>>>> Recreating edges (4, 3), (3, 6), (7, 3), adding edge (12, 3), creating edge (3, 25)");
        g.addEdge(new Edge(4, 3));
        g.addEdge(new Edge(3, 6));
        g.addEdge(new Edge(7, 3));
        g.addEdge(new Edge(12, 3));
        g.addEdge(3, 25);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println("");
        System.out.println("\n>>>>>>>>  Edges removal");
        System.out.println(">>>> Removing existing edges (7, 3) and (4, 8)");
        g.removeEdge(7, 3);
        g.removeEdge(4, 8);
        System.out.println(">>>> Removing absent edge (3, 4)");
        g.removeEdge(3, 4);
        System.out.println(">>>> Removing edges whith 1 or 2 not existing end-points: (-3, 4), (6, 0), (4, 11), (-1, -2), (13, 3), (9, 10)");
        g.removeEdge(-3, 4);
        g.removeEdge(6, 0);
        g.removeEdge(4, 11);
        g.removeEdge(-1, -2);
        g.removeEdge(13, 3);
        g.removeEdge(9, 10);

        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println("\n>>>>>>>> MULTIGRAPH: adding a self-loop on node 6, and a second edge (1, 4)");
        g.addEdge(6, 6);
        g.addEdge(1, 4);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);
        System.out.println("Degree of node 6: "+g.degree(6)+" (in: "+g.inDegree(6)+"/ out: "+g.outDegree(6)+")");

        System.out.println(">> Edges: ");
        System.out.println("---------------------------");
        System.out.println("Out-edges per node");
        for (Node n: nodes) {
            edges = g.getOutEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("In-edges per node");
        for (Node n: nodes) {
            edges = g.getInEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        // DELETE
        // System.out.println("DOT");
        // System.out.println(g.toDotString());
        // System.out.println("Successor array");
        // int[] tab = g.toSuccessorArray();
        // for (int i=0; i<tab.length; i++)
        //     System.out.print(tab[i]+", ");
        // System.out.println();
        // END DELETE

        System.out.println(">>>>>>>>>>    Get the reverse graph");
        System.out.println(g.getReverse().toDotString());

        System.out.println(">>>>>>>>>>    Get the transitive closure");
        System.out.println(g.getTransitiveClosure().toDotString());

        System.out.println(">>>>>>>>>>    Emptying the graph by removing all its nodes");
        nodes = g.getAllNodes();
        for (Node u: nodes)
            g.removeNode(u);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());

        System.out.println(">>>> Searching for node 7");
        if (g.existsNode(7))
            System.out.println("Node 7 exists");
        else
            System.out.println("There is no Node 7");

        System.out.println(">>>> Searching for edge (4, 2)");
        if (g.existsEdge(4, 2))
            System.out.println("Edge (4, 2) exists");
        else
            System.out.println("There is no edge (4, 2)");

        System.out.println("\n********* PART 2. READING GRAPHS FROM DOT FILES **************");
        System.out.println("\n>>> Graph with isolated nodes: reading file 'isolatedNodes.gv'");
        Graf gin = Graf.fromDotFile("dots/isolatedNodes");
        if (gin==null)
            System.out.println("Null graph was created from 'isolatedNodes.gv'");
        else {
            System.out.println("Read: OK. The graph with isolated nodes has been read as:");
            System.out.println("---------------------");
            System.out.println(gin.toDotString());
            System.out.println("---------------------");
        }

        System.out.println(">>> Simple graph: reading file 'simpleGraph.gv'");
        Graf sg = Graf.fromDotFile("dots/simpleGraph");
        if (sg==null)
            System.out.println("Null graph was created from 'simpleGraph.gv'");
        else {
            System.out.println("Read: OK. The simple graph has been read as:");
            System.out.println("---------------------");
            System.out.println(sg.toDotString());
            System.out.println("---------------------");
        }

        System.out.println("\n>>> Multi-graph: reading file 'multiGraph.gv'");
        Graf mg = Graf.fromDotFile("dots/multiGraph");
        if (mg==null)
            System.out.println("Null graph was created from 'multiGraph.gv'");
        else {
            System.out.println("Read: OK. The multi-graph has been read as:");
            System.out.println("---------------------");
            System.out.println(mg.toDotString());
            System.out.println("---------------------");
        }

        System.out.println("\n\n************ PART 3. WEIGHTED DIRECTED GRAPHS ***********************");
        int totalEdgesWeight = 0;

        System.out.println("\n>>>>>>>>>>");
        System.out.println("Reading a weighted directed simple graph from DOT file 'weightedSimpleGraph.gv'");
        Graf wsg = Graf.fromDotFile("dots/weightedSimpleGraph");
        if (wsg==null)
            System.out.println("Null graph was created from 'weightedSimpleGraph.gv'");
        else {
            System.out.println("Read: OK. The  weighted directed simple graph has been read as:");
            System.out.println("---------------------");
            System.out.println(wsg.toDotString());
            System.out.println("---------------------");

            for (Edge e: wsg.getAllEdges())
                totalEdgesWeight += e.getWeight();
            System.out.println("The sum of all edges weights equals "+totalEdgesWeight);
        }

        System.out.println("\n>>>>>>>>>>");
        System.out.println("Reading a weighted directed multi graph from DOT file 'weightedMultiGraph.gv'");
        Graf wmg = Graf.fromDotFile("dots/weightedMultiGraph");
        if (wmg==null)
            System.out.println("Null graph was created from 'weightedMultiGraph.gv'");
        else {
            System.out.println("Read: OK. The  weighted directed multi graph has been read as:");
            System.out.println("---------------------");
            System.out.println(wmg.toDotString());
            System.out.println("---------------------");

            totalEdgesWeight = 0;
            for (Edge e: wmg.getAllEdges())
                totalEdgesWeight += e.getWeight();
            System.out.println("The sum of all edges weights equals "+totalEdgesWeight);
        }





        System.out.println("\n\n************ PART 4. UNDIRECTED UNWEIGHTED GRAPHS ***********************");
//		System.out.println("\n------------------------------");
//		System.out.println("Now Testing UNDIRECTED GRAPHS");
//		System.out.println("------------------------------");

        System.out.println("Creating an undirected simple graph usg from scracth");
        UndirectedGraf usg = new UndirectedGraf(2,3,0, 3,4,0, 4,0, 0);
        System.out.println(usg.toDotString());
        System.out.println("usg has "+usg.nbNodes()+" nodes and "+usg.nbEdges()+" edges.");

        System.out.println("\n>>>>>> usg: Counting degrees and showing successors");
        for (Node u: usg.getAllNodes()) {
            System.out.println("Node "+u+". Degree: "+usg.degree(u.getId())+" (In: "+usg.inDegree(u.getId())+" / Out: "+usg.outDegree(u.getId())+")");
            System.out.println("\tSuccessors: "+usg.getSuccessors(u));
        }

        System.out.println(">>>>>> usg: Edges of the graph");
        System.out.println("// N.B. The edges are printed as though they were directed. This is due to the toString()\n"
                + "// method that was not overridden. It is possible to do better but not important.\n"
                + "// What is important is that each edge appears only once per direction.");
        System.out.println("All edges of the graph: "+usg.getAllEdges());
        System.out.println("Out-edges per node");
        for (Node u: usg.getAllNodes())
            System.out.println(""+u+": "+usg.getOutEdges(u));
        System.out.println("In-edges per node");
        for (Node u: usg.getAllNodes())
            System.out.println(""+u+": "+usg.getInEdges(u));
        System.out.println("Incident edges per node");
        for (Node u: usg.getAllNodes())
            System.out.println(""+u+": "+usg.getIncidentEdges(u));

        System.out.println("Creating an undirected multi-graph with self-loops umg from scratch");
        UndirectedGraf umg = new UndirectedGraf(1,1,2,2,3,0, 2,3,0, 0);

        String dotUMG = umg.toDotString();
        System.out.println(dotUMG);
        System.out.println("umg has "+umg.nbNodes()+" nodes and "+umg.nbEdges()+" edges.");


        System.out.println("\n>>>>>> umg: Counting degrees and showing successors");
        for (Node u: umg.getAllNodes()) {
            System.out.println("Node "+u+". Degree: "+umg.degree(u.getId())+" (In: "+umg.inDegree(u.getId())+" / Out: "+umg.outDegree(u.getId())+")");
            System.out.println("\tSuccessors: "+umg.getSuccessors(u));
        }

        System.out.println(">>>>>> umg: Edges of the graph");
        System.out.println("All edges of the graph: "+umg.getAllEdges());
        System.out.println("Out-edges per node");
        for (Node u: umg.getAllNodes())
            System.out.println(""+u+": "+umg.getOutEdges(u));
        System.out.println("In-edges per node");
        for (Node u: umg.getAllNodes())
            System.out.println(""+u+": "+umg.getInEdges(u));
        System.out.println("Incident edges per node");
        for (Node u: umg.getAllNodes())
            System.out.println(""+u+": "+umg.getIncidentEdges(u));

        System.out.println("\n>>>>>> umg: Successor Array, Adjacency Matrix, and Graph Reverse");
        System.out.println("umg Successor array\n"+Arrays.toString(umg.toSuccessorArray()));

        System.out.println("umg Adjacency Matrix");
        for (int[] row: umg.toAdjMatrix())
            System.out.println("\t"+Arrays.toString(row));

        System.out.println("Testing via toDotString() the equality with the reverse graph");
        String dotRUMG = umg.getReverse().toDotString();
        System.out.println("DOT of the reverse of umg\n"+dotRUMG);
        System.out.println("Graph gu and its reverse "+(dotUMG.equals(dotRUMG)?"are identical":"differ"));

        System.out.println("-----------------\n      NOW a disconnected GRAPH    \n----------------");
        System.out.println("Building guDisc, a disconnected undirected graph with multi-edges and self-loops");
        UndirectedGraf guDisc = new UndirectedGraf(1,1,2,2,6,0, 2,3,6,0, 0, 6,0, 6,0, 0, 0, 9,10,0, 0, 0);
        System.out.println(guDisc.toDotString());

        System.out.println(">>>> DFS of guDisc: "+guDisc.getDFS());
        System.out.println(">>>> BFS of guDisc: "+guDisc.getBFS());

        System.out.println(">>>>>>> Computing guDisc's transitive closure");
        UndirectedGraf guDiscTC = guDisc.getTransitiveClosure();
        System.out.println(guDiscTC.toDotString());


        System.out.println("\n\n************ PART 4. UNDIRECTED WEIGHTED GRAPHS ***********************");

        System.out.println(">>>>>> Reading uwmg an undirected weighted multi-graph with self loops\n"
                + "from file 'undirWeightedMultiGraph.gv'");

        UndirectedGraf uwmg = UndirectedGraf.fromDotFile("dots/undirWeightedMultiGraph");
        if (uwmg==null)
            System.out.println("Null graph was created from 'undirWeightedMultiGraph.gv'");
        else {
            System.out.println("Read: OK. The undirected weighted multi-graph has been read as:");
            System.out.println("---------------------");
            System.out.println(uwmg.toDotString());
            System.out.println("---------------------");

            totalEdgesWeight = 0;
            for (Edge e: uwmg.getAllEdges())
                totalEdgesWeight += e.getWeight();
            System.out.println("The sum of all edges weights equals "+totalEdgesWeight);
        }

    }







}
