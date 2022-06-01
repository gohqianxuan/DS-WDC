import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Payment {
    
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        PriorityQueue<Transaction> queue = new PriorityQueue<>();
        ArrayList<String> idlist = new ArrayList<>();
        ArrayList<Long> timelist = new ArrayList<>();
        long timeclone;
        while(true){
            String data = sc.nextLine();
            if (data.equals("EXIT"))
                break;
            else if (data.equals("REBOOT")){
                queue.clear(); 
                idlist.clear();
                timelist.clear();
            }                   
            else{
                Long time = Long.parseLong(data.substring(0, 13));
                String id = data.substring(14, 46);
                String tier = data.substring(47);
                Transaction tran = new Transaction(time, id , tier);
                queue.add(tran);
                idlist.add(id);
                timelist.add(time);
                if(queue.size()!=1 && time(timelist.get(0), time)){
                    if(queue.size()<100){
                        int size = idlist.size();
                        for(int i=0; i<size;i++){
                            if(i==size-1)
                                System.out.println(idlist.get(i));
                            else
                                System.out.print(idlist.get(i)+ " ");
                        }
                        idlist.clear();
                        queue.clear();
                        timelist.clear();
                    }
                    else{
                        timelist.clear();
                        for(int i=0; i<100;i++){
                            idlist.remove(queue.peek().id);
                            if(i==99)
                                System.out.println(queue.poll().id );
                            else
                                System.out.print(queue.poll().id + " ");
                        }
                    }   
                }
            }
        }
        
        
    }
    
    public static boolean time(long time1, long time2){
        if(time1/1000 != time2/1000)
            return true;
        return false;
    }
    

    static class Transaction implements Comparable<Transaction> {
        long epoch_time;
        String id;
        String tier;

        public void setEpoch_time(long epoch_time) {
            this.epoch_time = epoch_time;
        }
        
        //long prior_epoch_time;

        public long getEpoch_time() {
            return epoch_time;
        }
                
        public Transaction(long epoch_time, String id, String tier){
            //this.epoch_time = epoch_time;
            this.id = id;
            this.tier = tier;
            if(tier.equals("SILVER"))
                this.epoch_time = epoch_time - 1000;
            else if(tier.equals("GOLD"))
                this.epoch_time = epoch_time - 2000;
            else if(tier.equals("PLATINUM"))
                this.epoch_time = epoch_time - 3000;
            else 
                this.epoch_time = epoch_time;   
        }

        @Override
        public int compareTo(Transaction o) {
            if (this.getEpoch_time() >= o.getEpoch_time())
                return 1;
            else if (this.getEpoch_time() == o.getEpoch_time())
                return 0;
            else
                return -1;
        }
    }
}
