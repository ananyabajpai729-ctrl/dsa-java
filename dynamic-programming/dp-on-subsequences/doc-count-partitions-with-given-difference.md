# Count Partitions With Given Difference

## Problem Statement

We are given an array and an integer `diff`. We need to divide the array into two subsets such that the difference between their sums is equal to `diff`.

We need to count the number of ways to make such partitions.

## Pattern

**DP on Subsequences – Counting Subsets**

## Intuition

Let's say the sums of the two subsets are `s1` and `s2`.

We know:

`s1 - s2 = diff`

And the sum of both subsets is the total sum of the array:

`s1 + s2 = totalSum`

Adding these two equations:

`2 * s1 = totalSum + diff`

Similarly, we can calculate:

`s2 = (totalSum - diff) / 2`

So instead of directly finding two subsets, we can convert the problem into counting the number of subsets whose sum is:

`target = (totalSum - diff) / 2`

This is the same **Perfect Sum / Count Subsets with Sum K** problem we just solved!

## Approach

1. Calculate the total sum of the array.
2. Calculate `remaining = totalSum - diff`.
3. If `remaining` is negative or odd, return `0`.
   - A negative value means the required partition is impossible.
   - An odd value means we cannot divide it into two integer subset sums.
4. Calculate `target = remaining / 2`.
5. Apply the same counting DP used in Perfect Sum.
6. Return `dp[n-1][target]`.

## DP State

`dp[i][sum]` = number of subsets that form the given sum using elements from index `0` to `i`.

## Transition

For every element, we have two choices:

**1. Not Take**

Don't include the current element.

`notTaken = dp[i-1][sum]`

**2. Take**

Include the current element if it doesn't exceed the required sum.

`taken = dp[i-1][sum - arr[i]]`

The final transition is:

`dp[i][sum] = (taken + notTaken) % modulo`

We add both choices because we need to count all possible subsets.

## Base Cases

- If the first element is `0`, then `dp[0][0] = 2`, because we have two choices: take zero or don't take zero.
- Otherwise, `dp[0][0] = 1`, representing the empty subset.
- If the first element is non-zero and doesn't exceed the target, set `dp[0][arr[0]] = 1`.

## Dry Run

Consider:

`arr = [1, 1, 2, 3]`

`diff = 1`

Total sum:

`totalSum = 1 + 1 + 2 + 3 = 7`

Calculate the target:

`remaining = totalSum - diff = 7 - 1 = 6`

`target = 6 / 2 = 3`

Now we need to count the subsets whose sum is `3`.

Possible subsets:

1. `[1, 2]` using the first `1`
2. `[1, 2]` using the second `1`
3. `[3]`

So the answer is `3`.

The corresponding partitions are:

- `[1, 2]` and `[1, 3]`
- `[1, 2]` and `[1, 3]` (using the other `1`)
- `[3]` and `[1, 1, 2]`

Each partition has a difference of `1`.

## Complexity Analysis

Let `n` be the number of elements and `target = (totalSum - diff) / 2`.

- **Time Complexity:** `O(n * target)` — We fill the DP table of size `n × (target + 1)`.
- **Space Complexity:** `O(n * target)` — We use a 2D DP array.

## My Takeaway

This problem looks different from Perfect Sum, but the underlying DP is exactly the same.

The main work is the mathematical conversion:

`Count Partitions With Given Difference → Count Subsets With Sum (totalSum - diff) / 2`

Once we derive the target, we can directly reuse the subset-counting DP.
