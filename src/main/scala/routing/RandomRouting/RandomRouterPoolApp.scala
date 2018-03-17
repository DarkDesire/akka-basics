package routing.RandomRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, RandomPool}
import routing.Worker
import routing.Worker.Work


/**
  * This router type selects one of its routees randomly for each message.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#randompool-and-randomgroup
  */
object RandomRouterPoolApp extends App {
  val system = ActorSystem("RandomRouter")

  // from code
  // val router = system.actorOf(RandomPool(5).props(Props[Worker]),"random-router-pool-code")

  // from conf
  val router = system.actorOf(FromConfig.props(Props[Worker]),"random-router-pool-conf")

  (1 to 6).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
random-router-pool-conf
5 instances * 5 msg = 5 msg
некоторые из акторов даже не отработали 1 сообщения (например,е)
15:23:15.888 [RandomRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-conf/$d#-251419482]
15:23:15.888 [RandomRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-conf/$c#-1156718349]
15:23:15.888 [RandomRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-conf/$a#305274386]
15:23:15.888 [RandomRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-conf/$d#-251419482]
15:23:15.888 [RandomRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-conf/$c#-1156718349]
15:23:15.888 [RandomRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-conf/$d#-251419482]
 */

/*
random-router-pool-code
5 instances * 5 msg = 5 msg
некоторые из акторов даже не отработали 1 сообщения (например,d)
15:24:31.030 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-code/$b#-833181618]
15:24:31.030 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-code/$a#1437711461]
15:24:31.030 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-code/$c#1467673231]
15:24:31.030 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-code/$e#1476433434]
15:24:31.030 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-code/$c#1467673231]
15:24:31.030 [RandomRouter-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://RandomRouter/user/random-router-pool-code/$e#1476433434]
 */