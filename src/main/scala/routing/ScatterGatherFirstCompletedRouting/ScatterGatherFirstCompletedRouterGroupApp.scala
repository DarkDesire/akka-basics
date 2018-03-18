package routing.ScatterGatherFirstCompletedRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, ScatterGatherFirstCompletedGroup}
import routing.Worker
import routing.Worker.{WorkResult, WorkWithResult}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

/**
  * The ScatterGatherFirstCompletedRouter will send the message on to all its routees.
  * It then waits for first reply it gets back. This result will be sent back to original sender.
  * Other replies are discarded.
  *
  * It is expecting at least one reply within a configured duration, otherwise
  * it will reply with akka.pattern.AskTimeoutException in a akka.actor.Status.Failure.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#scattergatherfirstcompletedpool-and-scattergatherfirstcompletedgroup
  */
object ScatterGatherFirstCompletedRouterGroupApp extends App {
  val system = ActorSystem("ScatterGatherFirstCompletedRouter")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val timeout: Timeout = Timeout(15.seconds)

  (1 to 5).map { id => system.actorOf(Worker.props, s"w$id") }
  // from code
  // val workers: List[String] = (1 to 5).map{id => s"/user/w$id"}.toList
  // val router = system.actorOf(ScatterGatherFirstCompletedGroup(
  //  workers,
  //  within = 10.seconds).props(), "scatterGatherFirstCompleted-router-group-code")

  // from conf
  val router = system.actorOf(FromConfig.props(), "scatterGatherFirstCompleted-router-group-conf")

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
scatterGatherFirstCompleted-router-group-code
5 instances * 1 msg = 1 msg in result (see "work done!")
21:55:48.049 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w3#2125076358]
21:55:48.049 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w5#-693081707]
21:55:48.049 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w1#-532807187]
21:55:48.049 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w2#1332839127]
21:55:48.049 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w4#1454946564]
21:55:51.162 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - WorkWithResult done with 3099 delay!
work done!
21:55:52.710 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4647 delay!
21:55:52.762 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4699 delay!
21:55:52.865 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4802 delay!
21:55:52.916 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4839 delay!
 */

/*
scatterGatherFirstCompleted-router-group-conf
5 instances * 1 msg = 1 msg in result (see "work done!")
21:54:10.722 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w3#-448859056]
21:54:10.722 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w2#-1975887059]
21:54:10.722 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w1#1855739827]
21:54:10.722 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w4#-1317354358]
21:54:10.738 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/w5#1331738241]
21:54:14.638 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkWithResult done with 3897 delay!
work done!
21:54:15.014 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - WorkWithResult done with 4272 delay!
21:54:15.122 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - WorkWithResult done with 4381 delay!
21:54:15.346 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - WorkWithResult done with 4607 delay!
21:54:15.594 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - WorkWithResult done with 4850 delay!
 */