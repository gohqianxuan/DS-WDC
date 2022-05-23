import java.util.Scanner;


public class ReadFile {
    static PriorityQueue queue1 = new PriorityQueue();
    static TransactionDetails trans;
    static int index;
    static int index2;
    public static void main(String[] args) {

        
        Scanner in = new Scanner(System.in);

            
            

        while (true){
            System.out.println("Enter transaction details");


            String data = in.nextLine();


            if (data.equalsIgnoreCase("EXIT")){
                break;
            }else if (data.equalsIgnoreCase("REBOOT")){
                queue1.clear();
            }else {
                Long time = Long.parseLong(data.substring(0, 13));
                String id = data.substring(14, 46);
                String tier = data.substring(47);
                
                trans = new TransactionDetails(time, id, tier);
                queue1.enqueuePriority(trans);
                queue1.enqueueNormal(trans);

                if (checkTimeChange(trans)){
                    
                    System.out.print("Output : ");
                    while (queue1.getSize() != 0){
                        System.out.print(queue1.dequeue() + " ");
                    }
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
