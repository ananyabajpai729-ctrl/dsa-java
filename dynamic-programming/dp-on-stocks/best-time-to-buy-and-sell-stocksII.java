class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        for(int day = n - 1; day>= 0; day--){
            int buy = -prices[day] + dp[day + 1][0];
            int skip = dp[day + 1][1];
            dp[day][1] = Math.max(buy, skip);

            int sell = prices[day] + dp[day + 1][1];
            int hold = dp[day + 1][0];
            dp[day][0] = Math.max(sell, hold);

        }

        return dp[0][1];
    }
}
