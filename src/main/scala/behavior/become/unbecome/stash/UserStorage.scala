package behavior.become.unbecome.stash

import akka.actor.{Actor, ActorLogging, Stash}
import behavior.become.unbecome.stash.UserStorage.{Connect, Disconnect, Operation}

object UserStorage {
  sealed trait DBOperation

  object DBOperation {
    case object Create extends DBOperation
    case object Update extends DBOperation
    case object Read extends DBOperation
    case object Delete extends DBOperation
  }

  case object Connect
  case object Disconnect
  case class Operation(dBOperation: DBOperation, user: Option[User])
}

class UserStorage extends Actor with ActorLogging with Stash {
  override def receive: Receive = disconnected
  def connected: Receive = {
    case Disconnect =>
      log.debug("UserStorage: disconnected from DB")
      context.unbecome()
    case Operation(op, user) =>
      log.debug(s"UserStorage: received $op to do in user: $user")
    case msg => log.error(s"Unknown message: $msg")
  }
  def disconnected: Receive = {
    case Connect =>
      log.debug("UserStorage: connected to DB")
      unstashAll()
      context.become(connected)
    case _ =>
      stash()
  }
}