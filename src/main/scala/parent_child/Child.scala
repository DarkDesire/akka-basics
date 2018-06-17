package parent_child

import akka.actor.{ActorRef, Actor}

class Child(parent: ActorRef) extends Actor {
  def receive: Receive = {
    case "ping" => parent ! "pong"
  }
}