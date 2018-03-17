package routing

import akka.actor.{Actor, ActorLogging}
import routing.Worker.Work

import scala.util.Random

class RouterGroup(routees: List[String]) extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg@Work =>
      log.debug("RouterGroup: receive a message work")
      context.actorSelection(routees(Random.nextInt(routees.size))) ! msg
    case _ => log.error("Unknown message")
  }
}