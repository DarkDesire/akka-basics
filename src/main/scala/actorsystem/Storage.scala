package actorsystem

import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable.ArrayBuffer

class Storage extends Actor with ActorLogging{
  import Storage._
  val users: ArrayBuffer[User] = ArrayBuffer.empty[User]
  override def receive: Receive = {
    case AddUser(user) =>
      log.debug(s"Storage: $user added")
      users += user
    case GetAllUsers =>
      log.debug(s"Storage: [{}]",users.toList)
    case _ => log.error("Unknown message")
  }
}

object Storage {
  sealed trait StorageMsg
  // Storage messages
  case object GetAllUsers extends StorageMsg
  case class AddUser(user: User) extends StorageMsg
}