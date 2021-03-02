import java.util.*;
/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    public HashMap <Location, Waypoint> openVertex;
    public HashMap <Location, Waypoint> closedVertex;
    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        openVertex = new HashMap<>();
        closedVertex = new HashMap<>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0)
            return null;
        Iterator i = openVertex.values().iterator();
        Waypoint wp = (Waypoint)i.next();
        while (i.hasNext())
        {
            Waypoint w = (Waypoint)i.next();
            if (w.getTotalCost() < wp.getTotalCost())
            {
                wp = w;
            }
        }
        return wp;
        /* for (Waypoint w : openVertex.values()) */
    }

    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location newLoc = newWP.getLocation();
        Waypoint w = openVertex.get(newLoc);
        if (w == null || newWP.getPreviousCost() < w.getPreviousCost())
        {
            openVertex.put(newLoc, newWP);
            return true;
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openVertex.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint v = openVertex.remove(loc);
        closedVertex.put(loc,v);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closedVertex.containsKey(loc);
    }
}
