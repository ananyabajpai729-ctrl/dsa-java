# Minimum Subset Sum Difference

## Problem Statement

We are given an array and need to divide its elements into two subsets such that the absolute difference between their sums is minimum.

Every element must belong to exactly one subset.

## Pattern

**DP on Subsequences + Subset Sum**

## Intuition

Let's say the sum of the two subsets is `s1` and `s2`.

We know:

`s1 + s2 = totalSum`

We want to minimize:

`|s1 - s2|`

For the difference to be minimum, the two subset sums should be as close as possible.

Ideally, both should be equal to `totalSum / 2`.

So, instead of trying every possible partition, we can use the **Subset Sum DP** that we already know!

We just need to find the largest achievable subset sum that is less than or equal to `totalSum / 2`.

## Approach

1. Calculate the total sum of the array.
2. Set `target = totalSum / 2`.
3. Build the same DP table used in the Subset Sum problem.
4. After filling the table, check the last row starting from `target` and moving backwards.
5. Find the first sum that is achievable. This becomes `s1`.
6. Calculate the other subset sum:

   `s2 = totalSum - s1`

7. Return `Math.abs(s1 - s2)`.

## DP State

`dp[i][sum]` tells us whether it is possible to form the given sum using elements from index `0` to `i`.

## Transition

For every element, we have two choices:

- **Not Take:** Don't include the current element.
- **Take:** Include it if it doesn't exceed the required sum.

`dp[i][sum] = notTaken || taken`

## Base Cases

- `dp[i][0] = true` because sum `0` is always possible by choosing no elements.
- If the first element is within the target, mark `dp[0][arr[0]] = true`.

## Dry Run

Consider:

`arr = [1, 6, 11, 5]`

Total sum = `23`

`target = 23 / 2 = 11`

After building the DP table, the achievable subset sums up to `11` are:

`0, 1, 5, 6, 7, 11`

Now we start checking from `target = 11` and move backwards.

- Sum `11` is achievable, so `s1 = 11`.
- The remaining sum is `s2 = 23 - 11 = 12`.

Therefore:

`Minimum Difference = |11 - 12| = 1`

One possible partition is:

- Subset 1: `[11]` → Sum = 11
- Subset 2: `[1, 6, 5]` → Sum = 12

Answer = `1`

## Complexity Analysis

Let `n` be the number of elements and `target = totalSum / 2`.

- **Time Complexity:** `O(n * target)` — We fill an `n × (target + 1)` DP table and then scan the last row once.
- **Space Complexity:** `O(n * target)` — We use a 2D DP array.

## My Takeaway

This is another variation of Subset Sum.

The main trick is that we don't need to check every possible pair of subset sums. We just find the largest achievable sum closest to half of the total sum, and calculate the remaining difference.
