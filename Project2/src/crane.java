import java.util.ArrayList;

public class crane {
    int id = 1; // In hot storage there is only 1 crane
    int LocationId = -1; // -1 Reperesent the arrive stack, initially the crane is at the position
    int verticalPosition = 1; //1 represent max height, 0 represent ground
    int horizonPosition = 0; // 0 represent arrive stack, 1 represent handover stack, 2 represent buffer stack, the question omit the move time between buffers
    block Load = new block();
    ArrayList<block> Schedule = new ArrayList<>();
}

class craneSchedule {
    ArrayList<craneMove> Moves = new ArrayList<>();
    boolean isWorking = false;
    int SeqNr = 0;
}

class craneMove {
    crane crane = new crane();
    int BlockId = -1;
    int SourceId = -1;
    int TargetId = -1;
    int Sequence = -1;
    boolean emptyMove = true;
}