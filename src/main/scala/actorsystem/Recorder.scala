package actorsystem

import actorsystem.Checker.{BlackUser, CheckUser, WhiteUser}
import actorsystem.Recorder.NewUser
import actorsystem.Storage.AddUser
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class Recorder(checker:ActorRef, storage:ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case NewUser(user) => checker ! CheckUser(user)
    case WhiteUser(user) => storage ! AddUser(user)
    case _:BlackUser =>
    case _ => log.error("Unknown message")
  }
}

object Recorder {
  sealed trait RecorderMsg
  // Record messages
  case class NewUser(user: User) extends RecorderMsg
  def props(checker:ActorRef,storage:ActorRef) = Props(new Recorder(checker,storage))
}