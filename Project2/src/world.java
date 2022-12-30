import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class world{
    int timestamp = 0;// Time in this world
    int blockamount = 100;// Amount of blocks
    int bufferamount = 3;// The amount of the buffer

    int handover = 0;// The amount of already handed in block
    double performance = 0; // Evaluation
    crane crane = new crane(); // In hot storage there is only 1 crane
    craneSchedule schedule = new craneSchedule();
    craneMove currentMove = new craneMove(crane);

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

    public static boolean validMove(world world){
        craneMove currentMove = world.currentMove;
        if (currentMove.sourceId == 0) {
            if (currentMove.blockId != world.aristk.stack.get(0).id) {
                return false; // block not found
            }
            if (currentMove.targetId == 2 && currentMove.blockId == world.aristk.stack.get(0).id && !world.aristk.stack.get(0).ready) {
                return false; // block is not ready
            }
        }
        if (currentMove.sourceId == 1) {
            int[] ids = new int[world.bufstk.bufferamount];
            block[] blocksOnTop = new block[world.bufstk.bufferamount];
            for (ArrayList<block> stack : world.bufstk.stack) {
                ids[world.bufstk.stack.indexOf(stack)] = stack.get(stack.size()-1).id;
                blocksOnTop[world.bufstk.stack.indexOf(stack)] = stack.get(stack.size()-1);
            }
            if (IntStream.of(ids).noneMatch(x -> x == currentMove.blockId)) { // if block not top in any buffer
                return false; // block not found
            }

            for (block ontopblock : blocksOnTop) {
                if (currentMove.targetId == 2 && ontopblock.id == currentMove.blockId && !ontopblock.ready) {
                    return false; // block is not ready
                }
            }
        }
        if (currentMove.sourceId == 2) {
            return false; // invalid source
        }
        if (currentMove.targetId == 0) {
            return false; // invalid target
        }
        if (currentMove.targetId == 1) {
            int stacksTotalCapacity = 0;
            for ( ArrayList<block> stack : world.bufstk.stack) {
                stacksTotalCapacity += stack.size();
            }
            if (stacksTotalCapacity >= world.bufstk.totalCapacity) {
                return false; // height violation
            }
        }

        return true; // otherwise return true
    }
}
