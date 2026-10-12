class Solution {
    public int ninjaTraining(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][4];

        dp[0][0] = Math.max(matrix[0][1], matrix[0][2]);
        dp[0][1] = Math.max(matrix[0][0], matrix[0][2]);
        dp[0][2] = Math.max(matrix[0][0], matrix[0][1]);
        dp[0][3] = Math.max(matrix[0][0], Math.max(matrix[0][1], matrix[0][2]));

        for(int day = 1; day< n; day++){
            for(int last = 0; last < 4; last++){
                for(int activity = 0; activity < 3; activity++){
                    if(activity != last){
                        int points = matrix[day][activity] + dp[day - 1][activity];
                        dp[day][last] = Math.max(points, dp[day][last]);
                    }
                }
            }
        }

        return dp[n - 1][3];
    }
}
