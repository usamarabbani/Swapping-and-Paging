
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * This program simulates the FIFO, MFU, LFU, LRU, and random pick paging
 * algorithms based on various conditions:
 * 
 * +Process select random references of pages +Processes and Disk both have ten
 * page frames +Physical memory has four page frames
 * 
 * Each algorithm is simulated five times with a hundred page references per
 * run. The hit ratio average of pages in memory is computed after each
 * algorithm run and final statistics are printed after all simulations are
 * finished. For each reference the page numbers of pages in memory, which page
 * to be swapped in and out, and which page was evicted are printed
 */
public class PagingSimulator
{
    public static final int NUM_RUNS = 5;

    private static class Simulator
    {
        private Pager page;
        private String pageStr;
        private double hitRatioSum;

        public Simulator(Pager pager, String pageString)
        {
            page = pager;
            pageStr = pageString;
            hitRatioSum = 0.0;
        }
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        PrintStream printStream = new PrintStream(
                new FileOutputStream(new File("PagingOutput.txt")));
        System.setOut(printStream);

        Simulator[] sims = new Simulator[] {
                new Simulator(new FIFO(), "First In First Out"),
                new Simulator(new RandomPick(), "Random Pick"),
                new Simulator(new LRU(), "Least Recently Used"),
                new Simulator(new MFU(), "Most Frequently Used"),
                new Simulator(new LFU(), "Least Frequently Used") };

        for (int i = 0; i < NUM_RUNS; ++i)
        {
            System.out.println("+----------------------------+");
            System.out.format("| Paging Simulation Run %d    |", i + 1);
            System.out.println();
            System.out.println("+----------------------------+");

            for (Simulator sim : sims)
            {
                System.out.println("--------------------------------");
                System.out.format("Using %s Algorithm", sim.pageStr);
                System.out.println();
                System.out.print("--------------------------------");

                sim.page.simulate();

                double hitRatio = sim.page.getHitRatio();
                sim.hitRatioSum += hitRatio;
                System.out.println();
                System.out.println();
                System.out.format("Hit Ratio: %f", hitRatio);
                System.out.println();
                System.out.println();

                sim.page.reset();
            }
        }
        System.out.println("--------------------------------");
        System.out.format("Average Hit Ratios over %d runs:", NUM_RUNS);
        System.out.println();
        System.out.println();

        for (Simulator sim : sims)
        {
            System.out.format("    %s: %f", sim.pageStr,
                    sim.hitRatioSum / NUM_RUNS);
            System.out.println();
        }
    }
}