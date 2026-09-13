class Solution {
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][] dist = new int[m][n];
        for(int[] row : dist){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        q.offer(new int[]{0, 0, 0});

        while(!q.isEmpty()){
            int[] entry = q.poll();
            int diff = entry[0];
            int r = entry[1];
            int c = entry[2];

            if(r == m-1 && c== n-1){
                return diff;
            }

            for(int[] d: dirs){
                int nr = r + d[0];
                int nc = c + d[1];

                if(nr >= 0 && nc >= 0 && nr < m && nc <n){
                    int newDiff = Math.max(diff, Math.abs(heights[r][c] - heights[nr][nc]));

                    if(newDiff < dist[nr][nc]){
                        dist[nr][nc] = newDiff;
                        q.offer(new int[]{newDiff, nr, nc});
                    }
                }
            }
        }
        return 0;
    }
}
