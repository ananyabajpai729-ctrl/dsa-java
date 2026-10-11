# Frog Jump

## Pattern

**Dynamic Programming (DP) + Tabulation**

## Problem Statement

A frog is standing at the first stair and wants to reach the last stair.

It can jump either **1 stair or 2 stairs** at a time.

The energy used for a jump from stair `i` to stair `j` is:

`abs(heights[i] - heights[j])`

We need to find the **minimum energy required** for the frog to reach the last stair.

## Intuition

To reach any stair `i`, the frog has only two possible ways to get there:

- Jump from stair `i - 1`.
- Jump from stair `i - 2`.

We need to calculate the energy required for both options and choose the one that costs less.

But we also need to include the energy the frog has already spent reaching the previous stair.

So we compare:

- Energy to reach `i - 1` + energy for the jump from `i - 1` to `i`.
- Energy to reach `i - 2` + energy for the jump from `i - 2` to `i`.

We take the minimum of these two values.

## Approach — Tabulation

### 1. Create the DP array

We create a DP array of the same size as `heights`.

`dp[i]` represents the **minimum energy required to reach stair `i` from stair `0`**.

This is important because we're not just calculating the energy of the current jump. We're calculating the minimum total energy spent from the beginning.

### 2. Set the base cases

- `dp[0] = 0` because the frog is already standing on the first stair.
- `dp[1] = abs(heights[1] - heights[0])` because there's only one possible jump to reach the second stair.

### 3. Calculate the minimum energy

We start from index `2` and calculate the minimum energy needed to reach each stair.

**Option 1: Jump from the previous stair**

Total energy = `dp[i - 1] + abs(heights[i] - heights[i - 1])`

**Option 2: Jump from two stairs before**

Total energy = `dp[i - 2] + abs(heights[i] - heights[i - 2])`

We store the smaller value in `dp[i]`.

### 4. Return the answer

The last index represents the final stair, so we return:

`dp[heights.length - 1]`

## Dry Run

Suppose:

`heights = [10, 20, 30, 10]`

| Index | Height | Minimum energy calculation | `dp[i]` |
|---:|---:|---|---:|
| 0 | 10 | Starting stair | 0 |
| 1 | 20 | `0 + abs(20 - 10)` | 10 |
| 2 | 30 | `min(0 + 20, 10 + 10)` | 20 |
| 3 | 10 | `min(10 + 10, 20 + 20)` | 20 |

The answer is **20**.

The frog can take the route `10 → 20 → 10`, spending `10 + 10 = 20` energy.

Notice that at every stair, we use the best answer already calculated for the previous stairs.

## Why Is This Dynamic Programming?

The minimum energy needed to reach the current stair depends on the minimum energy needed to reach earlier stairs.

Instead of trying every possible sequence of jumps separately, we store the best answer for each stair and reuse it.

Since we calculate the DP array from left to right, this is **bottom-up DP (tabulation)**.

## Complexity

- **Time Complexity:** `O(n)` — we calculate each stair's answer once.
- **Space Complexity:** `O(n)` — the DP array stores the minimum energy for every stair.

Here, `n` is the number of stairs.

## Pattern Recognition

When a problem asks for the minimum cost or energy to reach a position, and the current position can be reached from a few previous positions, think about **DP on indices**.

Ask yourself:

1. What does `dp[i]` represent?
2. Which previous positions can reach index `i`?
3. What is the cost of each option?
4. Should I take the minimum or maximum?

For this problem, the state is the minimum energy needed to reach stair `i`, and the transition compares the two possible jumps.

## My Takeaway

In Climbing Stairs, I added the number of ways from the previous two stairs.

Here, I'm doing something slightly different: I'm calculating the **minimum cost** of reaching each stair.

The structure is similar, but the question changes what I store in the DP array.

That's the part I want to get comfortable with as I start learning DP.
