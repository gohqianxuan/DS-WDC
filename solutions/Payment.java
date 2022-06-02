import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Payment {
    static Long time;
    static String id, tier;
    static TransactionDetail trans;
    static int digit1 = 0, digit2;
    static long timer1, timer2;
    static Queue queue = new Queue();

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String[] details;

        while (in.hasNextLine()) {
            String data = in.nextLine();
            if (data.equals("EXIT")) {
                break;
            } else if (data.equals("REBOOT")) {
                queue.clear();
            } else {

                details = data.split(" ");
                time = Long.parseLong(details[0]);
                id = details[1];
                tier = details[2];
                TransactionDetail t1 = new TransactionDetail(time, id, tier);
                queue.enqueue(t1);

                timer2 = t1.getEpochtime();
                if (timer2 % 1000 == 0) {
                    digit2 = 10 ;

                } else {
                    digit2 = (int) (timer2 % 10000 / 1000);
                }

                if (queue.getSize() == 1) {
                    digit1 = digit2;
                }

                if (digit2 != digit1) {
                    digit1 = digit2;
                    
                    queue.mergeSort();
                    for (int x = 0; x < 100; x++) {
                        if (!queue.isEmpty()){
                            System.out.print(queue.dequeue().toString() + " ");
                        }
                        
                    }
                    System.out.println();
                }

            }
        }

    }
}




class MergeSort {

    static ArrayList<TransactionDetail> tmp;

    public static void mergeSort(ArrayList<TransactionDetail> a,  int left, int right) {
        int middle = (left + right) / 2;
        if (left < right) {
            mergeSort(a,  left, middle); //sort left half
            mergeSort(a,  middle + 1, right); //sort right half
            mergeSortedLists(a,  left, middle, right);
        } // merge
    }

    public static void mergeSortedLists(ArrayList<TransactionDetail> a,  int left, int middle, int right) {
        tmp = new ArrayList<TransactionDetail>();
        int tempLeft = left;
        int tempRight = middle + 1;
        while (tempLeft <= middle && tempRight <= right)
            if (a.get(tempLeft).getWaitingTime() <= (a.get(tempRight).getWaitingTime())) {
                {
                    tmp.add(a.get(tempLeft));
                }
                tempLeft++;
            } else {
                {
                    tmp.add(a.get(tempRight));
                }
                tempRight++;
            }

        while (tempLeft <= middle) {
            {
                tmp.add(a.get(tempLeft));
            }
            tempLeft++;
        }

        while (tempRight <= right) {
            {
                tmp.add(a.get(tempRight));
            }
            tempRight++;
        }
        int i = left;
        {
            for (TransactionDetail value : tmp) {
                a.set(i, value);
                i++;
            }
        }
    }
    

}


class Queue {
    ArrayList<TransactionDetail> priorityList;

    public Queue() {
        priorityList = new ArrayList<>();
    }

    public void enqueue(TransactionDetail elements){
        if (priorityList.size() == 0 ){
            priorityList.add(0,elements);;
        }else {
            priorityList.add(elements);
        }
    }

    public void setPriorityList(ArrayList<TransactionDetail> list){
        Collections.copy(priorityList, list);
    }


    public void clear() {
        priorityList.clear();
    }

    public int getSize(){
        return priorityList.size();
    }
    public boolean isEmpty(){
        return priorityList.isEmpty();
    }

    public TransactionDetail dequeue(){
        if (priorityList.isEmpty()) return null;
        return priorityList.remove(0);
        
    }

    public TransactionDetail get(int index){
        if(priorityList.isEmpty()) return null;
        return priorityList.get(index);
    }


    public void mergeSort (){
        
        MergeSort.mergeSort(priorityList, 0, priorityList.size()-1);
    }

    
    

}

class TransactionDetail {

    private long epochTime;
    private String tier;
    private String transactionID;
    private long waitingTime;

    public TransactionDetail(long time, String ID, String tier) {
        this.epochTime = time;
        this.tier = tier;
        this.transactionID = ID;
        calculateWaitingTime(time);
    }


    //maybe can try to cut to five digits then operation might be less
    // To give the starting time of a transaction in a queue according to tier
    public void calculateWaitingTime(long time) {

        switch (this.tier) {
            case "PLATINUM":
                waitingTime = epochTime - 3000 ;
                break;
            case "GOLD":
                waitingTime = epochTime - 2000 ;
                break;
            case "SILVER":
                waitingTime = epochTime - 1000 ;
                break;
            case "BRONZE":
                waitingTime = epochTime;
                break;
        }

    }

    
    public String getID() {
        return this.transactionID;
    }

    public long getWaitingTime() {
        return this.waitingTime;
    }

    public void addWaitingTime(){
        this.waitingTime += 1000;
    }

    public long getEpochtime(){
        return this.epochTime;
    }

    public String toString() {
        return transactionID;
    }

}
