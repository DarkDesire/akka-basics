package routing.RoundRobinRouting

import akka.actor.ActorSystem
import akka.routing.{FromConfig, RoundRobinGroup}
import routing.Worker
import routing.Worker.Work


/**
  * Routes in a round-robin fashion to its routees.
  * round-robin: http://en.wikipedia.org/wiki/Round-robin
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#roundrobinpool-and-roundrobingroup
  */
object RoundRobinRouterGroupApp extends App {
  val system = ActorSystem("RoundRobinRouter")
  (1 to 5).map{id => system.actorOf(Worker.props, s"w$id")}

  // from code
  // val workers: List[String] = (1 to 5).map{id => s"/user/w$id"}.toList
  // val router = system.actorOf(RoundRobinGroup(workers).props(), "roundrobin-router-group-code")

  // from conf
  val router = system.actorOf(FromConfig.props(), "roundrobin-router-group-conf")

  (1 to 10).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
roundrobin-router-group-code
5 instances * 10 msg = 10 msg
каждый актор обработал по 2 сообщения: w1,w2,w3,w4,w5
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w5#-204417330]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w2#-1700373038]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w1#-1111497451]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w5#-204417330]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w2#-1700373038]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w1#-1111497451]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w4#1995770509]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w3#-1826194785]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w4#1995770509]
15:20:18.727 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w3#-1826194785]
*/

/*
roundrobin-router-group-conf
5 instances * 10 msg = 10 msg
каждый актор обработал по 2 сообщения: w1,w2,w3,w4,w5
15:18:38.048 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w5#1997927336]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w1#1014912866]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w4#1856771399]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w3#1396186286]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w5#1997927336]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w4#1856771399]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w2#399248812]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w1#1014912866]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w2#399248812]
15:18:38.049 [RoundRobinRouter-akka.actor.default-dispatcher-6] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RoundRobinRouter/user/w3#1396186286]
*/