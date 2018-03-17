package routing.RandomRouting

import akka.actor.ActorSystem
import akka.routing.{FromConfig, RandomGroup}
import routing.Worker
import routing.Worker.Work

/**
  * This router type selects one of its routees randomly for each message.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#randompool-and-randomgroup
  */
object RandomRouterGroupApp extends App {
  val system = ActorSystem("RandomRouter")
  (1 to 5).map{id => system.actorOf(Worker.props, s"w$id")}

  // from code
  // val workers: List[String] = (1 to 5).map{id => s"/user/w$id"}.toList
  // val router = system.actorOf(RandomGroup(workers).props(), "random-router-group-code")

  // from conf
  val router = system.actorOf(FromConfig.props(), "random-router-group-conf")

  (1 to 6).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
random-router-group-code
5 instances * 6 msg = 6 msg
некоторые из акторов даже не отработали 1 сообщения (например,w5)
15:27:32.045 [RandomRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w4#-341949006]
15:27:32.045 [RandomRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w3#948307339]
15:27:32.045 [RandomRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w1#-648648847]
15:27:32.045 [RandomRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w2#-1079246763]
15:27:32.045 [RandomRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w1#-648648847]
15:27:32.045 [RandomRouter-akka.actor.default-dispatcher-3] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w1#-648648847]
 */

/*
random-router-group-conf
5 instances * 6 msg = 6 msg
некоторые из акторов даже не отработали 1 сообщения (например,w1,w4)
15:29:46.910 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w5#-1133573879]
15:29:46.910 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w3#-634916195]
15:29:46.910 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w2#1843199297]
15:29:46.910 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w3#-634916195]
15:29:46.910 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w2#1843199297]
15:29:46.910 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/w3#-634916195]
 */