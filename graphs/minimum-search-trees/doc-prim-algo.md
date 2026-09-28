# Minimum Spanning Tree (Prim's Algorithm)

### Pattern

**Greedy + Priority Queue + Minimum Spanning Tree**

The goal is to connect all the vertices in a graph with the minimum possible total edge weight, without forming any cycles.

This is called a **Minimum Spanning Tree (MST)**.

## Intuition

I can think of this as building a tree one vertex at a time.

I start from any vertex and keep adding the cheapest edge that connects my current tree to a vertex I haven't visited yet.

For this, I use a priority queue that stores edges in increasing order of their weights.

At every step, I pick the cheapest available edge. If its destination is already visited, I skip it. Otherwise, I add that vertex to my MST and add the edge's weight to the total sum.

I keep doing this until the priority queue becomes empty.

## How the Priority Queue Works

I store the following information in the priority queue:

* Edge weight
* Node that the edge leads to
* Parent node (although my current implementation doesn't actually need this)

The priority queue sorts these entries by edge weight, so the cheapest edge is always at the top.

Initially, I add the starting node `0` with weight `0`.

The weight is zero because I don't need an edge to reach the starting node.

## Why Do I Need `vis[]`?

The visited array helps me make sure that every node is added to the MST only once.

When I remove an entry from the priority queue, I check whether its node is already visited.

If it is, I skip it because that node has already been included in the MST through a cheaper or equally cheap edge.

Otherwise, I mark it as visited and add the edge weight to my answer.

This also prevents cycles from being added to the MST.

## Main Steps

1. Start from node `0` and add it to the priority queue with weight `0`.
2. Remove the entry with the smallest edge weight.
3. If its node is already visited, skip it.
4. Otherwise, mark the node as visited and add the edge weight to `sum`.
5. Go through all its adjacent nodes.
6. Add the edges leading to unvisited nodes to the priority queue.
7. Repeat until the queue is empty.

Since the graph is undirected, the adjacency list contains each edge in both directions.

## Example

Consider this graph:

```text
       2
   0 ----- 1
   |       /|
  3|     1/ |4
   |     /  |
   2 ----- 3
       5
```

Start from node `0`.

Initially, the priority queue contains the starting node with weight `0`.

From node `0`, I can reach:

* Node `1` with weight `2`
* Node `2` with weight `3`

The priority queue picks the edge with weight `2` first, so I add node `1` to the MST.

From node `1`, I can reach node `3` with weight `1`. This is now the cheapest available edge, so I add node `3`.

Finally, I can connect node `2` through node `0` with weight `3`.

The MST uses these edges:

```text
0 --2-- 1
         |
         1
         |
         3

0 --3-- 2
```

The total weight is:

`2 + 1 + 3 = 6`

## Why Is This Greedy?

At every step, I choose the smallest available edge that connects the current tree to an unvisited node.

I don't need to consider every possible spanning tree. Prim's algorithm builds the MST by repeatedly making this locally cheapest choice.

The visited check ensures that I only add edges that bring a new vertex into the tree.

## Why Can I Skip Already Visited Nodes?

The priority queue may contain multiple edges leading to the same node.

For example, I might have added two different edges that both lead to node `3`.

Once node `3` is visited through the cheaper edge, there's no reason to add it again through another edge.

That's why I check `vis[node]` immediately after removing an entry from the queue.

## Complexity

Let:

* `V` = number of vertices
* `E` = number of edges

**Time:** `O(E log E)`

Each edge can be added to the priority queue, and inserting or removing an entry takes logarithmic time. This is commonly also expressed as `O(E log V)` for a connected graph.

**Space:** `O(V + E)`

The adjacency list takes `O(V + E)`, the visited array takes `O(V)`, and the priority queue can hold up to `O(E)` entries.

## Pattern Recognition

The clue is:

> Connect all vertices with the minimum total edge weight, without forming cycles.

Think:

**Minimum Spanning Tree**

Two common algorithms for this are Prim's and Kruskal's.

Prim's grows the tree from a starting node, repeatedly choosing the cheapest edge that reaches a new node.

Kruskal's sorts all edges and adds them one by one, skipping edges that would form a cycle.
