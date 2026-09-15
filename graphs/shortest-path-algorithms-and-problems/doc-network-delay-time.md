# Network Delay Time

## Pattern

**Dijkstra's Algorithm**

This is a shortest path problem on a **weighted directed graph**.

The signal starts from node `k` and travels through the network. We need to find the minimum time required for every node to receive the signal.

Since the edge weights are positive and different paths can have different times, Dijkstra is a good fit.

---

# Intuition

Think of `k` as the starting point.

For every other node, I want to know:

> What is the shortest time in which the signal can reach this node?

So I maintain a `dist` array where:

`dist[i] = shortest time to reach node i`

Initially, I don't know the distance to any node, so everything is infinity.

The source node `k` has distance `0`.

---

# Approach

### 1. Build the graph

Each entry in `times` looks like:

`[u, v, w]`

which means:

`u → v` takes `w` units of time.

So I create a directed adjacency list.

---

### 2. Use a PriorityQueue

The PriorityQueue stores:

`(node, current shortest time)`

and keeps the node with the smallest time at the top.

This is the main idea of Dijkstra:

> Always process the currently known closest node first.

---

### 3. Relax the edges

Suppose I am currently at node `u` and it takes `currTime` to reach it.

For a neighbouring node `v` with edge weight `w`:

`newTime = currTime + w`

If this is smaller than the time currently stored for `v`, I update it.

So:

`dist[v] = currTime + w`

and put the new state into the PriorityQueue.

This is called **relaxation**.

---

# Why do we skip this?

`if (currTime > dist[u]) continue`

The PriorityQueue can contain the same node multiple times.

For example, I might first discover:

`u → 10`

and later discover:

`u → 5`

So the queue can contain both:

`(u, 10)`

and

`(u, 5)`

When `(u, 5)` is processed, the old `(u, 10)` entry is no longer useful.

Since `10 > dist[u]`, I simply skip it.

---

# Getting the Answer

After Dijkstra finishes, `dist[i]` contains the shortest time needed for the signal to reach node `i`.

But the question asks:

> How long until **all** nodes receive the signal?

So I need the **largest shortest distance**.

For example:

```text
Node       Shortest time
1          0
2          2
3          5
4          3
```

The last node to receive the signal is node `3`, at time `5`.

So the answer is:

`max(dist) = 5`

If even one node has distance `Integer.MAX_VALUE`, it means that node cannot be reached.

In that case, the answer is `-1`.

---

# Why Dijkstra?

This is a weighted graph and the edge weights represent travel time.

Normal BFS works when every edge has the same cost.

Here, one edge might take `2` units while another takes `10`.

So I need Dijkstra to always expand the currently cheapest known path.

---

# Dry Run

Suppose:

```text
1 → 2 : 1
1 → 3 : 4
2 → 3 : 2
```

and the signal starts at `1`.

Initially:

```text
dist[1] = 0
dist[2] = ∞
dist[3] = ∞
```

Start with:

```text
(1, 0)
```

Process node `1`:

```text
1 → 2 : 1
1 → 3 : 4
```

So:

```text
dist[2] = 1
dist[3] = 4
```

PriorityQueue:

```text
(2, 1)
(3, 4)
```

Now process node `2`.

Through node `2`:

```text
2 → 3 : 2
```

So reaching `3` through `2` takes:

`1 + 2 = 3`

which is better than `4`.

Therefore:

```text
dist[3] = 3
```

Final distances:

```text
[0, 1, 3]
```

The maximum is `3`, so the network delay is `3`.

---

# Complexity

Let:

* `V` = number of nodes
* `E` = number of edges

### Time

**O((V + E) log V)**

Building the graph takes `O(E)`.

Dijkstra processes nodes and edges using the PriorityQueue, giving the logarithmic factor.

### Space

**O(V + E)**

* Adjacency list → `O(V + E)`
* Distance array → `O(V)`
* PriorityQueue → up to `O(E)` entries because multiple entries for a node can exist

So overall:

**O(V + E)**

---

# Key Takeaway

The main pattern here is:

**Weighted graph + shortest path + non-negative edge weights → Dijkstra**

And for this particular problem:

**Network delay = maximum of all shortest distances from the source.**
