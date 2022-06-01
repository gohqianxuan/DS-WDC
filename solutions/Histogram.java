import java.util.Arrays;
import java.util.Scanner;

public class Histogram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCase = scanner.nextInt();

        for (int i = 0; i < numCase; i++) {
            int histogramSize = scanner.nextInt();
            int bin = scanner.nextInt();
            int temp;
                
            int[] histArr = new int[histogramSize];
            int[] cutoffs = new int[bin+1];
            int[] counts = new int[bin];
                
            for (int j = 0; j < histogramSize; j++) 
                histArr[j] = scanner.nextInt();
                
            Arrays.sort(histArr);
            int min = histArr[0];
            int max = histArr[histogramSize-1];
            int increment = (max - min) / bin;
            temp = min;
            int counter = 0;
            while (temp <= max) {
                cutoffs[counter] = temp;
                temp += increment;
                counter++;
                }
            
            temp = 1;
            int nextBin = cutoffs[temp];
            counter = 0;
                
            for (int j = 0; j < histogramSize; j++) {
                if (histArr[j] < nextBin)
                    counter++;
                else {
                    temp++;
                    if (temp == cutoffs.length) {
                        counts[temp-2] = ++counter;
                        break;
                    }
                    
                    counts[temp-2] = counter;
                    counter = 1;
                    nextBin = cutoffs[temp];
                }
            }
            
            //print cutoffs
            for (int each : cutoffs) 
                System.out.print(each + " ");
            System.out.println();
            
            //print counts
            for (int each : counts) 
                System.out.print(each + " ");
            System.out.println();
        }
    }
}
