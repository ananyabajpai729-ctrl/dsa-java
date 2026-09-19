class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];

        for(int i = 0; i < n; i++){
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        for(int[]edge : edges){
            dist[edge[0]][edge[1]] = edge[2];
            dist[edge[1]][edge[0]] = edge[2];
        }

        for(int i = 0; i < n; i++){
            dist[i][i] = 0;
        }

        for(int v = 0; v < n; v++){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(dist[i][v] != Integer.MAX_VALUE && dist[v][j] != Integer.MAX_VALUE){
                        dist[i][j] = Math.min(dist[i][j], dist[i][v] + dist[v][j]);
                    }
                }
            }
        }

        int minCount = n;
        int ans = -1;

        for(int city = 0; city < n; city++){
            int count = 0;
            for(int adjCity = 0; adjCity < n; adjCity++){
                if(dist[city][adjCity] <= distanceThreshold){
                    count++;
                }
            }
            if(count <= minCount){
                minCount = count;
                ans = city;
            }
        }

        return ans;
    }
}
