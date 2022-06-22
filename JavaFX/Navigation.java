import java.io.*;
import java.util.*;
import javafx.scene.image.Image;

public class Navigation {
    // Number of vertices in graph
    private int size;
    private Image mrt;

    // Vertices list
    private ArrayList<String> vertex = new ArrayList<>();

    // Adjacency list to keep adjacent vertices
    private ArrayList<ArrayList<String>> adjList;

    // Constructor
    public Navigation() {
        adjList = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public ArrayList<String> getVertex() {
        return vertex;
    }

    public ArrayList<ArrayList<String>> getAdjList() {
        return adjList;
    }

    // Add edge (connection between vertex1 and vertex2 which is bidirectional)
    public void addEdge(String vertex1, String vertex2) {
        // Add vertex1 and vertex2 to vertices list 
        if (!vertex.contains(vertex1)) {
            adjList.add(new ArrayList<String>());
            vertex.add(vertex1);
            size++;
	}
	    
        if (!vertex.contains(vertex2)) {
            adjList.add(new ArrayList<String>());
            vertex.add(vertex2);
            size++;
	}

        // Add edge
        adjList.get(vertex.indexOf(vertex1)).add(vertex2);
        adjList.get(vertex.indexOf(vertex2)).add(vertex1);
    }        

    public void printShortestPath(String source, String destination) {
   	// Initialize predecessor array with vertices count
        String[] pred = new String[this.size];
	    
	// Call Breadth First Search
	BFS(source, destination, pred);

        // LinkedList to store the path
        LinkedList<String> path = new LinkedList<String>();
	    
        // Starts from destination and add their previous vertex to the path
        String crawl = destination;
        path.add(crawl);
        while (pred[vertex.indexOf(crawl)] != null) {
            path.add(pred[vertex.indexOf(crawl)]);
            crawl = pred[vertex.indexOf(crawl)];
        }

        // Print the shortest path
        for (int i = path.size() - 1; i >= 0; i--) {
	    if (i > 0)
            	System.out.print(path.get(i) + " -> ");
	    else 
       	 	System.out.print(path.get(0) + "\n");
	}
    }
    
    // Return an ArrayList<String> with the shortest path info 
    // Modified from printShortestPath()
    public ArrayList<String> shortestPath(String source, String destination) {
        // Initialize predecessor array with vertices count
        String[] pred = new String[this.size];
	    
	// Call Breadth First Search
	BFS(source, destination, pred);

        // LinkedList to store the path
        ArrayList<String> path = new ArrayList<String>();
	    
        // Starts from destination and add their previous vertex to the path
        String crawl = destination;
        path.add(crawl);
        while (pred[vertex.indexOf(crawl)] != null) {
            path.add(pred[vertex.indexOf(crawl)]);
            crawl = pred[vertex.indexOf(crawl)];
        }
        
        // Print the shortest path
        for (int i = path.size() - 1; i >= 0; i--) {
	    if (i > 0)
            	System.out.print(path.get(i) + " -> ");
	    else 
       	 	System.out.print(path.get(0) + "\n");
	}
        
        return path;
    }
 
    // BFS that stores predecessor of each vertex in array pred[]
    public void BFS(String source, String destination, String[] pred) {
        // Queue to maintain a queue of vertices whose adjacency list is to be scanned 
        LinkedList<String> queue = new LinkedList<String>();
 
        // Boolean array which stores the boolean whether ith vertex is visited
        boolean visited[] = new boolean[size];
 
        // Initialize all vertex as unvisited
        for (int i = 0; i < size; i++) 
            visited[i] = false;
 
        // Visit source firstly
        visited[vertex.indexOf(source)] = true;
        queue.add(source);
 
        // Breadth First Search Algorithm
        while (!queue.isEmpty()) {
            String current = queue.removeFirst();
		
            // Loop to enqueue each vertex connected to current vertex (which hasnt been visited before) 
            for (String v : adjList.get(vertex.indexOf(current))) {  
		// If the ith string is not reached yet, change it to reached and add current vertex as its predecessor
                // Add ith string to queue
                if (!visited[vertex.indexOf(v)]) {
                    visited[vertex.indexOf(v)] = true;
                    pred[vertex.indexOf(v)] = current;
                    queue.add(v);
			
		    // Stop the search if ith string equals to destination
                    if (v.equalsIgnoreCase(destination))
                        return;
                }
            }
        }
    }

    // Create a graph for all the stations connected
    public void createGraph(String filename) {
        try{
            Scanner read = new Scanner(new FileInputStream("resources/" + filename));
            // Get connections from cases provided
            int numOfConnections = Integer.parseInt(read.nextLine());
            
            for (int i = 0; i < numOfConnections; i++) {
                String[] connection = read.nextLine().split(" => ");
                String source = connection[0];
                String destination = connection[1];
               
                addEdge(source, destination);
            }
            
            read.close();
        } catch (FileNotFoundException e){
            System.out.println("File was not found.");
        }
    }
}
