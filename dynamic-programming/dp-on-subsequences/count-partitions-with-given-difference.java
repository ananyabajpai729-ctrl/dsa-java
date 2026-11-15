class Solution {
 public int countPartitions(int n, int diff, int[] arr) {
        int totalSum = 0;
        int modulo = 1000000007;
        for(int i = 0; i < n; i++){
            totalSum+= arr[i];
        }

        int remaining = totalSum - diff;
        if(remaining < 0 || remaining%2 != 0){
            return 0;
        }
        int target = remaining/2;
        int[][] dp = new int[n][target + 1];
        if(arr[0] == 0){
            dp[0][0] = 2;
        }else{
            dp[0][0] = 1;
        }
        if(arr[0] != 0 && arr[0] <= target){
            dp[0][arr[0]] = 1;
        }
        for(int i = 1; i < n; i++){
            for(int sum = 0; sum <= target; sum++){
                int notTaken = dp[i -1][sum];
                int taken  = 0;
                if(arr[i] <= sum){
                    taken = dp[i - 1][sum - arr[i]];
                }
                dp[i][sum] = (taken + notTaken) % modulo;
            }
        }

        return dp[n - 1][target];
    }
}
