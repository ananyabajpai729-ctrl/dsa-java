# Subset Sum Problem

## Problem Statement

Given an array of integers and a target sum, determine whether there exists a subset of the array whose elements add up to the target.

We can either include or exclude each element, but we cannot use the same element more than once.

## Pattern

**Dynamic Programming + Subsequences + Take / Not Take**

## Intuition

For every element, we have two choices:

- Take the current element.
- Don't take the current element.

If we take an element, we need to find whether the remaining sum can be formed using the previous elements.

If we don't take it, we simply check whether the same sum was already possible without this element.

We keep track of these possibilities using a 2D DP array.

## Approach

1. Create a 2D boolean DP array of size `n × (target + 1)`.
2. Initialize the first column as `true` because a sum of `0` is always possible by taking an empty subset.
3. Initialize the first row:
   - If the first element is less than or equal to the target, mark its value as `true`.
   - All other sums remain `false` because we only have one element available.
4. Start traversing from the second element.
5. For every element and every possible sum, calculate two choices:
   - **Not Taken:** Check whether the same sum is possible using previous elements.
   - **Taken:** If the current element is not greater than the required sum, check whether the remaining sum is possible using previous elements.
6. Store `true` if either choice is possible.
7. Return `dp[n-1][target]`.

### DP State

`dp[i][sum]` represents whether we can form the given sum using elements from index `0` to `i`.

### Transitions

**Not Taken:**

`notTaken = dp[i-1][sum]`

**Taken:**

`taken = dp[i-1][sum-arr[i]]`

We only consider the taken case when `arr[i] <= sum`.

Finally:

`dp[i][sum] = taken || notTaken`

### Base Cases

- `dp[i][0] = true` for every index because an empty subset always gives sum `0`.
- `dp[0][arr[0]] = true` because we can form the sum equal to the first element by taking it.
- All other states in the first row remain `false`.

## Dry Run

Consider:

```text
arr = [1, 2, 3, 4]
target = 5
```

We want to check whether we can form sum `5`.

The DP table looks like this:

| Elements considered | Sum 0 | Sum 1 | Sum 2 | Sum 3 | Sum 4 | Sum 5 |
|---|---|---|---|---|---|---|
| `[1]` | T | T | F | F | F | F |
| `[1, 2]` | T | T | T | T | F | F |
| `[1, 2, 3]` | T | T | T | T | T | T |
| `[1, 2, 3, 4]` | T | T | T | T | T | T |

Here, `T` means the sum is possible and `F` means it is not possible.

We can form the target sum using:

`1 + 4 = 5`

So, the answer is **true**.

## Complexity

- **Time Complexity:** `O(n × target)` — We process every element for every possible sum.
- **Space Complexity:** `O(n × target)` — We use a 2D DP array.

## Pattern Recognition

Whenever a problem asks whether a target can be formed using some elements of an array, think about the take/not-take approach.

For every element, we decide whether to include it or skip it.

This is a common pattern in DP on subsequences and is also useful in problems like:
- Partition Equal Subset Sum
- Minimum Subset Sum Difference
- Count Subsets with a Given Sum

## My Takeaway

This is my introduction to the take/not-take pattern in subsequence DP.

Instead of thinking about all possible subsets directly, I make a decision for every element: either include it or leave it.

The DP table helps me remember which sums are already possible using the elements I've processed so far.
