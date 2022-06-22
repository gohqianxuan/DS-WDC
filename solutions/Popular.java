import java.util.*;
import java.io.*;

public class Popular {
    
    // Tester 
    public static void main(String[] args) {            
        Scanner sc = new Scanner(System.in);
        
	// Get number of test case
	int n = Integer.parseInt(sc.nextLine());

	for (int z = 0; z < n; z++) {
	    ArrayList<String> stationName = new ArrayList<>();
            ArrayList<Station> stationList = new ArrayList<>();
            int numOfTrip = Integer.parseInt(sc.nextLine());
            
            for (int i = 0; i < numOfTrip; i++) {
                String[] trip = sc.nextLine().split(" ");
                int indexSplit= 0, tickets = 0;
                String station1="", station2="";
                for(int j=0; j<=trip.length-1; j++){
                    if(trip[j].equalsIgnoreCase("->"))
                        indexSplit = j;
                    if(j == trip.length-1)
                        tickets = Integer.parseInt(trip[j]);
                }
                for(int m=0; m<=trip.length-2; m++){
                    if(m<indexSplit)
                        station1 += trip[m] + " ";
                    if(m>indexSplit)
                        station2 += trip[m] + " ";
                }
  
                if(!stationName.contains(station1)){
                    stationName.add(station1);
                    Station st1 = new Station(station1, tickets);
                    stationList.add(st1);
                }
                else{
                    int index1 = stationName.indexOf(station1);
                    int visited1 = stationList.get(index1).getVisited();
                    stationList.get(index1).setVisited(visited1 + tickets);
                }
                if(!stationName.contains(station2)){
                    stationName.add(station2);
                    Station st2 = new Station(station2, tickets);
                    stationList.add(st2);
                }
                else{
                    int index2 = stationName.indexOf(station2);
                    int visited2 = stationList.get(index2).getVisited();
                    stationList.get(index2).setVisited(visited2 + tickets);
                }
            }
        
            System.out.println(stationName.size());
            selectionSort(stationList);
            for(Station popular: stationList){
                System.out.println(popular.toString());
            }
        }
    }
    
    static class Station{
        String stationName;
        int visited;

        public int getVisited() {
            return visited;
        }

        public void setVisited(int visited) {
            this.visited = visited;
        }
        
        public Station(String name, int num){
            this.stationName = name;
            this.visited = num;
        }

        @Override
        public String toString() {
            return stationName + " , " + visited;
        }
        
    }
  
    
    public static void selectionSort(ArrayList<Station> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            // Find the minimum in the list[i..list.length-1]
            int currentMax = list.get(i).getVisited();
            int currentMaxIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (currentMax < list.get(j).getVisited()) {
                    currentMax = list.get(j).getVisited();
                    currentMaxIndex = j;
                }
            }
            // Swap list[i] with list[currentMinIndex] if necessary;
            if (currentMaxIndex != i) {
                Collections.swap(list, currentMaxIndex, i);
            }
        }
    }
}
