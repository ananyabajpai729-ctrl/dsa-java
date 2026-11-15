class Solution {
    public int perfectSum(int[] arr, int K) {
        int n = arr.length;
        int[][] dp = new int[n][K + 1];
        int modulo = 1000000007;
        for(int i = 0; i < n; i++){
            dp[i][0] = 1;
        }

        if(arr[0] <= K){
            dp[0][arr[0]] = 1;
        }

        for(int i = 1; i<n; i++){
            for(int sum = 1; sum <= K; sum++){
                int notTaken = dp[i -1][sum];
                int taken = 0;
                if(arr[i] <= sum){
                    taken = dp[i -1][sum - arr[i]];
                }
                dp[i][sum] = (taken + notTaken) %modulo;
            }
        }

        return dp[n - 1][K];
    }
}

