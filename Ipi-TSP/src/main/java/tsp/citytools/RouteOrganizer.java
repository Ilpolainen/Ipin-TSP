/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

/**
 * En jaksa enää kääntää suomeksi, Koita pärjäillä. Hyvää kesää!
 * 
 * A tool for RouteHandler for organizing an un-ordered Connection-Array to
 * route (route[a] = b stands for a->b). The Class is ment for RouteHandler
 * only, and requires input route which is a Hamilton-cycle.
 *
 * @author omistaja
 */
public class RouteOrganizer {

    private Connection[][] routeMap1;
    private Connection[][] routeMap2;

    public RouteOrganizer() {
        this.routeMap1 = null;
        this.routeMap2 = null;
    }

    /**
     * Converts Route presented as: Connection-objects in no particular order to
     * hamiltons cycle (if possible) presented as: route[a] = i, route[b] = i + 1 stands for a->b.
     * If the route isn't a hamiltons cycle, the method can behave irrationally.
     * O(n) (n stands for connection number). Analysis: mapping (O(n)) + new
     * n-array (O(n)) + for-loop (n-times) containing only O(1) - operations
     * (see searchFromMapOne or -Two). -> O(n) + O(n) + O(n) = O(n).
     *
     *
     * @param connections route presented as: Connection-objects in no
     * particular order
     * @return route presented as: route[a] = b stands for a->b.
     */
    public int[] convertConnectionsToRoute(Connection[] connections) {
        int size = connections.length;
        mapping(connections);
        int[] route = new int[size];
        int start = 0;
        int end = 0;
        for (int i = 0; i < size; i++) {
            start = end;
            Connection current = this.searchFromMap1(start);
            if (current != null) {
                end = current.getEnd().getNumber();
                route[start] = end;
            } else {
                current = this.searchFromMap2(start);
                if (current != null) {
                    end = current.getStart().getNumber();
                    route[start] = end;
                }
            }
        }
        return route;
    }

    /**
     * Maps Connection-List in two arrays so that every Connection is found from
     * each array. The first array contains all the connections indexed by their
     * startingCity number, and the the second array contains them indexed by
     * their endCity number. Because there may be Connections with wrong
     * direction, there can be two Connections with same start, so the arrays
     * have always two slots in each index. O(n) (n is the size of the
     * Connection List). Analysis: The method creates two n*2 arrays (2*O(2n) =
     * O(n)) + for loop goes to n containing only O(1) operations (O(n)). ->
     * O(n) + O(n) = O(n).
     *
     * @param connections Array containing the connections which are to be
     * mapped.
     */
    private void mapping(Connection[] connections) {
        int size = connections.length;
        this.routeMap1 = new Connection[size][2];
        this.routeMap2 = new Connection[size][2];
        for (Connection con : connections) {
            if (routeMap1[con.getStart().getNumber()][0] == null) {
                routeMap1[con.getStart().getNumber()][0] = con;
            } else {
                routeMap1[con.getStart().getNumber()][1] = con;
            }
            if (routeMap2[con.getEnd().getNumber()][0] == null) {
                routeMap2[con.getEnd().getNumber()][0] = con;
            } else {
                routeMap2[con.getEnd().getNumber()][1] = con;
            }
        }
    }

    /**
     * Searches if there exists a route from city-number-start in routeMapOne
     * and returns it. Returns null if not. O(1). Only Array-getters with
     * indexes and clearFromMapTwo O(1).
     *
     * @param start the number of the starting city of the route searched
     * @return a Connection from start to some other city. Returns null if not
     * found.
     */
    private Connection searchFromMap1(int start) {
        Connection current = null;
        if (routeMap1[start][0] != null) {
            current = routeMap1[start][0];
            routeMap1[start][0] = null;
        } else {
            if (routeMap1[start][1] != null) {
                current = routeMap1[start][1];
                routeMap1[start][1] = null;
            }
        }
        if (current != null) {
            this.clearFromMapTwo(current);
        }
        return current;
    }

    /**
     * Searches if there exists a route from city-number-start in routeMapTwo
     * and returns it. Returns null if not. O(1). Only Array-getters with
     * indexes and clearFromMapOne O(1).
     *
     * @param start the number of the starting city of the route searched
     * @return a Connection from start to some other city. Returns null if not
     * found.
     */
    private Connection searchFromMap2(int start) {
        Connection current = null;
        if (routeMap2[start][0] != null) {
            current = routeMap2[start][0];
            routeMap2[start][0] = null;
        } else {
            if (routeMap2[start][1] != null) {
                current = routeMap2[start][1];
                routeMap2[start][1] = null;
            }
        }
        if (current != null) {
            this.clearFromMapOne(current);
        }
        return current;
    }

    /**
     * Clears used (current) Connection from map two. O(1). Analysis: Only
     * getters and Array-index-getting.
     *
     * @param current the Connection to be erased
     */
    private void clearFromMapTwo(Connection current) {
        if (routeMap2[current.getEnd().getNumber()][0] != null) {
            if (routeMap2[current.getEnd().getNumber()][0].getStart().getNumber() == current.getStart().getNumber()) {
                routeMap2[current.getEnd().getNumber()][0] = null;
            }
        } else if (routeMap2[current.getEnd().getNumber()][1] != null) {
            if (routeMap2[current.getEnd().getNumber()][1].getStart().getNumber() == current.getStart().getNumber()) {
                routeMap2[current.getEnd().getNumber()][1] = null;
            }
        }
    }

    /**
     * Clears used (current) Connection from map one. O(1). Analysis: Only
     * getters and Array-index-getting.
     *
     * @param current the Connection to be erased.
     */
    private void clearFromMapOne(Connection current) {
        if (routeMap1[current.getStart().getNumber()][0] != null) {
            if (routeMap1[current.getStart().getNumber()][0].getEnd().getNumber() == current.getEnd().getNumber()) {
                routeMap1[current.getStart().getNumber()][0] = null;
            }
        } else if (routeMap1[current.getStart().getNumber()][1] != null) {
            if (routeMap1[current.getStart().getNumber()][1].getEnd().getNumber() == current.getEnd().getNumber()) {
                routeMap1[current.getStart().getNumber()][1] = null;
            }
        }
    }

    /**
     * to be erased
     */
    
    
    
    
}
