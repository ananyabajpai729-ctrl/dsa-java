# Best Time to Buy and Sell Stock with Cooldown

## Problem Statement

Given an array `prices`, where `prices[i]` represents the stock price on the `i-th` day, find the maximum profit you can achieve.

You can complete as many transactions as you want, but there is one restriction:

After selling a stock, you cannot buy another stock on the very next day. You must wait for one day (cooldown).

## Pattern

**Dynamic Programming + Stocks DP + Two States**

## Intuition

This problem is very similar to Stock II.

We still have two states:
- Whether we are allowed to buy.
- Whether we are already holding a stock.

But now there's a cooldown period after selling.

Normally, after selling, we would move to the next day and be allowed to buy again.

Here, we have to skip one day after selling. So, instead of moving to `day + 1`, we move to `day + 2`.

That's the only major change in our DP transitions.

## Approach

1. Create a 2D DP array of size `(n + 2) × 2`.
2. Traverse the days backwards, starting from `n - 1`.
3. For every day, calculate the maximum profit for both states.

### State 1: Allowed to Buy (`buy = 1`)

We have two choices:

- **Buy:** Pay today's price and move to the holding state on the next day.
- **Skip:** Do nothing and remain in the buy state.

Take the maximum of both choices.

### State 2: Holding a Stock (`buy = 0`)

Again, we have two choices:

- **Sell:** Earn today's price, but because of the cooldown, move to the buy state two days later.
- **Hold:** Keep holding the stock and move to the next day.

Take the maximum of both choices.

### DP State

`dp[day][1]` = Maximum profit from the current day onwards when we are allowed to buy.

`dp[day][0]` = Maximum profit from the current day onwards when we are already holding a stock.

### Transitions

When we are allowed to buy:

`dp[day][1] = max(-prices[day] + dp[day+1][0], dp[day+1][1])`

When we are holding a stock:

`dp[day][0] = max(prices[day] + dp[day+2][1], dp[day+1][0])`

### Base Cases

When there are no days left, the maximum profit is `0`.

We use an array of size `n + 2` because the selling transition accesses `day + 2`.

The extra rows are initialized to zero and handle these boundary cases.

## Dry Run

Consider:

```text
prices = [1, 2, 3, 0, 2]
```

One optimal sequence is:

- Day 1: Buy at `1`.
- Day 2: Sell at `2` → Profit = `1`.
- Day 3: Cooldown. Cannot buy.
- Day 4: Buy at `0`.
- Day 5: Sell at `2` → Profit = `2`.

Total profit = **3**.

Notice that after selling on day 2, we cannot buy on day 3. We have to wait until day 4.

Our `day + 2` transition handles this automatically.

## Complexity

- **Time Complexity:** `O(n)` — We traverse all days once and calculate two states per day.
- **Space Complexity:** `O(n)` — We use a 2D DP array of size `(n + 2) × 2`.

## Pattern Recognition

Whenever a stock problem introduces a cooldown period, pay attention to what happens after selling.

Instead of moving to the immediately next day, we may need to jump ahead depending on the cooldown duration.

Here, the cooldown is one day, so we move to `day + 2`.

## My Takeaway

This is another small modification of Stock II.

The buy and hold states haven't changed. The only difference is what happens after selling.

Earlier, selling moved us to the next day. Now, selling makes us skip one day before we can buy again.

It's interesting how one small condition changes just one part of the DP transition!
