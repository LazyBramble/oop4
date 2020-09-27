/**
 * ���� ����� ������ ������� ���������, ����������� ��������� A* ��� ���������� a
 * ���� �� �����.  ��� ��������� �������� � ���� ����� "�������� ������� �����" �
 * ��� ���� ��������� "�������� ������� �����"."����� ����, ���� ����� ������������
 * �������� ��������, ����������� ��������� ������ ���� ��� ���������� ���
 * ���������.
 **/
 
 import java.util.*;
 
public class AStarState
{
    /** ��� ������ �� �����, �� ������� ������������ �������� A*. **/
    private Map2D map;

	private HashMap<Location, Waypoint> openVertex = new HashMap<Location, Waypoint>();
	private HashMap<Location, Waypoint> closeVertex = new HashMap<Location, Waypoint>();


    /**
     * ��������������� ����� ������ ��������� ��� ������������� ��������� ������ ���� A*.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** ���������� �����, �� ������� ������������ ��������� A* **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * ���� ����� ��������� ��� �������� ������� ����� � ���������� ������� �����.
     * � ������������ ������ ���������.  ���� ��� �������� ������� �����, ���� �����
     * ���������� <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint() {
		
		//System.out.println("getMinOpenWaypoint called!");
        if (openVertex.isEmpty()) return null;	
		
		float minCost = 3.4e+38f;
		Waypoint minCostObject = null;
		
		ArrayList<Waypoint> values = new ArrayList<Waypoint>(openVertex.values());
		for (Waypoint element : values) {
			if (element.getTotalCost() < minCost) {
				minCost = element.getTotalCost();
				minCostObject = element;
			}
		}
		
		//System.out.println("\tminWaypoint coords: " + minCostObject.getLocation().xCoord + ", " + minCostObject.getLocation().yCoord + ", cost = " + minCost);
		return minCostObject;
    }

    /**
     * ���� ����� ��������� ������� ����� � (��� ������������ ��������� ��� ������������
     * �) ��������� "�������� ������� �����".  ���� ��� ��� ���������
     * * ������� ����� � ����� �������������� ������� �����, �� ����� ������� ����� ������
     * ��������� � ���������.  ������, ���� ��� ������� ������� ����� ��
     * * ����� ������������ ������� �����, ����� ������� ����� �������� ������ ������
     * * ���� ����� �������� ������� ����� "���������� ���������" ������ ��������
     * * ������� �����" ���������� ��������� " ��������.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
		//System.out.println("addOpenWaypoint called!");

        // ��������� ���� ������ �� HashMap
		ArrayList<Location> locations = new ArrayList<Location>(openVertex.keySet());

        // ��������� Location ��������� Waypoint
		Location newLoc = newWP.getLocation();
		//System.out.println("\tnew waypoint coords: " + newLoc.xCoord + ", " + newLoc.yCoord);

        //�������� ���� ������ �� locations
		for (Location index : locations) {
			if (newLoc.equals(index)) {
                // ������ 2 - ��������� ����������, �.�. index == newLoc

                // ���� ��������� ���� �� newWP ������ ��������� ���� �� ������� � ����� �� Location - ��������
				Waypoint oldWP = openVertex.get(index);
				//System.out.println("\tthere is equal point: " + index.xCoord + ", " + index.yCoord);
				double oldCost = oldWP.getPreviousCost();
				double newCost = newWP.getPreviousCost();
				//System.out.println("\told cost: " + oldCost + ", new cost: " + newCost);
				
				if (newCost < oldCost) {
					openVertex.put(newLoc, newWP);
					return true;
				}

                // ���� ����� ������� �� �������
				return false;
				
			}
		}
		
		openVertex.put(newLoc, newWP);
		//System.out.println("\tnew point opened");
		return true;
    }


    /** ���������� ������� ���������� �������� ������� �����. **/
    public int numOpenWaypoints()
    {
        return openVertex.size();
    }


    /**
     * ���� ����� ���������� ������� ����� � ��������� ����� ��
     ** ��������� ������� � �������� ������.
     **/
    public void closeWaypoint(Location loc)
    {
		//System.out.println("Closing waypoint: " + loc.xCoord + ", " + loc.yCoord);
        Waypoint wp = openVertex.get(loc);
		openVertex.remove(loc);
		closeVertex.put(loc, wp);
    }

    /**
     * ���������� true, ���� ��������� �������� ������� ����� �������� ������� �����
     ** ��� ���������� �����.
     **/
    public boolean isLocationClosed(Location loc)
    {
       return openVertex.containsKey(loc);
    }
}