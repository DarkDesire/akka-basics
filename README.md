## Examples of main futures of akka library

### Routing [read here](doc.akka.io/docs/akka/2.5/routing.html)
> Balancing Router(Pool) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/BalancingRouting)

> Broadcast Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/BroadcastRouting)

> Random Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/RandomRouting)

> Round-Robin Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/RoundRobinRouting)

> ScatterGatherFirstCompleted Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/ScatterGatherFirstCompletedRouting)

> Smallest-mailbox Router(Pool) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/SmallestMailboxRouting)

> TailChopping Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/TailChoppingRouting)

> ConsistentHashing Router(Pool,Group) > no example, but u can read it [here](https://doc.akka.io/docs/akka/2.5/routing.html#consistenthashingpool-and-consistenthashinggroup)

In every example u can find code and conf version of implementation.

### Replacing actor stack behavior
> via **Become-Unbecome-Stash** > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/behavior/become/unbecome/stash)

Read more about **Become/Unbecome** [here](https://doc.akka.io/docs/akka/2.5/actors.html#become-unbecome) and about **Stash** [here](https://doc.akka.io/docs/akka/2.5/actors.html#stash)

> via **FSM** [doc](https://doc.akka.io/docs/akka/2.5/fsm.html#fsm) [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/behavior/fsm)
