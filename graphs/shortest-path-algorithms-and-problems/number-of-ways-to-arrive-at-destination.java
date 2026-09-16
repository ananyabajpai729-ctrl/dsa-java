import java.util.*;

class Solution {
    public int countPaths(int n, int[][] roads) {
        
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] r : roads) {
            int u = r[0];
            int v = r[1];
            int time = r[2];
            adj.get(u).add(new int[]{v, time});
            adj.get(v).add(new int[]{u, time}); 
        }

        
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        long[] ways = new long[n];
        ways[0] = 1;

        int mod = (int) (1e9 + 7);

        PriorityQueue<long[]> q = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        q.offer(new long[]{0, 0});

        while (!q.isEmpty()) {
            long[] entry = q.poll();
            int node = (int) entry[0];
            long dis = entry[1];


            if (dis > dist[node]) continue;

            for (int[] neighbour : adj.get(node)) {
                int adjNode = neighbour[0];
                long d = neighbour[1];

                // Case 1: Found a shorter path to adjNode
                if (dis + d < dist[adjNode]) {
                    dist[adjNode] = dis + d;
                    q.offer(new long[]{adjNode, dist[adjNode]});
                    ways[adjNode] = ways[node];
                } 
                // Case 2: Found another path of the exact same shortest length
                else if (dis + d == dist[adjNode]) {
                    ways[adjNode] = (ways[adjNode] + ways[node]) % mod;
                }
            }
        }

        return (int) (ways[n - 1] % mod);
    }
}
