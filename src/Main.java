
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import m1graph2022.*;


public class Main {
    
    public static void main(String[] args)  {
        Graf g0 = Graf.fromDotFile("dots/demo1");;
        Graf g1 = Graf.fromDotFile("dots/demo2") ;
        Graf g2 = Graf.fromDotFile("dots/demo3");

        Graf g = g0;
        MaximalFlow.getMaxFlowAndFiles(g,AP_ALGORITHM.DFS, "output");
        MaximalFlow.getMaxFlowAndFiles(g,AP_ALGORITHM.GREEDY, "output");
        MaximalFlow.getMaxFlowAndFiles(g,AP_ALGORITHM.LOOKAHEAD1, "output");
        MaximalFlow.getMaxFlowAndFiles(g,AP_ALGORITHM.LOOKAHEAD2, "output");
    }

}
