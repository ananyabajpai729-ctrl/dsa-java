# Minimum Falling Path Sum

## Pattern

**Grid DP + Tabulation + Minimum Cost Path**

## Problem Statement

We are given an `n × n` matrix.

We need to start from any cell in the first row and reach any cell in the last row.

From a cell `(i, j)`, we can move to:

- Down → `(i + 1, j)`
- Down-left → `(i + 1, j - 1)`
- Down-right → `(i + 1, j + 1)`

We need to find the **minimum sum of values along a falling path**.

## Intuition

Unlike Unique Paths, where we were counting the number of possible paths, here we need to find the path with the minimum sum.

Let's think about the current cell.

To reach the bottom from this cell, we have three possible directions:

- Down
- Down-left
- Down-right

We need to find which of these directions gives us the minimum total cost.

But we don't just consider the value of the next cell. We also consider the minimum path sum already calculated for that cell.

So:

**Minimum path sum = current cell value + minimum of the three possible next paths**

## Approach — Tabulation

### 1. Create the DP array

We create a 2D DP array of the same dimensions as the matrix.

`dp[i][j]` represents the **minimum falling path sum starting from cell `(i, j)` and going down to the last row**.

Notice that this time, I'm calculating the answer from bottom to top.

### 2. Initialize the last row

The last row is our base case.

If we're already in the last row, there's no further movement required.

So:

`dp[m - 1][j] = matrix[m - 1][j]`

The minimum falling path sum from a cell in the last row is simply the value of that cell itself.

### 3. Traverse the remaining rows backwards

We start from the second-last row and move towards the first row.

For every cell, we calculate the minimum cost of reaching the bottom.

### 4. Calculate the three possible directions

For every cell `(i, j)`, we consider three options.

**Down:**

`dp[i + 1][j]`

This is always a valid move because we're processing rows above the last row.

**Down-right:**

`dp[i + 1][j + 1]`

This is valid only if `j + 1 < n`.

**Down-left:**

`dp[i + 1][j - 1]`

This is valid only if `j - 1 >= 0`.

If either diagonal move goes outside the matrix, we treat it as an invalid option by assigning it a large value.

This ensures that invalid moves won't be selected as the minimum.

### 5. Calculate the current DP state

Once we have all three possible values, we choose the minimum:

`dp[i][j] = matrix[i][j] + min(down, downLeft, downRight)`

This gives us the minimum falling path sum starting from the current cell.

### 6. Find the final answer

The problem allows us to start from **any cell in the first row**.

So we calculate the minimum among all the DP values in the first row.

`answer = min(dp[0][0], dp[0][1], ..., dp[0][n - 1])`

This gives us the minimum falling path sum for the entire matrix.

## Dry Run

Let's take:

`matrix = [[2,1,3],[6,5,4],[7,8,9]]`

The last row is our base case:

`[7, 8, 9]`

Now we move upwards.

### Second row

For the cell containing `6`:

- Down = 7
- Down-right = 8
- Down-left = invalid

Minimum = 7

So:

`dp[1][0] = 6 + 7 = 13`

For the cell containing `5`:

- Down = 8
- Down-left = 7
- Down-right = 9

Minimum = 7

So:

`dp[1][1] = 5 + 7 = 12`

For the cell containing `4`:

- Down = 9
- Down-left = 8
- Down-right = invalid

Minimum = 8

So:

`dp[1][2] = 4 + 8 = 12`

The second row becomes:

`[13, 12, 12]`

### First row

After calculating the first row, the complete DP table becomes:

| 15 | 13 | 15 |
|---:|---:|---:|
| 13 | 12 | 12 |
| 7 | 8 | 9 |

We take the minimum value from the first row:

`min(15, 13, 15) = 13`

The answer is **13**.

The minimum falling path is:

`1 → 4 → 8`

Total:

`1 + 4 + 8 = 13`

## Why Is This Dynamic Programming?

The minimum falling path sum from a cell depends on the minimum path sums of the cells directly below it.

Instead of exploring every possible falling path separately, we calculate the best answer for each cell and reuse it.

This is **bottom-up DP (tabulation)**.

## Complexity

Let `m` be the number of rows and `n` the number of columns.

- **Time Complexity:** `O(m × n)` — we visit every cell once and check at most three possible moves.
- **Space Complexity:** `O(m × n)` — we store the minimum path sum for every cell in the DP table.

## Pattern Recognition

When a grid problem asks for the minimum or maximum cost of reaching a destination, think about **Grid DP**.

Ask yourself:

1. What does `dp[i][j]` represent?
2. What are the possible next moves?
3. Which neighboring states contribute to the current answer?
4. Should I take the minimum or maximum?
5. What happens when a move goes outside the grid?

For this problem, each cell has at most three possible next moves, and we choose the one with the minimum falling path sum.

## My Takeaway

In Unique Paths, I was adding the number of paths from neighboring cells.

In Minimum Falling Path Sum, I'm taking the minimum cost among neighboring cells and adding the current cell's value.

The grid DP structure is still familiar, but now the transition is about **choosing the cheapest possible path instead of counting paths**.

I'm also getting more comfortable handling invalid moves using boundary checks.
