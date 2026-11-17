# Target Sum

## Problem Statement

We are given an array of integers and a target value.

We need to assign either a `+` or `-` sign to every element such that the resulting sum equals the target.

Our task is to count the number of different ways to achieve the target.

## Pattern

**DP on Subsequences – Counting Ways (Target Sum)**

## Intuition

For every element, we have exactly two choices:

- Add the element to the current sum.
- Subtract the element from the current sum.

We need to count all possible ways to reach the target.

One approach is to maintain a DP table where we track the number of ways to obtain every possible sum.

But since sums can be negative, we cannot directly use them as array indices.

To solve this, we use an **offset** equal to the total sum of the array.

This allows us to represent negative and positive sums using normal non-negative array indices.

## Approach

1. Calculate the total sum of the array.
2. If `abs(target) > totalSum`, return `0`, because the target is outside the possible sum range.
3. Create a 2D DP array of size `(n + 1) × (2 * totalSum + 1)`.
4. Initialize `dp[0][totalSum] = 1`, representing one way to obtain sum `0` before processing any elements.
5. For every element, consider both choices:
   - Assign a positive sign.
   - Assign a negative sign.
6. Update the number of ways for every possible sum.
7. Return `dp[n][target + totalSum]`.

## DP State

`dp[i][totalSum + sum]` = number of ways to obtain the signed sum `sum` using the first `i` elements.

We add `totalSum` to the actual sum because array indices cannot be negative.

## Transition

For every element, we have two choices.

**1. Assign a Positive Sign**

If we want to reach the current sum `sum` by adding the current element, the previous sum must have been:

`sum - nums[i-1]`

So:

`dp[i][totalSum + sum] += dp[i-1][totalSum + sum - nums[i-1]]`

**2. Assign a Negative Sign**

If we want to reach the current sum `sum` by subtracting the current element, the previous sum must have been:

`sum + nums[i-1]`

So:

`dp[i][totalSum + sum] += dp[i-1][totalSum + sum + nums[i-1]]`

We add both results because we need to count all possible ways.

## Base Case

`dp[0][totalSum] = 1`

Before processing any elements, there is exactly one way to obtain sum `0`: by choosing nothing.

The offset maps this sum to index `totalSum`.

## Dry Run

Consider:

`nums = [1, 1, 1, 1, 1]`

`target = 3`

Total sum:

`totalSum = 5`

The possible signed sums range from `-5` to `5`.

We need to count the ways to obtain sum `3`.

Possible sign assignments:

| Sign assignment | Result |
|---|---:|
| `+1 +1 +1 +1 -1` | 3 |
| `+1 +1 +1 -1 +1` | 3 |
| `+1 +1 -1 +1 +1` | 3 |
| `+1 -1 +1 +1 +1` | 3 |
| `-1 +1 +1 +1 +1` | 3 |

There are 5 ways to obtain the target.

Since the offset is `5`, the target is stored at index:

`target + totalSum = 3 + 5 = 8`

Therefore:

`dp[5][8] = 5`

Answer = `5`.

## Complexity Analysis

Let `n` be the number of elements and `totalSum` be the sum of all elements.

- **Time Complexity:** `O(n * totalSum)` — We process all elements and all possible signed sums.
- **Space Complexity:** `O(n * totalSum)` — We use a 2D DP array with `2 * totalSum + 1` columns.

## My Takeaway

This problem introduces another way of handling negative sums in DP.

Instead of converting the problem into Subset Sum, we directly track the number of ways to reach every possible signed sum.

The important trick is the offset:

`actual sum + totalSum = DP array index`

This lets us handle negative sums using a normal array.
