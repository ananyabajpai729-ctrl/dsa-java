class Solution {
    int[] parents;

    int find(int node) {
        if (parents[node] == node) {
            return node;
        }
        
        return parents[node] = find(parents[node]);
    }

    void connect(int root1, int root2) {
        parents[root2] = root1;
    }

    public int makeConnected(int n, int[][] connections) {
        
        if (connections.length < n - 1) {
            return -1;
        }

        parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        int extraEdges = 0;
        int components = n; 
        for (int[] c : connections) {
            int from = find(c[0]);
            int to = find(c[1]);

            if (from != to) {
                connect(from, to);
                components--; 
            } else {
                extraEdges++; 
            }
        }

        
        int edgesNeeded = components - 1;
        
        if (extraEdges >= edgesNeeded) {
            return edgesNeeded;
        } else {
            return -1;
        }
    }
}
