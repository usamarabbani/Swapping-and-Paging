import java.util.LinkedList;

/**
 * Class representing the Best Fit algorithm searching all of memory for the
 * smallest space available to allocate
 */
public class BestFit extends XFit
{
    /**
     * Constructs a new object to simulate the Best Fit algorithm
     * @param p the list of processes
     */
    public BestFit(LinkedList<SwappingProcess> p)
    {
        super(p);
    }

    /**
     * @return Best Fit empty block index and size in memory
     */
    public int[] findEmptyBlock(int processSize)
    {
        int emptyBlocks = 0, currentSize = 0, tempIndex = -1;
        int[] result = new int[2];
        boolean isFirst = true;

        // worst case is the size available is the size of memory
        int emptyMemSize[] = new int[memorySize];

        for (int i = 0; i < memorySize; i++)
        {
            if (mainMemory[i] == '.')
            {
                currentSize++;
                if (isFirst)
                {
                    isFirst = false;
                    tempIndex = i;
                }
            }
            else
            {
                if (currentSize > 0)
                {
                    emptyMemSize[tempIndex] = currentSize;
                }
                currentSize = 0;
                isFirst = true;
            }
        }

        if (currentSize > 0)
        {
            emptyMemSize[tempIndex] = currentSize;
        }
        currentSize = 0;
        isFirst = true;

        for (int i = 0; i < emptyMemSize.length; i++)
        {
            if (emptyMemSize[i] > 0)
            {
                emptyBlocks++;
            }
        }

        int blockSize[] = new int[emptyBlocks];
        int blockIndex[] = new int[emptyBlocks];
        int counter = 0;
        for (int i = 0; i < emptyMemSize.length; i++)
        {
            if (emptyMemSize[i] > 0)
            {
                blockSize[counter] = emptyMemSize[i];
                blockIndex[counter++] = i;
            }
        }
        emptyMemSize = null;

        int finalIndex = 0, finalSize = 0;
        isFirst = true;
        for (int i = 0; i < emptyBlocks; i++)
        {
            if (blockSize[i] >= processSize)
            {
                if (isFirst)
                {
                	isFirst = false;
                	finalSize = blockSize[i];
                	finalIndex = blockIndex[i];
                }
                else if (blockSize[i] < finalSize)
                {
                	finalSize = blockSize[i];
                	finalIndex = blockIndex[i];
                }
            }
        }

        result[0] = finalSize;
        result[1] = finalIndex;

        return result;
    }
}