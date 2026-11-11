class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;

        int[][][] dp = new int[n+1][2][3];

        for(int day = n - 1; day >= 0; day--){
            for(int transactions = 1; transactions <=2; transactions++){
                int buy = -prices[day] + dp[day + 1][0][transactions];
                int skip = dp[day + 1][1][transactions];
                dp[day][1][transactions] = Math.max(buy, skip);

                int sell = prices[day] + dp[day+ 1][1][transactions-1];
                int hold = dp[day + 1][0][transactions];
                dp[day][0][transactions] = Math.max(sell, hold);
            }
        }

        return dp[0][1][2];
    }
}
