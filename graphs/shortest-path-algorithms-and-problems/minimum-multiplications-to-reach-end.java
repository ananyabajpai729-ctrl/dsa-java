class Solution {
    public int minimumMultiplications(int[] arr, int start, int end) {
       Queue<int[]> q = new LinkedList<>();
       q.offer(new int[]{ start, 0 });

       int[] dist = new int[100000];
       Arrays.fill(dist, Integer.MAX_VALUE);
       dist[start] = 0;
       int mod = 100000;

       while(!q.isEmpty()){
        int[] curr = q.poll();
        int node = curr[0];
        int steps = curr[1];

        for(int factor : arr){
            int num = (factor * node)%mod;

            if(steps + 1 < dist[num]){
                dist[num] = steps + 1;

                if(num == end) return steps + 1;

                q.offer(new int[]{num, steps + 1});
            }
        }
       }

       return -1;
    }
}
