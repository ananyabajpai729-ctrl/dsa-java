# Number of Operations to Make Network Connected

## Pattern

**Disjoint Set Union (DSU) + Connected Components + Extra Edges**

---

## Intuition

We have `n` computers and some cables connecting them.

The goal is to make **all computers connected**.

There are basically two things we need to keep track of:

1. **How many connected components are there?**
2. **How many extra/redundant cables do we have?**

For example, if a cable connects two computers that are **already connected indirectly**, that cable isn't helping us connect a new component.

We can think of that cable as an **extra edge**.

Now suppose after processing all the connections, we have:

* `components = 3`

Then we need:

`3 - 1 = 2`

extra connections to join all three components together.

So the answer is simply:

**number of components - 1**

provided that we have enough extra edges to do it.

---

## Approach

### 1. First check if there are enough edges

To connect `n` computers, we need at least:

`n - 1`

connections.

So if:

`connections.length < n - 1`

then it is impossible, and we return `-1`.

---

### 2. Initialize DSU

Initially, every computer is its own component.

So:

`parent[i] = i`

and we start with:

`components = n`

For example, with 5 computers:

`0  1  2  3  4`

Every computer is currently separate.

---

### 3. Process every connection

For every edge `(u, v)`:

We find the parent/root of both nodes.

There are two cases.

### Case 1: Different roots

If:

`rootU != rootV`

then these computers belong to different components.

So we connect the two components.

Because two components have now become one:

`components--`

---

### Case 2: Same root

If:

`rootU == rootV`

then `u` and `v` are already connected somehow.

Adding this edge doesn't connect anything new.

So this is an **extra edge**.

We increase:

`extraEdges++`

These extra edges are useful because later we can conceptually remove them from their current position and use them to connect different components.

---

## Why `components - 1` edges are needed

Suppose we have:

`4 components`

To connect them all:

* Connect component 1 → component 2
* Connect component 2 → component 3
* Connect component 3 → component 4

So we need:

`4 - 1 = 3`

connections.

Therefore:

`edgesNeeded = components - 1`

---

## Final Check

Now we have:

* `extraEdges` → cables that can be reused
* `edgesNeeded` → cables required to connect all remaining components

If:

`extraEdges >= edgesNeeded`

then we can connect everything.

Otherwise, it's impossible.

---

## Example

Suppose there are 4 computers:

`0, 1, 2, 3`

and connections are:

`[0,1]`
`[1,2]`
`[0,2]`

After processing:

* `0` and `1` → merge
* `1` and `2` → merge
* `0` and `2` → already connected → extra edge

So we have:

* `components = 2`
* `extraEdges = 1`

We need:

`components - 1 = 1`

extra connection.

Since:

`extraEdges >= edgesNeeded`

answer = **1**.

The extra connection `[0,2]` can be reused to connect computer `3`.

---

## Important DSU Part

The `find()` function uses **path compression**.

Instead of keeping a long chain like:

`3 → 2 → 1 → 0`

path compression makes nodes point directly towards the root after finding it.

So future `find()` operations become much faster.

The `connect()` function simply connects one root to another.

---

## Complexity

Let `V = n` and `E = connections.length`.

### Time Complexity

**O(E × α(V))**

where `α(V)` is the inverse Ackermann function, which grows extremely slowly and is practically constant.

So in practice, DSU operations are almost **O(1)**.

### Space Complexity

**O(V)**

for the `parents` array.

---

## Pattern Recognition

Whenever a graph problem asks something like:

* Are these nodes connected?
* How many connected components are there?
* Are two groups already connected?
* Can we merge different groups?
* Are there redundant edges?
* Can we connect all nodes by reusing extra edges?

Think about **Disjoint Set Union (DSU)**.

For this problem, the key thought is:

> **Count the components + count the redundant edges.**

If we have enough redundant edges to join all the components, the answer is `components - 1`.
