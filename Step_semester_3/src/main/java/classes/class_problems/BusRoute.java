package classes.class_problems;

public class BusRoute implements Comparable<BusRoute> {
    private String routeCode;
    private String routeName;
    private int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 3);
    }

    public int compareTo(BusRoute other) {
        if (priority != other.priority) {
            return Integer.compare(other.priority, priority);
        }

        int codeCompare = routeCode.compareToIgnoreCase(other.routeCode);
        if (codeCompare != 0) {
            return codeCompare;
        }

        return routeName.compareToIgnoreCase(other.routeName);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) {
            return new BusRoute[0];
        }

        BusRoute[] result = routes.clone();

        for (int i = 0; i < result.length - 1; i++) {
            for (int j = 0; j < result.length - i - 1; j++) {
                if (result[j].compareTo(result[j + 1]) > 0) {
                    BusRoute temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }

        return result;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public int getPriority() {
        return priority;
    }
}
