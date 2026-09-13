# Shortest Path in Binary Matrix

## Pattern

**BFS + Shortest Path on a Grid**

The clue here is that every move from one cell to another costs exactly **1 step**.

So this is basically an unweighted graph where:

* each cell is a node
* each valid neighbouring cell is an edge
* every edge has weight 1

And for shortest paths with equal edge weights, **BFS** is the natural choice.

---

# Intuition

We have to find the shortest path from the top-left cell to the bottom-right cell.

A cell can move in **8 directions**:

* up
* down
* left
* right
* four diagonals

We start BFS from `(0, 0)`.

Since BFS explores things level by level, the first time we find a shorter distance to a cell, we can store that distance.

I use a `dist` matrix where `dist[r][c]` stores the minimum number of **moves** needed to reach that cell.

The starting cell has distance `0`.

Then whenever I move to a valid neighbouring cell:

`new distance = current distance + 1`

At the end, the problem wants the number of **cells** in the path, not the number of moves.

So if the destination has distance `d`, the answer is `d + 1`.

---

# Approach

1. First check whether the starting or ending cell is blocked.

   * If either is `1`, there is no possible path, so return `-1`.

2. Create the 8 possible directions.

3. Create a `dist` matrix and initialize everything to `Integer.MAX_VALUE`.

4. Set the starting cell's distance to `0`.

5. Put `(0, 0)` into the BFS queue.

6. While the queue is not empty:

   * Take the current cell.
   * Try all 8 directions.
   * Check that the new cell is inside the grid and is open (`0`).
   * If reaching this cell through the current cell gives a smaller distance, update it and add the cell to the queue.

7. After BFS:

   * If the destination still has distance `Integer.MAX_VALUE`, it was never reached → return `-1`.
   * Otherwise return its distance + `1`.

---

# Why BFS?

Suppose we have:

`start → A → B → destination`

Each move costs exactly `1`.

BFS explores:

* distance 0 cells
* then distance 1 cells
* then distance 2 cells
* and so on

So it naturally finds the shortest number of moves.

This is the same reason BFS works for an ordinary unweighted graph.

---

# Small Dry Run

Suppose:

`0 1 0`
`0 0 0`
`1 0 0`

We start at `(0,0)`.

From `(0,0)`, we can move to `(1,0)`.

From `(1,0)`, we can move diagonally/right to other cells.

Eventually we reach `(2,2)`.

If the shortest route takes `3` moves:

`(0,0) → (1,0) → (2,1) → (2,2)`

then:

* distance = `3`
* number of cells in path = `3 + 1 = 4`

So the answer is `4`.

---

# Why do we use 8 directions?

Unlike normal grid problems where we only move up, down, left and right, this problem also allows diagonal movement.

So the possible directions are:

* `(0,1)` → right
* `(0,-1)` → left
* `(1,0)` → down
* `(-1,0)` → up
* `(1,1)` → down-right
* `(-1,-1)` → up-left
* `(1,-1)` → down-left
* `(-1,1)` → up-right

This is an important thing to notice from the problem statement.

---

# Why use a distance matrix?

I could also mark cells as visited, but here I am directly storing the shortest distance to every cell.

For example:

`dist[r][c] = 5`

means I can reach that cell in 5 moves from the starting cell.

The condition that I use while exploring is basically:

`if new distance < old distance`

then update it.

This makes sure we only add a cell to the queue when we have found a better way to reach it.

---

# Edge Cases

### Starting cell is blocked

If `grid[0][0] == 1`, there is no path.

### Destination is blocked

If `grid[n-1][n-1] == 1`, there is no path.

### Single-cell grid

If the grid is just:

`[0]`

then the answer is `1`.

There are zero moves, but the path contains one cell.

### No possible path

If the destination remains at `Integer.MAX_VALUE`, return `-1`.

---

# Complexity

Let the grid be `n × n`.

### Time

\(O(n^2)\)

In the worst case, every cell is processed, and each cell checks 8 directions.

Since 8 is constant, the overall time is `O(n²)`.

### Space

\(O(n^2)\)

The `dist` matrix itself takes `O(n²)` space, and the BFS queue can also contain `O(n²)` cells in the worst case.

---

# Key Takeaway

The main pattern to remember is:

> **Shortest path + grid + every move costs the same → think BFS.**

Here the only extra thing to notice is that movement is allowed in **8 directions**, not just 4.

Also, I'm storing **number of moves** in `dist`, so I add `1` at the end because the problem asks for the **number of cells in the path**.
