class Solution {
    public int knapsack01(int[] wt, int[] val, int n, int W) {
        int[] dp = new int[W + 1];
        for(int i = wt[0]; i <= W; i++){
            dp[i] = val[0];
        }
        for(int i = 1; i < n; i++){
            for(int w = W; w>= 0; w--){
                int notTaken = dp[w];
                int taken = Integer.MIN_VALUE;
                if(wt[i] <= w){
                    taken = val[i] + dp[w- wt[i]]; 
                }

                dp[w] = Math.max(notTaken, taken);
            }
        }

        return dp[W];
    }
}
