# Perfect Sum

## Problem Statement

We are given an array and a target sum `K`. We need to count the number of subsets whose sum is exactly equal to `K`.

Each element can either be included or excluded from a subset.

## Pattern

**DP on Subsequences – Counting Subsets**

## Intuition

In the previous subset problems, we were checking whether a particular sum was possible or finding the closest possible sum.

Here, the question is slightly different. We need to **count how many subsets** can form the target sum.

The choices remain the same:

- Take the current element.
- Don't take the current element.

But instead of storing a boolean value, we store the number of ways to form each sum.

## Approach

1. Create a 2D DP array of size `n × (K + 1)`.
2. Initialize `dp[i][0] = 1` because there is exactly one way to form sum `0`: by choosing an empty subset.
3. For the first element, mark `dp[0][arr[0]] = 1` if it doesn't exceed `K`.
4. For every remaining element, calculate the number of ways to form each sum using the take/not-take choices.
5. Apply modulo `1000000007` to avoid integer overflow.
6. Return `dp[n-1][K]`.

## DP State

`dp[i][sum]` = number of subsets that form the given sum using elements from index `0` to `i`.

## Transition

For every element, we have two choices:

**1. Not Take**

Don't include the current element.

`notTaken = dp[i-1][sum]`

**2. Take**

Include the current element, provided it doesn't exceed the required sum.

`taken = dp[i-1][sum - arr[i]]`

So the final transition is:

`dp[i][sum] = (taken + notTaken) % modulo`

Unlike the previous problems, here we add the two choices because we want to count all possible subsets.

## Base Cases

- `dp[i][0] = 1` because we can always form sum `0` by choosing no elements.
- If `arr[0] <= K`, then `dp[0][arr[0]] = 1` because there is one way to form that sum using the first element.

**Note:** The given constraints guarantee that array elements are at least `1`, so we don't need special handling for zeroes.

## Dry Run

Consider:

`arr = [1, 2, 3, 3]`

`K = 6`

Let's see how many ways we can form sum `6`.

Possible subsets:

1. `[1, 2, 3]` using the first `3`
2. `[1, 2, 3]` using the second `3`
3. `[3, 3]`

Therefore, the answer is `3`.

The final DP row represents the number of ways to form each sum using all four elements.

| Sum | Number of ways |
|---|---:|
| 0 | 1 |
| 1 | 1 |
| 2 | 1 |
| 3 | 2 |
| 4 | 2 |
| 5 | 2 |
| 6 | 3 |

So, `dp[n-1][6] = 3`.

## Complexity Analysis

Let `n` be the number of elements and `K` be the target sum.

- **Time Complexity:** `O(n * K)` — We traverse all states of the DP table.
- **Space Complexity:** `O(n * K)` — We use a 2D DP array.

## My Takeaway

This problem is a counting variation of Subset Sum.

The main difference is that instead of storing `true` or `false`, we store the number of ways to form each sum.

The take/not-take choices are still the same, but we add their results instead of taking their logical OR.
