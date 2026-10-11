class Solution {
    public int frogJump(int[] heights) {
        if(heights.length == 1) return 0;
        if(heights.length == 0) return 0;
        int[] dp = new int[heights.length];
        dp[0] = 0;
        dp[1] = Math.abs(heights[1] - heights[0]);

        for(int i = 2; i < heights.length; i++){
            dp[i] = Math.min(dp[i - 2] + Math.abs(heights[i - 2] - heights[i]), dp[i - 1] + Math.abs(heights[i - 1] - heights[i]));
        }

        return dp[heights.length - 1];
    }
}
