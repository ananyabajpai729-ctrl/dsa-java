class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dirs = {{0,1} , {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) {
            return -1;
        }

        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        Queue<int[]> q = new LinkedList<>();

        q.offer(new int[]{0, 0});

        while(!q.isEmpty()){
            int[] entry = q.poll();
            int r= entry[0];
            int c = entry[1];

            for(int[]d : dirs){
                int nr = r + d[0];
                int nc = c + d[1];

                if(nr >= 0 && nr <n && nc >= 0 && nc <n && grid[nr][nc] == 0 && dist[nr][nc] > dist[r][c] + 1){
                    dist[nr][nc] = dist[r][c] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        return dist[n-1][n-1] == Integer.MAX_VALUE ? -1 : dist[n-1][n-1] + 1;
    }
}
