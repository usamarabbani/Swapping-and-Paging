
/**
 * Implementation of the least-frequently-used paging algorithm
 */
public class LFU extends Pager
{

    /**
     * @return index of the least frequently used process in the page table
     */
    public int getEvictionIndex()
    {
        int index = 0, min = 0, i = 0;
        for (Page p : getMemoryTable())
        {
            if (i == 0)
            {
                min = p.useCount;
                index = i;
            }
            // new min found, reset min
            else if (min > p.useCount)
            {
                min = p.useCount;
                index = i;
            }
            i++;
        }
        getMemoryTable().get(index).useCount = 0; // reset useCount after index found
        return index;
    }

}
