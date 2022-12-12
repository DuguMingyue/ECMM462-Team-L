import java.util.ArrayList;

public class world{
    double timestamp = 0;// Time in this world
    int bufferamount = 1;// The amount of the buffer
    int poolsize = 1;
    int handover = 0;// The amount of already handed in block
    double performance = 0;
    crane crane = new crane();
    arriveStack aristk = new arriveStack();
    bufferStack bufstk = new bufferStack(bufferamount);
    ArrayList<block> blockpool = new ArrayList<>();

    // Generate the world
    public world(int blkamount, int bufamount){
        this.poolsize = blkamount;
        this.bufferamount = bufamount;
        this.blockpool = block.GenerateRandomBlocks(blkamount);
        this.bufstk = new bufferStack(bufamount);
        this.aristk = new arriveStack();
        aristk.capacity = 4;
    }
    // Generate Blocks

}
