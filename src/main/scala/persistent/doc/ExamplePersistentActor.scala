package persistent.doc

import akka.actor.ActorLogging
import akka.persistence.{PersistentActor, Recovery, RecoveryCompleted, SnapshotOffer}

object ExamplePersistentActor {
  case class Cmd(data: String)
  case class Evt(data: String)
}

class ExamplePersistentActor extends PersistentActor with ActorLogging {

  import ExamplePersistentActor._
  override def persistenceId = "example-persistent-actor-1"

  var state = ExampleState()

  def updateState(event: Evt): Unit =
    state = state.updated(event)

  def numEvents: Int = state.size

  // override def recovery: Recovery = Recovery.none
  override def receiveRecover: Receive = {
    case evt: Evt =>
      updateState(evt)
    case SnapshotOffer(_, snapshot: ExampleState) =>
      state = snapshot
    case RecoveryCompleted =>
      log.debug("Recovery completed and now I'll switch to normal receiving mode")
  }

  val snapShotInterval = 1000
  override def receiveCommand: Receive = {
    case Cmd(data) ⇒
      persist(Evt(s"$data-$numEvents")) { event ⇒
        updateState(event)
        context.system.eventStream.publish(event)
        if (lastSequenceNr % snapShotInterval == 0 && lastSequenceNr != 0)
          saveSnapshot(state)
      }
    case "print" ⇒ log.debug(s"$state")
    case "printLast5" => log.debug(s"${state.printLast5}")
  }

}