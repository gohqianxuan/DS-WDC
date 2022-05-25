import java.util.Scanner;
import java.util.LinkedList;

public class Payment {
    static PriorityQueue queue1 = new PriorityQueue();
    static TransactionDetails trans;
    static int index;
    static int index2;
    
    public static void main(String[] args) {

        
        Scanner in = new Scanner(System.in);

        
        
        while (true){
            System.out.print("INPUT : ");
            String data = in.nextLine();

            //String data = in.nextLine();


            if (data.equalsIgnoreCase("EXIT")){
                break;
            }else if (data.equalsIgnoreCase("REBOOT")){
                queue1.clear();
            //}else if (data.equalsIgnoreCase("CLEAR")){
            //    while (queue1.getSize() != 0){
            //        System.out.print(queue1.dequeue() + " ");
                    
            //    }
            //    System.out.println();
            }else if (data.equalsIgnoreCase("END")){
                break;
            }else {
                Long time = Long.parseLong(data.substring(0, 13));
                String id = data.substring(14, 46);
                String tier = data.substring(47);
                
                trans = new TransactionDetails(time, id, tier);
                queue1.enqueuePriority(trans);
                queue1.enqueueNormal(trans);

                if (checkTimeChange(trans)){
                    int size = queue1.getSize();


                    System.out.print("OUTPUT : ");
                    if (size >= 100){
                        for (int x = 0 ; x<100 ; x++){
                            if (queue1.getSize() != 0 ){
                                System.out.print(queue1.dequeuePriority() + " ");
                                
                            }
                        }
                        break;
                    }else {
                        for (int x = 0 ; x<100 ; x++){
                            if (queue1.getSize() != 0 ){
                                System.out.print(queue1.dequeueNormal() + " ");
                                
                            }
                        }
                        break;
                    }
                    
                    
                    System.out.println();
                    break;
                
                }
            
            }
            
        }
        } 
        
        
        
        
        
    

    //detect changes in epochTime
    public static boolean checkTimeChange(TransactionDetails object){
            index = (int) (queue1.contains(0).getEpochtime() % 10000 / 1000);
            index2 = (int) (object.getEpochtime() % 10000 / 1000);
            if (index != index2){
                return true;
            }
            return false;
    }

    
    
}

class TransactionDetails {

    private long epochTime;
    private String tier;
    private String transactionID;
    private int timeTier;
    

    public TransactionDetails(long time , String ID , String tier){
        this.epochTime = time;
        this.tier = tier;
        this.transactionID = ID;
        tierTimeAdvantage();
    }


    //To give the starting time of a transaction in a queue according to tier
    public void tierTimeAdvantage(){
        
        switch(this.tier){

            case "PLATINUM":
                timeTier = 3000;
                break;
            case "GOLD":
                timeTier = 2000;
                break;
            case "SILVER":
                timeTier = 1000;
                break;
            case "BRONZE" :
                timeTier = 0;
                break;
        }

    }

    

    public long getEpochtime(){
        return this.epochTime;
    }

    public String getID(){
        return this.transactionID;
    }

    public int getTierTime(){
        return timeTier;
    }

    public String toString(){
        return transactionID + " " + tier;
    }

    
}

class PriorityQueue {
    
    LinkedList<TransactionDetails> Prioritylist;
    LinkedList<TransactionDetails> normalList;
    int count;

    public PriorityQueue(){
        Prioritylist = new LinkedList<>();
        normalList = new LinkedList<>();
        count = 0;
    }


    public void enqueuePriority(TransactionDetails elem){
        //check whether list is empty
        if (Prioritylist.isEmpty()){
            Prioritylist.addFirst(elem);
            return;
        }

        //queue it according to priority
        boolean positioned = false;
        for (int x = 0 ; x< Prioritylist.size() ; x++){
            if ( elem.getTierTime() > Prioritylist.get(x).getTierTime()){
                Prioritylist.add(x , elem);
                positioned = true;
                break;
            }
        }

        if (positioned == false){
            Prioritylist.addLast(elem);
        }

        
    }

    public void enqueueNormal(TransactionDetails elem){
        if (normalList.isEmpty()){
            normalList.addFirst(elem);
        }else {
            normalList.addLast(elem);
        }
    }

    public void clear(){
        Prioritylist.clear();
        normalList.clear();
    }

    public String dequeuePriority(){
        
            normalList.removeFirst();
            return Prioritylist.removeFirst().getID() ;
        
                
    }

    public String dequeueNormal(){
        Prioritylist.removeFirst();
        return normalList.removeFirst().getID() ;
    }

    public TransactionDetails contains(int index){        
        return normalList.get(index);
        
    }


    public int getSize(){
        //return different list according to size
        //if > 100 the only return Priority list
        //if not , return normallist
        if (Prioritylist.size() >= 100){
            return Prioritylist.size();
        
        }else if (Prioritylist.size() != 0 && normalList.size() != 0){
            return normalList.size();
        }
        return 0;
        
    }

    
    
    

    
}


