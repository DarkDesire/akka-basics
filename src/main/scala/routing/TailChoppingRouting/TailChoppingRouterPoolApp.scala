package routing.TailChoppingRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, TailChoppingPool}
import akka.util.Timeout
import routing.Worker
import routing.Worker.{WorkResult, WorkWithResult}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import akka.pattern.ask

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
object TailChoppingRouterPoolApp extends App {
  val system = ActorSystem("TailChoppingRouter")

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val timeout: Timeout = Timeout(15.seconds)

  // from code
  //val router = system.actorOf(TailChoppingPool(
  //  5,
  //  within = 10.seconds,
  //  interval = 2.seconds
  //).props(Props[Worker]),"tail-chopping-router-pool-code")

  // from conf
  val router = system.actorOf(FromConfig.props(Props[Worker]),"tail-chopping-router-pool-conf")

  // TODO: come up with a better loading test
  (router ? WorkWithResult)
    .mapTo[WorkResult]
    .map{_ =>
      println("work done!")
      system.terminate()
    }
    .recover{case _ => system.terminate()}
}

/*
tail-chopping-router-pool-code
interval = 2 seconds
5 instances * 1 msg = 1 msg in result (see "work done!")
22:10:21.679 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/tail-chopping-router-pool-code/$d#-872469931]
22:10:23.693 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/tail-chopping-router-pool-code/$e#2051204904]
22:10:25.697 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/tail-chopping-router-pool-code/$a#-1515039178]
22:10:26.517 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - WorkWithResult done with 4824 delay!
work done!
22:10:27.375 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - WorkWithResult done with 3681 delay!
22:10:29.499 [TailChoppingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - WorkWithResult done with 3802 delay!
 */

/*
tail-chopping-router-pool-conf
interval = 2 seconds
5 instances * 1 msg = 1 msg in result (see "work done!")
22:12:00.252 [TailChoppingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/tail-chopping-router-pool-conf/$b#370798953]
22:12:02.255 [TailChoppingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/tail-chopping-router-pool-conf/$c#2121331849]
22:12:04.254 [TailChoppingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://TailChoppingRouter/user/tail-chopping-router-pool-conf/$a#1664166826]
22:12:05.116 [TailChoppingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - WorkWithResult done with 4852 delay!
work done!
22:12:06.806 [TailChoppingRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - WorkWithResult done with 4549 delay!
22:12:08.857 [TailChoppingRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - WorkWithResult done with 4604 delay!
*/