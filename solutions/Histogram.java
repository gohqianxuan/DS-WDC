import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Histogram {
    
    public static void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    
    //input number of test case
    int numCase = scanner.nextInt();
                
    for (int i = 0; i < numCase; i++) {
       
        //input number of data and bin
        int histogramSize = scanner.nextInt();
        int bin = scanner.nextInt();
        int temp;
        
        List<Integer> histArr = new ArrayList<>();
        List<Integer> cutoffs = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        
        //input data point
        for (int j = 0; j < histogramSize; j++) {
            temp = scanner.nextInt();
            histArr.add(temp);
        }
		
        //find required data
        int min = Collections.min(histArr);
        int max = Collections.max(histArr);
        int increment = (max - min) / bin;
        
        //calculate cutoffs value
        temp = min;
        while (temp <= max) {
            cutoffs.add(temp);
            temp += increment;
        }
        
        //sort data points 
        Collections.sort(histArr);
        temp = 1;
        int k = cutoffs.get(temp);
        int counter = 0;
                        
        //calculate counts for each bin
        for (int j = 0; j < histogramSize; j++) {
            if (histArr.get(j) < k) 
                counter++;
            else {
                temp++;
                if (temp == cutoffs.size()) {
                    counts.add(++counter);
                    break;
                }
                counts.add(counter);
                counter = 1;
                k = cutoffs.get(temp);
                }
            }
        
        //print cutoffs of bin
        for (Integer each : cutoffs) 
            System.out.print(each + " ");
        System.out.println();
        
        //print number of data for each bin
        for (Integer each : counts) 
            System.out.print(each + " ");
        System.out.println();
        }
    }
}
