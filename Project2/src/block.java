import java.util.ArrayList;
import java.util.Random;

public class block{
    int id = -1;// ID of the block
    int position = 0;//0 represent arrive stack, 1 represent buffer stack, 2 represent handover stack
    int readytime = -1;// The time when block is ready, count in seconds
    int duetime = -1;// The due time of the block, count in seconds
    int handovertime = -1;
    boolean ready = false; // Is handed over or not or not
    boolean overdue = false; // Is overdue or not

    public block(){}

    public block(int id, int readytime, int due, boolean ready){
        this.id = id;
        this.readytime = readytime;
        this.duetime = due;
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
            int readytemp = r.nextInt(blockamount);// Add a basic time to the ready time of the
            int duetemp = r.nextInt(blockamount) + 2 * blockamount; //Add a basic time to avoid to short due data-
            boolean handovertemp = false;
            block newblock = new block(idtemp, readytemp, duetemp, handovertemp);
            result.add(newblock);
            temp++;
        }
        return result;
    }

}