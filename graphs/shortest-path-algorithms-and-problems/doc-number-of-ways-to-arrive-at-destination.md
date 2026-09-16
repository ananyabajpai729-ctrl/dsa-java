# Number of Ways to Arrive at Destination

### Pattern

**Dijkstra + Counting Shortest Paths**

This is basically Dijkstra, but instead of only finding the shortest distance to the destination, I also need to find **how many different shortest paths** give that distance.

---

## Intuition

Normally in Dijkstra, I only keep:

`dist[i]` → shortest distance needed to reach node `i`.

Here I need one extra array:

`ways[i]` → number of shortest ways to reach node `i`.

So I maintain two things for every node:

* `dist[i]` = shortest time to reach `i`
* `ways[i]` = number of ways to reach `i` using that shortest time

The starting node is `0`, so:

* `dist[0] = 0`
* `ways[0] = 1`

There is one way to be at the starting node — we are already there.

---

## Why Dijkstra?

The roads have different travel times, so normal BFS cannot be used.

Since all road times are non-negative, Dijkstra works.

The graph is undirected, so every road is added in both directions.

---

## Main Idea

While running Dijkstra, suppose I am at `node` and I want to go to `adjNode`.

The new distance would be:

`dis + d`

where:

* `dis` = shortest distance currently used to reach `node`
* `d` = time taken by this road

Now there are two important cases.

### Case 1: Found a shorter path

If:

`newDistance < dist[adjNode]`

then I have found a new shortest distance.

So I update:

* `dist[adjNode]` to the new smaller distance
* `ways[adjNode]` to `ways[node]`

Why replace `ways`?

Because the previous ways to reach `adjNode` were using a longer distance, so they are no longer shortest paths.

---

### Case 2: Found another shortest path

If:

`newDistance == dist[adjNode]`

then the distance is exactly the same as the shortest distance I already knew.

But this is a **different path**, so I need to count it too.

So:

`ways[adjNode] += ways[node]`

For example, if there were already 2 shortest ways to reach `adjNode`, and I found 3 more through the current node:

`ways[adjNode] = 2 + 3 = 5`

Since the answer can become very large, everything is taken modulo `10^9 + 7`.

---

## Small Example

Suppose there are two equally short ways from `0` to `3`:

```text
      1
0 --------> 1
|           |
|           |
1           1
|           |
v           v
2 --------> 3
      1
```

Both paths take the same total time:

```text
0 → 1 → 3
0 → 2 → 3
```

So eventually:

`dist[3]` = shortest time

and

`ways[3]` = `2`

The `dist` array finds the shortest distance, while `ways` counts how many paths achieve it.

---

## One More Important Part

The priority queue can contain old entries.

For example, I might first put:

`(node = 2, distance = 10)`

into the queue.

Later I discover a better route:

`(node = 2, distance = 5)`

The old `(2, 10)` entry does not automatically disappear from Java's `PriorityQueue`.

So when it is eventually popped:

`dis = 10`

but:

`dist[2] = 5`

That means this queue entry is outdated.

So:

`if (dis > dist[node]) continue`

just skips it.

In simple words:

> If the distance stored in the queue is worse than the best distance I currently know, ignore that entry.

---

## Why `long`?

The distances can become larger than what an `int` can safely hold for the given constraints.

So `dist` and the values inside the priority queue are stored as `long`.

The final answer is returned as an `int` after taking modulo `10^9 + 7`.

---

## Complexity

Let:

* `V` = number of nodes
* `E` = number of roads

Dijkstra with a priority queue takes:

**Time:** `O((V + E) log V)`

Each edge can cause a relaxation, and priority queue operations take logarithmic time.

**Space:** `O(V + E)`

* adjacency list → `O(V + E)`
* `dist` → `O(V)`
* `ways` → `O(V)`
* priority queue → can contain multiple entries, bounded within the usual Dijkstra analysis

---

## Pattern Recognition

The clue here is:

> **"Find the shortest path, but also count how many shortest paths exist."**

That immediately makes me think:

**Dijkstra + another array for counting ways.**

So instead of just asking:

> "What is the shortest distance?"

I ask two questions:

> "What is the shortest distance?" → `dist`

> "How many ways achieve that distance?" → `ways`
