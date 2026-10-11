class Solution {
    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        int[] dp = new int[n];
        for(int i = 1; i < n; i++){
            int minEnergy = Integer.MAX_VALUE;
            for(int j = 1; j <=k ; j++){
                int prevIndex = i - j;
                if(prevIndex >= 0){
                    int diff = Math.abs(heights[i] - heights[prevIndex]);
                    int energy = diff + dp[prevIndex];
                    minEnergy = Math.min(minEnergy, energy);
                }
            }
            dp[i] = minEnergy;
        }

        return dp[n - 1];
    }
}
