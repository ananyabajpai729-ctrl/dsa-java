# Longest Common Subsequence (LCS)

## Problem Statement

Given two strings `text1` and `text2`, find the length of their longest common subsequence.

A subsequence is formed by deleting zero or more characters without changing the relative order of the remaining characters.

## Pattern

**Dynamic Programming – Subsequences / String DP**

## Intuition

At every position, we compare the current characters of both strings.

- If they match, we can include that character in our common subsequence.
- If they don't match, we have to skip one character from either string and take the better result.

Instead of solving the same smaller problems repeatedly, we store their answers in a 2D DP array.

## Approach

I used tabulation to build the answer from smaller prefixes of both strings.

1. Create a 2D DP array of size `(n+1) x (m+1)`.
2. Start iterating from index `1` for both strings.
3. If the current characters match, add `1` to the answer from the previous diagonal cell.
4. Otherwise, take the maximum of the answer obtained by skipping either character.
5. The final answer is stored at `dp[n][m]`.

## DP State

`dp[i][j]` represents the length of the longest common subsequence between the first `i` characters of `text1` and the first `j` characters of `text2`.

## Transition

If the current characters match:

\[
dp[i][j] = 1 + dp[i-1][j-1]
\]

Otherwise:

\[
dp[i][j] = \max(dp[i-1][j], dp[i][j-1])
\]

## Base Cases

If either string is empty, the LCS length is `0`.

Therefore:

- `dp[0][j] = 0`
- `dp[i][0] = 0`

Java initializes the entire DP array with zero, so no separate initialization is needed.

## Dry Run

Consider:

```text
text1 = "abcde"
text2 = "ace"
```

The DP table:

| | "" | a | c | e |
|---|---:|---:|---:|---:|
| "" | 0 | 0 | 0 | 0 |
| a | 0 | 1 | 1 | 1 |
| b | 0 | 1 | 1 | 1 |
| c | 0 | 1 | 2 | 2 |
| d | 0 | 1 | 2 | 2 |
| e | 0 | 1 | 2 | 3 |

The longest common subsequence is `"ace"`, so the answer is **3**.

## Complexity Analysis

Let `n` be the length of `text1` and `m` be the length of `text2`.

- **Time Complexity:** \(O(n \times m)\), since we fill every cell of the DP table once.
- **Space Complexity:** \(O(n \times m)\), for the 2D DP array.

## My Takeaway

The important part of this problem is understanding what happens when the characters don't match. We explore both choices—skipping a character from either string—and keep the better result. This gives us the LCS length for every pair of prefixes.
