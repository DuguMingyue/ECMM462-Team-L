import java.util.Scanner;
import java.util.Random;
// Parameters adjustment is in world.java and property.java
public class simulation {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        Random r = new Random();

        // Generate world
        world sim = new world();
        solver agent = new solver();
        System.out.println("Generate world success, start simulation");


        // Run simulation
        while(sim.overblock.size() != sim.blockamount){
            sim.timestamp++;

            // If timestamp mod arrive intervals = 0 and the arrive stack is still empty to use, then release new block
            if(sim.timestamp % sim.property.arriveInterval == 0){
                // Still have space and still have storage
                if(sim.aristk.stack.size() < sim.aristk.capacity && sim.blockpool.size() > 0){
                    // Randomly choose a new block to release
                    int index = r.nextInt(sim.blockpool.size());
                    sim.blockpool.get(index).release = sim.timestamp;
                    sim.aristk.stack.add(sim.blockpool.get(index));
                    sim.nowblock.add(sim.blockpool.get(index));
                    sim.blockpool.remove(index);
                }
                // Already full
                else{
                    sim.eva.BlockedArrivalTime++;
                }
            }

            // Use solver to give the move
            sim.schedule = solver.solve(sim);
            world.update(sim);

//            if(sim.schedule.moves.get(sim.schedule.moves.size() - 1).stepIndex == 5 || sim.schedule.moves.get(sim.schedule.moves.size() - 1).stepIndex == 6){
//                sim.aristk.stack.remove(sim.schedule.moves.get(sim.schedule.moves.size() - 1).indexId);
//                sim.hndstk.stack.add(sim.nowblock.get(sim.schedule.moves.get(sim.schedule.moves.size() - 1).indexId));
//                sim.nowblock.remove(sim.schedule.moves.get(sim.schedule.moves.size() - 1).indexId);
//
//            }
            System.out.println(sim.timestamp);
        }

        // Evaluations
        //System.out.println(sim.timestamp);
    }
}







