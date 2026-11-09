import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int n = arrival.length;
        // Min-heap to store active servers: {end_time, server_id}
        PriorityQueue<long[]> busyServers = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        
        // TreeSet to keep track of available server IDs
        TreeSet<Integer> availableServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            availableServers.add(i);
        }
        
        int[] requestCount = new int[k];
        
        for (int i = 0; i < n; i++) {
            long currentTime = arrival[i];
            long duration = load[i];
            
            // Free up servers that have finished their tasks by current arrival time
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= currentTime) {
                availableServers.add((int) busyServers.poll()[1]);
            }
            
            // If no servers are available, drop the request
            if (availableServers.isEmpty()) {
                continue;
            }
            
            // Find the first available server >= (i % k)
            Integer serverId = availableServers.ceiling(i % k);
            
            // If none found, wrap around to the first available server from 0
            if (serverId == null) {
                serverId = availableServers.first();
            }
            
            // Assign the request to this server
            availableServers.remove(serverId);
            busyServers.offer(new long[] { currentTime + duration, serverId });
            requestCount[serverId]++;
        }
        
        // Find the maximum requests handled by any server
        int maxRequests = 0;
        for (int count : requestCount) {
            maxRequests = Math.max(maxRequests, count);
        }
        
        // Collect all servers that handled the maximum number of requests
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}