import java.util.ArrayList;
import java.util.Random;

public class block{
    int id = -1;// ID of the block
    int arrivalTime = -1; // time at which the block arrives at arrival stack after the previous
    int position = 0;//0 represent arrive stack, 1 represent buffer stack, 2 represent handover stack
    int release = -1;// The time when block is released, count in seconds
    int due = -1;// The due time of the block, count in seconds
    int readyTime = -1;
    boolean ready = false; // Is handed over or not or not
    boolean overdue = false; // Is overdue or not

    public block(){}

    public block(int id, int arrival, int due, int readytime, boolean ready){
        this.id = id;
        this.arrivalTime = arrival;
        this.due = due;
        this.readyTime = readytime;
        this.ready = ready;
    }

    // Functions to randomly generate the blocks
    public static ArrayList<block> GenerateRandomBlocks(int blockamount){
        ArrayList<block> result = new ArrayList<>();
        Random r = new Random();
        int temp = 0;
        while(temp < blockamount){
            int idtemp = temp;
            // Assume all block
            int averageServiceTime = 420; // average service time is 7 minutes
            int averageDueTime = 60; // average due time is 1 minute
            double arrivalRate = 0.01; // 0.001 block/second arriving at arrival stack
            double lambdaservice = (double) 1/averageServiceTime;
            double lambdadue = (double) 1/averageDueTime;
            double lambdaarrival = (double) 1/arrivalRate;

            int due = (int) RandomNumberGenerator.Exponential.getRandom(r, lambdaservice);
            int ready = (int) RandomNumberGenerator.Exponential.getRandom(r, lambdadue);
            int arrival = (int) RandomNumberGenerator.Poisson.getRandom(r, lambdaarrival);

            boolean readytemp = false;
            if (arrival - ready > 5 && ready > 0) { // avoid having ready time greater than due time
                block newblock = new block(idtemp, arrival, due, ready, readytemp);
                result.add(newblock);
                temp++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<block> blocks = GenerateRandomBlocks(100);
        System.out.println("id, position, release, arrivalTime, dueTime, readyTime, ready, overdue");
        for (block b : blocks) {
            System.out.println(b.id+","+b.position+","+b.release+","+b.arrivalTime+","+b.due+","+b.readyTime+","+b.ready+","+b.overdue);
        }
    }

    public static void handOver(block Block){
        Block.ready = true;
    }

}
