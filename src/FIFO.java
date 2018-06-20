
/**
 * Implementation of the first-in-first-out paging algorithm evicting pages in
 * the order they were placed in memory
 */
public class FIFO extends Pager
{

    /**
     * @return the index of the page placed into memory first if page is not in
     *         the table -1 is returned
     */
    public int getEvictionIndex()
    {
        int processNumber = getmemoryQueue().poll().number, i = 0;
        for (Page p : getMemoryTable())
        {
            if (p.number == processNumber)
                return i;
            ++i;
        }
        return -1; // should never reach this statement
    }
}
