
/**
 * Implementation of the most-frequently-used paging algorithm
 */
public class MFU extends Pager
{

    /**
     * @return index of the most frequently used process in the page table
     */
    public int getEvictionIndex()
    {
        int index = 0, max = 0;
        for (Page p : getMemoryTable())
        {
            // new max found, reset max
            if (max < p.useCount)
            {
                max = p.useCount;
                index++;
            }
        }
        getMemoryTable().get(index).useCount = 0; // reset useCount after index found
        return index;
    }
}
