# Longest Common Substring

## Problem Statement

Given two strings, `str1` and `str2`, find the length of their longest common substring.

A substring consists of consecutive characters, so unlike a subsequence, we cannot skip characters in between.

## Pattern

**Dynamic Programming – Strings**

## Intuition

This problem looks similar to Longest Common Subsequence, but there's one important difference: the characters must be continuous.

If the current characters match, we extend the common substring ending at the previous pair of positions.

If they don't match, the current substring breaks, so we reset the DP value to `0`.

Also, unlike LCS, the answer is not necessarily at the bottom-right corner of the DP table. We need to keep track of the maximum value found anywhere in the table.

## Approach

I used 2D tabulation.

1. Create a DP array of size `(n+1) x (m+1)`.
2. Traverse both strings starting from index `1`.
3. If the current characters match, extend the previous diagonal value by `1`.
4. Otherwise, set the current DP cell to `0`.
5. Keep updating the maximum length in a separate variable.
6. Return the maximum length.

## DP State

`dp[i][j]` represents the length of the common substring ending at `str1[i-1]` and `str2[j-1]`.

## Transition

If the current characters match:

\[
dp[i][j] = dp[i-1][j-1] + 1
\]

If they don't match:

\[
dp[i][j] = 0
\]

The final answer is the maximum value in the entire DP table.

## Base Cases

The first row and first column are initialized to `0`.

If either string is empty, the answer is also `0`.

## Dry Run

Consider:

```text
str1 = "abcjklp"
str2 = "acjkp"
```

The matching continuous portions include:

- `"a"` → length 1
- `"cjk"` → length 3
- `"p"` → length 1

The longest common substring is `"cjk"`.

Therefore, the answer is **3**.

## Complexity Analysis

Let `n` and `m` be the lengths of the two strings.

- **Time Complexity:** \(O(n \times m)\), since every DP cell is visited once.
- **Space Complexity:** \(O(n \times m)\), for the 2D DP array.

## My Takeaway

The main difference between LCS and Longest Common Substring is what happens on a mismatch.

In LCS, we can skip characters, so we take the maximum from the top and left cells.

In substring, continuity matters. A mismatch breaks the current match, so we reset the value to zero.

Another important point is that we maintain a separate answer variable because the maximum substring can end anywhere in the DP table.
