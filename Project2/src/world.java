import java.util.ArrayList;

public class world{
    int timestamp = 0;// Time in this world
    int blockamount = 100;
    int bufferamount = 3;// The amount of the buffer
    int poolsize = 1;
    int handover = 0;// The amount of already handed in block
    double performance = 0;
    crane crane = new crane(); // In hot storage there is only 1 crane
    arriveStack aristk = new arriveStack();
    bufferStack bufstk = new bufferStack(bufferamount);
    //Generate blocks
    ArrayList<block> blockpool = block.GenerateRandomBlocks(blockamount);
    evaluation eva = new evaluation();

}
