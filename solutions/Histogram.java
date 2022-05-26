import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Histogram {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int histogramSize = scanner.nextInt();
		int bin = scanner.nextInt();
		int temp;
		List<Integer> histArr = new ArrayList<>();
		List<Integer> cutoffs = new ArrayList<>();
		List<Integer> counts = new ArrayList<>();
                
		for (int i = 0; i < histogramSize; i++) {
	            temp = scanner.nextInt();
	            histArr.add(temp);
		}
		int min = Collections.min(histArr);
		int max = Collections.max(histArr);
		int increment = (max - min) / bin;
		temp = min;
		while (temp <= max) {
			cutoffs.add(temp);
			temp += increment;
		}
		Collections.sort(histArr);
		temp = 1;
		int k = cutoffs.get(temp);
		int counter = 0;
		for (int i = 0; i < histogramSize; i++) {
			if (histArr.get(i) < k) {
				counter++;
			} else {
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
                
		for (Integer each : cutoffs) {
			System.out.print(each + " ");
		}
		System.out.println();
		for (Integer each : counts) {
			System.out.print(each + " ");
		}
		scanner.close();
	}
}