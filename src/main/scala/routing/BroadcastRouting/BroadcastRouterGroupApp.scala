package routing.BroadcastRouting

import akka.actor.ActorSystem
import akka.routing.{BroadcastGroup, FromConfig}
import routing.Worker
import routing.Worker.Work

/**
  * A broadcast router forwards the message it receives to all its routees.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#broadcastpool-and-broadcastgroup
  */
object BroadcastRouterGroupApp extends App {
  val system = ActorSystem("BroadcastRouter")
  (1 to 5).map{id => system.actorOf(Worker.props, s"w$id")}

  // from code
  // val workers: List[String] = (1 to 5).map{id => s"/user/w$id"}.toList
  // val router = system.actorOf(BroadcastGroup(workers).props(), "broadcast-router-group-code")

  // from conf
  val router = system.actorOf(FromConfig.props(), "broadcast-router-group-conf")

  (1 to 6).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
broadcast-router-group-code
5 instances * 6 msg = 30 msg
каждый из 5 акторов получил по 6 сообщений
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-276349758]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#-1191385298]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#1303707385]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#-1191385298]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-276349758]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#1303707385]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-1838161773]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#-1191385298]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#1303707385]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-1838161773]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#-1191385298]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-1838161773]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#1303707385]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#-1191385298]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-1838161773]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#1303707385]
14:52:38.562 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-1838161773]
14:52:38.578 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#1303707385]
14:52:38.579 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-1838161773]
14:52:38.579 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#-1191385298]
14:52:38.580 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#-1098949867]
14:52:38.580 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-276349758]
14:52:38.581 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#-1098949867]
14:52:38.581 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-276349758]
14:52:38.582 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#-1098949867]
14:52:38.582 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-276349758]
14:52:38.583 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#-1098949867]
14:52:38.583 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-276349758]
14:52:38.583 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#-1098949867]
14:52:38.584 [BroadcastRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#-1098949867]
 */

/*
broadcast-router-group-conf
5 instances * 6 msg = 30 msg
каждый из 5 акторов получил по 6 сообщений
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#1457191375]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#903587202]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-1009789792]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#903587202]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#1457191375]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-1009789792]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#1457191375]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#903587202]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-1009789792]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#1457191375]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#903587202]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-1009789792]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#1457191375]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-1009789792]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#903587202]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#1671635819]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w1#1457191375]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#1671635819]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#1671635819]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w3#903587202]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#1671635819]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w4#-1009789792]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#1671635819]
14:57:48.875 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-299536692]
14:57:48.892 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w2#1671635819]
14:57:48.892 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-299536692]
14:57:48.893 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-299536692]
14:57:48.893 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-299536692]
14:57:48.893 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-299536692]
14:57:48.894 [BroadcastRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/w5#-299536692]
*/