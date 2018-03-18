package persistent.counter

import akka.actor.ActorLogging
import akka.persistence._

object Counter {

  sealed trait Operation {
    val count: Int
  }

  case class Increment(override val count: Int) extends Operation

  case class Decrement(override val count: Int) extends Operation

  case class Cmd(op: Operation)

  case class Evt(op: Operation)

  case class State(count: Int)

}

class Counter extends PersistentActor with ActorLogging {

  import Counter._

  log.debug("Starting ...")

  // Persistent Identifier
  override def persistenceId: String = "sample-id-18-03-2018-2"

  var state = State(count = 0)

  def updateState(evt: Evt): Unit = evt match {
    case Evt(Increment(count)) =>
      state = State(count = state.count + count)
      takeSnapshot()
    case Evt(Decrement(count)) =>
      state = State(count = state.count - count)
      takeSnapshot()
  }

  // override def recovery: Recovery = Recovery.none

  // Persistent receive in recovery mood
  override def receiveRecover: Receive = {
    case evt: Evt =>
      log.debug(s"Counter: receive $evt on recovering mood")
      updateState(evt)
    case SnapshotOffer(_, snapshot: State) =>
      log.debug(s"Counter: receive snapshot with data $snapshot on recovering mood")
      state = snapshot
    case RecoveryCompleted =>
      log.debug("Recovery completed and now I'll switch to normal receiving mode")
    case SaveSnapshotSuccess(_) =>
      log.debug("Save snapshot succeed.")
    case SaveSnapshotFailure(_, cause) =>
      log.debug(s"Save snapshot failed and failure is $cause.")
  }

  def takeSnapshot(): Unit = {
    if(state.count % 5 == 0){
      saveSnapshot(state)
    }
  }

  // Persistent receive in normal mood
  override def receiveCommand: Receive = {
    case cmd@Cmd(op) =>
      log.debug(s"Counter: receive $cmd")
      persist(Evt(op)) { evt =>
        updateState(evt)
      }
    case "print" =>
      log.debug(s"Counter: The current state is $state")
  }

}
