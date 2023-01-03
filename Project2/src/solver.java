import java.util.Random;

public class solver {
    public static craneSchedule solve(world world){
        //Decide the block
        Random r = new Random();
        //If the crane is not working, then generate new steps and work
        if(world.crane.isWorking == false){
            world.crane.isWorking = true;

            // Return value should be on the top of the stack
            int[] solution = evaluation.stepChoice(world);//source id
            int src = solution[0]; //source id
            int tgt = solution[1];//target id
            int Id = solution[2];
            //Find the index
            int index = 0;
            int temp = 0;
            if(world.nowblock.size() > 0){
                while(temp < world.nowblock.size()){
                    if(world.nowblock.get(temp).id == Id){
                        index = temp;
                    }
                    temp++;
                }
            }

            craneMove preMove = new craneMove(world.crane);//An empty move which might exist before the new move
            craneMove newMove = new craneMove(world.crane);//New move

            // If the crane is not at the position src, then execute a empty move first to that position
            if(world.crane.horizonPosition != src){
                preMove.emptyMove = true;
                preMove.sourceId = world.crane.horizonPosition;
                preMove.targetId = src;
                preMove.stepCost = stepCost(preMove.sourceId, preMove.targetId, true);
                preMove.timeCost = preMove.stepCost * world.property.craneMoveTime;
                preMove.stepIndex = 1;
                world.schedule.moves.add(preMove);
                world.eva.CraneManipulations++;
            }
            newMove.emptyMove = false;
            newMove.indexId = index;
            //BlockId is the index in the array nowblock
            newMove.blockId = Id;
            newMove.sourceId = src;
            newMove.targetId = tgt;
            newMove.stepCost = stepCost(newMove.sourceId, newMove.targetId, false);
            newMove.timeCost = newMove.stepCost * world.property.craneMoveTime;
            newMove.stepIndex = 1;

            world.eva.CraneManipulations++;
            world.schedule.moves.add(newMove);
            world.currentMove = world.schedule.moves.get(0);

//            newMove.emptyMove = false;
//            world.crane.load = world.nowblock.get(index);
//            if(world.nowblock.get(index).position == 0){// Possibilities that 0 move to 2
//                newMove.indexId = index;
//                newMove.blockId = world.nowblock.get(index).id;
//                newMove.sourceId = 0;
//                newMove.targetId = 1;
//                newMove.stepCost = stepCost(newMove.sourceId, newMove.targetId, false);
//                newMove.timeCost = newMove.stepCost * world.property.craneMoveTime;
//                newMove.stepIndex = 1;
//                world.currentMove = newMove;
//            }
//            if(world.nowblock.get(index).position == 1){
//                newMove.indexId = index;
//                newMove.blockId = world.nowblock.get(index).id;
//                newMove.sourceId = 1;
//                newMove.targetId = 2;
//                newMove.stepCost = stepCost(newMove.sourceId, newMove.targetId, false);
//                newMove.timeCost = newMove.stepCost * world.property.craneMoveTime;
//                newMove.stepIndex = 1;
//                world.currentMove = newMove;
//            }
//            world.schedule.moves.add(newMove);
        }

        else{// Should consider time cost, default == 1
            // If reach the last step, shut it down
            //For validation checking
            world.currentMove = world.schedule.moves.get(0);
            //step into next step
            if(world.currentMove.stepIndex < world.currentMove.stepCost){
                world.currentMove.stepIndex++;
            }
            if(world.currentMove.stepIndex >= world.currentMove.stepCost && world.schedule.moves.size() == 1){
                world.schedule.moves.remove(0);
                world.crane.isWorking = false;
            }
            if(world.currentMove.stepIndex >= world.currentMove.stepCost && world.schedule.moves.size() > 1){
                world.schedule.moves.remove(0);
            }

        }
        return world.schedule;
    }

    // Count the step cost of the move
    // Move between the buffer is not counted the horizontal time
    public static int stepCost(int sourceId, int targetId, boolean emptyMove){
        int cost = 0;
        if(sourceId == 0 && targetId >= 2){
            if(emptyMove == true){
                cost = 1;
            }
            if(emptyMove == false){
                cost = 5;
            }
        }
        if(sourceId == 0 && targetId == 1){
            if(emptyMove == true){
                cost = 2;
            }
            if(emptyMove == false){
                cost = 6;
            }
        }
        if(sourceId >= 2 && targetId == 0){
            if(emptyMove == true){
                cost = 1;
            }
            if(emptyMove == false){
                cost = 5;
            }
        }
        if(sourceId >= 2 && targetId == 1){
            if(emptyMove == true){
                cost = 1;
            }
            if(emptyMove == false){
                cost = 5;
            }
        }
        if(sourceId == 1 && targetId == 0){
            if(emptyMove == true){
                cost = 2;
            }
            if(emptyMove == false){
                cost = 6;
            }
        }
        if(sourceId == 1 && targetId >= 2){
            if(emptyMove == true){
                cost = 1;
            }
            if(emptyMove == false){
                cost = 5;
            }
        }
        if(sourceId == 0 && targetId == 0) {
            if (emptyMove == true) {
                cost = 0;
            }
            if (emptyMove == false) {
                cost = 2;
            }
        }
        if(sourceId == 1 && targetId == 1) {
            if (emptyMove == true) {
                cost = 0;
            }
            if (emptyMove == false) {
                cost = 2;
            }
        }
        if(sourceId >= 2 && targetId >= 2) {
            if (emptyMove == true) {
                cost = 1;
            }
            if (emptyMove == false) {
                cost = 5;
            }
        }
        return cost;
    }

    // Count the time cost of the move
    public static int stepCosttest(int srcId, int tgtId){
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
