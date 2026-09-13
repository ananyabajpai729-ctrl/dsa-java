# Dijkstra's Algorithm

## Problem Statement

We are given a weighted, undirected graph with `V` vertices and some edges.

We are also given a source node `S`.

We need to find the shortest distance from `S` to every other node.

## Pattern

**Dijkstra's Algorithm**

This is useful for finding shortest paths in a **weighted graph**, as long as the edge weights are **non-negative**.

## Intuition

The main difference from the previous shortest path problems is that now the graph can have different weights, and it is not necessarily a DAG.

So BFS won't work here because it doesn't care about the total weight of a path.

The important idea in Dijkstra is:

> Always process the node whose currently known distance is the smallest.

That's why I use a `PriorityQueue`.

For example, if I currently know:

* node `1` → distance `10`
* node `2` → distance `4`
* node `3` → distance `7`

I want to process node `2` first because it has the smallest distance.

Then I check whether going through node `2` can give shorter paths to its neighbours.

## Approach

First, I create an adjacency list.

Since the graph is undirected, for an edge:

`u --w-- v`

I add:

`u → v` with weight `w`

and:

`v → u` with weight `w`

Then I create the `dist` array.

Initially, every distance is `Integer.MAX_VALUE` because I haven't reached those nodes yet.

The source has distance `0`:

`dist[S] = 0`

Then I put the source into the PriorityQueue.

The queue stores:

`{distance, node}`

So the node with the smallest distance comes out first.

### Processing a node

I take the smallest-distance node from the queue.

Suppose:

`dis = 5`

and the current node has an edge to `adjNode` with weight `3`.

Then going through the current node would give:

`5 + 3 = 8`

So I check:

`dis + weight < dist[adjNode]`

If it is smaller, I update the distance and put the new pair into the PriorityQueue.

This is the same relaxation idea I used in the DAG shortest path problem.

The difference is that here the **PriorityQueue decides which node should be processed next**.

## Dry Run

Consider:

```text
        4
    0 ------ 1
    |        |
   1|        |2
    |        |
    2 ------ 3
        1
```

Start:

`S = 0`

Initially:

`dist = [0, INF, INF, INF]`

PriorityQueue:

`[(0, 0)]`

### Process 0

From `0`:

`0 → 1` has weight `4`

So:

`dist[1] = 4`

`0 → 2` has weight `1`

So:

`dist[2] = 1`

Now:

`dist = [0, 4, 1, INF]`

The PriorityQueue puts node `2` first because its distance is `1`.

### Process 2

From `2` to `3`:

`1 + 1 = 2`

So:

`dist[3] = 2`

Now:

`dist = [0, 4, 1, 2]`

### Process 3

From `3` to `1` has weight `2`.

Going through `3` gives:

`2 + 2 = 4`

So `dist[1]` stays `4`.

Final answer:

`[0, 4, 1, 2]`

## Why PriorityQueue?

This is probably the most important thing to remember.

Suppose I have:

```text
0 → 1  (10)
0 → 2  (2)
2 → 1  (3)
```

After processing `0`:

`dist[1] = 10`

`dist[2] = 2`

If I used a normal queue, I might process `1` before `2`.

But the path:

`0 → 2 → 1`

has cost:

`2 + 3 = 5`

So `1` can still be improved.

With a PriorityQueue, node `2` comes first because its distance is `2`.

So I process:

`0`

↓

`2`

↓

improve `1` from `10` to `5`

This is why Dijkstra needs the smallest-distance node first.

## What Is Relaxation?

The condition:

`dis + weight < dist[adjNode]`

basically asks:

> "If I go to this neighbour through the current node, do I get a shorter path?"

If yes, update it.

So:

`new distance = current distance + edge weight`

and if that is better:

`update dist`

## One Important Condition

Dijkstra works when the edge weights are **non-negative**.

If negative edge weights are allowed, Dijkstra can fail because a node that looked like it already had the smallest distance could later be improved through a negative edge.

So the pattern to remember is:

**Weighted graph + non-negative weights → Dijkstra**

## Time Complexity

**O((V + E) log V)**

Building the adjacency list takes `O(V + E)`.

Each time a distance is improved, the node is added to the PriorityQueue, and queue operations take `O(log V)` in the usual complexity analysis.

So the overall complexity is:

`O((V + E) log V)`

## Space Complexity

**O(V + E)**

The adjacency list stores the graph, which takes `O(V + E)`.

The distance array takes `O(V)`.

The PriorityQueue can hold multiple entries, so its space can be `O(E)` in the worst case.

Overall:

`O(V + E)`

## Key Takeaway

The shortest-path patterns are starting to connect now:

**Unweighted graph:**

`BFS`

because every edge costs the same.

**Weighted DAG:**

`Topological Sort + Relaxation`

because the DAG gives us a safe processing order.

**Weighted graph with non-negative weights:**

`Dijkstra`

because we don't have a topological order, so we use a **PriorityQueue to always process the smallest known distance first**.

The main thing to remember for Dijkstra:

> **Take the closest node → try to improve its neighbours → repeat.**
