
import java.util.LinkedList;


public class PriorityQueue {
    
    LinkedList<TransactionDetails> Prioritylist;
    LinkedList<TransactionDetails> normalList;


    public PriorityQueue(){
        Prioritylist = new LinkedList<>();
        normalList = new LinkedList<>();
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

    public String dequeue(){
        
        if (Prioritylist.size() >=100){
            return Prioritylist.removeFirst().getID();
        }else {
            return normalList.removeFirst().getID();
        }
    }

    public TransactionDetails contains(int index){        
        return normalList.get(index);
        
    }


    public int getSize(){
        return normalList.size();
    }

    
    
    

    
}
