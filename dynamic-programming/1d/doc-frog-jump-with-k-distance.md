# Frog Jump with K Distances

## Pattern

**Dynamic Programming (DP) + Tabulation**

## Problem Statement

A frog starts at the first stair and wants to reach the last stair.

From any stair, it can jump back by **any distance from 1 to `k` stairs**, as long as it doesn't go before the first stair.

The energy spent on a jump from stair `j` to stair `i` is:

`abs(heights[i] - heights[j])`

We need to find the minimum total energy required to reach the last stair.

## Intuition

In the previous Frog Jump problem, the frog could jump either 1 or 2 stairs.

Now it can jump up to `k` stairs.

So, to reach stair `i`, we need to consider all possible previous stairs:

- `i - 1`
- `i - 2`
- `i - 3`
- ...
- `i - k`

Of course, we only consider indices that are valid.

For every possible jump, we calculate:

**Energy already spent to reach the previous stair + energy needed for the current jump**

Then we choose the minimum among all these possibilities.

## Approach — Tabulation

### 1. Create the DP array

We create a DP array of size `n`.

`dp[i]` represents the minimum energy required to reach stair `i` from stair `0`.

Initially, Java fills the array with zeros. This works here because `dp[0] = 0` is the correct starting value, and every other state is calculated before it is used.

### 2. Traverse the stairs

We start from index `1` and calculate the minimum energy required to reach each stair.

### 3. Try every possible jump

For each stair `i`, we try jump distances from `1` to `k`.

We calculate the previous index:

`prevIndex = i - j`

If `prevIndex >= 0`, the jump is valid.

For every valid jump, we calculate:

`energy = dp[prevIndex] + abs(heights[i] - heights[prevIndex])`

Here:

- `dp[prevIndex]` is the minimum energy already spent reaching the previous stair.
- `abs(heights[i] - heights[prevIndex])` is the energy needed for the current jump.

We keep updating `minEnergy` with the smallest value found.

### 4. Store the answer

After checking all valid jumps, we store the minimum energy in:

`dp[i] = minEnergy`

This becomes the answer we can use when calculating the energy needed for future stairs.

### 5. Return the final answer

Once all stairs have been processed, we return:

`dp[n - 1]`

This stores the minimum energy required to reach the last stair.

## Dry Run

Let's take:

`heights = [10, 30, 20, 10]`

`k = 2`

| Stair `i` | Possible jumps | Minimum energy | `dp[i]` |
|---:|---|---|---:|
| 0 | Starting stair | 0 | 0 |
| 1 | From 0: `0 + 20` | 20 | 20 |
| 2 | From 1: `20 + 10`; from 0: `0 + 10` | 10 | 10 |
| 3 | From 2: `10 + 10`; from 1: `20 + 20` | 20 | 20 |

The final answer is **20**.

The frog can jump directly from stair 0 to stair 2, spending 10 energy, and then jump from stair 2 to stair 3, spending another 10.

Total energy = `10 + 10 = 20`.

## Why Is This Dynamic Programming?

The answer for each stair depends on answers already calculated for earlier stairs.

Instead of checking every possible sequence of jumps, we reuse the minimum energy stored in `dp`.

This is **bottom-up DP (tabulation)** because we calculate the answers in order, starting from the first stair.

## Complexity

Let `n` be the number of stairs and `k` the maximum jump distance.

- **Time Complexity:** `O(n × k)` — for each stair, we try up to `k` possible jumps.
- **Space Complexity:** `O(n)` — we store the minimum energy for every stair.

## Pattern Recognition

When a problem asks for the minimum cost to reach a position, and that position can be reached from several previous positions, think about **DP on indices**.

The useful questions to ask are:

1. What does `dp[i]` represent?
2. Which previous indices can reach `i`?
3. What is the cost of moving from each previous index to `i`?
4. Which previous option gives the minimum total cost?

The main change from the original Frog Jump problem is that **we now loop through all valid jump distances instead of checking only one or two previous stairs**.

## My Takeaway

In the original Frog Jump problem, I checked only two possibilities: jumping from the previous stair or from two stairs before.

Now, I use a loop to check every possible jump from `1` to `k`.

The overall DP idea stays the same. Only the number of previous states I consider has changed.

This is a useful reminder that sometimes, moving from a smaller version of a problem to a more general version only requires changing the transition.
