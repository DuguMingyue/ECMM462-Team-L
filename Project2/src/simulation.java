import java.util.ArrayList;
import java.util.Scanner;

public class simulation {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        // Input informations
        System.out.println("Please input the amount of the blocks");
        int blkamount = in.nextInt();
        System.out.println("Please input the amount of the buffer: ");
        int bufamount = in.nextInt();

        world sim = new world(blkamount, bufamount);
        System.out.println("Generate world success");

        while(sim.blockpool.size() > 0){

        }

    }
}







