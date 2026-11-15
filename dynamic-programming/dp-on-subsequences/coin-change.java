class Solution {
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        for(int i = 0; i <= amount; i++ ){
            dp[0][i] = Integer.MAX_VALUE - 1;
        }
        for(int i = 0; i <= n ; i++){
            dp[i][0] = 0;
        }

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= amount; j++){
                int notTaken = dp[i - 1][j];
                int taken = Integer.MAX_VALUE - 1;
                if(coins[i-1] <= j){
                    taken = dp[i][j - coins[i - 1]] + 1;
                }
                dp[i][j] = Math.min(taken, notTaken);
            }
        }

        return dp[n][amount]>= Integer.MAX_VALUE - 1? -1: dp[n][amount];
    }
}
