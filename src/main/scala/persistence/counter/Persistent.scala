package persistence.counter

import akka.actor.{ActorSystem, Props}
import persistence.counter.Counter.{Cmd, Increment}

object Persistent extends App {
  val system = ActorSystem("persistent-actors")
  val counter = system.actorOf(Props[Counter], "counter")

  counter ! Cmd(Increment(3))
  counter ! Cmd(Increment(2))
  counter ! Cmd(Increment(6))

  counter ! "print"

  Thread.sleep(1000)
  system.terminate()
}
