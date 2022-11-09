
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import m1graph2022.*;


public class Main {

    static private void println(Object...o) {
        for (int i = 0; i < o.length; i++) {
            System.out.println(o[i]);
        }
    }

    private static void print(Object... o) {
        for (int i = 0; i < o.length; i++) {
            System.out.print(o[i]);
        }
    }

    public static void main(String[] args)  {
        Graf g = new Graf();
        g.addNode(0,"s");
        g.addNode(1,"t");
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);

        g.addEdge(new Edge("s","t"));
        g.addEdge(new Edge("s",2));
        g.addEdge(new Edge(2,"t"));
        g.addEdge(2,3);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(5,"t");

        print(g.toDotString());
    }

}
