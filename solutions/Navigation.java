package test;

import java.util.*;


public class Navigation {

    // Number of vertices in graph
    private int v;

    // Vertices list
    private ArrayList<String> vertex = new ArrayList<>(v);

    // Adjacency list
    private ArrayList<String>[] adjList;

    // Constructor
    public Navigation(int vertices)
    {
        // Initialise number of vertices
        this.v = vertices;

        // Initialise adjacency list
        initAdjList();
    }

    // Initialise adjacency list
    @SuppressWarnings("unchecked")
    private void initAdjList()
    {
        // Initialise adjacency list with vertices count
        adjList = new ArrayList[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    // Add edge (connection between u and v which is bidirectional)
    public void addEdge(String u, String v)
    {
        // Add u and v to vertices list 
        if(!vertex.contains(u))
            vertex.add(u);
        if(!vertex.contains(v))
            vertex.add(v);

        // Add edge
        adjList[vertex.indexOf(u)].add(v);
        adjList[vertex.indexOf(v)].add(u);
    }        

    private void printShortestDistance(String source, String dest, int v)
    {
    // Initialize predecessor array with vertices count
        String pred[] = new String[v];

        // LinkedList to store path
        LinkedList<String> path = new LinkedList<String>();
        // Starts from destination and add to the path
        String crawl = dest;
        path.add(crawl);
        while (!pred[vertex.indexOf(crawl)].equalsIgnoreCase("")) 
        {
            path.add(pred[vertex.indexOf(crawl)]);
            crawl = pred[vertex.indexOf(crawl)];
        }

        // Print path
        for (int i = path.size() - 1; i >= 0; i--) 
        {
            if(i == path.size()-1)
                System.out.print(path.get(i) + " -> ");
            else if (i == 0)
                System.out.print(path.get(i));
            else
                System.out.print(path.get(i) + " -> " + path.get(i) + " -> ");
        }
    }
 
    // BFS that stores predecessor of each vertex in array pred
    private void BFS(String src, String dest, int v, String pred[])
    {
        // Queue to maintain queue of vertices whose adjacency list is to be scanned as per normal
        LinkedList<String> queue = new LinkedList<String>();
 
        // Boolean array which stores the boolean whether ith vertex is reached
        boolean visited[] = new boolean[v];
 
        // Initialize all vertex as unreached
        for (int i = 0; i < v; i++) 
        {
            visited[i] = false;
            pred[i] = "";
        }
 
        // Visit source firstly
        visited[vertex.indexOf(src)] = true;
        queue.add(src);
 
        // Breadth First Seaech Algorithm
        while (!queue.isEmpty()) 
        {
            String u = queue.remove();
            // Try strings in the adjacent list of u
            for (String i : adjList[vertex.indexOf(u)]) 
            {  
		// If the ith string is not reached yet, change it to reached and add u as its pred
                // Add ith string to queue
                // Return the search if ith string equals to destination
                if (!visited[vertex.indexOf(i)]) 
                {
                    visited[vertex.indexOf(i)] = true;
                    pred[vertex.indexOf(i)] = u;
                    queue.add(i);

                    if (i.equalsIgnoreCase(dest))
                        return;
                }
            }
        }
    }

    // Tester
    public static void main(String[] args)
    {            
        Scanner sc = new Scanner(System.in);
        // Get number of test cases
        int n = sc.nextInt();

        for(int i=0; i<n; i++){
            // Get number of connections from cases provided
            int numOfConnections = sc.nextInt();
            Navigation g = new Navigation(numOfConnections);

            // Add edge for every conncetions
            for (int j = 0; j < numOfConnections; j++) 
            {
                String[] connection = sc.nextLine().split(" => ");
                String source = connection[0];
                String destination = connection[1];

                g.addEdge(source, destination);
            }

            // Get rid of the QUERIES line
            sc.nextLine();

            // Get queries from the cases provided
            int numOfQueries = Integer.parseInt(sc.nextLine());

            // Find the shortest path for every queries
            for (int k = 0; k < numOfQueries; k++) 
            {
                String[] path = sc.nextLine().split(" -> ");
                String from = path[0];
                String to = path[1];

                g.printShortestDistance(from, to, numOfConnections);
            }
        }            
    }
}