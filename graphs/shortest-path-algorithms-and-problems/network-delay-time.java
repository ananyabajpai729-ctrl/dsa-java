class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for(int i = 0; i <= n; i++){
            adj.add(new ArrayList<>());
        }
        for(int[] t : times){
            adj.get(t[0]).add(new int[]{t[1], t[2]});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        q.add(new int[]{k, 0});

        while(!q.isEmpty()){
            int[] entry = q.poll();
            int u = entry[0];
            int currTime = entry[1];

            if (currTime > dist[u]) continue;
            for(int[] neighbour: adj.get(u)){
                int v = neighbour[0];
                int w = neighbour[1];
                
                if(currTime + w < dist[v]){
                    dist[v] = currTime + w;
                    q.offer(new int[]{v,  dist[v]});
                }
            }
        }
        int ans = Integer.MIN_VALUE;
        for(int i = 1; i <= n; i++){
            if(dist[i] == Integer.MAX_VALUE) return -1;
            if(dist[i] > ans) ans = dist[i];
        }

        return ans;
    }
}
