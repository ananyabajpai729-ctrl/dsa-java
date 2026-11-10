# Count Square Submatrices with All Ones

## Problem Statement

Given a binary matrix, count the total number of square submatrices that contain only `1`s.

We need to count squares of **all possible sizes**, not just the largest square.

## Pattern

**Dynamic Programming + 2D Grid DP**

## Intuition

Instead of checking every possible square separately, we can use DP to find the largest square ending at each cell.

The interesting part is that if the largest square ending at a cell has size `3`, then that cell also contributes:
- one square of size `1`,
- one square of size `2`,
- one square of size `3`.

So, we don't need to count these squares individually. We can simply add the DP value to our answer.

## Approach

1. Create a 2D DP array of the same size as the input matrix.
2. Initialize the first row and first column directly from the matrix because a square ending at any of these cells can only have size `0` or `1`.
3. Start processing from `(1, 1)`.
4. If the current matrix cell contains `0`, its DP value remains `0`.
5. If it contains `1`, calculate the largest square ending at this cell using the minimum of:
   - the cell above,
   - the cell to the left,
   - the top-left diagonal cell.
6. Add the current DP value to the answer.
7. Return the total answer.

### DP State

`dp[i][j]` represents the size of the largest square submatrix containing only `1`s, with `(i, j)` as its bottom-right corner.

### Transition

`dp[i][j] = 1 + min(up, left, diagonal)`

This transition is used only when `matrix[i][j] == 1`.

## Dry Run

Consider this matrix:

```text
1  1  1
1  1  1
1  1  1
```

The resulting DP table will be:

```text
1  1  1
1  2  2
1  2  3
```

Let's understand what these values mean:

- First row and column can only contribute squares of size `1`.
- At `(1,1)`, we can form a square of size `2`.
- At `(1,2)` and `(2,1)`, the largest square size is `2`.
- At `(2,2)`, we can form a square of size `3`.

Now add all DP values:

`1 + 1 + 1 + 1 + 2 + 2 + 1 + 2 + 3 = 14`

So, the total number of square submatrices is **14**.

## Complexity

- **Time Complexity:** `O(m × n)` — We visit every cell once.
- **Space Complexity:** `O(m × n)` — We use a 2D DP array.

## Pattern Recognition

Whenever a problem asks for square submatrices containing only `1`s, think about tracking the largest valid square ending at each cell.

The key is to use the minimum of the top, left, and diagonal values. All three directions must support the square we are trying to extend.

## My Takeaway

The interesting thing here is that we're not directly counting every square.

We're finding the largest square ending at each cell and using its size to count all smaller squares ending there too.

One DP value is doing more work than it initially seems!
