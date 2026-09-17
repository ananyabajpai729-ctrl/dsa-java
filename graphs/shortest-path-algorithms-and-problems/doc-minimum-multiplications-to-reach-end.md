# Minimum Multiplications to Reach End

### Pattern

**BFS + Shortest Path on an Implicit Graph**

This problem looks like a multiplication problem at first, but the easiest way to think about it is as a graph.

Every number from `0` to `99999` can be considered a node.

From a current number, I can multiply it by any factor from `arr` and take modulo `100000`.

So for example, if:

`node = 5`

and one factor is `3`, then:

`5 × 3 % 100000 = 15`

which means there is an edge:

`5 → 15`

Every multiplication takes exactly **1 step**.

So now the problem becomes:

> What is the minimum number of edges needed to go from `start` to `end`?

That is a shortest path problem on an **unweighted graph**, so BFS fits here.

---

## Intuition

I start BFS from `start`.

For every number I remove from the queue, I try every factor in `arr`.

The next number is:

`(current × factor) % 100000`

Then I check whether I have reached this number in fewer steps than before.

If yes, I update its distance and put it into the queue.

I use `dist[]` to store the minimum number of multiplications needed to reach each number.

Initially everything is `Integer.MAX_VALUE` because I haven't reached those numbers yet.

---

## Why BFS?

Every multiplication costs exactly **one step**.

For example:

```text
start
  |
  | × factor
  v
number
  |
  | × factor
  v
number
```

Each edge has the same cost: `1`.

This is exactly the situation where BFS gives the shortest path.

If different multiplications had different costs, then normal BFS would not be enough and I would need something like Dijkstra.

---

## Why modulo 100000?

The problem says every multiplication is followed by:

`% 100000`

So the result always stays between:

`0` and `99999`

This means there are only **100000 possible states**.

That's why I create:

`dist[100000]`

I don't actually need to build an adjacency list because I can generate the neighbours of a number whenever I process it.

This is why this is called an **implicit graph** — the graph exists logically, but I generate its edges when needed.

---

## Example

Suppose:

`arr = [2, 3]`

and:

`start = 2`

Then from `2`, I can go to:

```text
2 × 2 = 4
2 × 3 = 6
```

So:

```text
        2
       / \
      4   6
     /     \
   ...     ...
```

From every number, I again try multiplying by `2` and `3`.

BFS explores these possibilities level by level:

```text
0 multiplications → start

1 multiplication  → all numbers reachable in 1 move

2 multiplications → all numbers reachable in 2 moves

3 multiplications → ...
```

So when I first reach `end`, I know that I have found the minimum number of multiplications.

---

## `dist[]` is also acting like visited

I don't need a separate `visited[]` array.

If:

`dist[num] == Integer.MAX_VALUE`

then I haven't reached that number yet.

Once I give it a distance, I know it has already been reached.

The condition:

`steps + 1 < dist[num]`

makes sure I only update the number when I have found a better distance.

---

## Why can I return immediately when I find `end`?

Because this is BFS.

BFS processes states in increasing order of steps.

So if I am currently at `steps` and discover `end` using `steps + 1`, there cannot be another route with fewer than `steps + 1` multiplications that hasn't already been considered.

Therefore:

`return steps + 1`

is safe.

---

## Edge Case

If:

`start == end`

then the answer should be `0`, because I am already at the destination without doing any multiplication.

So an early check for this case can be added before starting BFS.

---

## Pattern Recognition

The important clue is:

> Every operation costs exactly **one step**, and I need the minimum number of operations.

That should make me think:

**BFS**

Here the "nodes" aren't given explicitly. I create the next states using:

`(current × factor) % 100000`

So the full pattern is:

**BFS + Implicit Graph + Shortest Path**

---

## Complexity

There are at most `100000` possible numbers/states.

For every state, I try every factor in `arr`.

If `N = 100000` and `K = arr.length`:

**Time:** `O(N × K)`

because each state can be processed with all `K` factors.

**Space:** `O(N)`

for the `dist` array and the BFS queue.

The graph itself isn't stored, which saves a lot of space compared to explicitly creating all the edges.
