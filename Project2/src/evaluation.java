import java.util.ArrayList;

public class evaluation {
    double BlockedArrivalTime = 0;
    double CraneManipulations = 0;
    double DeliveredBlocks = 0;
    double ServiceLevelMean = 0;
    double weightMatrix[] = new double[4]; // Weight matrix of above

    double LeadTimeMean = 0;
    int TotalBlocksOnTime = 0;
    double TardinessMean = 0;
    double BufferUtilizationMean = 0;
    double CraneUtilizationMean = 0;
    double HandoverUtilizationMean = 0;
    double UpstreamUtilizationMean = 0;

    public int[] stepChoice(world world){
        ArrayList<int[]> population = ea.initialize(world);
        ArrayList<Integer> fitnessScores = ea.fitness(world, population);

        int evaluations = 0;
        while (evaluations < 1000) {

            ArrayList<int[]> parents = ea.tournament(fitnessScores, population);
            


            evaluations++;
        }

    }
}
