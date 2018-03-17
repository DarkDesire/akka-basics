package persistence.counter

import akka.actor.ActorLogging
import akka.persistence.{PersistentActor, SnapshotOffer}

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
  override def persistenceId: String = "sample-id-18-03-2018"

  var state = State(count = 0)

  def updateState(evt: Evt) = evt match {
    case Evt(Increment(count)) =>
      state = State(count = state.count + count)
    case Evt(Decrement(count)) =>
      state = State(count = state.count - count)
  }

  // Persistent receive in recovery mood
  override def receiveRecover: Receive = {
    case evt: Evt =>
      log.debug(s"Counter: receive $evt on recovering mood")
      updateState(evt)
    case SnapshotOffer(_, snapshot: State) =>
      log.debug(s"Counter: receive snapshot with data $snapshot on recovering mood")
      state = snapshot
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
