# Accounts Merge

## Pattern

**Disjoint Set Union (DSU) + HashMap + Sorting**

---

## Problem Statement

We are given multiple accounts.

Each account has:

* A person's name
* One or more email addresses

Two accounts belong to the same person if they have **at least one common email**.

We need to merge all accounts that belong to the same person and return the merged emails in sorted order.

---

## Intuition

The important thing here is that **the name itself doesn't tell us whether two accounts are the same person**.

The email does.

For example:

* Account 0 → `a@gmail.com`, `b@gmail.com`
* Account 1 → `c@gmail.com`, `a@gmail.com`

Since both accounts contain `a@gmail.com`, they belong to the same person.

So we can use the email as the connection between accounts.

This is basically a **connected components** problem.

Instead of explicitly building a graph, we can use **DSU** to merge accounts whenever we find the same email in two different accounts.

---

## Approach

### 1. Create DSU for all accounts

Each account initially belongs to its own component.

So initially:

`0 → 0`

`1 → 1`

`2 → 2`

and so on.

We use **union by size + path compression** to efficiently merge accounts.

---

### 2. Map every email to an account

We maintain:

`HashMap<String, Integer>`

where:

**email → account index**

While going through every account:

* If we see an email for the first time, store its account index in the map.
* If we've already seen that email, then the current account and the previously stored account belong to the same person.

So we perform:

`union(currentAccount, previousAccount)`

---

## Example

Suppose we have:

* Account 0 → `a@gmail.com`, `b@gmail.com`
* Account 1 → `c@gmail.com`
* Account 2 → `b@gmail.com`, `d@gmail.com`

While processing:

### Account 0

`a@gmail.com` → store `0`

`b@gmail.com` → store `0`

### Account 1

`c@gmail.com` → store `1`

### Account 2

`b@gmail.com` is already present in the map.

It belongs to account `0`.

So we merge:

`2 ↔ 0`

Now accounts `0` and `2` belong to the same DSU component.

---

## 3. Group emails according to their final parent

After all accounts have been merged, we go through the email map.

For every email:

1. Get the account it was originally associated with.
2. Find its final DSU parent.
3. Put the email into that parent's list.

For example, after merging:

`0 ← 2`

Both accounts will have the same root.

So their emails end up in the same list.

This is why we use:

`findPar(account)`

before adding the email.

---

## 4. Sort the emails

The problem requires emails to be returned in sorted order.

So for every merged account:

`Collections.sort(...)`

is used.

---

## 5. Add the person's name

The DSU component is represented by its root account.

The name is taken from that root account:

`accounts.get(i).get(0)`

Then we add all the sorted emails after the name.

---

## Why DSU works here

The key observation is:

> **A common email means two accounts belong to the same person.**

So whenever we find the same email again, we merge the two corresponding account indices.

Even if the connection is indirect, DSU handles it.

For example:

`Account 0 -- email A -- Account 1`

and

`Account 1 -- email B -- Account 2`

Even though Account 0 and Account 2 don't directly share an email, they end up in the same DSU component.

That's exactly what we need.

---

## DSU Used Here

### `findPar()`

Finds the root of an account.

It also uses **path compression**, so nodes get connected directly to their root after finding it.

### `unionBySize()`

When two accounts need to be merged:

* Find both roots.
* Attach the smaller component under the larger component.
* Update the size of the larger component.

This keeps the DSU tree relatively small.

---

## Important Bug to Remember

The DSU constructor must initialize **account `0` too**.

The loop should cover:

`0 → n - 1`

not:

`1 → n - 1`

Otherwise `size[0]` remains `0`, which breaks the assumption behind **union by size**.

This is easy to miss because `parent[0]` happens to default to `0` in Java, so some cases can still appear to work.

---

## Complexity

Let:

* `N` = number of accounts
* `M` = total number of emails
* `K` = size of the largest merged email list

### DSU operations

With path compression and union by size:

**O(M × α(N))**

which is practically almost linear.

### Sorting

The emails belonging to each merged account are sorted.

Overall sorting takes approximately:

**O(M log M)**

in the worst case.

### Overall

**Time:** `O(M log M)`

**Space:** `O(N + M)`

for DSU, the email map, and the merged email lists.

---

## Pattern Recognition

When you see:

* Multiple groups/accounts
* Some common identifier connecting them
* Need to determine which groups belong together
* Connections can be indirect

Think:

**DSU / Connected Components**

For this problem, the mental model is:

> **Email = connection between accounts**

If two accounts share an email → **union them**.

After processing all emails → each DSU component represents one actual person.
