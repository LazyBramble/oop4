/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A* для вычисления a
 * путь по карте.  Это состояние включает в себя набор "открытых путевых точек" и
 * еще одна коллекция "закрытых путевых точек"."Кроме того, этот класс обеспечивает
 * основные операции, необходимые алгоритму поиска пути для выполнения его
 * обработки.
 **/
 
 import java.util.*;
 
public class AStarState
{
    /** Это ссылка на карту, по которой перемещается алгоритм A*. **/
    private Map2D map;

	private HashMap<Location, Waypoint> openVertex = new HashMap<Location, Waypoint>();
	private HashMap<Location, Waypoint> closeVertex = new HashMap<Location, Waypoint>();


    /**
     * Инициализируйте новый объект состояния для использования алгоритма поиска пути A*.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Возвращает карту, по которой перемещается навигатор A* **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку.
     * с минимальными общими затратами.  Если нет открытых путевых точек, этот метод
     * возвращает <code>null</code>.
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
     * Этот метод добавляет путевую точку К (или потенциально обновляет уже существующую
     * в) Коллекция "открытые путевые точки".  Если еще нет открытого
     * * путевая точка в новом местоположении путевых точек, то новая путевая точка просто
     * добавлено в коллекцию.  Однако, если уже имеется путевая точка на
     * * новое расположение путевых точек, новая путевая точка заменяет только старую
     * * если новое значение путевых точек "предыдущая стоимость" меньше текущего
     * * путевые точки" предыдущая стоимость " значение.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
		//System.out.println("addOpenWaypoint called!");

        // Получение всех ключей из HashMap
		ArrayList<Location> locations = new ArrayList<Location>(openVertex.keySet());

        // Получение Location входящего Waypoint
		Location newLoc = newWP.getLocation();
		//System.out.println("\tnew waypoint coords: " + newLoc.xCoord + ", " + newLoc.yCoord);

        //Просмотр всех ключей из locations
		for (Location index : locations) {
			if (newLoc.equals(index)) {
                // Стадия 2 - сравнение стоимостей, т.к. index == newLoc

                // Если стоимость пути до newWP меньше стоимости пути до вершины с такой же Location - заменяем
				Waypoint oldWP = openVertex.get(index);
				//System.out.println("\tthere is equal point: " + index.xCoord + ", " + index.yCoord);
				double oldCost = oldWP.getPreviousCost();
				double newCost = newWP.getPreviousCost();
				//System.out.println("\told cost: " + oldCost + ", new cost: " + newCost);
				
				if (newCost < oldCost) {
					openVertex.put(newLoc, newWP);
					return true;
				}

                // Если новая вершина не подошла
				return false;
				
			}
		}
		
		openVertex.put(newLoc, newWP);
		//System.out.println("\tnew point opened");
		return true;
    }


    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints()
    {
        return openVertex.size();
    }


    /**
     * Этот метод перемещает путевую точку в указанное место из
     ** открытого списока в закрытый список.
     **/
    public void closeWaypoint(Location loc)
    {
		//System.out.println("Closing waypoint: " + loc.xCoord + ", " + loc.yCoord);
        Waypoint wp = openVertex.get(loc);
		openVertex.remove(loc);
		closeVertex.put(loc, wp);
    }

    /**
     * Возвращает true, если коллекция закрытых путевых точек содержит путевую точку
     ** для указанного места.
     **/
    public boolean isLocationClosed(Location loc)
    {
       return openVertex.containsKey(loc);
    }
}