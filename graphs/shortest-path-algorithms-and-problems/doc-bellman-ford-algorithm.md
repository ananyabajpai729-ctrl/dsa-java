# Bellman-Ford Algorithm

### Pattern

**Shortest Path + Negative Edge Weights**

Bellman-Ford is a shortest path algorithm that can handle **negative edge weights** and can also detect a **negative weight cycle**.

---

## Intuition

The basic idea is pretty simple:

I repeatedly go through **every edge** and ask:

> Can I reach `v` with a shorter distance if I go through `u`?

For an edge:

`u → v` with weight `w`

the possible new distance is:

`dist[u] + w`

If this is smaller than the current `dist[v]`, I update it.

This is called **relaxation**.

---

## Why `V - 1` times?

A shortest path without a cycle can contain at most `V - 1` edges.

For example:

```text
0 → 1 → 2 → 3
```

There are 4 vertices and the path uses 3 edges.

If I had `V` or more edges in a path, some vertex would have to repeat, which means there is a cycle.

So I relax all the edges `V - 1` times.

Each round gives the shortest-distance information a chance to move one more edge forward.

---

## Relaxation

For every edge:

`u → v` with weight `w`

I check:

`dist[u] + w < dist[v]`

If true:

`dist[v] = dist[u] + w`

But before doing this, I check:

`dist[u] != Integer.MAX_VALUE`

This is important because `Integer.MAX_VALUE` represents an unreachable node.

I don't want to add a weight to it because that can cause integer overflow.

---

## Example

Suppose the graph has:

```text
0 → 1 = 4
0 → 2 = 5
1 → 2 = -2
```

Starting from `0`:

```text
dist[0] = 0
dist[1] = ∞
dist[2] = ∞
```

After processing `0`:

```text
dist[1] = 4
dist[2] = 5
```

Then I can use the edge:

```text
1 → 2 = -2
```

to get:

`dist[1] + (-2) = 4 - 2 = 2`

Since `2 < 5`:

`dist[2] = 2`

So the shorter path is:

`0 → 1 → 2`

---

## Detecting a Negative Cycle

This is the extra part of Bellman-Ford.

After doing `V - 1` rounds, I go through all the edges **one more time**.

Normally, nothing should improve anymore.

So if I still find:

`dist[u] + w < dist[v]`

then there is a reachable negative weight cycle.

For example:

```text
1 → 2 = 3
2 → 3 = -5
3 → 1 = 1
```

The total weight of the cycle is:

`3 + (-5) + 1 = -1`

Every time I travel around this cycle, the total distance becomes smaller.

So there is no fixed shortest distance.

That's why the extra iteration can tell me that a negative cycle exists.

---

## Why Check `dist[u] != Integer.MAX_VALUE`?

I use `Integer.MAX_VALUE` to represent:

> "I haven't reached this node from the source."

So I should only try to relax an edge if `u` is actually reachable.

This check appears both while relaxing edges and while checking for a negative cycle:

`dist[u] != Integer.MAX_VALUE`

This also prevents integer overflow.

---

## Why Bellman-Ford Instead of Dijkstra?

The main difference I want to remember is:

**Dijkstra**

* Works with non-negative edge weights
* Uses a priority queue
* Usually faster

**Bellman-Ford**

* Can handle negative edge weights
* Can detect negative weight cycles
* Repeatedly relaxes every edge

So when I see:

> **Shortest path + negative edge weights**

I should think of **Bellman-Ford**.

---

## One Small Optimization

My current implementation always performs all `V - 1` rounds.

I can make it slightly faster by keeping a flag like:

`updated`

If an entire round passes without updating even one distance, I can stop early.

That means all distances have already settled.

This is only an optimization — the current implementation is already correct.

---

## Complexity

Let:

* `V` = number of vertices
* `E` = number of edges

I go through all `E` edges `V - 1` times.

**Time:** `O(V × E)`

The extra pass for negative-cycle detection is `O(E)`, which doesn't change the overall complexity.

**Space:** `O(V)`

The main extra space used is the `dist` array.

---

## Pattern Recognition

The thing I want to remember is:

**Bellman-Ford = Relax every edge repeatedly.**

The main clue is:

> **Shortest path with negative edge weights or negative-cycle detection**

And the core operation is always:

`dist[u] + weight < dist[v]`

If it's smaller, update `dist[v]`.
