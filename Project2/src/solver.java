import java.util.Random;

public class solver {
    public static craneSchedule solve(world world){
        //Decide the block
        Random r = new Random();

        if(world.crane.isWorking == false){
            world.crane.isWorking = true;
            // Return value should be on the top of the stack and emptyMove is true or not
            int index = r.nextInt(world.nowblock.size());
            int src = 0;
            int tgt = 0;

            craneMove newMove = new craneMove(world.crane);
            newMove.emptyMove = false;
            world.crane.load = world.nowblock.get(index);

            if(world.nowblock.get(index).position == 0){// Possibilities that 0 move to 2
                newMove.indexId = index;
                newMove.blockId = world.nowblock.get(index).id;
                newMove.sourceId = 0;
                newMove.targetId = 1;
                newMove.stepCost = stepCost(newMove.sourceId, newMove.targetId);
                newMove.timeCost = newMove.stepCost * world.property.craneMoveTime;
                newMove.stepIndex = 1;
            }
            if(world.nowblock.get(index).position == 1){
                newMove.indexId = index;
                newMove.blockId = world.nowblock.get(index).id;
                newMove.sourceId = 1;
                newMove.targetId = 2;
                newMove.stepCost = stepCost(newMove.sourceId, newMove.targetId);
                newMove.timeCost = newMove.stepCost * world.property.craneMoveTime;
                newMove.stepIndex = 1;
            }
            world.schedule.moves.add(newMove);
        }
        else{
            // If reach the last step, shut it down
            if(world.schedule.moves.get(world.schedule.moves.size() - 1).stepIndex == world.schedule.moves.get(world.schedule.moves.size() - 1).stepCost){
                world.crane.isWorking = false;
            }
            // Else step into next step
            else{
                world.schedule.moves.get(world.schedule.moves.size() - 1).stepIndex++;
            }
        }
        return world.schedule;
    }

    // Count the time cost of the move
    public static int stepCost(int srcId, int tgtId){
        int step = 0;
        if(srcId == 0 && tgtId == 1){
            step = 5;
        }
        if(srcId == 0 && tgtId == 2){
            step = 6;
        }
        if(srcId == 1 && tgtId == 0){
            step = 1;
        }
        if(srcId == 1 && tgtId == 2){
            step = 5;
        }
        if(srcId == 2 && tgtId == 0){
            step = 2;
        }
        if(srcId == 2 && tgtId == 1){
            step = 1;
        }
        return step;
    }
}
