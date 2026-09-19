# Find the City With the Smallest Number of Neighbors

### Pattern

**Floyd-Warshall + All-Pairs Shortest Path**

This problem is basically a Floyd-Warshall problem with one extra step.

I need to find the city that has the **fewest other cities reachable within a given distance threshold**.

So first I need the shortest distance between **every pair of cities**.

That's exactly what Floyd-Warshall gives me.

---

## Intuition

I maintain a `dist` matrix.

`dist[i][j]` means:

> The shortest distance I currently know from city `i` to city `j`.

Initially, I only know the direct roads.

For example, if there is:

`0 → 1 = 4`

then:

`dist[0][1] = 4`

Since the roads are undirected, I also have:

`dist[1][0] = 4`

For cities that aren't directly connected, I put `Integer.MAX_VALUE`, which basically means:

> I don't currently know any path between these two cities.

And:

`dist[i][i] = 0`

because the distance from a city to itself is zero.

---

## Step 1: Find All-Pairs Shortest Paths

I use Floyd-Warshall:

`dist[i][j] = min(dist[i][j], dist[i][v] + dist[v][j])`

Here `v` is the intermediate city.

Basically:

> Is going from `i` to `j` directly better, or is it shorter if I go through `v`?

For example:

```text id="j9h3c7"
0 ----10----> 2

0 --4--> 1 --3--> 2
```

Initially:

`dist[0][2] = 10`

But when I consider city `1` as the intermediate city:

`dist[0][1] + dist[1][2]`

becomes:

`4 + 3 = 7`

Since `7 < 10`, I update:

`dist[0][2] = 7`

After Floyd-Warshall finishes, I know the shortest distance between every pair of cities.

---

## Why Check `Integer.MAX_VALUE`?

I use `Integer.MAX_VALUE` to represent that two cities currently have no known path.

So before doing:

`dist[i][v] + dist[v][j]`

I check that both distances are actually reachable.

Otherwise, adding something to `Integer.MAX_VALUE` could cause integer overflow.

That's why I have:

`dist[i][v] != Integer.MAX_VALUE`

and

`dist[v][j] != Integer.MAX_VALUE`

---

## Step 2: Count Reachable Cities

Once I have all the shortest distances, I go through every city.

For each city, I check every other city:

`dist[city][adjCity] <= distanceThreshold`

If true, that city is reachable within the allowed distance.

So I increase `count`.

For example, if the threshold is `10` and:

```text id="p7d1jf"
dist[0][1] = 4
dist[0][2] = 8
dist[0][3] = 15
```

then city `0` can reach:

* city `1` → yes
* city `2` → yes
* city `3` → no

So its count is `2` excluding itself.

In the actual code, the city itself is also counted because:

`dist[0][0] = 0`

and `0 <= 10`.

This is fine because every city gets its own self-count, so the comparison between cities is unchanged.

---

## Tie-Breaking

The problem says that if multiple cities have the same minimum number of reachable cities, I should return the city with the **largest index**.

That's why I use:

`if(count <= minCount)`

instead of:

`if(count < minCount)`

Suppose:

```text id="n9m1xq"
city 0 → count = 3
city 1 → count = 2
city 2 → count = 2
```

When city `1` gives count `2`, it becomes the answer.

Then city `2` also gives `2`.

Because I use `<=`, city `2` replaces city `1`.

So the final answer is the larger index.

---

## Why Floyd-Warshall Fits Here

The important clue is that I need to know:

> shortest distance from **every city to every other city**

That immediately points towards **Floyd-Warshall**.

After that, the actual problem is just counting how many distances are within the threshold.

---

## Complexity

Let `V` be the number of cities.

### Floyd-Warshall

There are three nested loops:

`O(V³)`

### Counting reachable cities

For every city, I check every other city:

`O(V²)`

So the total is:

**Time:** `O(V³ + V²)` → `O(V³)`

**Space:** `O(V²)`

The `dist` matrix stores the shortest distance between every pair of cities.

---

## Pattern Recognition

The main clue I want to remember:

> **Need shortest paths between every pair + some calculation based on those distances**

Think:

**Floyd-Warshall**

For this problem:

**Floyd-Warshall → shortest distances → count distances within threshold → apply tie-breaking**
