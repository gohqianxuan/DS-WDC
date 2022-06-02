import java.util.*;

public class Payment {

    public static void main(String[] args) {
        PriorityQueue<Transactions> q = new PriorityQueue<>();
        String transaction;
        String[] details;
        long timer, timer1, timer2;
        String id, tier;
        int digit1 = 0, digit3 = 0, digit2 = 0;

        Scanner in = new Scanner(System.in);
    
        while (in.hasNextLine()) {
            
                transaction = in.nextLine();
                if (transaction.equals("EXIT")) {
                    break;
                } else if (transaction.equals("REBOOT")) {
                    q.clear();
                }else {
                    details = transaction.split(" ");
                    timer = Long.parseLong(details[0]);
                    id = details[1];
                    tier = details[2];
                    Transactions t1 = new Transactions(timer, id, tier);
                    if (q.peek() != null && digit3 < digit1) {
                        timer1 = q.peek().getTimer();
                        digit1 = (int) (timer1 % 10000 / 1000);
                    }
                    q.offer(new Transactions(timer, id, tier));
                    if (t1.getTimer() % 10000 == 0）break;
                        
                    timer2 = t1.getTimer();
               
                    digit2 = (int) (timer2 % 10000 / 1000);
                    
                    
                    
                    
                    while (q.size() == 1) {
                        timer1 = timer2;
                        digit1 = (int) (timer1 % 10000 / 1000);
                        break;
                    }
                    if (digit2 > digit1) {
                        for (int i = 0; i < 100; i++) {
                            if (!q.isEmpty()) {
                                Transactions t = q.poll();
                                System.out.print(t + " ");
                            }
                        }
                        System.out.println();
    
                    }
                    digit1 = digit3 = digit2;
                }
            } 
                
            }

        }
    

class Transactions implements Comparable<Transactions> {
    long timer;
    String id;
    String tier;
    Long stime;

    public Transactions(long timer, String id, String tier) {
        this.timer = timer;
        this.id = id;
        this.tier = tier;
    }

    public long getTimer() {
        return timer;
    }

    public String getId() {
        return id;
    }

    public String getTier() {
        return tier;
    }

    public Long getStartingTime() {
        switch (tier) {
            case "PLATINUM":
                return stime = timer - 3000;
            case "GOLD":
                return stime = timer - 2000;
            case "SILVER":
                return stime = timer - 1000;
            case "BRONZE":
                return stime = timer;
            default:
                break;
        }
        return stime;
    }

    @Override
    public int compareTo(Transactions o1) {
        return this.getStartingTime().compareTo(o1.getStartingTime());
    }

    @Override
    public String toString() {
        return id + " ";
    }

}
