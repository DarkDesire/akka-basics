package persistent.doc

import akka.actor.{ActorRef, ActorSystem, Props}

import scala.util.Random

object PersistentDocApp extends App {
  import ExamplePersistentActor._
  val system = ActorSystem("persistent-system")
  val persistentActor: ActorRef = system.actorOf(Props(classOf[ExamplePersistentActor]), "persistent")
  (1 to 10).foreach{_ => persistentActor ! Cmd(Random.nextString(10))}
  Thread.sleep(10000)
  persistentActor ! "printLast5"
  Thread.sleep(500)
  system.terminate()
}

/*
14:40:31.007 [persistent-system-akka.actor.default-dispatcher-5] DEBUG akka.serialization.Serialization(akka://persistent-system) - Using serializer [akka.persistence.serialization.MessageSerializer] for message [akka.persistence.PersistentRepr]
14:40:31.122 [persistent-system-akka.actor.default-dispatcher-3] DEBUG persistence.doc.ExamplePersistentActor - Recovery completed and now I'll switch to normal receiving mode
14:40:31.154 [persistent-system-akka.actor.default-dispatcher-3] DEBUG akka.serialization.Serialization(akka://persistent-system) - Using serializer [akka.persistence.serialization.MessageSerializer] for message [akka.persistence.PersistentImpl]
14:40:31.169 [persistent-system-akka.actor.default-dispatcher-17] WARN akka.serialization.Serialization(akka://persistent-system) - Using the default Java serializer for class [persistence.doc.ExamplePersistentActor$Evt] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
14:40:31.169 [persistent-system-akka.actor.default-dispatcher-17] DEBUG akka.serialization.Serialization(akka://persistent-system) - Using serializer [akka.serialization.JavaSerializer] for message [persistence.doc.ExamplePersistentActor$Evt]
14:40:40.249 [persistent-system-akka.actor.default-dispatcher-14] DEBUG persistence.doc.ExamplePersistentActor - List(㱢꿍᚟ៗ덇᪯ᨑᥦሣ浀-55, ᷲ贛ۈʭ⾑鉻串鹶㈃㈙-56, 몸غꏾ櫽鍤僕儢꓃㑣꫌-57, ㆌ哊愢Ԯ磩䟍襘맇阥㫕-58, ꨂַ㏖ᔛ촋憓魸뗷玷ᶒ-59)
*/
