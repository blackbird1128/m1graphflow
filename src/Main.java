
import java.io.File;

import m1graph2022.*;


public class Main {


    static public void createFolder(String directoryName)
    {

        File directory = new File(directoryName);
        if (!directory.exists()) {
            boolean result = directory.mkdir();
            if(result) {
                System.out.println("Directory " + directoryName + " created");
            } else {
                System.out.println("Directory " + directoryName +  " creation failed");
            }
        } else {
            System.out.println("Directory " + directoryName + " already exists");
        }
    }
    
    public static void main(String[] args)  {
        Graf g0 = Graf.fromDotFile("dots/demo1");;
        Graf g1 = Graf.fromDotFile("dots/demo2") ;
        Graf g2 = Graf.fromDotFile("dots/demo3");


        String outputDirectory = "output";
        createFolder(outputDirectory);

        String dijkstraDir = outputDirectory + "/dijkstra";
        createFolder(dijkstraDir);
        MaximalFlow.getMaxFlowAndFiles(g0,AP_ALGORITHM.DIJKSTRA, dijkstraDir);


        String greedy = outputDirectory + "/greedy";
        createFolder(greedy);
        MaximalFlow.getMaxFlowAndFiles(g2, AP_ALGORITHM.GREEDY, greedy);

        String lookahead1 = outputDirectory + "/lookahead1";
        createFolder(lookahead1);
        MaximalFlow.getMaxFlowAndFiles(g1,AP_ALGORITHM.LOOKAHEAD1, lookahead1);

        String lookahead2 = outputDirectory + "/lookahead2";
        createFolder(lookahead2);
        MaximalFlow.getMaxFlowAndFiles(g2,AP_ALGORITHM.LOOKAHEAD2, lookahead2);

        String dfs = outputDirectory + "/dfs";
        createFolder(dfs);
        MaximalFlow.getMaxFlowAndFiles(g2, AP_ALGORITHM.DFS, dfs);





    }

}
