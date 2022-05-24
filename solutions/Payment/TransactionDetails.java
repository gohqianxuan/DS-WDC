package Payment;
public class TransactionDetails {

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
        return transactionID + " " + epochTime;
    }

    
}