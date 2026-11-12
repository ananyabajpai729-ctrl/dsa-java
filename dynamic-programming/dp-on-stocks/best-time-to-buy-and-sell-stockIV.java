class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][] next = new int[2][k + 1];

        for(int day = n - 1; day>=0; day--){
            int[][] curr = new int[2][k+1];
            for(int t = 1; t<=k; t++){
                int buy = -prices[day] + next[0][t];
                int skip = next[1][t];
                curr[1][t] = Math.max(buy, skip);

                int sell = prices[day] + next[1][t-1];
                int hold = next[0][t];
                curr[0][t]= Math.max(sell, hold); 
            }
            next = curr;
        }

        return next[1][k];
    }
}
