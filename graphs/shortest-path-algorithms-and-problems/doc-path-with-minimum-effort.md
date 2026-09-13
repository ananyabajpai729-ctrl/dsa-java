# Path With Minimum Effort

## Pattern

**Dijkstra's Algorithm + Grid**

This looks like a shortest path problem, but there is a small twist.

Usually in Dijkstra, the cost of a path is the **sum of all edge weights**.

Here, the cost of a path is the **maximum absolute height difference between two consecutive cells** along that path.

So instead of asking:

> What is the total cost of this path?

we are asking:

> What is the smallest possible maximum jump that I have to make on this path?

That is why we still need a priority queue, just like Dijkstra.

---

# Intuition

Suppose I take a path like:

`0 → 2 → 5 → 6`

The differences are:

* `|0 - 2| = 2`
* `|2 - 5| = 3`
* `|5 - 6| = 1`

The effort of this entire path is:

`max(2, 3, 1) = 3`

So even though some moves are small, the path's effort is determined by its **largest jump**.

Our goal is to find a path where this largest jump is as small as possible.

---

# The Main Difference From Normal Dijkstra

In normal Dijkstra, when moving from one node to another:

`new distance = current distance + edge weight`

But here:

`new effort = max(current effort, edge difference)`

Why?

Because the effort of a path is its **largest edge difference**.

For example:

Current path has effort `4`.

The next move has a height difference of `2`.

The new path's effort is:

`max(4, 2) = 4`

If the next move has a difference of `7`:

`max(4, 7) = 7`

So the new move only increases the path's effort if its difference is larger than everything we have seen so far.

---

# Approach

1. Treat every cell as a node in a graph.

2. From each cell, we can move:

   * up
   * down
   * left
   * right

3. Create a `dist` matrix.

   Here `dist[r][c]` means:

   > The minimum effort currently known to reach this cell.

4. Initially, every distance is infinity except the starting cell, whose effort is `0`.

5. Use a `PriorityQueue` so that the cell with the **smallest current effort** is processed first.

6. For every neighbouring cell:

   * Calculate the height difference between the current cell and neighbour.
   * Calculate the effort required to reach that neighbour.
   * If this effort is better than the previously known effort, update it and put the neighbour into the priority queue.

7. Once the destination cell is removed from the priority queue, return its effort.

---

# Small Example

Suppose we have:

`1 2 2`

`3 8 2`

`5 3 5`

Consider the path:

`1 → 2 → 2 → 2 → 5`

The height differences are:

`1, 0, 0, 3`

So the effort is:

`max(1, 0, 0, 3) = 3`

Another path might contain a jump of `7`.

Even if that path has fewer moves, its effort would be `7`, so it is worse.

This is why **number of moves is not what we are minimizing**.

We are minimizing the **largest jump**.

---

# Why PriorityQueue?

Imagine two cells are waiting to be processed:

* Cell A has effort `3`
* Cell B has effort `8`

We should process A first because it currently has a better path.

The priority queue keeps the smallest effort at the front.

This is the same basic idea as Dijkstra:

> Always expand the node with the smallest currently known cost.

The only difference is how we calculate that cost.

---

# Why Can We Return Immediately When We Reach the Destination?

This is an important Dijkstra idea.

Suppose the destination comes out of the priority queue with effort `5`.

Could there be some path later that reaches it with effort `3`?

No.

The priority queue always gives us the smallest current effort first.

So if the destination is now the smallest-effort cell available, any path that could reach it later would have to come through an effort of at least `5`.

Therefore, once the destination is popped from the priority queue, its effort is finalized.

That's why returning `diff` immediately is correct.

---

# The Relaxation Step

The important calculation is:

`newEffort = max(currentEffort, heightDifference)`

Then we check whether:

`newEffort < dist[neighbour]`

If yes, we found a better path to that neighbour.

For example:

Current effort = `4`

Height difference = `6`

So:

`newEffort = max(4, 6) = 6`

If the neighbour previously had effort `9`, we improve it to `6`.

---

# Difference From Normal Shortest Path

This is worth remembering because the two problems look very similar.

### Normal Dijkstra

Path:

`2 → 5 → 8`

Edge weights:

`3, 3`

Total distance:

`3 + 3 = 6`

So we minimize the **sum**.

### Minimum Effort Path

Same kind of path:

`2 → 5 → 8`

Height differences:

`3, 3`

Effort:

`max(3, 3) = 3`

So we minimize the **maximum**.

The algorithm is still Dijkstra-like because we can maintain the best known cost for each cell and always process the smallest one first.

---

# Why Not Normal BFS?

BFS works when every move has the same cost and we are minimizing the **number of moves**.

Here, different moves can have different height differences.

For example:

`difference = 1`

and another move might have:

`difference = 10`

So ordinary BFS cannot decide which path is better just from the number of levels.

We need the priority queue to prioritize the path with the smaller current effort.

---

# Complexity

Let the grid contain `m × n` cells.

There are `m × n` vertices and each cell has at most 4 edges.

### Time

\(O(mn \log(mn))\)

Each cell can be inserted into the priority queue when we find a better effort, and priority queue operations take logarithmic time.

### Space

\(O(mn)\)

We have:

* `dist` matrix → `O(mn)`
* adjacency is not explicitly stored
* priority queue → can contain many cells/entries

So overall it is `O(mn)` auxiliary space.

---

# Key Takeaway

The biggest thing I want to remember from this problem is:

> **Dijkstra does not always have to mean "sum of edge weights."**

The important idea is that we have:

* a graph/grid
* a cost associated with reaching each node
* a way to calculate the best cost of extending a path
* and we always process the currently smallest cost first

Here the path cost is:

**maximum height difference encountered on the path**

So the Dijkstra relaxation becomes:

`newEffort = max(currentEffort, edgeDifference)`

rather than the usual:

`newDistance = currentDistance + edgeWeight`

That is the key twist of this problem.
