# Triangle - Minimum Path Sum

## Problem Statement

Given a triangle of numbers, find the minimum path sum from the top to the bottom.

From a position `(i, j)`, we can move to:
- `(i + 1, j)` — directly below
- `(i + 1, j + 1)` — diagonally below-right

We need to find the minimum possible sum along such a path.

## Pattern

**Dynamic Programming + Grid/Path DP + Tabulation**

## Intuition

Instead of starting from the top and trying every possible path, we can start from the bottom.

Why?

For every cell, we only need to know the minimum path sum from the two cells directly below it.

So, once we know the answers for the last row, we can use them to calculate the answers for the rows above.

## Approach

1. Create a 2D DP array of size `n × n`.
2. Copy the last row of the triangle into the last row of `dp`.
3. Start processing from the second-last row and move upwards.
4. For every cell `(i, j)`:
   - Consider the cell directly below: `dp[i + 1][j]`.
   - Consider the diagonally lower-right cell: `dp[i + 1][j + 1]`.
   - Take the minimum of these two values and add the current triangle value.
5. Finally, return `dp[0][0]`.

### DP State

`dp[i][j]` represents the minimum path sum starting from cell `(i, j)` and going down to the last row.

### Transition

`dp[i][j] = triangle[i][j] + min(dp[i+1][j], dp[i+1][j+1])`

## Dry Run

Consider:

```text
       2
      3 4
     6 5 7
    4 1 8 3
```

We start by copying the last row:

```text
       - 
      - -
     - - -
    4  1  8  3
```

Now calculate the minimum path sums from bottom to top.

After processing the third row:

```text
       -
      - -
     7  6 10
    4  1  8  3
```

After processing the second row:

```text
       -
      9 10
     7  6 10
    4  1  8  3
```

After processing the first row:

```text
       11
      9 10
     7  6 10
    4  1  8  3
```

So the answer is **11**.

The minimum path is:

`2 → 3 → 5 → 1 = 11`

## Complexity

- **Time Complexity:** `O(n²)` — We process the cells of the triangle using nested loops.
- **Space Complexity:** `O(n²)` — We use a 2D DP array of size `n × n`.

## Pattern Recognition

Whenever a problem asks for the minimum or maximum path sum in a triangle or grid, think about defining a DP state for each cell.

Here, since we can only move to the two cells below, we can solve the problem from bottom to top using tabulation.

## My Takeaway

This feels quite similar to Minimum Falling Path Sum.

The main difference is the available moves:
- In Minimum Falling Path Sum, we could move down, down-left, or down-right.
- Here, we can only move down or diagonally down-right.

The overall idea stays the same: start from the bottom, calculate the best answer for each cell, and keep moving upwards.
