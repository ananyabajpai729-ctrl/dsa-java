# Unique Paths

## Pattern

**Dynamic Programming (DP) + Tabulation + Space Optimization**

## Problem Statement

We are given an `m × n` grid.

We start from the top-left corner and want to reach the bottom-right corner.

We can only move:

- Right
- Down

We need to find the total number of unique paths to reach the destination.

## Intuition

Let's think about the possible ways to reach any cell.

We can reach a cell either:

- From the cell above it by moving down.
- From the cell on its left by moving right.

So, the total number of ways to reach the current cell is:

**Ways from above + Ways from left**

This gives us our DP relation:

`dp[row][column] = dp[row + 1][column] + dp[row][column + 1]`

I'm using this relation in reverse because I'm starting from the destination and calculating the number of ways to reach it from every other cell.

## Approach — Space-Optimized Tabulation

### 1. Start from the bottom-right corner

Instead of starting from the top-left corner, I work backwards from the destination.

The destination itself has exactly one way to reach it: we're already there.

So:

`dp[m - 1][n - 1] = 1`

This becomes our base case.

### 2. Why am I iterating backwards?

I'm traversing:

- Rows from `m - 1` to `0`.
- Columns from `n - 1` to `0`.

This direction is important because I need the answers for the cell below and the cell on the right.

Since I'm moving backwards, both of these cells have already been processed.

### 3. Understand the two arrays

Instead of maintaining the entire 2D DP table, I use two arrays.

**`nextRow`**

Stores the number of paths for the row below the current row.

**`currentRow`**

Stores the number of paths for the row I'm currently calculating.

This works because I don't need the answers for every previous row. I only need the immediately next row.

### 4. Calculate the current cell

For every cell:

**Down paths**

`downPaths = nextRow[column]`

This represents the number of ways to reach the destination from the cell below.

**Right paths**

`rightPaths = currentRow[column + 1]`

This represents the number of ways to reach the destination from the cell on the right.

If the current cell is in the last column, there is no cell to its right, so I use `0`.

Then:

`current = downPaths + rightPaths`

I store this value in `currentRow[column]`.

### 5. Move to the previous row

Once I've calculated the entire current row:

`nextRow = currentRow`

Now the current row becomes the next row for the upcoming iteration.

This is the main space optimization.

Instead of storing all rows, I keep replacing the old row with the newly calculated one.

### 6. Return the answer

After processing all rows, `nextRow[0]` contains the number of unique paths from the top-left corner to the destination.

So I return:

`nextRow[0]`

## Dry Run

Let's take a grid with:

`m = 3, n = 3`

The grid looks like:

| | | |
|---|---|---|
| Start | → | |
| ↓ | | |
| | | Destination |

We start from the bottom-right corner.

The destination has 1 path.

The last row becomes:

`[1, 1, 1]`

The last column also has only one possible direction to move, so its path count is 1.

Now we calculate the remaining cells using:

`downPaths + rightPaths`

The final DP table would look like this:

| 1 | 3 | 6 |
|---:|---:|---:|
| 1 | 2 | 3 |
| 1 | 1 | 1 |

The number of unique paths from the top-left to the bottom-right is **6**.

Notice that the actual full table is only for understanding. My code doesn't store this entire table.

It only keeps the current row and the row below it.

## Why Is This Dynamic Programming?

The number of ways to reach a cell depends on the number of ways to reach its neighboring cells.

Instead of recalculating these values repeatedly, we calculate them once and reuse them.

This is **bottom-up DP (tabulation)**.

The space optimization works because each cell depends only on:

- The row below.
- The cell to its right in the current row.

So we don't need to store the entire DP table.

## Complexity

Let `m` be the number of rows and `n` the number of columns.

- **Time Complexity:** `O(m × n)` — we visit every cell exactly once.
- **Space Complexity:** `O(n)` — we store only two arrays of size `n`.

## Pattern Recognition

When a grid problem asks for the number of ways to reach a destination, think about **Grid DP**.

Ask yourself:

1. What are the possible ways to reach the current cell?
2. Which neighboring cells contribute to the answer?
3. What should the base case be?
4. Can I calculate the answer using only the previous row?

Here, the two possible directions are right and down.

## My Takeaway

In my earlier DP problems, I was storing answers for every index or day in a DP array.

Here, I'm working with a grid, and the current cell depends on two neighboring cells.

The important part is realizing that **I don't always need to store the entire DP table**.

If the current state only depends on a small portion of previously calculated states, I can keep only those states and reuse the space.

This is my first step into space optimization while still understanding the tabulation approach.
