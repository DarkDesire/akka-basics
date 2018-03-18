package persistent.counter

import akka.actor.{ActorSystem, Props}
import persistent.counter.Counter.{Cmd, Increment}

object PersistentApp extends App {
  val system = ActorSystem("persistent-actors")
  val counter = system.actorOf(Props[Counter], "counter")

  counter ! Cmd(Increment(3))
  counter ! Cmd(Increment(2))
  counter ! Cmd(Increment(6))
  counter ! Cmd(Increment(7))
  counter ! Cmd(Increment(2))

  counter ! "print"

  Thread.sleep(1000)
  system.terminate()
}

/*
after 2 runs of this app: State(40)
20 per time * 2times = 40
+ receive snapshot with data State(20)
4:49:03.152 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG persistence.counter.Counter - Starting ...
14:49:03.152 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG akka.persistence.Persistence(akka://persistent-actors) - Create plugin: akka.persistence.snapshot-store.local akka.persistence.snapshot.local.LocalSnapshotStore
14:49:03.453 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG persistence.counter.Counter - Counter: receive snapshot with data State(20) on recovering mood
14:49:03.707 [persistent-actors-akka.actor.default-dispatcher-3] DEBUG persistence.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
14:49:03.707 [persistent-actors-akka.actor.default-dispatcher-2] DEBUG persistence.counter.Counter - Counter: receive Cmd(Increment(3))
14:49:03.723 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG akka.serialization.Serialization(akka://persistent-actors) - Using serializer [akka.persistence.serialization.MessageSerializer] for message [akka.persistence.PersistentImpl]
14:49:03.754 [persistent-actors-akka.actor.default-dispatcher-4] WARN akka.serialization.Serialization(akka://persistent-actors) - Using the default Java serializer for class [persistence.counter.Counter$Evt] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
14:49:03.754 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG akka.serialization.Serialization(akka://persistent-actors) - Using serializer [akka.serialization.JavaSerializer] for message [persistence.counter.Counter$Evt]
14:49:03.785 [persistent-actors-akka.actor.default-dispatcher-2] DEBUG persistence.counter.Counter - Counter: receive Cmd(Increment(2))
14:49:03.785 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG persistence.counter.Counter - Counter: receive Cmd(Increment(6))
14:49:03.785 [persistent-actors-akka.actor.default-dispatcher-4] DEBUG persistence.counter.Counter - Counter: receive Cmd(Increment(7))
14:49:03.803 [persistent-actors-akka.actor.default-dispatcher-2] DEBUG persistence.counter.Counter - Counter: receive Cmd(Increment(2))
14:49:03.805 [persistent-actors-akka.actor.default-dispatcher-3] WARN akka.serialization.Serialization(akka://persistent-actors) - Using the default Java serializer for class [persistence.counter.Counter$State] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
14:49:03.806 [persistent-actors-akka.actor.default-dispatcher-3] DEBUG persistence.counter.Counter - Counter: The current state is State(40)
14:49:03.807 [persistent-actors-akka.actor.default-dispatcher-3] DEBUG akka.serialization.Serialization(akka://persistent-actors) - Using serializer [akka.serialization.JavaSerializer] for message [persistence.counter.Counter$State]
*/