
import java.util.LinkedList;

/**
 * Class representing the Next Fit algorithm searching memory for the next space
 * available to allocate starting from the last index the search previously
 * stopped at
 */
public class NextFit extends XFit
{

    public NextFit(LinkedList<SwappingProcess> p)
    {
        super(p);
    }

    /**
     * @return next fit empty block index and size in memory
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
                if (isFirst)
                {
                	tempIndex = i;
                	isFirst = false;
                }
                currentSize++;
            }
            else
            {
                if (currentSize > 0)
                {
                    emptyMemSize[tempIndex] = currentSize;
                }
                isFirst = true;
                currentSize = 0;
            }
        }
        if (currentSize > 0)
        {
            emptyMemSize[tempIndex] = currentSize;
        }
        isFirst = true;
        currentSize = 0;

        for (int i = 0; i < emptyMemSize.length; i++)
        {
            if (emptyMemSize[i] > 0)
            {
                emptyBlocks++;
            }
        }
        int blockIndex[] = new int[emptyBlocks];
        int blockSize[] = new int[emptyBlocks];
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

        if (firstTime)
        {
            firstTime = false;
            for (int i = 0; i < emptyBlocks; i++)
            {
                if (blockSize[i] >= processSize)
                {
                	stoppingIndex = blockIndex[i] + processSize;
                	result[0] = blockSize[i];
                    result[1] = blockIndex[i];
                    
                }
            }
        }

        inSecondHalf = false;

        for (int i = 0; i < emptyBlocks; i++)
        {
            if (processSize <= blockSize[i] && stoppingIndex <= blockIndex[i] )
            {
            	inSecondHalf = true;
            	stoppingIndex = blockIndex[i] + processSize;
            	result[0] = blockSize[i];
                result[1] = blockIndex[i];
            }
        }

        if (!inSecondHalf)
        {
            for (int i = 0; i < emptyBlocks; i++)
            {
                if (processSize <= blockSize[i] && stoppingIndex >= blockIndex[i])
                {
                	stoppingIndex = blockIndex[i] + processSize;
                	result[0] = blockSize[i];
                    result[1] = blockIndex[i];
                }
            }
        }

        return result;
    }
}