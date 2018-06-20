
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An abstract class to represent Paging Algorithms such as FIFO, LFU, MFU,
 * Random Pick, and LRU. Prints the memory reference number, memory contents at
 * the time, and any pages that are swapped in and out of the table, and keeps
 * track of the hit ratio and the number of page references
 */
public abstract class Pager
{
    public static final int memoryFrames = 4, diskFrames = 10, maxRefs = 100;
    private List<Page> disk, memory;
    private Queue<Page> memoryQueue;
    protected int pageRefs = 0;
    private int hits = 0;

    /**
     * Construct a Pager
     */
    public Pager()
    {
    	memory = new ArrayList<>(memoryFrames);
    	disk = new ArrayList<>(diskFrames);
        memoryQueue = new LinkedList<>();
    }

    /**
     * Reset the Pager's attributes
     */
    public void reset()
    {
    	pageRefs = 0;
        hits = 0;
    	disk = new ArrayList<>(diskFrames);
        memory = new ArrayList<>(memoryFrames);
        memoryQueue = new LinkedList<>();
    }

    /**
     * Get properties of the Pager
     * 
     * @return
     */
    public int getPageRefs()
    {
        return pageRefs;
    }

    public double getHitRatio()
    {
        return (double) hits / pageRefs;
    }

    public List<Page> getDiskTable()
    {
        return disk;
    }

    public List<Page> getMemoryTable()
    {
        return memory;
    }

    public Queue<Page> getmemoryQueue()
    {
        return memoryQueue;
    }

    /**
     * Simulate the paging algorithm
     */
    public void simulate()
    {
        PagingProcess proc = new PagingProcess(this);
        int pageNum;
        while (pageRefs < maxRefs)
        {
            System.out.println();
            // print memory contents every time a reference is made
            System.out.format("Ref %-4d| Memory: ", pageRefs + 1);
            for (Page p : memory)
                System.out.format("%d ", p.number);

            pageNum = proc.getNextPageNumber();
            System.out.format(" | Referencing Page %d.", pageNum);

            // update the status of a page if in memory
            if (proc.pages[pageNum].inMemory)
            {
            	++hits;
            	proc.pages[pageNum].previousRef = pageRefs;
            }
            else
            { // get the page from disk and evict a page if needed
                for (int i = 0; i < disk.size(); ++i)
                {
                    Page p = disk.get(i);
                    if (pageNum == p.number) // desired page found
                        swapPageIntoMemory(p);
                }
            }
            ++pageRefs;
            if (this instanceof MFU || this instanceof LFU)
            {
                for (Page p : memory)
                {
                    p.useCount++;
                }
            }
        }
    }

    /**
     * Swap a Page from the disk into memory
     * @param fromDisk the Page that came from the disk
     */
    private void swapPageIntoMemory(Page fromDisk)
    {
        int evictIndex = -1;
        fromDisk.inMemory = true;
        fromDisk.previousRef = pageRefs;

        // while memory is not full fill its available space
        if (memory.size() < memoryFrames)
        {
            memoryQueue.add(fromDisk);
            evictIndex = memory.size();
            memory.add(evictIndex, fromDisk);
        }
        else
        {
            // find a page in memory to replace with one from the disk
            evictIndex = getEvictionIndex();
            Page fromMem = memory.get(evictIndex);
            // reset Page's attributes to be evicted
            fromMem.previousRef = -1;
            fromMem.inMemory = false;
            fromMem.useCount = 0;
            memoryQueue.remove(fromMem);
            System.out.format(" Evicting Page %d.", fromMem.number);

            // move a page from memory to the disk
            for (int i = 0; i < disk.size(); ++i)
                if (disk.get(i).number == fromDisk.number)
                    disk.set(i, fromMem);

            // reset the memory with the page from the disk
            memory.set(evictIndex, fromDisk);
            memoryQueue.add(fromDisk);
        }
        System.out.format(" Swapping in Page %d.", fromDisk.number);
    }

    /**
     * @return index of the page to evict
     */
    public abstract int getEvictionIndex();
}