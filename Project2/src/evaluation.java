import java.util.ArrayList;

public class evaluation {

    double BlockedArrivalTime = 0;
    double CraneManipulations = 0;
    double DeliveredBlocks = 0;

    // Other evaluation parameters not used
    double ServiceLevelMean = 0;
    double weightMatrix[] = new double[4]; // Weight matrix of above
    double LeadTimeMean = 0;
    int TotalBlocksOnTime = 0;
    double TardinessMean = 0;
    double BufferUtilizationMean = 0;
    double CraneUtilizationMean = 0;
    double HandoverUtilizationMean = 0;
    double UpstreamUtilizationMean = 0;

    public static int[] stepChoice(world world){
//        if(world.nowblock.size() > 0){
//          If the deliver intervals is over 1, need to do this
//        }
        ArrayList<int[]> population = ea.initialize(world);
        ArrayList<Integer> fitnessScores = ea.fitness(world, population);

        int evaluations = 0;
        while (evaluations < 1000) {
            //Tournament
            ArrayList<int[]> parent = ea.tournament(fitnessScores, population);
            //ArrayList<int[]> parentB = ea.tournament(fitnessScores, population);
            //Mutation
            ea.mutation(parent);
            //Crossover
            ea.corssover(parent.get(0), parent.get(1));
            //WeakestReplacement
            ea.weakestReplace(population, parent, fitnessScores);
            evaluations++;
        }

        //Find best fit individuals
        int index = 0;
        int score = 0;
        int temp = 0;
        while(temp < fitnessScores.size()){
            if(fitnessScores.get(temp) > score){
                score = fitnessScores.get(temp);
                index = temp;
            }
            temp++;
        }

        //Return the bestfit individual
        return population.get(index);
    }
}
