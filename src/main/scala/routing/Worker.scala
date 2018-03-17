package routing

import akka.actor.{Actor, ActorLogging, Props}
import routing.Worker.{Work, WorkHard, WorkResult, WorkWithResult}

import scala.util.Random

class Worker extends Actor with ActorLogging {
  val INIT_DELAY = 2000
  def baseDelay(moreDelay: Long = 0L) = Thread.sleep(moreDelay)
  override def receive: Receive = {
    case msg@Work =>
      log.debug(s"I received a message $msg and my ActorRef:$self")
    case msg@WorkHard =>
      log.debug(s"I received a message $msg and my ActorRef:$self")
      baseDelay(INIT_DELAY)
      log.debug(s"$msg done with $INIT_DELAY delay!")
    case msg@WorkWithResult =>
      log.debug(s"I received a message $msg and my ActorRef:$self")
      val delay = INIT_DELAY + (for (i <- 1 to 5) yield Random.nextInt(3000).toLong).max
      baseDelay(delay)
      log.debug(s"$msg done with $delay delay!")
      sender() ! WorkResult()
    case _ => log.error("Unknown message")
  }
}

object Worker {
  def props = Props(new Worker)
  sealed trait WorkerMsg
  // Router messages
  case object Work extends WorkerMsg
  case object WorkHard extends WorkerMsg
  case object WorkWithResult extends WorkerMsg
  case class WorkResult() extends WorkerMsg
}