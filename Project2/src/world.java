import java.util.ArrayList;
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
            int temp = 0;
            while(temp < world.nowblock.size()){
                if(world.timestamp > world.nowblock.get(temp).due){
                    world.nowblock.get(temp).overdue = true;
                }
                if(world.timestamp > world.nowblock.get(temp).readyTime){
                    world.nowblock.get(temp).ready = true;
                }
                temp++;
            }

            // Adjusting the state of crane, stack and block
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
                        world.aristk.stack.remove(0);
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
                        world.aristk.stack.remove(0);
                    }
                    if(curMove.stepIndex == 3){
                        world.nowblock.get(curMove.indexId).position = curMove.targetId;
                        world.crane.horizonPosition = curMove.targetId;
                    }
                    if(curMove.stepIndex == 4) {
                        world.crane.verticalPosition = 0;
                        world.bufstk.stack.get(curMove.targetId - 2).add(world.nowblock.get(curMove.indexId));
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
                        world.bufstk.stack.get(curMove.sourceId - 2).remove(world.bufstk.stack.get(curMove.sourceId - 2).size() - 1);
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
                        world.bufstk.stack.get(curMove.sourceId - 2).remove(world.bufstk.stack.get(curMove.sourceId - 2).size() - 1);
                    }
                    if(curMove.stepIndex == 3){
                        world.nowblock.get(curMove.indexId).position = curMove.targetId;
                        world.crane.horizonPosition = curMove.targetId;
                    }
                    if(curMove.stepIndex == 4) {
                        world.crane.verticalPosition = 0;
                        world.bufstk.stack.get(curMove.targetId - 2).add(world.nowblock.get(curMove.indexId));
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

        if (currentMove.sourceId == 2 && world.bufstk.stack.get(0).size() <= 0
                || currentMove.sourceId == 3 && world.bufstk.stack.get(1).size() <= 0
                || currentMove.sourceId == 4 && world.bufstk.stack.get(2).size() <= 0) {
            return false;
        }
        if (currentMove.sourceId == 2 || currentMove.sourceId == 3 || currentMove.sourceId == 4) {
            int[] ids = new int[world.bufstk.bufferamount];
            block[] blocksOnTop = new block[world.bufstk.bufferamount];
            for (ArrayList<block> stack : world.bufstk.stack) {
                if (stack.size() != 0) {
                    ids[world.bufstk.stack.indexOf(stack)] = stack.get(stack.size()-1).id;
                    blocksOnTop[world.bufstk.stack.indexOf(stack)] = stack.get(stack.size()-1);
                } else {
                    ids[world.bufstk.stack.indexOf(stack)] = -1;
                    blocksOnTop[world.bufstk.stack.indexOf(stack)] = null;
                }
            }
            if (IntStream.of(ids).noneMatch(x -> x == currentMove.blockId)) { // if block not top in any buffer
                return false; // block not found
            }
            for (block ontopblock : blocksOnTop) {
                if (ontopblock!=null) {
                    if (currentMove.targetId == 1 && ontopblock.id == currentMove.blockId && !ontopblock.ready) {
                        return false; // block is not ready
                    }
                }
            }
        }
        if (currentMove.sourceId == 1) {
            return false; // invalid source
        }
        if (currentMove.targetId == 0) {
            return false; // invalid target
        }
        if (currentMove.targetId == 2 || currentMove.targetId == 3 || currentMove.targetId == 4) {
            switch (currentMove.targetId) {
                // check for height violation in buffers
                case 2:
                    if (world.bufstk.stack.get(0).size() >= 7) {
                        return false;
                    }
                    break;
                case 3:
                    if (world.bufstk.stack.get(1).size() >= 7) {
                        return false;
                    }
                    break;
                case 4:
                    if (world.bufstk.stack.get(2).size() >= 7) {
                        return false;
                    }
                    break;
            }
        }
        if (currentMove.targetId == currentMove.sourceId) {
            return false; // pointless move
        }
        return true; // otherwise return true
    }



//    public static boolean validMove1(world world){
//        // 0=arrival, 1=handover, {2,3,4}=buffer
//        craneMove currentMove = world.currentMove;
//        if (currentMove.sourceId == 0) {
//            if (currentMove.blockId != world.aristk.stack.get(0).id && !currentMove.emptyMove) {
//                return false; // block not found
//            }
//            if (currentMove.targetId == 1 && currentMove.blockId == world.aristk.stack.get(0).id && !world.aristk.stack.get(0).ready) {
//                return false; // block is not ready
//            }
//        }
//        if (currentMove.sourceId == 2 || currentMove.sourceId == 3 || currentMove.sourceId == 4) {
//            int[] ids = new int[world.bufstk.bufferamount];
//            block[] blocksOnTop = new block[world.bufstk.bufferamount];
//            for (ArrayList<block> stack : world.bufstk.stack) {
//                ids[world.bufstk.stack.indexOf(stack)] = stack.get(stack.size()-1).id;
//                blocksOnTop[world.bufstk.stack.indexOf(stack)] = stack.get(stack.size()-1);
//            }
//            if (IntStream.of(ids).noneMatch(x -> x == currentMove.blockId)) { // if block not top in any buffer
//                return false; // block not found
//            }
//
//            for (block ontopblock : blocksOnTop) {
//                if (currentMove.targetId == 1 && ontopblock.id == currentMove.blockId && !ontopblock.ready) {
//                    return false; // block is not ready
//                }
//            }
//        }
//        if (currentMove.sourceId == 1) {
//            return false; // invalid source
//        }
//        if (currentMove.targetId == 0) {
//            return false; // invalid target
//        }
//        if (currentMove.targetId == 2 || currentMove.targetId == 3 || currentMove.targetId == 4) {
//            int stacksTotalCapacity = 0;
//            for ( ArrayList<block> stack : world.bufstk.stack) {
//                stacksTotalCapacity += stack.size();
//            }
//            if (stacksTotalCapacity >= world.bufstk.totalCapacity) {
//                return false; // height violation
//            }
//        }
//
//        return true; // otherwise return true
//    }
}
