import java.io.*;
import java.util.*;

public class Payment {
    private PriorityQueue<Transaction> queue = new PriorityQueue<>();
    private ArrayList<Transaction> transaction = new ArrayList<>();
    
    // Get the tier of the transaction ID
    public String getTier(String id) {
        int idIndex = -1;
        
        for (int i = 0; i < transaction.size(); i++) {
            if (transaction.get(i).getId().equals(id)) {
                idIndex = i;
                break;
            }
        }
        
        // Return "Error" String if the ID is not found in the ArrayList
        if (idIndex < 0)
            return "Error: The transaction ID is invalid";
        return transaction.get(idIndex).getTier();
    }
    
    // Get the waiting time of the transaction ID
    public int getWaitingTime(String id) {
        int idIndex = -1;
        
        for (int i = 0; i < transaction.size(); i++) {
            if (transaction.get(i).getId().equals(id)) {
                idIndex = i;
                break;
            }
        }
        
        // Return -1 if the ID is not found in the ArrayList
        if (idIndex < 0)
            return -1;
        return transaction.get(idIndex).getWaiting();
    }
    
    // Get the time (second) the transaction is done 
    public int getTransSec(String id) {
        int idIndex = -1;
        
        for (int i = 0; i < transaction.size(); i++) {
            if (transaction.get(i).getId().equals(id)) {
                idIndex = i;
                break;
            }
        }
        
        // Return -1 if the ID is not found in the ArrayList
        if (idIndex < 0)
            return -1;
        return transaction.get(idIndex).getTransSec();
    }
    
    public void sortTransaction(String filename){
        try{
            Scanner read = new Scanner(new FileInputStream("resources/" + filename));
            PriorityQueue<Transaction> queue = new PriorityQueue<>();
            int seq = 0; // sequence
            int currentSecond, prevSecond = 0; // second digits in epoch time
            int seconds = 0; // the time (second) the transaction enters the queue
            
            while(read.hasNextLine()) {
                String data = read.nextLine();
                
                if (data.equals("EXIT"))
                    break;
                
                else if (data.equals("END")) { // equals to "REBOOT" 
                    queue.clear(); 
                    currentSecond = 0;
                    prevSecond = 0;
                    seq = 0;
                    seconds = 0;
                }   
                
                // Skip the answer checking lines in .txt
                else if (data.equals("CLEAR")) 
                    read.nextLine();
                
                else{
                    seq++;
                    Long time = Long.parseLong(data.substring(0, 13));
                    String id = data.substring(14, 46);
                    String tier = data.substring(47);
                    Transaction trans = new Transaction(time, id, tier, seq, seconds);
                    // Add the transaction into priority queue
                    queue.add(trans);
                    
                    // Update the second digit for every transaction
                    currentSecond = (int)((time/1000)%10);
                    
                    // Initialize prevSecond = currentSecond for the first transaction
                    if(seq == 1) 
                        prevSecond = currentSecond;
                    
                    // Keep currentSecond unchanged if the epoch time has not reached 
                    // the 1st millisecond of the next second
                    if(time%1000 == 0)
                        currentSecond = prevSecond;
                    
                    // Check if the curent second digit is different from the previous second Digit
                    if(currentSecond != prevSecond) {
                        seconds++; 
                        int currSecond = seconds;
                        while (currSecond > 9)
                            currSecond %= 10;
                        
                        if(currSecond == 0)
                            System.out.println("The transactions of the ids listed below will be done at " + (seconds+1) + "st second. ");
                        else if (currSecond == 1)
                            System.out.println("The transactions of the ids listed below will be done at " + (seconds+1) + "nd seconds. ");
                        else if (currSecond == 2)
                            System.out.println("The transactions of the ids listed below will be done at " + (seconds+1) + "rd seconds. ");
                        else
                            System.out.println("The transactions of the ids listed below will be done at " + (seconds+1) + "th seconds. ");
                        
                        int size = queue.size();
                        
                        if(size<100){
                            for(int i=0; i<size; i++) {
                                int waiting = (seconds - queue.peek().seconds);
                                System.out.println(queue.peek().id + " wait for " + waiting + "s."); 
                                transaction.add(new Transaction(queue.peek().id, queue.poll().tier, waiting, seconds));
                            }
                            System.out.println();
                        }
                        
                        else{
                            for(int i=0; i<100;i++){
                                int waiting = (seconds - queue.peek().seconds);
                                System.out.println(queue.peek().id + " wait for " + waiting + "s."); 
                                transaction.add(new Transaction(queue.peek().id, queue.poll().tier, waiting, seconds));
                            }
                            System.out.println();
                        }
                        prevSecond = currentSecond;
                        seconds++;
                    }
                }
            } 
            read.close();
        } catch (FileNotFoundException e){
            System.out.println("File was not found.");
        }
    }
    
    static class Transaction implements Comparable<Transaction> {
        private long epoch_time;
        private String id, tier;
        private int sequence, seconds;
        private int waiting, transSec;

        public String getId() {
            return id;
        }

        public String getTier() {
            return tier;
        }

        public int getWaiting() {
            return waiting;
        }

        public int getTransSec() {
            return transSec;
        }        

        public int getSequence() {
            return sequence;
        }

        public long getEpoch_time() {
            return epoch_time;
        }

        public Transaction(long epoch_time, String id, String tier, int sequence, int seconds){
            this.id = id;
            this.tier = tier;
            this.sequence = sequence;
            this.seconds = seconds;

            if(tier.equals("SILVER"))
                this.epoch_time = epoch_time - 1000;
            else if(tier.equals("GOLD"))
                this.epoch_time = epoch_time - 2000;
            else if(tier.equals("PLATINUM"))
                this.epoch_time = epoch_time - 3000;
            else 
                this.epoch_time = epoch_time;   
        }

        public Transaction(String id, String tier, int waiting, int transSec){
            this.id = id;
            this.tier = tier;
            this.waiting = waiting;
            this.transSec = transSec;
        }

        @Override
        public int compareTo(Transaction o) {
            if (this.getEpoch_time() > o.getEpoch_time())
                return 1;
            else if (this.getEpoch_time() == o.getEpoch_time()){
                if(this.getSequence() > o.getSequence())
                    return 1;
                else
                    return -1;
            }
            else
                return -1;                
        }  
    }
}