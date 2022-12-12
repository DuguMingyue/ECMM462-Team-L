import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
// Parameters adjustment is in world.java and property.java
public class simulation {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        Random r = new Random();

        // Generate world
        world sim = new world();
        property prop = new property();
        System.out.println("Generate world success, start simulation");

        // Run simulation
        while(sim.blockpool.size() > 0){
            sim.timestamp++;

            // If timestamp mod arrive intervals = 0 and the arrive stack is still empty to use, then release new block
            if(sim.timestamp % prop.arriveInterval == 0){
                // Still empty
                if(sim.aristk.stack.size() < sim.aristk.capacity){
                    // Randomly choose a new block to release
                    int index = r.nextInt(sim.blockpool.size());
                    sim.aristk.stack.add(sim.blockpool.get(index));
                    sim.blockpool.remove(index);
                }
                // Already full
                else{
                    sim.eva.BlockedArrivalTime++;
                }
            }

            // Move of the crane

        }
    }
}







