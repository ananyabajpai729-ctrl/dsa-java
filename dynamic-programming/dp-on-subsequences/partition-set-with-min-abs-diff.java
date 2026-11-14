class Solution {
    public int minDifference(int[] arr, int n) {
        int totalSum = 0;
        for(int i = 0; i < n; i++){
            totalSum += arr[i];
        }
        int target = totalSum/2;

        boolean[][] dp = new boolean[n][target + 1];
        for(int i = 0; i < n; i++){
            dp[i][0] = true;
        }

        if(arr[0] <= target){
            dp[0][arr[0]] = true;
        }

        for(int i = 1; i < n ; i++){
            for(int sum = 1; sum <= target; sum++){
                boolean notTaken = dp[i - 1][sum];

                boolean taken = false;
                if(arr[i] <= sum){
                    taken = dp[i -1][sum - arr[i]];
                }

                dp[i][sum] = taken || notTaken;
            }
        }

        int s1 = 0;
        for(int i = target; i >= 0; i--){
            if(dp[n - 1][i] == true){
                s1 = i;
                break;
            }
        }
        int s2 = totalSum - s1;
        return Math.abs(s1- s2);
    }
}
