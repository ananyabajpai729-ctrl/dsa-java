# Ninja Training

## Pattern

**Dynamic Programming (DP) + Tabulation + State Tracking**

## Problem Statement

A ninja has to train for `n` days. Every day, there are 3 possible activities, and each activity gives a certain number of points.

The ninja can perform only one activity per day.

The rule is that **the same activity cannot be performed on two consecutive days**.

We need to find the maximum total points the ninja can earn over all `n` days.

## Intuition

For every day, the ninja has three activities to choose from.

But we can't choose an activity blindly. We also need to remember which activity was performed on the previous day, because we cannot repeat it.

So, instead of tracking just the day, we track two things:

- The current day.
- The activity that we cannot perform today.

This second part is what makes the DP state a little different from the previous problems.

## Approach — Tabulation

### 1. Create the DP array

We create a 2D DP array of size `n × 4`.

`dp[day][last]` represents the **maximum points we can earn up to the current day when activity `last` is not allowed on that day**.

Why 4 values for `last` when there are only 3 activities?

- `last = 0` means activity 0 is not allowed today.
- `last = 1` means activity 1 is not allowed today.
- `last = 2` means activity 2 is not allowed today.
- `last = 3` means no activity is restricted. This is our special starting state.

### 2. Handle the base cases

On day 0, we can choose any activity except the one represented by `last`.

So:

- `dp[0][0]` = maximum points from activities 1 and 2.
- `dp[0][1]` = maximum points from activities 0 and 2.
- `dp[0][2]` = maximum points from activities 0 and 1.
- `dp[0][3]` = maximum points among all three activities.

These are our starting answers. We use them to calculate the maximum points for the remaining days.

### 3. Traverse each day

We start from day 1 because day 0 is already initialized.

For every day, we consider all four possible values of `last`.

Then, we try all three activities.

### 4. Choose a valid activity

We only consider an activity if:

`activity != last`

This ensures that the activity represented by `last` is not selected.

For every valid activity, we calculate:

`points = matrix[day][activity] + dp[day - 1][activity]`

Let's understand why we use `dp[day - 1][activity]`.

Suppose we choose activity 1 today.

Then activity 1 cannot be performed on the previous day, because repeating it on consecutive days is not allowed.

So we use the previous day's state where activity 1 is restricted:

`dp[day - 1][1]`

We add today's points to that previous best score.

Finally, we keep the maximum among all valid choices in `dp[day][last]`.

### 5. Return the answer

We return:

`dp[n - 1][3]`

The last index represents the final day, and `last = 3` means no activity is restricted.

Therefore, this state gives us the maximum points possible across all days.

## Dry Run

Let's take this example:

| Day | Activity 0 | Activity 1 | Activity 2 |
|---:|---:|---:|---:|
| 0 | 10 | 40 | 70 |
| 1 | 20 | 50 | 80 |
| 2 | 30 | 60 | 90 |

One optimal choice is:

- Day 0: Activity 2 → 70 points
- Day 1: Activity 1 → 50 points
- Day 2: Activity 2 → 90 points

Activity 2 is not repeated on consecutive days because activity 1 is performed in between.

Total points:

`70 + 50 + 90 = 210`

The maximum answer is **210**.

The DP table considers all valid activity choices, so it finds this maximum without needing to manually list every possible schedule.

## Why Is This Dynamic Programming?

The decision for the current day depends on the best score we could achieve on the previous day.

But the previous day's answer depends on which activity we want to perform today, because that activity becomes restricted for the previous day.

That's why tracking only `day` is not enough. We also need the `last` state.

We calculate these states from day 0 to day `n - 1`, storing the best answer for each one.

This is **bottom-up DP (tabulation)**.

## Complexity

Let `n` be the number of days.

- **Time Complexity:** `O(n)` — we process every day, 4 possible `last` states, and 3 activities. The number of operations per day is constant.
- **Space Complexity:** `O(n)` — the DP table has `n × 4` entries.

## Pattern Recognition

When a problem asks you to maximize points or minimize cost while following a restriction based on a previous choice, think about **DP with state tracking**.

Ask yourself:

1. What information from the previous decision affects my current choice?
2. Can I include that information in my DP state?
3. Which choices are valid for the current state?
4. How do I combine the current reward with the best previous result?

Here, the extra state is `last`, which tracks the activity that cannot be chosen today.

## My Takeaway

In House Robber, I had two choices: take or skip the current house.

In Ninja Training, I have three choices, but one of them may be restricted depending on the previous activity.

The important learning here is that **sometimes, tracking the index alone isn't enough**. We need an additional state to represent the information that affects our choices.

And that additional state becomes part of the DP array.
