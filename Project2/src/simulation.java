import java.util.Random;

// Parameters adjustment is in world.java and property.java

public class simulation {
    public static void main(String args[]){
        Random r = new Random();

        // Generate world
        world sim = new world();
        // Generate agent
        solver agent = new solver();

        System.out.println("Generate world success, start simulation");

        // Run simulation
        while(sim.overblock.size() != sim.blockamount){
            sim.timestamp++;

            // Release the block
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

            // Update all the state in the world
            world.update(sim);

            System.out.println(sim.timestamp);
        }

        System.out.println("Simulation end, Evaluation starts");
        // Evaluations
        System.out.println(sim.eva.CraneManipulations);
        System.out.println(sim.eva.BlockedArrivalTime);
        int temp = 0;
        while(temp < sim.overblock.size()){
            if(sim.overblock.get(temp).overdue == true){
                sim.eva.DeliveredBlocks++;
            }
            temp++;
        }
        System.out.println(sim.eva.DeliveredBlocks);
        //System.out.println(sim.overblock);

    }
}







