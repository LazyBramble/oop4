/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X координата данной локации. **/
    public int xCoord;

    /** Y координата данной локации. **/
    public int yCoord;

    /** Реализация метода equals() **/
	//@Override
    public boolean equals(Object obj) {
    if (obj instanceof Location) {
        // Cast another object to Location type,
        // then compare.  Return true if equal.
        Location other = (Location) obj;
        if (xCoord == other.xCoord && yCoord == other.yCoord) {
            return true;
        }
    }
       // If we got here then they're not equal.  Return false.
       return false;
   }
	
	//@Override
	public int hashCode() {
		
		return ((xCoord + 1) * 100 + yCoord); 
	}

    /** Создание новой локации со специальными координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создание новой локации с координатами (0.0). **/
    public Location()
    {
        this(0, 0);
    }
}