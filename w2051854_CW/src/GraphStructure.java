import java.util.*;

public class GraphStructure {

    // Inner class to represent an edge with destination, capacity, and flow
    static  class Edge {
        int to;
        int capacity;
        int flow;
        public Edge(int to, int capacity) {
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        @Override
        public String toString() {
            return "Edge [to=" + to + ", capacity=" + capacity + ", flow=" + flow + "]";
        }
    }

    private List<List<Edge>> adjList; // Adjacency list for graph representation
    private int numNodes;

    /**
     * Constructs a graph with a specified number of nodes.
     * @param numNodes The number of nodes in the graph.
     */
    public GraphStructure(int numNodes) {
        this.numNodes = numNodes;
        this.adjList = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    public int getNumNodes() {
        return numNodes;
    }

    /**
     * INSERT: Add an edge
     * Adds a directed edge from one node to another with a specified capacity.
     * Also adds a reverse edge with 0 capacity to support the residual graph.
     * @param from     The source node of the edge.
     * @param to       The destination node of the edge.
     * @param capacity The capacity of the edge.
     */
    public void addEdge(int from, int to, int capacity) {
        adjList.get(from).add(new Edge(to, capacity));
        adjList.get(to).add(new Edge(from, 0)); // Backward edge for residual graph
    }

    /**
     * DELETE: Remove an edge
     * Removes a directed edge between two nodes, including the corresponding reverse edge.
     * @param from The source node of the edge to remove.
     * @param to   The destination node of the edge to remove.
     */
    public void deleteEdge(int from, int to) {
        adjList.get(from).removeIf(edge -> edge.to == to);
        adjList.get(to).removeIf(edge -> edge.to == from);
    }

    /**
     * SEARCH: Find an edge
     * Searches for a directed edge from one node to another.
     *
     * @param from The source node of the edge.
     * @param to   The destination node of the edge.
     * @return if found returns the edge,or if no such edge exists, returns null.
     */
    public Edge getEdge(int from, int to) {
        for (Edge edge : adjList.get(from)) {
            if (edge.to == to) {
                return edge;
            }
        }
        return null;
    }

    public List<Edge> getEdges(int u) {
        return adjList.get(u);
    }

}


