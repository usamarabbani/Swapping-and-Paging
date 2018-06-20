import java.util.Random;

/**
 * Implementation of the random pick paging algorithm
 */
public class RandomPick extends Pager
{
    /**
     * @return a random page to evict in the page table
     */
    public int getEvictionIndex()
    {
        return new Random().nextInt(memoryFrames);
    }
}
