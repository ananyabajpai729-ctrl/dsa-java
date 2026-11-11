# Best Time to Buy and Sell Stock III

## Problem Statement

Given an array `prices`, where `prices[i]` represents the stock price on the `i-th` day, find the maximum profit you can achieve.

You can complete at most **two transactions**.

A transaction consists of buying one stock and then selling it. You must sell your current stock before buying another one.

## Pattern

**Dynamic Programming + Stocks DP + Three States**

## Intuition

This problem is an extension of Stock II.

In Stock II, we could make unlimited transactions, so we only needed to track whether we were allowed to buy or already holding a stock.

Here, we have one additional restriction: we can complete at most two transactions.

So, along with the current day and buying/holding state, we also need to track how many transactions are still available.

This gives us three DP dimensions.

## Approach

1. Create a 3D DP array of size `(n + 1) × 2 × 3`.
2. Traverse the days backwards, starting from `n - 1`.
3. Loop through the available transactions from `1` to `2`.
4. For every day and transaction count, calculate the maximum profit for both states.

### State 1: Allowed to Buy (`buy = 1`)

We have two choices:

- **Buy:** Pay today's price and move to the holding state. The transaction count stays the same.
- **Skip:** Do nothing and remain in the buy state.

Take the maximum of both choices.

### State 2: Holding a Stock (`buy = 0`)

Again, we have two choices:

- **Sell:** Earn today's price and decrease the remaining transaction count by one.
- **Hold:** Keep the stock and move to the next day without changing the transaction count.

Take the maximum of both choices.

### DP State

`dp[day][buy][transactions]` represents the maximum profit we can make from the current day onwards, given our current buying/holding state and the number of transactions still available.

- `buy = 1`: We are allowed to buy.
- `buy = 0`: We are currently holding a stock.
- `transactions`: Number of transactions still available.

### Transitions

When we are allowed to buy:

`dp[day][1][transactions] = max(-prices[day] + dp[day+1][0][transactions], dp[day+1][1][transactions])`

When we are holding a stock:

`dp[day][0][transactions] = max(prices[day] + dp[day+1][1][transactions-1], dp[day+1][0][transactions])`

### Base Cases

- When `day == n`, there are no days left, so the profit is `0`.
- When `transactions == 0`, we cannot complete any more transactions, so the profit is `0`.

Both base cases are handled by the zero-initialized DP array.

## Dry Run

Consider:

```text
prices = [3, 3, 5, 0, 0, 3, 1, 4]
```

We can complete at most two transactions.

One optimal choice is:

- Buy at `3`, sell at `5` → Profit = `2`
- Buy at `0`, sell at `4` → Profit = `4`

Total profit = **6**.

Our DP explores the buy, skip, sell, and hold choices while keeping track of the remaining transactions.

The final answer is `dp[0][1][2] = 6`.

## Complexity

- **Time Complexity:** `O(n × 2 × 2)` = `O(n)` — We process every day, two buying/holding states, and at most two transaction counts.
- **Space Complexity:** `O(n × 2 × 3)` = `O(n)` — We use a 3D DP array.

## Pattern Recognition

When a stock problem introduces a limit on the number of transactions, we need to include the remaining transaction count in our DP state.

The key observation is that we decrease the transaction count only when we sell, because that completes one full transaction.

## My Takeaway

Stock DP is becoming easier to understand now.

- Stock I: Only one transaction, solved using a greedy approach.
- Stock II: Unlimited transactions, using two DP states.
- Stock III: At most two transactions, so we add the transaction count as another DP dimension.

The main idea stays the same. We just keep adding state information whenever the problem introduces a new restriction.
