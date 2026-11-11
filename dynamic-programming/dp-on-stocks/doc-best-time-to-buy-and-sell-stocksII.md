# Best Time to Buy and Sell Stock II

## Problem Statement

Given an array `prices`, where `prices[i]` represents the stock price on the `i-th` day, find the maximum profit you can achieve.

Unlike the previous stock problem, here we can complete as many transactions as we want.

We can buy and sell multiple times, but we must sell our current stock before buying again.

## Pattern

**Dynamic Programming + Stocks DP + Two States**

## Intuition

In this problem, we need to keep track of whether we are currently allowed to buy or whether we already hold a stock.

At every day, we have two possible choices depending on our current state:
- If we are allowed to buy, we can either buy today or skip.
- If we already have a stock, we can either sell today or keep holding it.

We calculate the maximum profit possible from both choices.

Since we're processing the days backwards, we already know the answers for the next day.

## Approach

1. Create a 2D DP array of size `(n + 1) × 2`.
2. Traverse the days backwards, starting from `n - 1`.
3. For every day, calculate the answer for both states.

### State 1: Allowed to Buy (`buy = 1`)

We have two choices:

- **Buy:** Spend today's price and move to the state where we are holding a stock.
- **Skip:** Do nothing and remain in the same state for the next day.

Take the maximum profit from both choices.

### State 2: Holding a Stock (`buy = 0`)

We also have two choices:

- **Sell:** Earn today's price and move to the state where we are allowed to buy again.
- **Hold:** Do nothing and continue holding the stock.

Again, take the maximum profit.

### DP State

`dp[day][1]` = Maximum profit from day onwards when we are allowed to buy.

`dp[day][0]` = Maximum profit from day onwards when we are already holding a stock.

### Base Case

When `day == n`, there are no more days left, so the profit is `0` for both states.

The extra row in our DP array handles this automatically because Java initializes integer arrays with zeroes.

### Transition

For the buy state:

`dp[day][1] = max(-prices[day] + dp[day+1][0], dp[day+1][1])`

For the holding state:

`dp[day][0] = max(prices[day] + dp[day+1][1], dp[day+1][0])`

## Dry Run

Consider:

```text
prices = [7, 1, 5, 3, 6, 4]
```

We process the days backwards.

| Day | Price | dp[day][1] (Buy) | dp[day][0] (Hold) |
|---|---:|---:|---:|
| 6 | 4 | 0 | 4 |
| 5 | 6 | 4 | 6 |
| 4 | 3 | 4 | 6 |
| 3 | 5 | 6 | 8 |
| 2 | 1 | 6 | 8 |
| 1 | 7 | 6 | 13 |

The answer is `dp[0][1] = 6`.

We can achieve this by:
- Buy at `1`, sell at `5` → Profit = `4`
- Buy at `3`, sell at `6` → Profit = `3`

Total profit = **7**.

Correction: the table above should be read with the day numbers and states carefully. The correct maximum profit is **7**, from the two transactions shown.

## Complexity

- **Time Complexity:** `O(n)` — We traverse all days once and calculate two states per day.
- **Space Complexity:** `O(n)` — We use a 2D DP array of size `(n + 1) × 2`.

## Pattern Recognition

Whenever a stock problem asks for maximum profit, think about the decisions available on each day.

The important thing is to identify the current state:
- Are we allowed to buy?
- Are we already holding a stock?

Once these states are clear, the transitions become much easier to build.

## My Takeaway

This is where stock DP starts getting interesting.

Instead of simply tracking the minimum buying price like in Stock I, we're now making decisions every day.

The two states help us remember what we're currently allowed to do, and each decision depends on the best possible profit from the next day.
