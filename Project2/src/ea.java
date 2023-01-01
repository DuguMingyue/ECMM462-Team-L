import java.util.ArrayList;
import java.util.Random;

public class ea {

    //Evaluation of single individual
    public static int evaluation(int[] individual){
        int eva = 0;
        //Need to be done
        //Weight matrix of different evalutaion
        int[] weightMatrix = {1, 1, 1};
        return eva;
    }

    // initialize the population
    public static ArrayList<int[]> initialize(world world) {
        // generate random solutions
        ArrayList<int[]> rndSol = new ArrayList<>();
        if (world.nowblock.size()>0) {
            int t = 0;
            while (t < world.nowblock.size()*10) {
                Random random = new Random();
                int index = (int)(Math.random() * world.nowblock.size());
                int blockId = world.nowblock.get(index).id;
                int source = world.nowblock.get(index).position;
                int destination = random.nextInt(5); // stacks are 0,1,2,3,4
                world.currentMove.sourceId = source;
                world.currentMove.targetId = destination;
                world.currentMove.blockId = blockId;
                if (world.validMove(world)) {
                    int[] sol = new int[]{source,destination,blockId};
                    rndSol.add(sol);
                    t++;
                }
            }
        }
        return rndSol;
    }

    // Function to evaluate the fitness, the index of fitness and the index of population is the same
    public static ArrayList<Integer> fitness(world world, ArrayList<int[]> population){
        // a greater fitness score will indicate a higher priority to move, with the highest fitness being the best move
        ArrayList<Integer> fit = new ArrayList<>();
        int i = 0;
        // is the block ready ###
        while(i < population.size()){
            int score = 0;
            block currblock = null;
            for (block b : world.nowblock) {
                if (b.id == population.get(i)[2]) {
                    currblock = b;
                }
            }
            if (currblock==null) continue;

            boolean isHandover = false;
            if (currblock.ready) {
                score++;
                // is destination handover
                if (population.get(i)[1] == 1) {
                    isHandover = true;
                    score++;
                    // can it be put on handover in time
                    if (world.crane.horizonPosition == currblock.position) {
                        int stepCost = solver.stepCost(population.get(i)[0], population.get(i)[1], false);
                        if (world.timestamp + stepCost > currblock.due) {
                            score+=4;
                        } else {
                            score+=2;
                        }
                    } else {
                        int stepCost = solver.stepCost(world.crane.horizonPosition, population.get(i)[0], true)
                                + solver.stepCost(population.get(i)[0], population.get(i)[1], false);
                        if (world.timestamp + stepCost > currblock.due) {
                            score+=4;
                        } else {
                            score+=2;
                        }
                    }
                }
            }
            if (!isHandover) {
                switch (currblock.position) {
                    case 0:
                        for (block b : world.aristk.stack) {
                            if (b!=currblock && b.ready) {
                                if (!currblock.ready && population.get(i)[1] != 1 && population.get(i)[1] !=0) {
                                    score++;
                                }
                            }
                        }
                        break;
                    case 2:
                        for (block b : world.bufstk.stack.get(0)) {
                            if (b!=currblock && b.ready) {
                                if (!currblock.ready && population.get(i)[1] != 1 && population.get(i)[1] !=0) {
                                    if (getCurrentCapacity(population.get(i)[1], world) > 0) {
                                        score+=2;
                                    } else {
                                        score++;
                                    }
                                }
                            }
                        }
                        break;
                    case 3:
                        for (block b : world.bufstk.stack.get(1)) {
                            if (b!=currblock && b.ready) {
                                if (!currblock.ready && population.get(i)[1] != 1 && population.get(i)[1] !=0) {
                                    if (getCurrentCapacity(population.get(i)[1], world) > 0) {
                                        score+=2;
                                    } else {
                                        score++;
                                    }
                                }
                            }
                        }
                        break;
                    case 4:
                        for (block b : world.bufstk.stack.get(2)) {
                            if (b!=currblock && b.ready) {
                                if (!currblock.ready && population.get(i)[1] != 1 && population.get(i)[1] !=0) {
                                    if (getCurrentCapacity(population.get(i)[1], world) > 0) {
                                        score+=2;
                                    } else {
                                        score++;
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        // not sure
                }
            }
            if (population.get(i)[0] == 0
                    && getCurrentCapacity(0, world) <= 0
                    && population.get(i)[1] != 0
                    && ((!currblock.ready && population.get(i)[1] != 1) || (currblock.ready && population.get(i)[1] == 1))) {
                score+=2;
            }

            fit.set(i, score);
            i++;
        }
        return fit;
    }

    private static int getCurrentCapacity(int stackIndex, world world) {
        switch (stackIndex) {
            case 0:
                return world.aristk.capacity-world.aristk.stack.size();
            case 2:
                return world.bufstk.capacity-world.bufstk.stack.get(0).size();
            case 3:
                return world.bufstk.capacity-world.bufstk.stack.get(1).size();
            case 4:
                return world.bufstk.capacity-world.bufstk.stack.get(2).size();
            default:
                return 1;
        }
    }

    //Tounament selection, can adjust the amount of winner by changing time of the loop
    public static ArrayList<int[]> tournament(ArrayList<Integer> fit, ArrayList<int[]> population){
        ArrayList<int[]> winner = new ArrayList<>();
        int temp = 0;
        while(temp < 2) {
            Random r = new Random();
            int rand1 = r.nextInt(population.size() - 1);
            int rand2 = r.nextInt(population.size() - 1);
            if (fit.get(rand1) >= fit.get(rand2)) {
                winner.add(population.get(rand1));
            } else {
                winner.add(population.get(rand2));
            }
            temp++;
        }
        return winner;
    }

    //Mutation functions
    public static void mutation(ArrayList<int[]> children){
        double mutationRate = 0.1;
        Random r = new Random();
        int temp = 0;
        while(temp < children.size()){
            double rule = r.nextDouble();
            if(rule < mutationRate){
                // Change the destination, 0 arrive, 1,2,3 buffer, 4 handover
                children.get(temp)[1] = r.nextInt(5);
            }
            temp++;
        }
    }

    //Cross over functions
    public static void corssover(int[] ind1, int[] ind2){
        double crossRate = 0.1;
        Random r = new Random();
        double rule = r.nextDouble();
        //Switch the destination
        if(rule < crossRate){
            int temp = 0;
            temp = ind1[2];
            ind1[1] = ind2[1];
            ind2[1] = temp;
        }
    }

    // Replace the weakest one between children and population each roll
    // Input: population and children generated
    public static void weakestReplace(ArrayList<int[]> population, ArrayList<int[]> children){
        int temp0 = 0;
        while(temp0 < children.size()){
            int weakestIndex = 0;
            double weakestEva = evaluation(population.get(0));
            int temp1 = 0;
            while(temp1 < population.size() - 1){
                if(evaluation(population.get(temp1)) > evaluation(population.get(temp1 + 1))){
                    weakestIndex = temp1 + 1;
                    weakestEva = evaluation(population.get(temp1 + 1));
                }
                temp1++;
            }
            // If children is better than the weakest one in the population, then replace
            if(evaluation(children.get(temp0)) > weakestEva){
                population.set(weakestIndex, children.get(temp0));
            }
            temp0++;
        }
    }

}
