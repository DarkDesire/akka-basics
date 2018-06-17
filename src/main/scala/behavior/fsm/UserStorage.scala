package behavior.fsm

import akka.actor.{ActorLogging, FSM, Stash}

object UserStorage {
  // FSM State
  sealed trait State
  case object Connected extends State
  case object Disconnected extends State

  // FSM Data
  sealed trait Data
  case object EmptyData extends Data


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

class UserStorage extends FSM[UserStorage.State, UserStorage.Data] with Stash with ActorLogging {
  import UserStorage._

  // 1. define start with
  startWith(Disconnected, EmptyData)
  // 2. defines states
  when(Disconnected){
    case Event(Connect,_) =>
      log.debug("UserStorage: connected to DB")
      unstashAll()
      goto(Connected) using EmptyData
    case Event(_,_) =>
      stash()
      stay using EmptyData
  }

  when(Connected){
    case Event(Disconnect, _) =>
      log.debug("UserStorage: disconnected from DB")
      goto(Disconnected) using EmptyData
    case Event(Operation(op,user),_) =>
      log.debug(s"UserStorage: received $op to do in user: $user")
      stay using EmptyData
    case Event(msg,_) =>
      log.error(s"Unknown message: $msg")
      stay using EmptyData
  }
  // 3. initialize
  initialize()
}
