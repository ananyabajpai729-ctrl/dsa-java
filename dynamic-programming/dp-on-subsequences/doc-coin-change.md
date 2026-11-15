# Coin Change

## Problem Statement

We are given an array of coin denominations and an integer `amount`.

We need to find the minimum number of coins required to make the given amount.

We can use each coin denomination an unlimited number of times. If the amount cannot be formed, return `-1`.

## Pattern

**DP on Subsequences – Unbounded Knapsack**

## Intuition

In the previous subset problems, we were either taking or not taking an element, but each element could be used only once.

Here, we can use the same coin multiple times.

For every coin, we have two choices:

- Take the coin.
- Don't take the coin.

The important difference is that when we take a coin, we can use it again. So we don't move to the previous row in the DP table. We stay on the same row.

Since we need the minimum number of coins, we take the minimum of these two choices.

## Approach

1. Create a 2D DP array of size `(n + 1) × (amount + 1)`.
2. Initialize the first row with `Integer.MAX_VALUE - 1`, because we cannot form a positive amount without any coin types.
3. Initialize the first column with `0`, because zero coins are needed to make amount `0`.
4. For every coin and every possible amount:
   - Find the minimum coins needed without taking the current coin.
   - Find the minimum coins needed by taking the current coin, if possible.
5. Store the minimum of both choices.
6. If the final answer is still an impossible state, return `-1`; otherwise, return the minimum number of coins.

## DP State

`dp[i][j]` = minimum number of coins required to make amount `j` using the first `i` coin types.

## Transition

For every coin, we have two choices:

**1. Not Take**

Don't use the current coin. Move to the previous row.

`notTaken = dp[i-1][j]`

**2. Take**

Use the current coin, provided its denomination doesn't exceed the current amount.

`taken = dp[i][j - coins[i-1]] + 1`

Notice that we stay on the same row when taking a coin because we can use it unlimited times.

The final transition is:

`dp[i][j] = min(taken, notTaken)`

## Base Cases

- `dp[i][0] = 0` because we need zero coins to make amount zero.
- `dp[0][j] = Integer.MAX_VALUE - 1` for every positive amount because no coins are available.

We use `Integer.MAX_VALUE - 1` instead of `Integer.MAX_VALUE` so that adding `1` to an impossible state doesn't cause integer overflow.

## Dry Run

Consider:

`coins = [1, 2, 5]`

`amount = 11`

We need to find the minimum number of coins required to make `11`.

One possible combination is:

`5 + 5 + 1 = 11`

This uses 3 coins.

The DP table gives us:

| Coin types considered | Minimum coins for amount 11 |
|---|---:|
| No coins | Impossible |
| `[1]` | 11 |
| `[1, 2]` | 6 |
| `[1, 2, 5]` | 3 |

Therefore:

`dp[3][11] = 3`

Answer = `3`.

## Complexity Analysis

Let `n` be the number of coin denominations and `amount` be the target amount.

- **Time Complexity:** `O(n * amount)` — We calculate every state in the DP table once.
- **Space Complexity:** `O(n * amount)` — We use a 2D DP array.

## My Takeaway

This problem introduces the **Unbounded Knapsack** pattern.

The main difference from 0/1 Subset Sum is what happens when we take an element:

- **0/1 Knapsack:** Move to the previous row because an element can be used only once.
- **Unbounded Knapsack:** Stay on the same row because an element can be used multiple times.

Since we need the minimum number of coins, we take the minimum between the take and not-take choices.
