import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Abstract class that simulates Best Fit, Next Fit, and First Fit algorithms
 * swapping processes in and out of memory
 */
public abstract class XFit
{
	private ArrayList<SwappingProcess> currentProcesses;
	private LinkedList<SwappingProcess> processes;
    protected int freeSpace, swappedIn, stoppingIndex = 0;
    private final int seconds = 60, minSize = 5;
    protected final int memorySize = 100;
    protected final char[] mainMemory = new char[memorySize];
    boolean firstTime = true, inSecondHalf;

    /**
     * Initialize a list in memory to prepare for simulation
     */
    public XFit(LinkedList<SwappingProcess> p)
    {
        processes = p;
        freeSpace = memorySize;
        swappedIn = 0;
        currentProcesses = new ArrayList<SwappingProcess>();
        
        for (int i = 0; i < memorySize; i++)
        {
            mainMemory[i] = '.';
        }
    }

    /**
     * @return the amount of processes swapped in to memory
     */
    public int SwappedIn()
    {
        return swappedIn;
    }

    /**
     * Swap a given process out of memory
     * @param p the process to be swapped out
     */
    private void swapOut(SwappingProcess p)
    {
    	int size = p.getSize(), i = 0;
    	char name = p.getName();
    	
        for (; i < memorySize; i++)
        {
            if (mainMemory[i] == name)
            {
                break;
            }
        }
        size += i;
        for (; i < size && i < memorySize; i++)
        {
            mainMemory[i] = '.';
        }
    }

    /**
     * Prints the current processes in memory
     */
    public void print()
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < memorySize; i++)
        {
            buffer.append(mainMemory[i]);
        }
        System.out.println(buffer.toString());
    }

    /**
     * Run the simulation
     */
    public void run()
    {
        for (int i = 0; i < seconds; i++)
        {
            int j = 0, tempDuration = 0;
            while (j < currentProcesses.size())
            {
                tempDuration = currentProcesses.get(j).getDuration() - 1;
                currentProcesses.get(j).setDuration(tempDuration);
                if (tempDuration == 0)
                {
                    SwappingProcess p = currentProcesses.remove(j);
                    freeSpace += p.getSize();
                    System.out.println(
                            String.format("time %2d: Swapped Out Process %s", i,
                                    p.getName()));
                    swapOut(p);
                    print();
                }
                else
                {
                    j++;
                }
            }
            j = 0;
            while (freeSpace >= minSize && j < processes.size())
            {
                int[] fitBlock = findEmptyBlock(processes.get(j).getSize());

                int count = fitBlock[1];
                if (fitBlock[0] >= processes.get(j).getSize())
                {
                    SwappingProcess p = processes.remove(j);
                    for (int k = 0; k < p.getSize(); k++)
                    {
                        mainMemory[count] = p.getName();
                        count++;
                    }
                    freeSpace -= p.getSize();
                    currentProcesses.add(p);
                    swappedIn++;
                    System.out.println(String.format(
                            "time %2d: Swapped In Process %s Size is %2d Duration is %d",
                            i, p.getName(), p.getSize(), p.getDuration()));
                    print();
                }
                else
                {
                    j++;
                }
            }
        }
    }

    /**
     * Abstract method implementing swapping algorithms
     * @param processSize the size of the process that needs an empty block
     * @return size and index of empty block in memory
     */
    public abstract int[] findEmptyBlock(int processSize);
}
