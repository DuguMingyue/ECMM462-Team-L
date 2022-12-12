import java.util.ArrayList;

public class crane {
    int id = -1;
    int LocationId = -1; // -1 Reperesent the arrive stack, initially the crane is at the position
    double GirderPosition = 5;
    double HoistPosition = 6;
    block Load = new block();
    ArrayList<block> Schedule = new ArrayList<>();
}

