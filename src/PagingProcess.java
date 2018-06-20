import java.util.List;
import java.util.Random;

/**
 * Class represents a paging process in which pages randomly pick the following
 * reference while taking into account the probability of locality of reference
 * will be within 1 delta max
 */
public class PagingProcess
{
    static final double LOCAL_REF_PROB = .70;
    static final int NUM_PROCESS_PAGES = 10;
    int currentPage;
    Page[] pages;
    /**
     * Constructs a paging process by placing all pages onto the disk
     * @param pager a paging algorithm with loaded pages on memory and disk
     */
    public PagingProcess(Pager pager)
    {
        List<Page> disk = pager.getDiskTable();

        currentPage = -1;
        pages = new Page[NUM_PROCESS_PAGES];
        for (int i = 0; i < NUM_PROCESS_PAGES; ++i)
        {
            pages[i] = new Page(i);
            disk.add(i, pages[i]);
        }
    }

    /**
     * Gets the next page randomly with a locality of reference probability of
     * .7 that the next page will be within 1 delta max of the current page,
     * mutates the currentPage to the returned value
     * @return index of the next page to reference
     */
    public int getNextPageNumber()
    {
        Random r = new Random();

        // no reference so select a random page
        if (currentPage == -1)
            currentPage = r.nextInt(NUM_PROCESS_PAGES);
        else
        {
            int delta = r.nextInt(NUM_PROCESS_PAGES);
            // non local : delta of |i| > 1, range is [2,8]
            if (delta >= NUM_PROCESS_PAGES * LOCAL_REF_PROB)
            {
                delta = r.nextInt(7) + 2;
            }
            else // local reference : delta of |i| in range of [-1,1]
                delta = r.nextInt(3) - 1;

            currentPage = (currentPage + delta < 0) // mutate currentPage reference
                    ? NUM_PROCESS_PAGES - 1 + (currentPage + delta)
                    : (currentPage + delta) % NUM_PROCESS_PAGES; // wrap if needed
        }
        return currentPage;
    }
}
