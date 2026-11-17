class Solution {
    public int knapSack(int val[], int wt[], int capacity) {
        int n = val.length;
        int[][] dp = new int[n][capacity + 1];

        for(int curr = 0; curr <= capacity; curr++){
            int copies = curr/ wt[0];
            dp[0][curr] = copies * val[0];
        }

        for(int i = 1; i < n; i++){
            for(int curr = 1; curr <= capacity; curr++){
                int notTaken = dp[i -1][curr];

                int taken = Integer.MIN_VALUE;
                if(curr >= wt[i]){
                    int rem = dp[i][curr-wt[i]];
                    taken = rem+ val[i];
                }

                dp[i][curr] = Math.max(notTaken, taken);
            }
        }

        return dp[n-1][capacity];
    }
}
