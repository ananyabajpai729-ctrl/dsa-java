class Solution {
    public int spanningTree(int V, List<List<List<Integer>>> adj) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0], b[0]));
        int[] vis = new int[V];
        int sum = 0;

        pq.add(new int[]{0, 0, -1});

        while(!pq.isEmpty()){
            int[] entry = pq.poll();
            if(vis[entry[1]] == 1) continue;
            vis[entry[1]] = 1;
            sum += entry[0];
            for(int i = 0; i < adj.get(entry[1]).size(); i++){
                int edW = adj.get(entry[1]).get(i).get(1);
                int adjNode = adj.get(entry[1]).get(i).get(0);
                if(vis[adjNode] == 0){
                    pq.add(new int[]{edW, adjNode, entry[1]});
                }
            }
            
        }
        return sum;
    }
}

