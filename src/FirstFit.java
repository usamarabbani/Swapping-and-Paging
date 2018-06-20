
import java.util.LinkedList;

/**
 * Class representing the First Fit algorithm searching memory for the first
 * space available to allocate
 */
public class FirstFit extends XFit
{

    public FirstFit(LinkedList<SwappingProcess> p)
    {
        super(p);
    }

    /**
     * @return First Fit empty block index and size in memory
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
        int ctr = 0;
        for (int i = 0; i < emptyMemSize.length; i++)
        {
            if (emptyMemSize[i] > 0)
            {
                blockSize[ctr] = emptyMemSize[i];
                blockIndex[ctr++] = i;
            }
        }
        emptyMemSize = null;

        for (int i = 0; i < blockSize.length; i++)
        {
            if (processSize <= blockSize[i])
            {
                result[0] = blockSize[i];
                result[1] = blockIndex[i];
                break;
            }
        }

        return result;
    }
}