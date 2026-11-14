# Partition Equal Subset Sum

## Problem Statement

We are given an array and need to check whether it can be divided into two subsets such that both subsets have the same sum.

Every element must belong to exactly one of the two subsets.

## Pattern

**DP on Subsequences + Subset Sum**

## Intuition

The first thing to notice is that both subsets must have an equal sum.

Let's calculate the total sum of the array.

- If the total sum is odd, we cannot divide it into two equal parts.
- If the total sum is even, each subset must have a sum equal to `totalSum / 2`.

So instead of directly finding two subsets, we just need to check whether we can form a subset whose sum is `totalSum / 2`.

And this is nothing but the **Subset Sum problem**!

## Approach

1. Calculate the total sum of the array.
2. If the total sum is odd, return `false`.
3. Otherwise, set `target = totalSum / 2`.
4. Apply the same DP approach used in Subset Sum:
   - `dp[i][sum]` tells us whether we can form `sum` using elements from index `0` to `i`.
   - We can either take the current element or skip it.
5. Finally, return `dp[n-1][target]`.

## DP State

`dp[i][sum]` = whether it is possible to form the given sum using elements from index `0` to `i`.

## Transition

For every element, we have two choices:

- **Not Take:** Don't include the current element. The answer comes from the previous row.
- **Take:** Include the current element, if it doesn't exceed the required sum.

So,

`dp[i][sum] = notTaken || taken`

## Base Cases

- `dp[i][0] = true` because we can always form sum `0` by choosing no elements.
- For the first element, `dp[0][nums[0]] = true`, if it is within the target.

## Dry Run

Consider:

`nums = [1, 5, 11, 5]`

Total sum = `22`

Since the sum is even:

`target = 22 / 2 = 11`

Now we need to check whether we can form a subset with sum `11`.

| Elements considered | Possible sums (up to 11) |
|---|---|
| `[1]` | 0, 1 |
| `[1, 5]` | 0, 1, 5, 6 |
| `[1, 5, 11]` | 0, 1, 5, 6, 11 |
| `[1, 5, 11, 5]` | 0, 1, 5, 6, 10, 11 |

We can form `11`, so the answer is `true`.

One possible partition is:

- Subset 1: `[11]` → Sum = 11
- Subset 2: `[1, 5, 5]` → Sum = 11

Both subsets have equal sums.

## Complexity Analysis

Let `n` be the number of elements and `target = totalSum / 2`.

- **Time Complexity:** `O(n * target)` — We fill an `n × (target + 1)` DP table.
- **Space Complexity:** `O(n * target)` — We use a 2D DP array.

## My Takeaway

This problem is basically Subset Sum with one extra observation.

Whenever a problem asks us to divide an array into two equal-sum subsets, first check the total sum. If it's even, the problem reduces to finding a subset with half of that sum.
