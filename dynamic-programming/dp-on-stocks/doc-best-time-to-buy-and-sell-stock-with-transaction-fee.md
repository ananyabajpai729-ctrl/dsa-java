# Best Time to Buy and Sell Stock with Transaction Fee

## Problem Statement

Given an array `prices`, where `prices[i]` represents the stock price on the `i-th` day, and an integer `fee` representing the transaction fee, find the maximum profit you can achieve.

You can complete as many transactions as you want, but you must sell your current stock before buying another one.

A transaction fee is charged whenever you sell a stock.

## Pattern

**Dynamic Programming + Stocks DP + Two States**

## Intuition

This problem is very similar to Stock II.

We can make unlimited transactions, so we still need to track only two things:
- Whether we are allowed to buy.
- Whether we are already holding a stock.

The only new thing is the transaction fee.

Whenever we sell a stock, we subtract the fee from the selling price.

Everything else remains the same!

## Approach

1. Create a 2D DP array of size `(n + 1) × 2`.
2. Traverse the days backwards, starting from `n - 1`.
3. For every day, calculate the maximum profit for both states.

### State 1: Allowed to Buy (`buy = 1`)

We have two choices:

- **Buy:** Pay today's stock price and move to the holding state.
- **Skip:** Do nothing and remain in the buy state.

Take the maximum of both choices.

### State 2: Holding a Stock (`buy = 0`)

Again, we have two choices:

- **Sell:** Earn today's price, subtract the transaction fee, and move to the buy state.
- **Hold:** Keep holding the stock and move to the next day.

Take the maximum of both choices.

### DP State

`dp[day][1]` = Maximum profit from the current day onwards when we are allowed to buy.

`dp[day][0]` = Maximum profit from the current day onwards when we are already holding a stock.

### Transitions

When we are allowed to buy:

`dp[day][1] = max(-prices[day] + dp[day+1][0], dp[day+1][1])`

When we are holding a stock:

`dp[day][0] = max(prices[day] + dp[day+1][1] - fee, dp[day+1][0])`

### Base Case

When `day == n`, there are no days left, so the maximum profit is `0` for both states.

The extra row in our DP array handles this automatically because Java initializes integer arrays with zeroes.

## Dry Run

Consider:

```text
prices = [1, 3, 2, 8, 4, 9]
fee = 2
```

One optimal way to complete the transactions:

- Buy at `1`, sell at `8` → Profit = `8 - 1 - 2 = 5`
- Buy at `4`, sell at `9` → Profit = `9 - 4 - 2 = 3`

Total profit = **8**.

The DP explores the buy, skip, sell, and hold choices while subtracting the fee whenever a sale happens.

The final answer is `dp[0][1] = 8`.

## Complexity

- **Time Complexity:** `O(n)` — We traverse all days once and calculate two states per day.
- **Space Complexity:** `O(n)` — We use a 2D DP array of size `(n + 1) × 2`.

## Pattern Recognition

Whenever a stock problem allows multiple transactions, start by identifying the two states:

- Are we allowed to buy?
- Are we already holding a stock?

If the problem introduces an additional condition like a transaction fee, check which operation it affects.

Here, the fee is charged only when we sell.

## My Takeaway

This is basically Stock II with one extra condition.

The two-state DP remains exactly the same. We just subtract the transaction fee during the selling operation.

It's nice to see how these stock problems keep reusing the same DP structure with small changes in the transitions!
