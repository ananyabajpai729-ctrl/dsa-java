# Floyd-Warshall Algorithm

### Pattern

**All-Pairs Shortest Path + Dynamic Programming**

Floyd-Warshall is used when I need the shortest distance **between every pair of vertices**.

Unlike Dijkstra, where I usually start from one source, here I want:

```text
0 → 1
0 → 2
1 → 0
1 → 2
2 → 0
2 → 1
...
```

So instead of running a shortest path algorithm separately from every node, Floyd-Warshall handles all of them together.

---

## Intuition

The main question is:

> Can going through some intermediate node give me a shorter path from `i` to `j`?

Suppose I want to go:

```text
i → j
```

I can either use the path I already know:

`i → j`

or I can try going through another node `via`:

```text
i → via → j
```

So I compare:

`matrix[i][j]`

with:

`matrix[i][via] + matrix[via][j]`

and keep the smaller one.

That's exactly what this line does:

`matrix[i][j] = Math.min(matrix[i][j], matrix[i][via] + matrix[via][j])`

---

## Why is `via` the outer loop?

This is the most important part of Floyd-Warshall.

I gradually allow more and more vertices to be used as intermediate nodes.

For example:

```text
via = 0
```

means:

> Try improving paths using node `0` as an intermediate node.

Then:

```text
via = 1
```

means:

> Now I can also use node `1` as an intermediate node.

Then node `2`, node `3`, and so on.

So after processing `via = k`, I have considered paths that can use vertices from `0` to `k` as intermediate vertices.

This is why `via` has to be the outer loop.

---

## Example

Suppose:

```text
0 → 1 = 5
1 → 2 = 3
0 → 2 = 10
```

Initially:

```text
dist(0,2) = 10
```

When `via = 1`, I check:

```text
0 → 1 → 2
```

Its cost is:

`5 + 3 = 8`

Since:

`8 < 10`

I update:

`matrix[0][2] = 8`

So Floyd-Warshall discovers that going through `1` gives a shorter path.

---

## Initial Setup

The input uses `-1` to represent that there is no direct edge.

But doing arithmetic with `-1` would be wrong.

So I first replace every `-1` with a very large value:

`1e9`

This basically means:

> There is currently no path between these two nodes.

Then I set:

`matrix[i][i] = 0`

because the distance from a node to itself is zero.

---

## Why Modify the Matrix Directly?

I don't create a separate `dist` matrix.

The input matrix itself becomes my distance matrix.

Initially it represents:

> Direct edge distances.

As Floyd-Warshall runs, it gradually becomes:

> Shortest distances between every pair.

So by the end, `matrix[i][j]` contains the shortest distance from `i` to `j`.

---

## Why Can We Update In-Place?

This is one of the nice things about Floyd-Warshall.

When processing a particular `via`, the matrix already contains the best distances found using previously processed intermediate vertices.

So I can directly update:

`matrix[i][j]`

instead of creating another matrix for every `via`.

---

## Converting `1e9` Back to `-1`

After Floyd-Warshall finishes, some pairs may still have distance `1e9`.

That means there is still no path between those nodes.

So I convert them back:

`1e9 → -1`

which restores the format expected by the problem.

---

## Negative Edges

Unlike Dijkstra, Floyd-Warshall can also work with negative edge weights, as long as there is no relevant negative cycle.

It can even be used to detect negative cycles.

A negative cycle exists if, after the algorithm, some:

`matrix[i][i] < 0`

because the shortest distance from a node to itself should normally be `0`.

---

## The Three Loops

The structure is:

```text
for every possible intermediate node
    for every starting node
        for every destination node
            try going through the intermediate node
```

In terms of the variables:

* `via` → intermediate node
* `i` → starting node
* `j` → destination node

The important formula is:

`dist[i][j] = min(dist[i][j], dist[i][via] + dist[via][j])`

---

## Complexity

There are three nested loops, each going through `n` nodes.

**Time:** `O(V³)`

where `V` is the number of vertices.

**Space:** `O(1)` auxiliary space because the matrix itself is modified in-place.

If counting the input matrix itself, the matrix occupies `O(V²)` space, but we don't create any additional matrix.

---

## Pattern Recognition

The main clue is:

> **I need shortest paths between every pair of nodes.**

Think:

**Floyd-Warshall**

And the core question is:

> "Is going through `via` better than the path I currently have?"

```text
i → j

vs.

i → via → j
```

So the entire algorithm boils down to:

`min(current distance, distance through via)`
