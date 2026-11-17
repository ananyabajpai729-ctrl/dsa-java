class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int n  = nums.length;
        int totalSum = 0;
        for(int ele: nums){
            totalSum += ele;
        }
        if (Math.abs(target) > totalSum) {
            return 0;
        }
        int[][] dp = new int[n + 1][2* totalSum + 1];
        dp[0][totalSum] = 1;
        for(int i = 1; i <=n; i++){
            for(int sum = -totalSum; sum <= totalSum; sum++){
                if(sum - nums[i -1] >= -totalSum){
                    dp[i][totalSum + sum] += dp[i - 1][sum - nums[i -1] + totalSum];
                }
                if(sum + nums[i -1] <= totalSum){
                    dp[i][totalSum + sum] += dp[i -1][sum + nums[i -1] + totalSum];
                }
            }
        }
        return dp[n][target + totalSum];
    }
}
