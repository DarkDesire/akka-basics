package persistent.query

import akka.NotUsed
import akka.actor.ActorSystem
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.persistence.query.{EventEnvelope, PersistenceQuery}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

object ReporterApp extends App {
  implicit val system: ActorSystem = ActorSystem("persistent-query")
  implicit val mat: ActorMaterializer = ActorMaterializer()

  val queries = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)

  val events: Source[EventEnvelope, NotUsed] = queries.eventsByPersistenceId("account-id")
  events.runForeach{evt => println(s"Event $evt")}

  Thread.sleep(1000)
  system.terminate()
}

/*
16:09:55.699 [persistent-query-akka.actor.default-dispatcher-3] DEBUG akka.persistence.query.PersistenceQuery - Create plugin: akka.persistence.query.journal.leveldb akka.persistence.query.journal.leveldb.LeveldbReadJournalProvider
16:09:55.859 [persistent-query-akka.actor.default-dispatcher-5] DEBUG akka.persistence.Persistence(akka://persistent-query) - Create plugin: akka.persistence.journal.leveldb akka.persistence.journal.leveldb.LeveldbJournal
16:09:55.922 [persistent-query-akka.actor.default-dispatcher-3] DEBUG akka.persistence.query.journal.leveldb.LiveEventsByPersistenceIdPublisher - request replay for persistenceId [account-id] from [0] to [9223372036854775807] limit [100]
16:09:56.338 [persistent-query-akka.actor.default-dispatcher-4] DEBUG akka.serialization.Serialization(akka://persistent-query) - Using serializer [akka.persistence.serialization.MessageSerializer] for message [akka.persistence.PersistentRepr]
16:09:56.423 [persistent-query-akka.actor.default-dispatcher-3] DEBUG akka.persistence.query.journal.leveldb.LiveEventsByPersistenceIdPublisher - replay completed for persistenceId [account-id], currSeqNo [8]
Event EventEnvelope(Sequence(1),account-id,1,AcceptedTransaction(1000.0,CR))
Event EventEnvelope(Sequence(2),account-id,2,StateChangeEvent(Active,None))
Event EventEnvelope(Sequence(3),account-id,3,AcceptedTransaction(100.0,DR))
Event EventEnvelope(Sequence(4),account-id,4,AcceptedTransaction(1000.0,CR))
Event EventEnvelope(Sequence(5),account-id,5,AcceptedTransaction(100.0,DR))
Event EventEnvelope(Sequence(6),account-id,6,AcceptedTransaction(1000.0,CR))
Event EventEnvelope(Sequence(7),account-id,7,AcceptedTransaction(100.0,DR))
 */
