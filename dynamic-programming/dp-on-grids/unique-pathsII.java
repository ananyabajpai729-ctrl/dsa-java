class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        for(int i= m - 1; i >= 0; i--){
            for(int j = n - 1; j >= 0; j--){
                if(obstacleGrid[i][j] == 1){
                    dp[i][j] = 0;
                    continue;
                }

                if(i == m - 1 && j == n -1){
                    dp[i][j] = 1;
                    continue;
                }

                int downPaths = i + 1 < m? dp[i + 1][j] : 0;
                int rightPaths = j + 1 < n? dp[i][j + 1] : 0;

                dp[i][j] = downPaths + rightPaths;
            }
        }

        return dp[0][0];
    }
}
