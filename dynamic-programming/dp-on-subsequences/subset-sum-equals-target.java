class Solution {
    public boolean isSubsetSum(int[] arr, int target) {
        int n = arr.length;
        boolean[][] dp = new boolean[n][target + 1];
        for(int i = 0; i < n; i++){
            dp[i][0] = true;
        }
        if(arr[0] <= target){
            dp[0][arr[0]] = true;
        }

        for(int i = 1; i < n; i++){
            for(int sum = 1; sum <= target; sum++){
                boolean notTaken = dp[i-1][sum];

                boolean taken = false;
                if(arr[i] <= sum){
                    taken = dp[i - 1][sum - arr[i]];
                }

                dp[i][sum] = taken || notTaken;
            }
        }

        return dp[n - 1][target];
    }
}
