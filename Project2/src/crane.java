import java.util.ArrayList;

public class crane {
    int id = 1; // In hot storage there is only 1 crane
    int LocationId = -1; // -1 Reperesent the arrive stack, initially the crane is at the position
    double GirderPosition = 5;
    double HoistPosition = 6;
    block Load = new block();
    ArrayList<block> Schedule = new ArrayList<>();
}

class craneMove {
    int BlockId = -1;
    int SourceId = -1;
    int TargetId = -1;
    int Sequence = -1;
    boolean EmptyMove = true;
}

class craneSchedule {
    craneMove Moves = new craneMove();
    int SequenceNr = 2;
}