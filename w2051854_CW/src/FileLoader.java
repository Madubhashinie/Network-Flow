
import java.io.*;

public class FileLoader {
    public static GraphStructure file(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Read number of nodes
        String line = reader.readLine();
        if (line == null){
            throw new IOException("Empty file" + filename);
        }
        int numNodes;
        try {
            numNodes = Integer.parseInt(line.trim());
        }catch (NumberFormatException e){
            throw new IOException("IInvalid number of nodes " + filename);
        }

        if (numNodes <= 0){
            throw new IOException("Invalid number of nodes" + filename);
        }


        //create flow network
        GraphStructure graph = new GraphStructure(numNodes);

        //Read edges
        int lineNumber = 1;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3){
                throw new IOException("Invalid edge format " + filename);
            }

            int nodeU, nodeV, capacity;
            try{
                nodeU = Integer.parseInt(parts[0]);
                nodeV = Integer.parseInt(parts[1]);
                capacity= Integer.parseInt(parts[2]);
            }catch (NumberFormatException e){
                throw new IOException("Invalid integer" + filename);
            }


            if(nodeU < 0 || nodeV < 0  ||  nodeU >= numNodes ||  nodeV >= numNodes){
                throw new IOException("Invalid node number" + filename);
            }
            if(capacity < 0 ){
                throw new IOException("Invalid capacity" + filename);
            }
            graph.addEdge(nodeU, nodeV, capacity);
        }
        reader.close();
        return graph;
    }
}
