class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];

        for(int i = 0; i < n; i++){
            dp[m - 1][i] = matrix[m - 1][i];
        }

        for(int i = m -2; i >= 0; i--){
            for(int j = n -1; j >= 0; j--){
                int downLeft = 1000000;
                int downRight = 1000000;
                int down= dp[i + 1][j];

                if(j + 1 < n){
                    downRight = dp[i + 1][j +1];
                }
                if(j -1 >=0){
                    downLeft = dp[i + 1][j -1];
                }

                dp[i][j] = matrix[i][j] + Math.min(down, Math.min(downLeft, downRight));
            }
        }

        int answer= Integer.MAX_VALUE;

        for(int i = 0; i <n; i++){
            answer = Math.min(answer, dp[0][i]);
        }

        return answer;
    }
}
