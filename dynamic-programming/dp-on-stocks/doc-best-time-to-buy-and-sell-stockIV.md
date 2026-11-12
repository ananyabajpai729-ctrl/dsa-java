# Best Time to Buy and Sell Stock IV

## Problem Statement

Given an array `prices`, where `prices[i]` represents the stock price on the `i-th` day, find the maximum profit you can achieve.

You are allowed to complete at most `k` transactions.

A transaction consists of buying one stock and then selling it. You must sell your current stock before buying another one.

## Pattern

**Dynamic Programming + Stocks DP + Space Optimization**

## Intuition

This problem is an extension of Stock III.

Previously, we had a fixed limit of two transactions. Now, the limit is given as `k`.

The DP states remain exactly the same:
- Current day
- Whether we are allowed to buy or already holding a stock
- Number of transactions still available

But we don't need to store the DP values for every day.

If we look at our transitions, every state depends only on the next day. So, we can maintain just two 2D arrays:

- `next`: Stores the answers for the next day.
- `curr`: Stores the answers for the current day.

This reduces the space complexity from `O(n × 2 × k)` to `O(2 × k)`.

## Approach

1. Create a 2D array `next` of size `2 × (k + 1)`, initialized with zeroes.
2. Traverse the days backwards, starting from `n - 1`.
3. For every day, create another 2D array `curr` of the same size.
4. Loop through the remaining transaction counts from `1` to `k`.
5. Calculate the maximum profit for both states.

### State 1: Allowed to Buy (`buy = 1`)

We have two choices:

- **Buy:** Pay today's price and move to the holding state. The transaction count stays the same.
- **Skip:** Do nothing and remain in the buy state.

`curr[1][t] = max(-prices[day] + next[0][t], next[1][t])`

### State 2: Holding a Stock (`buy = 0`)

We again have two choices:

- **Sell:** Earn today's price and decrease the remaining transaction count by one.
- **Hold:** Keep holding the stock and move to the next day.

`curr[0][t] = max(prices[day] + next[1][t-1], next[0][t])`

6. After processing all transaction counts for a day, assign `next = curr`.
7. Finally, return `next[1][k]`.

### DP State

`dp[day][buy][t]` represents the maximum profit possible from the current day onwards, given the current buying/holding state and `t` remaining transactions.

We only store the current and next day's values instead of the entire DP table.

### Base Cases

- After the last day, no more profit can be made, so all states are `0`.
- When `t = 0`, no transactions are available, so the profit is `0`.

Both are handled by the zero-initialized arrays.

## Dry Run

Consider:

```text
prices = [2, 4, 1, 7]
k = 2
```

We can make at most two transactions.

One optimal choice is:

- Buy at `2`, sell at `4` → Profit = `2`
- Buy at `1`, sell at `7` → Profit = `6`

Total profit = **8**.

The DP processes the days backwards and calculates the best possible profit for each state and remaining transaction count.

The final answer is `next[1][2] = 8`.

## Complexity

Let `n` be the number of days.

- **Time Complexity:** `O(n × k)` — For every day, we process all `k` transaction counts and two states.
- **Space Complexity:** `O(k)` — We maintain two arrays of size `2 × (k + 1)`.

## Pattern Recognition

Whenever a stock problem introduces a transaction limit, include the number of remaining transactions in the DP state.

If every state depends only on the next day, we can optimize space by keeping just the current and next day's DP values.

## My Takeaway

The progression is becoming clearer:

- Stock I: One transaction → Greedy.
- Stock II: Unlimited transactions → Two DP states.
- Stock III: At most two transactions → Three-dimensional DP.
- Stock IV: At most `k` transactions → Same DP logic, but space optimized using two 2D arrays.

The interesting part is that the logic hasn't really changed. We're just storing less information because we noticed that we only need the next day's results.
