package Network-Layer;

import java.util.*;

/**
 * Routing table for static routing.
 * It holds a list of static routes that can be queried to find the gateway which can reach the destination.
 */
class RoutingTable {
    public static int ERROR_NO_ROUTE = -1;

    public List<Integer>[] routes = new ArrayList[5];

    /**
     * Adds a route to the routing table.
     *
     * @param networkPrefix Network prefix
     * @param subnetMask    Subnet mask
     * @param gateway       Gateway
     */
    public void addRoute(int networkPrefix, int subnetMask, int gateway) {
        // TODO
        routes[0].add(networkPrefix, gateway);
    }

    /**
     * Queries the routing table to determine which gateway can reach the desired network the IP address belongs to.
     *
     * @param address The IP address we want to route to.
     * @return The gateway that can reach the desired network, or ERROR_NO_ROUTE when no route to that network can be found.
     */
    public int lookupRoute(int address) {
        // TODO
        for (int i = 0; i < routes.length; i++) {
            if (routes[i].get(i).equals(address)) {
                return routes[i].get(i + 1);
            }
        }
        return 0;
    }

    /**
     * IPv4 class to convert IP addresses to integers
     */
    static class IPv4 {
        public int toInt(String ipString) {
            String[] ipParts = ipString.split("\\.");
            int ipInt = 0b0;
            for (int i = 0; i < ipParts.length; i++) {
                ipInt += (Integer.parseInt(ipParts[i]) << (8 * (3 - i)));
            }
            return ipInt;
        }
    }
}

