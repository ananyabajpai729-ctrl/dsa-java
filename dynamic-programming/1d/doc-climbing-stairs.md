# Climbing Stairs

## Pattern

**Dynamic Programming (DP) + Tabulation**

## Problem Statement

We have `n` stairs, and we need to reach the top.

At every step, we can either climb **1 stair or 2 stairs**.

We need to find the total number of distinct ways to reach the top.

## Intuition

Let's think about the last step we take to reach stair `n`.

There are only two possibilities:

- We came from stair `n - 1` by taking 1 step.
- We came from stair `n - 2` by taking 2 steps.

So, the total number of ways to reach stair `n` is the sum of the ways to reach these two previous stairs.

In other words:

**ways to reach `n` = ways to reach `n - 1` + ways to reach `n - 2`**

This gives us the relation:

`dp[i] = dp[i - 1] + dp[i - 2]`

And that's the main idea behind this problem!

## Approach — Tabulation

Instead of calculating the same smaller problems again and again, we store their answers in a DP array.

### 1. Create the DP array

We create an array of size `n + 1`.

`dp[i]` represents the number of distinct ways to reach stair `i`.

We use `n + 1` because we want to store answers for stairs from `0` to `n`.

### 2. Set the base cases

- `dp[0] = 1`
- `dp[1] = 1`

For stair 1, there is only one way: take one step.

For stair 0, we consider there to be one way to stay at the starting position — doing nothing. This also helps establish the recurrence naturally.

### 3. Fill the DP array

We start from stair 2 and move towards stair `n`.

For every stair `i`:

`dp[i] = dp[i - 1] + dp[i - 2]`

Both previous answers are already calculated, so we can use them to find the current answer.

### 4. Return the answer

Once the loop finishes, `dp[n]` contains the total number of ways to reach the top.

## Dry Run

Let's take `n = 5`.

| Stair `i` | Calculation | `dp[i]` |
|---:|---|---:|
| 0 | Base case | 1 |
| 1 | Base case | 1 |
| 2 | `dp[1] + dp[0]` | 2 |
| 3 | `dp[2] + dp[1]` | 3 |
| 4 | `dp[3] + dp[2]` | 5 |
| 5 | `dp[4] + dp[3]` | 8 |

So the answer is **8 ways**.

Notice how every new answer is built using the two answers before it.

## Why Is This Dynamic Programming?

We break the problem into smaller problems:

- Find the ways to reach stair 1.
- Find the ways to reach stair 2.
- Use these answers to find the ways to reach stair 3.
- Continue until we reach stair `n`.

We store the answers instead of recalculating them.

This is **tabulation**, a bottom-up approach to DP. We start with the smallest subproblems and gradually build the answer to the original problem.

## Complexity

- **Time Complexity:** `O(n)` — we calculate each DP state once.
- **Space Complexity:** `O(n)` — we store the answers in a DP array of size `n + 1`.

## What About Constant Space?

This problem can also be solved using just three variables, because each new answer depends only on the previous two answers.

That would reduce the space complexity to `O(1)`.

But for now, I'm starting with **tabulation using a DP array** so I can clearly understand how states are defined, how base cases work, and how previous states help calculate the current state.

Space optimization can come later.

## My Takeaway

The most important thing I learned from this problem is that I don't need to think about the entire problem at once.

I can ask myself:

**What smaller problems can help me solve the current problem?**

Here, the answer is simple: the number of ways to reach the previous two stairs.

And that's my first step into Dynamic Programming!
