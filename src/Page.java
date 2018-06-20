
/**
 * Class that represents a page in a page table keeping track of which page
 * number it is in the table, if it's being referenced and how frequently it is
 * used
 */
public class Page
{
    public boolean inMemory;
    public int number, previousRef, useCount;

    public Page(int pageNumber)
    {
        inMemory = false;
        number = pageNumber;
        previousRef = -1;
        useCount = 0;
    }
}
