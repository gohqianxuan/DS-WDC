import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
 
class Navigation {
    int size;
    ArrayList<String> vertex = new ArrayList<>(size);
    ArrayList<ArrayList<String>> adjList; // list of adjacent vertex
 
    public Navigation() {
        adjList = new ArrayList<>();
    }
    
    // Driver Program
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
            
        // get connections from cases provided
        int numOfConnections = Integer.parseInt(input.nextLine());
        Navigation map = new Navigation();

        for (int i = 0; i < numOfConnections; i++) {
            String[] connection = input.nextLine().split(" => ");
            String source = connection[0];
            String destination = connection[1];

            map.addEdge(source, destination);
        }

        // get rid of the QUERIES line
        input.nextLine();

        // get queries from the cases provided
        int numOfQueries = Integer.parseInt(input.nextLine());

        for (int i = 0; i < numOfQueries; i++) {
            String[] path = input.nextLine().split(" -> ");
            String from = path[0];
            String to = path[1];

            map.printShortestDistance(from, to);
        }

        input.close();
    }
 
    // method to form edge between two vertices
    private void addEdge(String vertex1, String vertex2){
        if(!vertex.contains(vertex1)) {
            adjList.add(new ArrayList<String>());
            vertex.add(vertex1);
            size++;
        }
        
        if(!vertex.contains(vertex2)) {
            adjList.add(new ArrayList<String>());
            vertex.add(vertex2);
            size++;
        }
        
        adjList.get(vertex.indexOf(vertex1)).add(vertex2);
        adjList.get(vertex.indexOf(vertex2)).add(vertex1);
    }
 
    // method to print the shortest path between source vertex and destination vertex
    private void printShortestDistance(String source, String destination) {
        // predecessor[] array stores predecessor of i 
        String[] pred = new String[size];
 
        if (!BFS(source, destination, pred)) {
            System.out.println("There isn't a path between " + source + " " + destination + ".");
            return;
        }
 
        // LinkedList to store the shortest path
        LinkedList<String> path = new LinkedList<String>();
        String crawl = destination;
        path.add(crawl);
        while (pred[vertex.indexOf(crawl)] != null) {
            path.add(pred[vertex.indexOf(crawl)]);
            crawl = pred[vertex.indexOf(crawl)];
        }
 
        // print the shortest path
        for (int i = path.size() - 1; i >= 1; i--) {
            System.out.print(path.get(i) + " -> ");
        }
        System.out.println(path.get(0));
    }
 
    // a modified version of BFS that stores predecessor of each vertex in predecessor[] array
    private boolean BFS(String source, String destination, String[] pred) {
        LinkedList<String> queue = new LinkedList<>();
 
        // boolean array visited[] stores the information whether the vertex is visited in the Breadth first search
        boolean visited[] = new boolean[size];
 
        // initially all vertices are unvisited so v[i] for all i is false
        for (int i = 0; i < size; i++) 
            visited[i] = false;
 
        // source is the first to be visited
        visited[vertex.indexOf(source)] = true;
        queue.add(source);
 
        // BFS algorithm
        while (!queue.isEmpty()) {
            // dequeue the vertex from the queue
            String current = queue.removeFirst();
            int currentIndex = vertex.indexOf(current);
            
            // loop to enqueue each vertex connected to current vertex (which hasnt been visited before) 
            for (String v: adjList.get(currentIndex)) {
                if (!visited[vertex.indexOf(v)]) {
                    // set the vertex as visited 
                    visited[vertex.indexOf(v)] = true;
                    // set the predecessor of the vertex as current vertex
                    pred[vertex.indexOf(v)] = current;
                    // enqueue the vertex
                    queue.add(v);
 
                    // stopping condition (when we find our destination)
                    if (v.equals(destination))
                        return true;
                }
            }
        }
        return false;
    }
}
