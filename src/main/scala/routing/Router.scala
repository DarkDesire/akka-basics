package routing

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import routing.Worker.Work

import scala.util.Random

object Router {
  def props = Props(new Router)
}

class Router extends Actor with ActorLogging {
  var routees: List[ActorRef] = _

  override def preStart: Unit =
    routees = (1 to 5).map{id => context.actorOf(Worker.props, s"worker-$id")}.toList

  override def receive: Receive = {
    case msg@Work =>
      log.debug("I'm a router and I received a Message ....")
      routees(Random.nextInt(routees.size)) forward  msg
    case _ => log.error("Unknown message")
  }
}