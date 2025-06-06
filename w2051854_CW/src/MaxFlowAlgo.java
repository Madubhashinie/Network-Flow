import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxFlowAlgo {
    private GraphStructure graph;
    private boolean[] visited; // For DFS
    private  int source;
    private  int sink;

    public MaxFlowAlgo(GraphStructure graph) {
        this.graph = graph;
        this.source = 0; // Node 0 is the source
        this.sink = graph.getNumNodes() - 1 ; // Node n-1 is the sink
        this.visited = new boolean[graph.getNumNodes()];
    }


    /**
     * Finds an augmenting path from the current node to the sink using DFS.
     * @param nodeU The current node being explored.
     * @param path  The current path of edges from source to the current node.
     * @return A list of edges forming an augmenting path to the sink, or null if no path exists.
     */
    private List<GraphStructure.Edge> findAugmentingPath(int nodeU, List<GraphStructure.Edge> path) {
        if( nodeU == sink){
            return path;
        }
        visited[nodeU] = true;
        for(GraphStructure.Edge edge: graph.getEdges(nodeU)){
            int nodeV = edge.to;
            int residual = edge.capacity - edge.flow;
            if(!visited[nodeV] && residual > 0){
                List<GraphStructure.Edge> newPath = new ArrayList<>(path);
                newPath.add(edge);
                List<GraphStructure.Edge> result = findAugmentingPath(nodeV, newPath);
                if(result != null){
                    return result;
                }
            }

        }
        return null;
    }

    /**
     * Computes the maximum flow from source to sink using the Ford-Fulkerson algorithm.
     * Prints detailed output for each iteration, including paths, bottleneck capacities, and updated flows.
     * @param filename The name of the input file.
     * @return The maximum flow value achieved.
     */
    public int computeMaxFlow(String filename) {
        int maxFlow = 0;
        int iteration = 0;

        // Record start time
        long startTime = System.nanoTime();

        System.out.println("\nProcessing file: " + filename);
        System.out.println("Number of nodes: " + graph.getNumNodes());

        while (true) {
            Arrays.fill(visited, false);
            List<GraphStructure.Edge> path = findAugmentingPath(source, new ArrayList<>());
            if (path == null) {
                break;
            }

            iteration++;
            System.out.println("\nIteration " + iteration + ": Augmenting path found");

            int bottleneck = Integer.MAX_VALUE;
            System.out.print("Path: " + source);
            for (GraphStructure.Edge edge : path) {
                int residual = edge.capacity - edge.flow;
                bottleneck = Math.min(bottleneck, residual);
                System.out.print(" -> " + edge.to);
            }
            System.out.println();
            System.out.println("Bottleneck capacity: " + bottleneck);

            for (GraphStructure.Edge edge : path) {
                edge.flow += bottleneck;
                GraphStructure.Edge backward = graph.getEdge(edge.to, path.get(path.indexOf(edge)).to == edge.to ? path.get(path.indexOf(edge)).to : source);
                if (backward != null) {
                    backward.flow -= bottleneck;
                }
            }

            maxFlow += bottleneck;
            System.out.println("Current flow value: " + maxFlow);
            System.out.println("Updated flows:");
            for (int u = 0; u < graph.getNumNodes(); u++) {
                for (GraphStructure.Edge edge : graph.getEdges(u)) {
                    if (edge.flow > 0) {
                        System.out.println("Edge " + u + " -> " + edge.to + ": flow = " + edge.flow);
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        double elapsedTimeMs = (endTime - startTime) / 1000000.0;

        System.out.println("\nMaximum flow for " + filename + ": " + maxFlow);
        System.out.printf("Execution time for %s: %.2f ms%n", filename, elapsedTimeMs);

        return maxFlow;
    }

}
