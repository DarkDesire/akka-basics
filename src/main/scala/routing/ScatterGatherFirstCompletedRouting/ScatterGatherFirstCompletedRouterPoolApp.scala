package routing.ScatterGatherFirstCompletedRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, ScatterGatherFirstCompletedPool}
import akka.util.Timeout
import routing.Worker
import routing.Worker.{Work, WorkResult, WorkWithResult}
import akka.pattern.ask

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
object ScatterGatherFirstCompletedRouterPoolApp extends App {
  val system = ActorSystem("ScatterGatherFirstCompletedRouter")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val timeout: Timeout = Timeout(15.seconds)

  // from code
  // val router = system.actorOf(ScatterGatherFirstCompletedPool(5, within = 10.seconds).props(Props[Worker]),"scatterGatherFirstCompleted-router-pool-code")

  // from conf
  val router = system.actorOf(FromConfig.props(Props[Worker]),"scatterGatherFirstCompleted-router-pool-conf")

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
scatterGatherFirstCompleted-router-pool-code
5 instances * 1 msg = 1 msg in result (see "work done!")
21:57:31.787 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-code/$a#580458479]
21:57:31.787 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-code/$b#-1437526863]
21:57:31.787 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-code/$d#2040032679]
21:57:31.787 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-code/$e#1656290627]
21:57:31.787 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-code/$c#2009534414]
21:57:35.165 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - WorkWithResult done with 3365 delay!
work done!
21:57:36.086 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4287 delay!
21:57:36.338 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4537 delay!
21:57:36.430 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4630 delay!
21:57:36.554 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - WorkWithResult done with 4755 delay!
 */

/*
scatterGatherFirstCompleted-router-pool-conf
5 instances * 1 msg = 1 msg in result (see "work done!")
21:58:25.409 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-conf/$e#-2103013371]
21:58:25.409 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-conf/$a#542708173]
21:58:25.409 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-conf/$d#1498084964]
21:58:25.409 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-conf/$c#676462679]
21:58:25.409 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message WorkWithResult and my ActorRef:Actor[akka://ScatterGatherFirstCompletedRouter/user/scatterGatherFirstCompleted-router-pool-conf/$b#887592363]
21:58:29.666 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - WorkWithResult done with 4242 delay!
work done!
21:58:30.052 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkWithResult done with 4628 delay!
21:58:30.173 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkWithResult done with 4750 delay!
21:58:30.353 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkWithResult done with 4930 delay!
21:58:30.398 [ScatterGatherFirstCompletedRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkWithResult done with 4977 delay!
 */