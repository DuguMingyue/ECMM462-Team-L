import java.util.ArrayList;

public class world{
    int timestamp = 0;// Time in this world
    int blockamount = 100;// Amount of blocks
    int bufferamount = 3;// The amount of the buffer

    int handover = 0;// The amount of already handed in block
    double performance = 0; // Evaluation
    crane crane = new crane(); // In hot storage there is only 1 crane
    craneSchedule schedule = new craneSchedule();
    craneMove currentMove = new craneMove();

    arriveStack aristk = new arriveStack();
    bufferStack bufstk = new bufferStack(bufferamount);
    handoverStack hndstk = new handoverStack();

    //Generate blocks
    ArrayList<block> blockpool = block.GenerateRandomBlocks(blockamount); //Initial block pool to be moved
    ArrayList<block> nowblock = new ArrayList<>(); //Block in the world now
    evaluation eva = new evaluation();

    property property = new property();

    // Functions to change the variable state in the world
    public static void state(world world){
        if(world.schedule.moves.size() > 0){

        }
    }

    //
    public static boolean validMove(world world){
        boolean rst = true;

        return rst;
    }

}
