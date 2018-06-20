import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Class that simulates swapping swapping algorithms such as
 * First Fit, Next Fit, and Best Fit
 */

public class SwappingSimulator
{

    final static int[] sizes = { 5, 11, 17, 31 }; // possible sizes of a process
    static String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" // 62
            + "¡™£¢∞§¶•ªº–≠!@#$%^&*()+å∫ç∂´ƒ©˙ˆ∆˚¬µ˜øπœ®ß†¨√∑≈¥Ω{}:'<>?|/" // 58
            + "ÅıÇÎ´Ï˝ÓˆÔÒÂ˜Ø∏Œ‰Íˇ¨◊„˛Á¸€‹›‡°·‚±ÚÆ˘¿"; // 38 + 58 + 62 = 158
                                                        // possible processes;
    static char[] names = s.toCharArray();

    public static void main(String[] args) throws FileNotFoundException
    {

        PrintStream printStream = new PrintStream(
                new FileOutputStream(new File("SwappingOutput.txt")));
        System.setOut(printStream);

        int firstFitSwappedIn = 0;
        int nextFitSwappedIn = 0;
        int bestFitSwappedIn = 0;

        for (int i = 0; i < 5; i++)
        {
            LinkedList<SwappingProcess> process = generate();

            System.out.println();
            System.out.println("+------------------------------+");
            System.out.format("| Swapping Simulation Run %d    |", i + 1);
            System.out.println();
            System.out.println("+------------------------------+");
            System.out.println();

            for (SwappingProcess p : process)
            {
                System.out.println(p);
            }

            LinkedList<SwappingProcess> firstFitProcesses = new LinkedList<SwappingProcess>();
            for (SwappingProcess p : process)
            {
                firstFitProcesses.add(p.clone());
            }
            LinkedList<SwappingProcess> nextFitProcesses = new LinkedList<SwappingProcess>();
            for (SwappingProcess p : process)
            {
                nextFitProcesses.add(p.clone());
            }
            LinkedList<SwappingProcess> bestFitProcesses = new LinkedList<SwappingProcess>();
            for (SwappingProcess p : process)
            {
                bestFitProcesses.add(p.clone());
            }

            System.out.println();
            System.out.println();
            System.out.println(
                    "============================================"
                    + "==============================================");
            System.out.println();
            System.out.println("FIRST FIT ALGORITHM SIMULATION");
            System.out.println();
            XFit firstFit = new FirstFit(firstFitProcesses);
            firstFit.run();
            firstFitSwappedIn += firstFit.SwappedIn();

            System.out.println();
            System.out.println();
            System.out.println(
                    "============================================"
                    + "==============================================");
            System.out.println();
            System.out.println("NEXT FIT ALGORITHM SIMULATION");
            System.out.println();
            XFit nextFit = new NextFit(nextFitProcesses);
            nextFit.run();
            nextFitSwappedIn += nextFit.SwappedIn();

            System.out.println();
            System.out.println();
            System.out.println(
                    "============================================"
                    + "==============================================");
            System.out.println();
            System.out.println("BEST FIT ALGORITHM SIMULATION");
            System.out.println();
            XFit bestFit = new BestFit(bestFitProcesses);
            bestFit.run();
            bestFitSwappedIn += bestFit.SwappedIn();

            // add a line between each simulation
            System.out.println();
            System.out.println();
        }

        // Print the statistics of each algorithm
        System.out.println();
        System.out.println(
                "========================================================");
        System.out.println();
        System.out.println(
                "STATISTICS FOR AVERAGE PROCESSES SWAPPED IN OVER 5 RUNS");
        System.out.println();
        System.out.println("FIRST FIT: " + (firstFitSwappedIn / 5.0));
        System.out.println();
        System.out.println("NEXT FIT: " + (nextFitSwappedIn / 5.0));
        System.out.println();
        System.out.println("BEST FIT: " + (bestFitSwappedIn / 5.0));
    }

    /**
     * Generate a LinkedList of processes
     * 
     * @return a queue of processes
     */
    public static LinkedList<SwappingProcess> generate()
    {
        LinkedList<SwappingProcess> readyQueue = new LinkedList<SwappingProcess>();
        int counter = 0; // counter to iterate through the list of processes
        do
        {
            SwappingProcess p = new SwappingProcess();
            int sizeIndex = (int) (Math.random() * 4); // randomly select a
                                                       // processes' size
            int duration = (int) ((Math.random() * 5) + 1); // randomly select a
                                                            // processes' duration

            // set the process and add onto queue
            p.setSize(sizes[sizeIndex]);
            p.setDuration(duration);
            p.setName(names[counter++]);
            readyQueue.add(p);
        }
        while (counter < names.length);
        return readyQueue;
    }
}
