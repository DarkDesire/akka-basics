package actorsystem

import akka.actor.{Actor, ActorLogging}

class Checker extends Actor with ActorLogging {
  import Checker._
  val blackList = List(
    User("Obi-wan", "obi-wan@kenobi.com")
  )
  override def receive: Receive = {
    case CheckUser(user) if blackList.contains(user) =>
      log.debug(s"Checker: $user in blacklist")
      sender() ! BlackUser(user)
    case CheckUser(user) =>
      log.debug(s"Checker: $user is not in blacklist")
      sender() ! WhiteUser(user)
    case _ => log.error("Unknown message")
  }
}

object Checker {
  sealed trait CheckerMsg
  // Checker messages
  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckerResponseMsg
  // Checker responses
  case class BlackUser(user: User) extends CheckerResponseMsg
  case class WhiteUser(user: User) extends CheckerResponseMsg
}