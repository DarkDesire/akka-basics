package routing.BalancingRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{BalancingPool, FromConfig}
import routing.Worker
import routing.Worker.Work

/**
  * A Router that will try to redistribute work from busy routees to idle routees.
  * All routees share the same mailbox.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#balancing-pool
  */
object BalancingRouterPoolApp extends App {
  val system = ActorSystem("BalancingRouter")

  // from code
  // val router = system.actorOf(BalancingPool(5).props(Props[Worker]),"balancing-router-pool-code")

  // from conf
  val router = system.actorOf(FromConfig.props(Props[Worker]),"balancing-router-pool-conf")

  // TODO: come up with a better load test
  (1 to 10).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
balancing-router-pool-code
5 instances * 10 msg = 10 msg
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$c#27729135]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$d#-1460518592]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$b#-994835093]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$d#-1460518592]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$b#-994835093]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$c#27729135]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$a#-494732581]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$d#-1460518592]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$b#-994835093]
15:51:02.634 [BalancingRouter-akka.actor.default-dispatcher-2] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-code/$e#1203703195]
 */

/*
balancing-router-pool-conf
5 instances * 10 msg = 10 msg
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$e#28307404]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$c#-1049312203]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$b#-1347520497]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$a#-1077743018]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$c#-1049312203]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$e#28307404]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$a#-1077743018]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$c#-1049312203]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$d#-875748752]
15:52:44.182 [BalancingRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BalancingRouter/user/balancing-router-pool-conf/$b#-1347520497]
 */