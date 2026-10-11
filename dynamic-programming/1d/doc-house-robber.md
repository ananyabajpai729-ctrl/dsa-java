# House Robber

## Pattern

**Dynamic Programming (DP) + Tabulation + Take or Skip**

## Problem Statement

A robber wants to rob houses arranged in a row.

Each house has some amount of money, but there's one rule: **the robber cannot rob two adjacent houses**.

Given an array `nums`, where `nums[i]` is the money in house `i`, find the maximum amount of money the robber can collect without robbing adjacent houses.

## Intuition

For every house, we have two choices:

- **Take:** Rob the current house.
- **Skip:** Don't rob the current house.

If we rob the current house, we cannot rob the previous house. So we add its money to the maximum amount we could collect up to house `i - 2`.

If we skip the current house, we simply keep the maximum amount we could collect up to house `i - 1`.

We compare both choices and store whichever gives us more money.

In short:

**Maximum money = max(take, skip)**

## Approach — Tabulation

### 1. Create the DP array

We create a DP array of size `n`.

`dp[i]` represents the **maximum money we can rob from houses `0` to `i` without robbing adjacent houses**.

### 2. Handle the base cases

**First house:**

`dp[0] = nums[0]`

With only one house, we can rob it and collect its money.

**Second house:**

`dp[1] = max(nums[0], nums[1])`

Since the first two houses are adjacent, we can rob only one of them. We choose the one with more money.

### 3. Calculate the answer for every house

Starting from index `2`, we calculate two possibilities.

**Option 1: Take the current house**

`take = nums[i] + dp[i - 2]`

We add the current house's money to the best amount from two houses back, because the previous house cannot be robbed.

**Option 2: Skip the current house**

`skip = dp[i - 1]`

We keep the best amount we could collect up to the previous house.

Then we store the better choice:

`dp[i] = max(take, skip)`

### 4. Return the answer

After processing every house, `dp[n - 1]` contains the maximum money we can rob.

## Dry Run

Let's take:

`nums = [2, 7, 9, 3, 1]`

| Index | Money | Take | Skip | `dp[i]` |
|---:|---:|---:|---:|---:|
| 0 | 2 | — | — | 2 |
| 1 | 7 | — | — | 7 |
| 2 | 9 | `9 + 2 = 11` | 7 | 11 |
| 3 | 3 | `3 + 7 = 10` | 11 | 11 |
| 4 | 1 | `1 + 11 = 12` | 11 | 12 |

The answer is **12**.

We rob houses with amounts `2`, `9`, and `1`, collecting:

`2 + 9 + 1 = 12`

We never rob two adjacent houses.

## Why Is This Dynamic Programming?

At every house, we solve a smaller version of the same problem.

We already know the maximum money we can collect up to the previous house and the house before that. We use those answers to decide whether taking the current house is better than skipping it.

We store these results in `dp` instead of recalculating them.

This is **bottom-up DP (tabulation)**.

## Complexity

- **Time Complexity:** `O(n)` — we process every house once.
- **Space Complexity:** `O(n)` — the DP array stores the best answer for every index.

## Pattern Recognition

When a problem gives you a sequence and asks for the maximum or minimum result while following a restriction, think about the choices available at each index.

Here, the restriction is that we cannot take two adjacent houses.

The key questions are:

1. What happens if I take the current element?
2. What happens if I skip it?
3. Which earlier DP states do I need for each choice?
4. Which choice gives the better result?

This leads to the **take-or-skip DP pattern**.

## My Takeaway

In Frog Jump, I was choosing the jump that required the least energy.

Here, I'm choosing between taking and skipping a house to collect the maximum money.

The useful habit is the same: define what `dp[i]` means, identify the choices available, and build the current answer using previously calculated states.

I can also optimize the space to `O(1)` because each state only needs the previous two states, but for now, I'm sticking with tabulation to get comfortable with the DP logic.
