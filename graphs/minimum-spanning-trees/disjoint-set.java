class DisjointSet {
    private int[] parent;
    private int[] rank;
    private int[] size;
    public DisjointSet(int n) {
        parent = new int[n+1];
        rank = new int[n+1];
        size = new int[n+1];

        for(int i = 1; i <= n; i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    private int findPar(int node){
        if(parent[node] == node) return node;

        return parent[node] = findPar(parent[node]);
    }

    public boolean find(int u, int v) {
        return findPar(u) == findPar(v);
    }

    public void unionByRank(int u, int v) {
        int rootU = findPar(u);
        int rootV = findPar(v);

        if(rootU == rootV) return;

        if(rank[rootU] < rank[rootV]){
            parent[rootU] = rootV;
        }else if(rank[rootU] > rank[rootV]){
            parent[rootV] = rootU;
        }else{
            parent[rootV] = rootU;
            rank[rootU]++;
        }
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
