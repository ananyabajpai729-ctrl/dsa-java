class DisjointSet {
    private int[] parent;
    private int[] size;
    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];

        for(int i = 1; i < n; i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int findPar(int node){
        if(parent[node] == node) return node;

        return parent[node] = findPar(parent[node]);
    }

    public void unionBySize(int u, int v) {
        int rootU = findPar(u);
        int rootV = findPar(v);

        if(rootU == rootV) return;

        if(size[rootU] < size[rootV]){
            parent[rootU] = rootV;
            size[rootV] += size[rootU];
        }else{
            parent[rootV] = rootU;
            size[rootU] += size[rootV];
        }
    }
}
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        DisjointSet ds = new DisjointSet(n);
        HashMap<String, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            for(int j = 1; j < accounts.get(i).size(); j++){
                String mail = accounts.get(i).get(j);
                if(!map.containsKey(mail)){
                    map.put(mail, i);
                }else{
                    ds.unionBySize(i, map.get(mail));
                }
            }
        }

        List<String>[] merged = new ArrayList[n];
        for(int i = 0; i <n; i++){
            merged[i] = new ArrayList<String>();
        }

        for(Map.Entry<String, Integer> it: map.entrySet()){
            String mail = it.getKey();
            int node = ds.findPar(it.getValue());
            merged[node].add(mail);
        }

        List<List<String>> ans = new ArrayList<>();

        for(int i = 0; i < n; i++){
            if(merged[i].isEmpty()) continue;
            Collections.sort(merged[i]);
            List<String> temp = new ArrayList<>();
            temp.add(accounts.get(i).get(0));
            temp.addAll(merged[i]);
            ans.add(temp);
        }

        return ans;
    }
}
