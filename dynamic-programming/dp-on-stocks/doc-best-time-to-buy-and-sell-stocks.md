# Best Time to Buy and Sell Stock

## Problem Statement

Given an array `prices`, where `prices[i]` represents the stock price on the `i-th` day, find the maximum profit you can achieve.

You can:
- Buy the stock on one day.
- Sell it on a future day.

You can complete only one transaction (one buy and one sell).

If no profit is possible, return `0`.

## Pattern

**Greedy + Single Pass**

## Intuition

The idea is pretty simple.

If I want to sell the stock today, I should have bought it on the cheapest day before today.

So, instead of trying every possible buying and selling day, I can just keep track of the minimum price I've seen so far.

For every day, I'll check how much profit I would make if I sold on that day.

## Approach

1. Initialize `minPrice` with the first day's price.
2. Initialize `maxProfit` with `0`, because we don't want to make a loss.
3. Start traversing the array from the second day.
4. For every day:
   - Calculate the profit by subtracting the minimum price seen so far from today's price.
   - Update `maxProfit` if this profit is greater.
   - Update `minPrice` if today's price is smaller.
5. Return `maxProfit`.

### Variables

- `minPrice`: The minimum stock price encountered so far. This gives us the best buying opportunity.
- `maxProfit`: The maximum profit found so far.

## Dry Run

Consider:

```text
prices = [7, 1, 5, 3, 6, 4]
```

| Day | Price | Min Price | Profit if Sold Today | Max Profit |
|---|---:|---:|---:|---:|
| 1 | 7 | 7 | - | 0 |
| 2 | 1 | 1 | -6 | 0 |
| 3 | 5 | 1 | 4 | 4 |
| 4 | 3 | 1 | 2 | 4 |
| 5 | 6 | 1 | 5 | 5 |
| 6 | 4 | 1 | 3 | 5 |

The maximum profit is **5**.

We buy at price `1` and sell at price `6`.

## Complexity

- **Time Complexity:** `O(n)` — We traverse the array only once.
- **Space Complexity:** `O(1)` — We only use two variables.

## Pattern Recognition

Whenever a problem asks for the maximum difference between a current value and a previous value, maintaining the minimum value seen so far can be useful.

Here, the minimum price gives us the best possible buying opportunity for each selling day.

## My Takeaway

Instead of checking every buy-sell combination, I only need to remember the cheapest price I've seen so far.

For every new price, I ask myself: "If I sell today, how much profit can I make?"

Simple one-pass greedy approach!
