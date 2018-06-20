
/**
 * Implementation of the least-recently-used paging algorithm
 */
public class LRU extends Pager
{
    /**
     * @return index of the least recently used process in the page table
     */
    public int getEvictionIndex()
    {
        int oldest = -1, index = 0, i = 0;
        for (Page p : getMemoryTable())
        {
            // older process found so retrieve its index
            if (p.previousRef < oldest || oldest == -1)
            {
            	index = i;
            	oldest = p.previousRef;
            }
            ++i;
        }
        return index;
    }
}