# Coin Change II

## Problem Statement

We are given an array of coin denominations and an integer `amount`.

We need to find the number of different combinations that can make up the given amount.

We can use each coin denomination an unlimited number of times.

Unlike Coin Change I, here we need to count the combinations instead of finding the minimum number of coins.

## Pattern

**DP on Subsequences – Unbounded Knapsack (Counting Ways)**

## Intuition

In Coin Change I, we were finding the minimum number of coins required to make an amount.

Here, the objective is different. We need to count the number of combinations.

For every coin, we have two choices:

- Take the current coin.
- Don't take the current coin.

Since we can use a coin unlimited times, when we take it, we can use it again.

Also, we process coins one by one, which ensures that we count combinations rather than different permutations of the same combination.

## Approach

1. Create a 1D DP array of size `amount + 1`.
2. Initially, consider only the first coin denomination.
3. If an amount is divisible by the first coin, there is exactly one way to make it using that coin.
4. Process the remaining coins one by one.
5. For every coin, iterate through all amounts from `0` to `amount`.
6. Calculate:
   - Not take: Keep the existing number of ways.
   - Take: Add the number of ways to form the remaining amount using the current coin.
7. Add both choices and update the DP array.
8. Return `dp[amount]`.

## DP State

`dp[curr]` = number of ways to make the amount `curr` using the coin denominations processed so far.

## Transition

For every coin, we have two choices:

**1. Not Take**

Don't use the current coin.

`notTaken = dp[curr]`

**2. Take**

Use the current coin. Since we can use it unlimited times, we can use the already updated result for the remaining amount.

`taken = dp[curr - coins[i]]`

The final transition is:

`dp[curr] = taken + notTaken`

We add both choices because we need to count all possible combinations.

## Base Case

Initially, we consider only the first coin.

If the current amount is divisible by the first coin denomination, there is exactly one way to make it.

For example, if the first coin is `2`:

- Amount `0` → 1 way
- Amount `2` → 1 way
- Amount `4` → 1 way
- Amount `6` → 1 way

Otherwise, the number of ways remains `0`.

## Why Iterate Forwards?

We iterate from `0` to `amount`:

`for(curr = 0; curr <= amount; curr++)`

This is important because coins can be used unlimited times.

When we update `dp[curr]`, the value at `dp[curr - coins[i]]` may already include the current coin. This allows us to use it multiple times.

In contrast, 0/1 Knapsack iterates backwards because each item can be used only once.

## Dry Run

Consider:

`coins = [1, 2, 5]`

`amount = 5`

Initially, considering only coin `1`:

`dp = [1, 1, 1, 1, 1, 1]`

After processing coin `2`:

`dp = [1, 1, 2, 2, 3, 3]`

After processing coin `5`:

`dp = [1, 1, 2, 2, 3, 4]`

The four combinations are:

1. `[1, 1, 1, 1, 1]`
2. `[1, 1, 1, 2]`
3. `[1, 2, 2]`
4. `[5]`

Therefore:

`dp[5] = 4`

Answer = `4`.

## Complexity Analysis

Let `n` be the number of coin denominations and `amount` be the target amount.

- **Time Complexity:** `O(n * amount)` — We process every coin and every possible amount.
- **Space Complexity:** `O(amount)` — We use a single 1D DP array.

## My Takeaway

This is another variation of Unbounded Knapsack.

The important difference between Coin Change I and Coin Change II is the objective:

- **Coin Change I:** Find the minimum number of coins using `Math.min(taken, notTaken)`.
- **Coin Change II:** Count the number of combinations using `taken + notTaken`.

Also, iterating through coins one by one ensures that we count combinations, not permutations.
