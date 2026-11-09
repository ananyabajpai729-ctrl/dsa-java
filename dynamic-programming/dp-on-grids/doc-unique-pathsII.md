# Unique Paths II

## Pattern

**Grid DP + Tabulation + Obstacles**

## Problem Statement

We are given an `m × n` grid containing:

- `0` → Empty cell
- `1` → Obstacle

We start from the top-left corner and want to reach the bottom-right corner.

We can only move:

- Right
- Down

We need to find the number of unique paths to reach the destination.

The only difference from the previous problem is that **we cannot move through obstacles**.

## Intuition

In the previous Unique Paths problem, we calculated the number of ways to reach a cell by adding:

- Paths from the cell below.
- Paths from the cell on the right.

The same idea works here.

But now, before calculating the number of paths for a cell, we need to check whether it contains an obstacle.

If it does, then:

`dp[i][j] = 0`

Because no path can pass through that cell.

Otherwise, we use the same recurrence as before:

`dp[i][j] = dp[i + 1][j] + dp[i][j + 1]`

## Approach — Tabulation

### 1. Create the DP array

We create a 2D DP array of size `m × n`.

`dp[i][j]` represents the **number of unique paths from cell `(i, j)` to the destination**.

I'm working backwards from the destination, just like in the previous problem.

### 2. Traverse the grid backwards

We start from the bottom-right corner and move towards the top-left corner.

This way, the answers for the cell below and the cell on the right are already available when calculating the current cell.

### 3. Handle obstacles

For every cell, first check:

`obstacleGrid[i][j] == 1`

If the cell contains an obstacle, we set:

`dp[i][j] = 0`

There are no paths through an obstacle, so we don't need to calculate anything else for that cell.

### 4. Handle the destination

If the current cell is the bottom-right corner and it isn't an obstacle:

`dp[i][j] = 1`

There is exactly one way to reach the destination from itself.

This check comes after the obstacle check because **if the destination itself contains an obstacle, the answer must be 0**.

### 5. Calculate paths for normal cells

For an empty cell, we consider two possible directions.

**Down paths**

`dp[i + 1][j]`

If there is no cell below, we use `0`.

**Right paths**

`dp[i][j + 1]`

If there is no cell on the right, we use `0`.

Then:

`dp[i][j] = downPaths + rightPaths`

We store the result and continue.

### 6. Return the answer

After processing the entire grid:

`dp[0][0]`

contains the number of unique paths from the starting cell to the destination.

## Dry Run

Let's take:

`obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]`

The grid looks like:

| Start | 0 | 0 |
|---|---|---|
| 0 | Obstacle | 0 |
| 0 | 0 | Destination |

The obstacle blocks the middle route.

After filling the DP table backwards, we get:

| 2 | 1 | 1 |
|---:|---:|---:|
| 1 | 0 | 1 |
| 1 | 1 | 1 |

The answer is **2**.

There are two possible paths around the obstacle:

- Move right, right, down, down.
- Move down, down, right, right.

The middle cell has `0` paths because it contains an obstacle.

## Why Is This Dynamic Programming?

The number of paths from the current cell depends on the number of paths from the neighboring cells.

We calculate these answers once and reuse them.

The obstacle condition simply tells us that certain cells have no valid paths through them.

This is **bottom-up DP (tabulation)**.

## Complexity

Let `m` be the number of rows and `n` the number of columns.

- **Time Complexity:** `O(m × n)` — we visit every cell once.
- **Space Complexity:** `O(m × n)` — the DP table stores the answer for every cell.

## Pattern Recognition

When a grid problem asks for the number of ways to reach a destination, think about **Grid DP**.

If the grid contains obstacles or blocked cells, ask:

1. What should the DP value be for a blocked cell?
2. What is the base case?
3. Which neighboring cells contribute to the current answer?
4. Can I reuse the same recurrence from the original grid problem?

Here, the recurrence stays the same. We just make sure that obstacle cells contribute zero paths.

## My Takeaway

In the previous Unique Paths problem, every cell was accessible.

Now, some cells are blocked, so I need to handle them before calculating the usual DP transition.

The main learning is that **a small change in the problem often means a small change in the DP logic**, rather than an entirely new approach.
