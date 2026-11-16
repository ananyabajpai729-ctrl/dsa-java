# 0/1 Knapsack (Space Optimization)

## Problem Statement

We are given `n` items, each having a weight and a value, along with a knapsack of capacity `W`.

We need to select items such that:

- The total weight does not exceed `W`.
- The total value is maximum.
- Each item can be selected at most once.

## Pattern

**DP on Subsequences – 0/1 Knapsack (Space Optimization)**

## Intuition

In the normal 0/1 Knapsack problem, we use a 2D DP array where each state represents the maximum value we can get using the first `i` items with a given capacity.

But if we look at the transition, the current row depends only on the previous row.

So instead of maintaining the entire 2D table, we can optimize it using a single 1D array.

The important part is that we need to iterate through the capacities **in reverse order**.

This ensures that while processing an item, we don't accidentally use it more than once.

## Approach

1. Create a 1D DP array of size `W + 1`.
2. Initialize the DP array using the first item:
   - If the capacity is at least `wt[0]`, we can take the first item and get `val[0]`.
3. Start processing the remaining items.
4. For every item, iterate from capacity `W` down to `0`.
5. Calculate two choices:
   - Not take the current item.
   - Take the current item if it fits.
6. Store the maximum value between these two choices.
7. Return `dp[W]`.

## DP State

Conceptually, the original 2D state is:

`dp[i][w]` = maximum value we can obtain using items from index `0` to `i` with capacity `w`.

After space optimization, `dp[w]` stores the maximum value for the current processed items and capacity `w`.

## Transition

For every item, we have two choices:

**1. Not Take**

Don't include the current item.

`notTaken = dp[w]`

**2. Take**

Include the current item if its weight doesn't exceed the current capacity.

`taken = val[i] + dp[w - wt[i]]`

The final transition is:

`dp[w] = max(notTaken, taken)`

## Base Case

We initialize the DP array using the first item:

- If `wt[0] <= W`, then every capacity from `wt[0]` to `W` can accommodate the first item, so we store `val[0]`.
- Capacities smaller than `wt[0]` remain `0`, since we cannot take the item.

## Why Iterate Backwards?

This is the most important part of the space optimization.

We iterate from `W` down to `0` so that `dp[w - wt[i]]` still contains the result from the previous item.

If we iterate forwards, the updated value could be reused while processing the same item, allowing us to take it multiple times.

That would turn the problem into Unbounded Knapsack instead of 0/1 Knapsack.

## Dry Run

Consider:

`wt = [1, 2, 4, 5]`

`val = [5, 4, 8, 6]`

`W = 5`

Initial DP array after considering the first item:

`[0, 5, 5, 5, 5, 5]`

After processing the remaining items:

| Items considered | DP array (capacities 0 to 5) |
|---|---|
| `[1]` | `[0, 5, 5, 5, 5, 5]` |
| `[1, 2]` | `[0, 5, 5, 9, 9, 9]` |
| `[1, 2, 4]` | `[0, 5, 5, 9, 13, 13]` |
| `[1, 2, 4, 5]` | `[0, 5, 5, 9, 13, 13]` |

The maximum value we can obtain with capacity `5` is `13`.

We achieve this by selecting:

- Item with weight `1`, value `5`.
- Item with weight `4`, value `8`.

Total weight = `5`

Total value = `13`

Answer = `13`.

## Complexity Analysis

Let `n` be the number of items and `W` be the knapsack capacity.

- **Time Complexity:** `O(n * W)` — We process every item and iterate through all capacities.
- **Space Complexity:** `O(W)` — We use a single 1D DP array.

## My Takeaway

This problem helped me understand how to optimize the space of 0/1 Knapsack from a 2D DP array to a 1D array.

The most important thing to remember is the direction of iteration:

- **0/1 Knapsack:** Iterate backwards because each item can be used only once.
- **Unbounded Knapsack:** Iterate forwards because each item can be used multiple times.
