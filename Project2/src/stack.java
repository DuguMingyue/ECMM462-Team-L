import java.util.ArrayList;
// Father class
public class stack {
    ArrayList<block> stack = new ArrayList<>();
    int capacity = -1;

}
// Buffer stack
class bufferStack {
    int bufferamount = 3;
    int capacity = 7;
    int totalCapacity = capacity * bufferamount;
    ArrayList<ArrayList<block>> stack = new ArrayList<>();

    public bufferStack(int bufferamount){
        this.bufferamount = bufferamount;
        while(bufferamount-- > 0){
            ArrayList<block> newblock = new ArrayList<>();
            stack.add(newblock);
        }
    }
}
// Arriving stack
class arriveStack extends stack{
    int capacity = 5;
}
// Handover stack
class handoverStack extends stack{
    int capacity = 1;
}