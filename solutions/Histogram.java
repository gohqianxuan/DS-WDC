import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Histogram {

	public static void main(String[] args) {
	//try{    
            Scanner scanner = new Scanner(System.in);
            int numCase = scanner.nextInt();

            for (int i = 0; i < numCase; i++) {
                int histogramSize = scanner.nextInt();
                int bin = scanner.nextInt();
                int temp;
                
                List<Integer> histArr = new ArrayList<>();
                int[] cutoffs = new int[bin+1];
                int[] counts = new int[bin];
                
                for (int j = 0; j < histogramSize; j++) 
                    histArr.add(scanner.nextInt());
                
                int min = Collections.min(histArr);
                int max = Collections.max(histArr);
                int increment = (max - min) / bin;
                
                temp = min;
                int count = 0;
                while (temp <= max) {
                    cutoffs[count] = temp;
                    temp += increment;
                    count++;
                }
                
                Collections.sort(histArr);
                temp = 1;
                int nextBin = cutoffs[temp];
                int counter = 0;
                
                for (int j = 0; j < histogramSize; j++) {
                    if (histArr.get(j) < nextBin) {
                        counter++;
                    } else {
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

                for (Integer each : cutoffs) {
                    System.out.print(each + " ");
                }
                System.out.println();
                for (Integer each : counts) {
                    System.out.print(each + " ");
                }
                System.out.println();
            }
            //scanner.close();
        //} catch (FileNotFoundException e) {}
    }
}
