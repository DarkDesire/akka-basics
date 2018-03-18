package remoting

import akka.actor.{Actor, ActorLogging}

object Worker {
  sealed trait WorkerMsg
  case class Work(message: String) extends WorkerMsg
}
class Worker extends Actor with ActorLogging{
  import Worker._
  override def receive: Receive = {
    case Work(message) =>
      log.info(s"I received Work message: $message and my ActorRef: $self")
    case msg => log.error(s"Unknown message: $msg")
  }
}
