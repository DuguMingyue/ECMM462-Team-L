import java.util.ArrayList;

public class crane {
    int id = 1; // In hot storage there is only 1 crane
    int locationId = -1; // -1 Reperesent the arrive stack, initially the crane is at the position
    int verticalPosition = 1; //1 represent max height, 0 represent ground
    int horizonPosition = 0; // 0 represent arrive stack, 1 represent buffer stack, 2 represent handover stack, the question omit the move time between buffers
    block load = new block();
    boolean isWorking = false;

    // Change the state of crane with step
    public void craneState(crane crane, craneMove move, int step){
        if(move.stepCost == 5){
            if(step == 1 || step == 4){
                crane.verticalPosition = 0;
            }
            if(step == 2 || step == 5){
                crane.verticalPosition = 1;
            }
        }
        if(move.stepCost == 6){
            if(step == 1 || step == 5){
                crane.verticalPosition = 0;
            }
            if(step == 2 || step == 6){
                crane.verticalPosition = 1;
            }
        }
    }
}

// This is a queue for craneMove, capacity is 100, which means keep 100 moves
class craneSchedule {
    ArrayList<craneMove> moves = new ArrayList<>();
    int SeqNr = 0;
}

class craneMove {
    crane crane = new crane();
    int blockId = -1;
    int indexId = -1;
    int sourceId = -1;
    int targetId = -1;
    int stepIndex = 1;
    int stepCost = -1;
    int timeCost = -1;
    boolean emptyMove = true;
    boolean hangUpStart = false; //Is the crane hang up at the start point

    public craneMove(crane crane){
        this.crane = crane;
    }


}