# Cheapest Flights Within K Stops

## Pattern

**BFS + shortest path with a stop constraint**

This problem looks like a shortest path problem, but there is one extra condition:

> We don't just care about the cheapest price. We also have to make sure we don't use more than `k` stops.

So the state I need to keep track of is:

**`(stops, node, fare)`**

That's why I store all three in the queue.

---

# Intuition

Normally, in a shortest path problem, I might just keep the cheapest distance to every node.

But here, the number of stops matters too.

For example, reaching a city for ₹50 is great, but if I used too many stops to get there, that route may not be usable.

Since I'm using BFS, the queue processes routes according to their number of stops.

For every state in the queue, I know:

* how many stops I've used
* which city I'm currently at
* how much I've spent so far

Then I try all flights going out from that city.

---

# Approach

### 1. Build the graph

The flights are directed.

If a flight is:

`u → v` with price `w`

I store `v` and `w` in the adjacency list of `u`.

---

### 2. Start BFS

Initially:

* `stops = 0`
* `node = src`
* `fare = 0`

So the first queue entry represents:

`(0 stops, source, ₹0)`

---

### 3. Process the queue

For every state, I look at all neighbouring cities.

If I can reach a neighbour for a cheaper price, I update its `dist` and put the new state into the queue.

The new state becomes:

`(stops + 1, nextNode, newFare)`

The fare is simply:

`current fare + flight price`

---

### 4. Stop constraint

If:

`stops > k`

I don't process that state further.

This is important because `k` represents the **number of stops**, while taking a flight increases the number of flights by one.

So a route with at most `k` stops can contain at most `k + 1` flights.

---

# Why do I keep `stops` in the queue?

This is the main idea of the problem.

Two routes can reach the **same city** but have different numbers of stops and different fares.

For example:

```text
src
├── B : ₹100
│
└── A : ₹50
      └── B : ₹50
```

I might reach B as:

```text
(B, 1 stop, ₹100)
(B, 2 stops, ₹50)
```

The second route is cheaper, but it uses more stops.

So I cannot think only in terms of:

`cheapest fare to B`

I also need to know **how many stops were used to get there**.

That's why the queue stores all three values.

---

# Why BFS works here

Every flight adds exactly **one more flight/level** to the route.

So BFS naturally explores routes based on the number of flights taken.

The queue can contain different states for the same city, for example:

```text
(1, B, 100)
(2, B, 50)
```

Even if the cheaper route is found later, the earlier valid state is still already in the queue.

So the stop count is not lost.

---

# Dry Run

Suppose:

```text
src = 0
dst = 3
k = 1
```

Flights:

```text
0 → 1 : 100
0 → 2 : 50
2 → 3 : 50
```

Start:

```text
Queue:
(0, 0, 0)

dist:
0 → 0
```

Process node `0`:

```text
0 → 1 : ₹100
0 → 2 : ₹50
```

Queue becomes:

```text
(1, 1, 100)
(1, 2, 50)
```

Process node `2`:

```text
2 → 3 : ₹50
```

New fare:

```text
50 + 50 = ₹100
```

So:

```text
dist[3] = 100
```

We reach the destination with 1 stop, which is allowed.

Answer:

**₹100**

---

# Important Difference From Normal Dijkstra

This is not a normal Dijkstra problem.

In normal Dijkstra, the cheapest distance to a node is usually enough.

Here, the number of stops is also a restriction.

So the state becomes:

```text
node + stops + fare
```

The queue lets me explore these stop-limited routes without simply committing to one cheapest route for every node.

---

# Complexity

Let:

* `V` = number of cities
* `E` = number of flights
* `K` = maximum number of stops

### Time

**O(K × E)**

In the worst case, we can process the flights across multiple BFS levels, up to the allowed number of stops.

### Space

**O(V + E)**

* Adjacency list takes `O(V + E)`
* `dist` takes `O(V)`
* Queue can contain multiple route states
