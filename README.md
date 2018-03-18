## Examples of main futures of akka library

### Routing [read here](doc.akka.io/docs/akka/current/routing.html)
> Balancing Router(Pool) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/BalancingRouting)

> Broadcast Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/BroadcastRouting)

> Random Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/RandomRouting)

> Round-Robin Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/RoundRobinRouting)

> ScatterGatherFirstCompleted Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/ScatterGatherFirstCompletedRouting)

> Smallest-mailbox Router(Pool) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/SmallestMailboxRouting)

> TailChopping Router(Pool,Group) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/routing/TailChoppingRouting)

> ConsistentHashing Router(Pool,Group) > no example, but u can read it [here](https://doc.akka.io/docs/akka/current/routing.html#consistenthashingpool-and-consistenthashinggroup)

In every example u can find code and conf version of implementation.
___
### Replacing actor stack behavior
> via **Become-Unbecome-Stash** > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/behavior/become/unbecome/stash)

Read more about **Become/Unbecome** [here](https://doc.akka.io/docs/akka/current/actors.html#become-unbecome) and about **Stash** [here](https://doc.akka.io/docs/akka/current/actors.html#stash)

> via **FSM** [doc](https://doc.akka.io/docs/akka/current/fsm.html#fsm) [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/behavior/fsm)
___

### Persistence [read here](https://doc.akka.io/docs/akka/current/persistence.html)
> ExamplePersistentActor [doc](https://doc.akka.io/docs/akka/current/persistence.html#event-sourcing) > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/persistent/doc)

> CounterActor > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/persistent/counter)

#### Persistent FSM [read here](https://doc.akka.io/docs/akka/current/persistence.html#persistent-fsm)

> Bank Account FSM > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/persistent/fsm)

#### Persistence Query [read here](https://doc.akka.io/docs/akka/current/persistence-query.html#persistence-query)

> ReporterApp > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/persistent/query) 
// reading events from previous created persistent fsm actor **Bank Account FSM**
___

### Remoting [read here](https://doc.akka.io/docs/akka/current/remoting.html?language=scala)
> Simple Remoting Example > [example](https://github.com/DarkDesire/akka-basics/tree/master/src/main/scala/remoting)
