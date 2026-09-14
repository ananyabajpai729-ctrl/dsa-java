class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<int[]>> adj = new ArrayList<>();

        for(int i = 0; i < n; i++){
            adj.add(new ArrayList<>());
        }

        for(int[] f : flights){
            adj.get(f[0]).add(new int[]{f[1], f[2]});
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, src, 0});

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        while(!q.isEmpty()){
            int[] entry = q.poll();
            int stops = entry[0];
            int node = entry[1];
            int fare = entry[2];
            if(stops > k) continue;
            for(int[] neighbour: adj.get(node)){
                int nextNode = neighbour[0];
                int nextFare = neighbour[1];

                if(fare + nextFare < dist[nextNode] && stops <= k){
                    q.offer(new int[]{stops + 1, nextNode, nextFare + fare});
                    dist[nextNode] = nextFare + fare;
                }
            }
        }

        if(dist[dst] == Integer.MAX_VALUE){
            return -1;
        }else{
            return dist[dst];
        }
    }
}
