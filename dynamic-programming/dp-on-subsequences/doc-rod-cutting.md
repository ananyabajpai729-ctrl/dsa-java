# Rod Cutting

## Problem Statement

We are given a rod of length `n` and an array `price[]`, where `price[i]` represents the price of a rod piece of length `i + 1`.

We need to cut the rod into smaller pieces such that the total selling price is maximum.

We can use each piece length multiple times.

## Pattern

**DP on Subsequences – Unbounded Knapsack**

## Intuition

This problem is basically Unbounded Knapsack in disguise.

In Unbounded Knapsack, we have items with weights and values, and we can use each item unlimited times.

Here:

- The length of a rod piece acts as its weight.
- The price of that piece acts as its value.
- The total rod length acts as the knapsack capacity.

For every possible piece length, we have two choices:

- Take the piece.
- Don't take the piece.

Since we can use the same piece length multiple times, this follows the Unbounded Knapsack pattern.

## Approach

1. Create a 2D DP array of size `n × (n + 1)`.
2. Initialize the first row:
   - The first piece has length `1`.
   - We can use it as many times as needed.
   - So, for a rod length `i`, the maximum price is `i * price[0]`.
3. Process the remaining piece lengths from `2` to `n`.
4. For every possible remaining rod length:
   - Calculate the price without taking the current piece.
   - Calculate the price by taking the current piece, if it fits.
5. Store the maximum of both choices.
6. Return `dp[n-1][n]`.

## DP State

`dp[i][curr]` = maximum price we can obtain using rod pieces of lengths `1` to `i + 1`, with a total rod length of `curr`.

## Transition

For every piece length, we have two choices:

**1. Not Take**

Don't use the current piece.

`notTaken = dp[i-1][curr]`

**2. Take**

Use the current piece if its length doesn't exceed the remaining rod length.

`taken = dp[i][curr-piece] + price[i]`

We stay on the same row when taking a piece because we can use that length multiple times.

The final transition is:

`dp[i][curr] = max(taken, notTaken)`

## Base Case

The first piece has length `1`.

Since we can use it unlimited times, a rod of length `curr` can be cut into `curr` pieces of length `1`.

Therefore:

`dp[0][curr] = curr * price[0]`

## Dry Run

Consider:

`price = [2, 5, 7, 8, 10]`

`n = 5`

The rod has length `5`.

Initially, using only pieces of length `1`:

| Rod length | Maximum price |
|---:|---:|
| 0 | 0 |
| 1 | 2 |
| 2 | 4 |
| 3 | 6 |
| 4 | 8 |
| 5 | 10 |

After considering all possible piece lengths, the final DP row becomes:

| Rod length | Maximum price |
|---:|---:|
| 0 | 0 |
| 1 | 2 |
| 2 | 5 |
| 3 | 7 |
| 4 | 10 |
| 5 | 12 |

For a rod of length `5`, one optimal choice is:

- One piece of length `2` → Price `5`
- One piece of length `2` → Price `5`
- One piece of length `1` → Price `2`

Total length = `5`

Total price = `12`

Answer = `12`.

## Complexity Analysis

Let `n` be the length of the rod.

- **Time Complexity:** `O(n²)` — We process `n` possible piece lengths and `n + 1` possible rod lengths.
- **Space Complexity:** `O(n²)` — We use a 2D DP array of size `n × (n + 1)`.

## My Takeaway

Rod Cutting is another variation of Unbounded Knapsack.

The main idea is to treat each possible rod piece length as an item:

- Piece length = Weight
- Piece price = Value
- Rod length = Knapsack capacity

Since we can use the same piece length multiple times, when we take a piece, we stay on the same DP row.
