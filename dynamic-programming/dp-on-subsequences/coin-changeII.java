class Solution {
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];

        for(int curr= 0; curr<= amount; curr++){
            if(curr % coins[0] == 0){
                dp[curr] = 1;
            }
        }

        for(int i = 1; i < n; i++){
            for(int curr=0;curr<=amount;curr++){
                int notTaken = dp[curr];

                int taken = 0;
                if(curr >= coins[i]){
                    taken = dp[curr - coins[i]];
                }

                dp[curr] = taken + notTaken;
            }
        }

        return dp[amount];
    }
}
