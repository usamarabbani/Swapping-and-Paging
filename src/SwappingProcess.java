/**
 * Class that represents a process that has a name, size, and a duration
 */

public class SwappingProcess
{
    char name;
    int size, duration;

    /**
     * Get and Set properties of a processes's name, size, and duration
     */
    public void setName(char aName)
    {
        name = aName;
    }

    public char getName()
    {
        return name;
    }

    public void setSize(int aSize)
    {
        size = aSize;
    }

    public int getSize()
    {
        return size;
    }

    public void setDuration(int aDuration)
    {
        duration = aDuration;
    }

    public int getDuration()
    {
        return duration;
    }

    /**
     * Override the toString method to print a processes's name, size and
     * duration
     */
    public String toString()
    {
        return String.format("Process: Name: %c Size: %2d Duration %2d", name,
                size, duration);
    }

    /**
     * Override the clone method to set a processes's properties
     */
    public SwappingProcess clone()
    {
        SwappingProcess p = new SwappingProcess();
        p.setName(name);
        p.setSize(size);
        p.setDuration(duration);
        return p;
    }
}
