import java.util.*;

public class Payment {

    static boolean flag = true;
    static int index1st = 0;
    // static int checkTimeChange;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        PriorityQueue<Transactions> queue = new PriorityQueue<>();
        String[] inputDetails;
        long time;
        String transID, transTier, input;
        
        while (true) {
            input = in.nextLine();

            if (input.equals("EXIT")) {
                break;
            } else if (input.equals("REBOOT")) {
                queue.clear();
                break;
            }

            // split the data
            inputDetails = input.split(" ");
            time = Long.parseLong(inputDetails[0]);
            transID = inputDetails[1];
            transTier = inputDetails[2];

            //insert it to queue
            queue.offer(new Transactions(time, transID, transTier));

            // check time change
            // if flag is true, meaning that it is the first time entering the queue
            if (queue.size() == 1) {
                long timer1st = (queue.peek().getTimer());
                index1st = (int) (timer1st % 10000 / 1000);
                break;
            }

            if (time % 10000 == 0) {
                break;
            }
            int currenttimer = (int) (time % 10000 / 1000);

            if (currenttimer != index1st) {
                index1st = currenttimer;
                int size = queue.size();
                if (size < 100) {
                    while (queue.size() != 0) {
                        System.out.print(queue.poll().getId() + " ");
                    }
                    System.out.println();
                } else {
                    for (int x = 0; x < 100; x++) {
                        System.out.print(queue.poll().getId() + " ");
                    }
                    System.out.println();
                }
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
