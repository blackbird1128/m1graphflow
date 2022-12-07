
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import m1graph2022.*;


public class Main {


    static Graf g0;
    static Graf g1;
    static Graf g2;
    static Graf gSubject;

    static void init(){
        Node s = new Node("s");
        Node t = new Node("t");


        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        g0 = new Graf();
        g0.addNode(s);
        g0.addNode(n1);
        g0.addNode(t);

        g0.addEdge(s,n1,2);
        g0.addEdge(n1,t,2);

        g1 = new Graf();
        g1.addNode(s);
        g1.addNode(n1);
        g1.addNode(n2);
        g1.addNode(t);

        g1.addEdge(s,n1,2);
        g1.addEdge(n1,n2,3);
        g1.addEdge(n2,t,2);

        g2 = new Graf();
        g2.addNode(s);
        g2.addNode(n1);
        g2.addNode(n2);
        g2.addNode(n3);
        g2.addNode(t);

        g2.addEdge(s,n1,2);
        g2.addEdge(s,n3,5);
        g2.addEdge(n3,t,6);
        g2.addEdge(n1,n2,3);
        g2.addEdge(n2,t,2);

        gSubject =Graf.fromDotFile("dots/demo");
    }
    
    public static void main(String[] args)  {
        init();
        Graf maxFlow;
        Graf g = gSubject;
        System.out.println(g);
        maxFlow = MaximalFlow.getMaxFlowAndFiles(g,AP_ALGORITHM.DIJKSTRA, "output");
        System.out.println(maxFlow);
    }

}
