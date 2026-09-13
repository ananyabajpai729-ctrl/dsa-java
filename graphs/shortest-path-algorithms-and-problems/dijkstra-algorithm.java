class Solution
{
    public  int[] dijkstra(int V, ArrayList<ArrayList<Integer>> edges, int S)
    {
       int[] dist = new int[V];
       Arrays.fill(dist, Integer.MAX_VALUE);
       dist[S] = 0;

       List<List<int[]>> adj = new ArrayList<>();
       
       for(int i = 0; i < V; i++){
        adj.add(new ArrayList<>());
       }
       for(int i = 0; i < edges.size(); i++){
        int u = edges.get(i).get(0);
        int v = edges.get(i).get(1);
        int w = edges.get(i).get(2);

        adj.get(u).add(new int[]{v, w});
        adj.get(v).add(new int[]{u, w});
       }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        pq.offer(new int[] {0, S});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int dis = curr[0];
            int node = curr[1];

            for(int[] edge: adj.get(node)){
                int adjNode = edge[0];
                int weight = edge[1];

                if(dis + weight < dist[adjNode]){
                    dist[adjNode] = dis + weight;
                    pq.offer(new int[]{dist[adjNode], adjNode});
                }
            }
        }

        return dist;
    }
}
