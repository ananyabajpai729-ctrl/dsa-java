class Solution{
    public int RodCutting(int price[], int n) {
        int[][] dp = new int[n][n+1];

        for(int i = 0; i <= n; i++){
            dp[0][i] = i* price[0];
        }

        for(int i = 1; i < n; i++){
            int piece= i+1;
            for(int curr = 0; curr <= n; curr++){
                int notTaken = dp[i -1][curr];

                int taken = Integer.MIN_VALUE;
                if(piece <= curr){
                    int rem = dp[i][curr- piece];
                    taken = rem + price[i];
                }

                dp[i][curr] = Math.max(taken, notTaken);
            }
        }

        return dp[n-1][n];
    }
}
