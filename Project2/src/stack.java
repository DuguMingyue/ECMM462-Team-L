import java.util.ArrayList;

public class stack {
    ArrayList<block> stack = new ArrayList<>();
    int capacity = 1;
}

class bufferStack {
    ArrayList<ArrayList<block>> stack = new ArrayList<>();
    int bufferamount = 1;
    int capacity = 1;

    public bufferStack(int bufferamount){
        this.bufferamount = bufferamount;
        while(bufferamount-- > 0){
            ArrayList<block> newblock = new ArrayList<>();
            stack.add(newblock);
        }
    }
}

class arriveStack extends stack{

}

class handoverStack extends stack{

}