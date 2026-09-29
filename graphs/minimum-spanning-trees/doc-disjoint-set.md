# Disjoint Set Union (DSU)

### Pattern

**Disjoint Set Union + Path Compression + Union by Rank / Size**

DSU is a data structure used to keep track of groups of connected elements.

It helps me answer two questions:

* Are two nodes in the same component?
* How can I merge two components into one?

Instead of traversing the graph every time to check connectivity, I can use DSU to do these operations efficiently.

---

## Intuition

Imagine I have a few separate groups of nodes.

Initially, every node belongs to its own group.

For example:

```text
1    2    3    4    5
```

Each node is its own parent, meaning each node is the representative of its own group.

If I perform `union(1, 2)`, nodes `1` and `2` become part of the same group.

If I then perform `union(2, 3)`, node `3` also joins that group.

Now the groups look something like this:

```text
1 --- 2 --- 3       4       5
```

The actual structure is maintained using parent pointers, not necessarily this exact shape.

The main idea is that every group has a **representative**, also called its root. If two nodes have the same representative, they belong to the same group.

---

## 1. Parent Array

The `parent` array stores the parent of each node.

Initially:

`parent[i] = i`

This means every node is its own parent and represents its own group.

When two groups are merged, the root of one group becomes a child of the root of the other group.

For example, if I merge nodes `1` and `2`, one possible structure is:

```text
1
|
2
```

Here, `1` is the root and `2` points to it.

The root is the representative of the entire group.

---

## 2. Find Operation

The `findPar(node)` method finds the representative of the group containing a node.

It keeps moving up through the parent pointers until it reaches a node whose parent is itself.

That node is the root.

For example:

```text
1
|
2
|
3
```

If I call `findPar(3)`, it follows:

`3 → 2 → 1`

So the representative is `1`.

### Path Compression

While finding the root, I also use path compression.

Instead of making every node keep pointing to its old parent, I directly connect it to the root.

For example, after finding the parent of `3`:

```text
    1
   / \
  2   3
```

Now both `2` and `3` point directly to `1`.

This makes future `find` operations much faster because the tree becomes flatter.

The important line is:

`parent[node] = findPar(parent[node])`

It finds the root and updates the current node's parent to that root.

---

## 3. Find Whether Two Nodes Are Connected

The `find(u, v)` method checks whether two nodes belong to the same group.

I find the representative of both nodes.

If both representatives are equal, they belong to the same component.

Otherwise, they belong to different components.

For example:

```text
1 --- 2 --- 3       4 --- 5
```

Nodes `1` and `3` have the same representative, so they are connected.

Nodes `1` and `5` have different representatives, so they are not connected.

---

## 4. Union by Rank

Union means merging two different groups.

The idea behind union by rank is to attach the shorter tree below the taller tree.

The `rank` array stores an estimate of the height of each tree.

When merging two groups, I first find their roots.

Then there are three cases:

* If the first root has a smaller rank, attach it below the second root.
* If the second root has a smaller rank, attach it below the first root.
* If both have the same rank, attach one below the other and increase the rank of the new root by one.

Why do I do this?

If I always attach one tree below another without considering their heights, the tree can become very long.

A long tree makes `find` slower.

By attaching the shorter tree below the taller one, I keep the structure relatively shallow.

### Important detail

I increase the rank only when both trees have the same rank.

If one tree is already taller, attaching the shorter tree below it doesn't increase the height.

Also, rank is only an estimate of height. After path compression, it doesn't necessarily represent the tree's current height.

---

## 5. Union by Size

Union by size follows a similar idea, but instead of comparing tree heights, I compare the number of nodes in each component.

The `size` array stores the number of nodes in a component, at its root.

Initially, every node is in its own component, so:

`size[i] = 1`

When merging two groups:

* If the first group is smaller, attach it below the second group.
* Otherwise, attach the second group below the first group.

Then update the size of the new root by adding the sizes of both groups.

For example:

```text
Group A: 1 --- 2

Group B: 3 --- 4 --- 5
```

Group A has size `2`, and group B has size `3`.

I attach the smaller group below the larger group.

The new component has size:

`2 + 3 = 5`

Only the size of the new root needs to be updated. The size values of non-root nodes aren't used for future unions.

---

## Why Do We Need Both Rank and Size?

Both are ways to keep the trees shallow.

* **Union by Rank:** Attach the tree with smaller rank below the one with larger rank.
* **Union by Size:** Attach the smaller component below the larger component.

I don't need to use both methods in the same union operation. They are two alternative ways of merging components.

Both can be combined with path compression.

---

## Complexity

With path compression and either union by rank or union by size:

**Time:** Nearly `O(1)` amortized per operation, more precisely `O(α(V))`, where `α` is the inverse Ackermann function.

For practical input sizes, this grows so slowly that it is effectively constant.

**Space:** `O(V)`

The `parent`, `rank`, and `size` arrays each use `O(V)` space.

---

## Pattern Recognition

The main clue is:

> I need to repeatedly check whether two nodes belong to the same connected component, and sometimes merge those components.

Think:

**Disjoint Set Union (DSU)**

It is especially useful in problems involving:

* Connected components
* Cycle detection in undirected graphs
* Kruskal's algorithm for Minimum Spanning Tree
* Merging groups or sets

The two operations to remember are:

* `find` → find the representative of a group
* `union` → merge two groups

And the two optimizations are:

* Path compression
* Union by rank or union by size
