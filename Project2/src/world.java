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
    ArrayList<block> overblock = new ArrayList<>(); //Block already hand over
    evaluation eva = new evaluation();

    property property = new property();

    // Functions to change the variable state in the world
    public static void update(world world){
        if(world.schedule.moves.size() > 0){
            craneMove curMove = world.schedule.moves.get(0);
            // Examine the handover stack
            if(world.timestamp % world.property.handoverReadyIntervals == 0){
                if(world.hndstk.stack.size() > 0){
                    world.hndstk.stack.remove(0);
                }
            }
            // Examine where the block is overdue/ready or not
            if(curMove.sourceId == 0 && curMove.targetId == 1){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        //If just pass the buffer, default crane is at buffer 1;
                        world.crane.horizonPosition = 2;
                    }
                    if(curMove.stepIndex == 2){
                        world.crane.horizonPosition = 1;
                    }
                }
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){
                        world.crane.verticalPosition = 0;
                    }
                    if(curMove.stepIndex == 2){
                        world.crane.verticalPosition = 1;
                    }
                    if(curMove.stepIndex == 3){
                        world.crane.horizonPosition = 2;
                        world.nowblock.get(curMove.indexId).position = 2;
                    }
                    if(curMove.stepIndex == 4){
                        world.crane.horizonPosition = 1;
                        world.nowblock.get(curMove.indexId).position = 1;

                    }
                    if(curMove.stepIndex == 5){
                        world.crane.verticalPosition = 0;
                        world.overblock.add(world.nowblock.get(curMove.indexId)); // move to handover pool
                        world.hndstk.stack.add(world.nowblock.get(curMove.indexId)); // move to handover block
                        world.nowblock.remove(curMove.indexId);// remove from now block
                    }
                    if(curMove.stepIndex == 6){
                        world.crane.verticalPosition = 1;
                    }
                }
            }

            if(curMove.sourceId == 0 && curMove.targetId >= 2){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        world.crane.horizonPosition = curMove.targetId;
                    }
                }
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){
                        world.crane.verticalPosition = 0;
                    }
                    if(curMove.stepIndex == 2){
                        world.crane.verticalPosition = 1;
                    }
                    if(curMove.stepIndex == 3){
                        world.nowblock.get(curMove.indexId).position = curMove.targetId;
                        world.crane.horizonPosition = curMove.targetId;
                    }
                    if(curMove.stepIndex == 4) {
                        world.crane.verticalPosition = 0;
                    }
                    if(curMove.stepIndex == 5){
                        world.crane.verticalPosition = 1;
                    }
                }
            }

            if(curMove.sourceId == 1 && curMove.targetId == 0){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        world.crane.horizonPosition = 2;
                    }
                    if(curMove.stepIndex == 2){
                        world.crane.horizonPosition = 0;
                    }
                }
                //Invalid
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){
                    }
                }
            }

            if(curMove.sourceId == 1 && curMove.targetId >= 2){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        world.crane.horizonPosition = curMove.targetId;
                    }
                }

                //Invalid
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){
                    }
                }
            }


            if(curMove.sourceId >= 2 && curMove.targetId == 0){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        world.crane.horizonPosition = 0;
                    }
                }
                //Invalid
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){

                    }
                }
            }

            if(curMove.sourceId >= 2 && curMove.targetId == 1){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        world.crane.horizonPosition = 1;
                    }
                }
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){
                        world.crane.verticalPosition = 0;
                    }
                    if(curMove.stepIndex == 2){
                        world.crane.verticalPosition = 1;
                    }
                    if(curMove.stepIndex == 3){
                        world.crane.horizonPosition = 1;
                        world.nowblock.get(curMove.indexId).position = 1;
                    }
                    if(curMove.stepIndex == 4){
                        world.crane.verticalPosition = 0;
                        world.overblock.add(world.nowblock.get(curMove.indexId)); // move to handover pool
                        world.hndstk.stack.add(world.nowblock.get(curMove.indexId)); // move to handover block
                        world.nowblock.remove(curMove.indexId);// remove from now block
                    }
                    if(curMove.stepIndex == 5){
                        world.crane.verticalPosition = 1;
                    }
                }
            }
            if(curMove.sourceId >= 2 && curMove.targetId >= 2){
                if(curMove.emptyMove == true){
                    if(curMove.stepIndex == 1){
                        world.crane.horizonPosition = curMove.targetId;
                    }
                }
                if(curMove.emptyMove == false){
                    if(curMove.stepIndex == 1){
                        world.crane.verticalPosition = 0;
                    }
                    if(curMove.stepIndex == 2){
                        world.crane.verticalPosition = 1;
                    }
                    if(curMove.stepIndex == 3){
                        world.nowblock.get(curMove.indexId).position = curMove.targetId;
                        world.crane.horizonPosition = curMove.targetId;
                    }
                    if(curMove.stepIndex == 4) {
                        world.crane.verticalPosition = 0;
                    }
                    if(curMove.stepIndex == 5){
                        world.crane.verticalPosition = 1;
                    }
                }
            }

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
