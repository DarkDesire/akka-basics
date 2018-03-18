package routing.TailChoppingRouting

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.routing.{FromConfig, TailChoppingGroup}
import akka.util.Timeout
import routing.Worker
import routing.Worker.{WorkResult, WorkWithResult}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

/**
  * The TailChoppingRouter will first send the message to one, randomly picked,
  * routee and then after a small delay to a second routee (picked randomly from the remaining routees)
  * and so on. It waits for first reply it gets back and forwards it back to original sender.
  * Other replies are discarded.
  *
  * The goal of this router is to decrease latency by performing redundant queries to multiple routees,
  * assuming that one of the other actors may still be faster to respond than the initial one.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#tailchoppingpool-and-tailchoppinggroup
  */
object TailChoppingRouterGroupApp extends App {
  val system = ActorSystem("TailChoppingRouter")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val timeout: Timeout = Timeout(15.seconds)

  (1 to 5).map { id => system.actorOf(Worker.props, s"w$id") }
  // from code
  // val workers: List[String] = (1 to 5).map { id => s"/user/w$id" }.toList
  // val router = system.actorOf(
  //   TailChoppingGroup(
  //     workers,
  //     within = 10.seconds,
  //     interval = 2.seconds).props(), "tail-chopping-router-group-code")

  // from conf
  val router = system.actorOf(FromConfig.props(), "tail-chopping-router-group-conf")

  // TODO: come up with a better loading test
  (router ? WorkWithResult)
    .mapTo[WorkResult]
    .map { _ =>
      println("work done!")
      system.terminate()
    }
    .recover { case _ => system.terminate() }
}

/*
tail-chopping-router-group-conf
interval = 2 seconds
5 instances * 1 msg = 1 msg in result (see "work done!")
13:42:01.314 [TailChoppingRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/w1#405365303]
13:42:03.337 [TailChoppingRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/w3#1406446405]
13:42:05.311 [TailChoppingRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/w5#-1828990845]
13:42:05.960 [TailChoppingRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkWithResult done with 4630 delay!
work done!
13:42:08.083 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - WorkWithResult done with 4747 delay!
13:42:08.663 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - WorkWithResult done with 3352 delay!
 */

/*
tail-chopping-router-group-code
interval = 2 seconds
5 instances * 1 msg = 1 msg in result (see "work done!")
13:44:05.059 [TailChoppingRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/w3#-1697784476]
13:44:07.060 [TailChoppingRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/w2#-1930270343]
13:44:09.073 [TailChoppingRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/w5#1401259828]
13:44:09.411 [TailChoppingRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4331 delay!
work done!
13:44:11.711 [TailChoppingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - WorkWithResult done with 4650 delay!
13:44:13.690 [TailChoppingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - WorkWithResult done with 4616 delay!
 */