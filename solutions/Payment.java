import java.util.*;

public class Payment {
    
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        
        //priority queue to store the transactions
        PriorityQueue<Transaction> queue = new PriorityQueue<>();
        
        //give sequence to each transaction
        int seq = 0;
        
        //calculate change in epoch time
        int milli1, milli2=0;

        while(sc.hasNextLine()){
            String data = sc.nextLine();
            
            //exit the program if input equals to "EXIT"
            if (data.equals("EXIT"))
                break;
            
            //reset everything
            else if (data.equals("REBOOT")){
                queue.clear(); 
                milli1 =0;
                milli2=0;
                seq=0;
            }                   
            else{
                //give sequence to every transaction
                seq++;
                
                //get time, transactionID, tier from user input and store in Transaction class
                Long time = Long.parseLong(data.substring(0, 13));
                String id = data.substring(14, 46);
                String tier = data.substring(47);
                Transaction tran = new Transaction(time, id , tier, seq);
                queue.add(tran);

                //detect change of 1000ms in epoch time
                milli1 = (int) ((time/1000)%10);
                if(seq==1){
                    milli2 = milli1;
                }
                
  
                if(time%1000 == 0)
                    milli1 = milli2;

                //for every 1000ms, dequeue transactions according to the queue's size
                if(milli1 != milli2 ){
                    int size = queue.size();
                    if(size<100){
                        for(int i=0; i<size; i++)
                            System.out.print(queue.poll().id + " ");
                        System.out.println();
                    }
                    else{
                        for(int i=0; i<100;i++){
                             System.out.print(queue.poll().id + " ");
                        }
                        System.out.println();
                    }
                    milli2 = milli1;
                }
            }
        }  
    }
       

    static class Transaction implements Comparable<Transaction> {
        long epoch_time;
        String id;
        String tier;
        int sequence;

        public int getSequence() {
            return sequence;
        }
        
        public long getEpoch_time() {
            return epoch_time;
        }
        

        //store transaction details
        //update epoch time according to user tier     
        public Transaction(long epoch_time, String id, String tier, int sequence){

            this.id = id;
            this.tier = tier;
            this.sequence = sequence;
            if(tier.equals("SILVER"))
                this.epoch_time = epoch_time - 1000;
            else if(tier.equals("GOLD"))
                this.epoch_time = epoch_time - 2000;
            else if(tier.equals("PLATINUM"))
                this.epoch_time = epoch_time - 3000;
            else 
                this.epoch_time = epoch_time;   
        }
        

        //give priority to transactions 
        @Override
        public int compareTo(Transaction o) {
            if (this.getEpoch_time() > o.getEpoch_time())
                return 1;
            else if (this.getEpoch_time() == o.getEpoch_time()){
                if(this.getSequence()>o.getSequence())
                    return 1;
                else
                    return -1;
            }
            else
                return -1;                
        }  
    }
}
