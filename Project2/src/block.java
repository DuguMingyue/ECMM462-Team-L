import java.util.ArrayList;
import java.util.Random;

public class block{
    int id = -1;// ID of the block
    int position = 0;//0 represent arrive stack, 1 represent buffer stack, 2 represent handover stack
    int release = -1;// The time when block is released, count in seconds
    int due = -1;// The due time of the block, count in seconds
    boolean ready = false; // Is handed over or not or not
    boolean overdue = false; // Is overdue or not

    public block(){}

    public block(int id, int due, boolean ready){
        this.id = id;
        this.due = due;
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
            int duetemp = r.nextInt(blockamount) + 2 * blockamount; //Add a basic time to avoid to short due data
            boolean readytemp = false;
            block newblock = new block(idtemp, duetemp, readytemp);
            result.add(newblock);
            temp++;
        }
        return result;
    }

    public static void handOver(block Block){
        Block.ready = true;
    }

}