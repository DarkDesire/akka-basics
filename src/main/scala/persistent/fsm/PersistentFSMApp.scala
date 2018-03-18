package persistent.fsm

import akka.actor.{ActorSystem, Props}
import persistent.fsm.Account.{CR, DR, Operation}

object PersistentFSMApp extends App {
  val system = ActorSystem("persistent-fsm")
  val account = system.actorOf(Props[Account], "account")

  account ! Operation(1000, CR)
  account ! Operation(100, DR)

  Thread.sleep(1000)
  system.terminate()
}

/*
15:28:53.478 [persistent-fsm-akka.actor.default-dispatcher-5] DEBUG akka.persistence.Persistence(akka://persistent-fsm) - Create plugin: akka.persistence.journal.leveldb akka.persistence.journal.leveldb.LeveldbJournal
15:28:53.531 [persistent-fsm-akka.actor.default-dispatcher-5] DEBUG akka.persistence.Persistence(akka://persistent-fsm) - Create plugin: akka.persistence.snapshot-store.local akka.persistence.snapshot.local.LocalSnapshotStore
15:28:54.032 [persistent-fsm-akka.actor.default-dispatcher-2] DEBUG akka.serialization.Serialization(akka://persistent-fsm) - Using serializer [akka.persistence.serialization.MessageSerializer] for message [akka.persistence.PersistentRepr]
15:28:54.117 [persistent-fsm-akka.actor.default-dispatcher-3] DEBUG persistence.fsm.Account - Your new balance is 1000.0
15:28:54.117 [persistent-fsm-akka.actor.default-dispatcher-2] DEBUG persistence.fsm.Account - Your new balance is 900.0
15:28:54.164 [persistent-fsm-akka.actor.default-dispatcher-5] DEBUG akka.serialization.Serialization(akka://persistent-fsm) - Using serializer [akka.persistence.serialization.MessageSerializer] for message [akka.persistence.PersistentImpl]
15:28:54.180 [persistent-fsm-akka.actor.default-dispatcher-5] WARN akka.serialization.Serialization(akka://persistent-fsm) - Using the default Java serializer for class [persistence.fsm.Account$AcceptedTransaction] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
15:28:54.180 [persistent-fsm-akka.actor.default-dispatcher-5] DEBUG akka.serialization.Serialization(akka://persistent-fsm) - Using serializer [akka.serialization.JavaSerializer] for message [persistence.fsm.Account$AcceptedTransaction]
15:28:54.197 [persistent-fsm-akka.actor.default-dispatcher-2] DEBUG persistence.fsm.Account - Your new balance is 1900.0
15:28:54.199 [persistent-fsm-akka.actor.default-dispatcher-5] DEBUG persistence.fsm.Account - Your new balance is 1800.0
 */