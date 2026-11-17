# Unbounded Knapsack

## Problem Statement

We are given `n` items, each having a weight and a value, along with a knapsack of capacity `capacity`.

We need to maximize the total value without exceeding the given capacity.

The important difference from 0/1 Knapsack is that **each item can be selected unlimited times**.

## Pattern

**DP on Subsequences – Unbounded Knapsack**

## Intuition

In 0/1 Knapsack, we could either take or skip an item, but we could take each item only once.

Here, we can take the same item multiple times.

So the choices remain the same:

- Take the current item.
- Don't take the current item.

But when we take an item, we stay on the same row because we are allowed to select it again.

Since we want to maximize the total value, we take the maximum of both choices.

## Approach

1. Create a 2D DP array of size `n × (capacity + 1)`.
2. Initialize the first row using the first item:
   - For every capacity, calculate how many copies of the first item can fit.
   - Multiply that number by its value.
3. Process the remaining items one by one.
4. For every item and current capacity:
   - Calculate the value without taking the current item.
   - Calculate the value by taking the current item, if it fits.
5. Store the maximum of both choices.
6. Return `dp[n-1][capacity]`.

## DP State

`dp[i][curr]` = maximum value we can obtain using items from index `0` to `i` with capacity `curr`.

## Transition

For every item, we have two choices:

**1. Not Take**

Don't include the current item. Move to the previous row.

`notTaken = dp[i-1][curr]`

**2. Take**

Include the current item if its weight doesn't exceed the remaining capacity.

`taken = dp[i][curr - wt[i]] + val[i]`

Notice that we stay on the same row when taking an item because it can be selected unlimited times.

The final transition is:

`dp[i][curr] = max(taken, notTaken)`

## Base Case

We initialize the first row using the first item.

For every capacity:

`copies = curr / wt[0]`

`dp[0][curr] = copies * val[0]`

This gives us the maximum value possible when we can use only the first item.

## Dry Run

Consider:

`val = [10, 40, 50, 70]`

`wt = [1, 3, 4, 5]`

`capacity = 8`

Initially, using only the first item (weight 1, value 10):

| Capacity | Maximum value |
|---:|---:|
| 0 | 0 |
| 1 | 10 |
| 2 | 20 |
| 3 | 30 |
| 4 | 40 |
| 5 | 50 |
| 6 | 60 |
| 7 | 70 |
| 8 | 80 |

After considering all items, the final DP row is:

| Capacity | Maximum value |
|---:|---:|
| 0 | 0 |
| 1 | 10 |
| 2 | 20 |
| 3 | 40 |
| 4 | 50 |
| 5 | 70 |
| 6 | 80 |
| 7 | 90 |
| 8 | 110 |

For capacity `8`, we can take:

- One item with weight `3`, value `40`.
- One item with weight `5`, value `70`.

Total weight = `8`

Total value = `110`

Answer = `110`.

## Complexity Analysis

Let `n` be the number of items and `capacity` be the knapsack capacity.

- **Time Complexity:** `O(n * capacity)` — We calculate every state in the DP table once.
- **Space Complexity:** `O(n * capacity)` — We use a 2D DP array.

## My Takeaway

This problem is the direct opposite of 0/1 Knapsack in terms of item reuse.

The transition is almost identical, but the important difference is:

- **0/1 Knapsack:** When taking an item, move to the previous row.
- **Unbounded Knapsack:** When taking an item, stay on the same row.

This same idea also appears in Coin Change and Coin Change II.
