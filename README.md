# Projet Graph PW2:
Alexandre JEAN / Nathan Gallone


## How to launch 


To launch the program, run the main file contained into the src directory
you will need to compile the `m1graph2022` first but as the project
is an IntelliJ project, you can just open the root folder
`graph` in IntelliJ, and it will automatically compile the required files 
when you will run the Main file.

Running the main will create a directory called `output` with sub-directories
corresponding to each augmenting path algorithm output.

If you don't have access to a visualization tool inside your IDE, 
you can use the command: `dot -Tpng *.gv -O` in each sub-directories.

## Architecture 

The Maximal flow solver is divided into multiple files:
````
AP_ALGORITHM:

This is an enum containing the name of every implemented algorithms.
This enum is used when you call a MaximalFlow solving method
to select what algorithm you want to use to get the augmenting path.

AugmentingPath:  
Contain the actual implementation of each algorithms named in
AP_ALGORITHM.


MaximalFlow:  
This is where the magic happen, or more precisely this
is the class to use to solve the Maximal flow problem for a graph.
Use the MaximalFlow.getMaxFlowAndFiles(graph, algorithm, outputDir) method where
graph is a Graf object, algorithm one of the option available in the AP_ALGORITHM method
and outputDir a String representing the directory you want the outputted files to go in.
get the same files than in the main if you wanna use it yourself.
See Main for more details.

Pair:  
A fairly simple implementation of a Pair to support Java8
as it doesn't include a Pair class.

Main:  
the main class, contains the main method and a function to simply
create directories.

````

## Output

The solver output files that follow the conventions used in the PW2
subject. It also output an additional file for each category (flow and residual graph)
to show the end flow graph without flow and the last residual graph.


## Known Issues

Due to a lack of time, the Dijkstra algorithm was not properly tested
and may output invalid results on some graph.
It is mainly proposed as an elegant idea to solve the problem but 
the actual implementation may lack some edges cases and output incorrect
results sometime.
(The output for the graph used to test it in main is the actual answer)  

Idea for the implementation can be found in this handout:
http://theory.stanford.edu/~trevisan/cs261/lecture10.pdf  
Note: this is just a pseudo-code from a Stanford class handout, that contain
typography issues and is not correct but the actual implementation of
Dijkstra was mainly based on it.


