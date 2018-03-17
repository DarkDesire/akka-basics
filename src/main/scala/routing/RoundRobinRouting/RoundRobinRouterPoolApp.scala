package routing.RoundRobinRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, RoundRobinPool}
import routing.Worker
import routing.Worker.Work

/**
  * Routes in a round-robin fashion to its routees.
  * round-robin: http://en.wikipedia.org/wiki/Round-robin
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#roundrobinpool-and-roundrobingroup
  */
object RoundRobinRouterPoolApp extends App {
  val system = ActorSystem("RoundRobinRouter")

  // from code
  // val router = system.actorOf(RoundRobinPool(5).props(Props[Worker]),"roundrobin-router-pool-code")

  // from conf
   val router = system.actorOf(FromConfig.props(Props[Worker]),"roundrobin-router-pool-conf")

  (1 to 10).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
roundrobin-router-pool-code
5 instances * 10 msg = 10 msg
каждый актор обработал по 2 сообщения: a,b,c,e,d
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$e#-700208180]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$c#2106860951]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$d#1850509881]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$c#2106860951]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$e#-700208180]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$d#1850509881]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$a#746956333]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$b#-1730919275]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$b#-1730919275]
15:03:06.736 [RoundRobinRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-code/$a#746956333]
 */

/*
roundrobin-router-pool-conf
5 instances * 10 msg = 10 msg
каждый актор обработал по 2 сообщения: a,b,c,e,d
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$c#-1183674022]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$b#-485912485]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$e#-751984792]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$c#-1183674022]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$e#-751984792]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$d#1546581871]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$b#-485912485]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$d#1546581871]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$a#-1446095524]
15:14:29.012 [RoundRobinRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/roundrobin-router-pool-conf/$a#-1446095524]
 */