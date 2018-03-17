package routing.BroadcastRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{BroadcastPool, FromConfig}
import routing.Worker
import routing.Worker.Work

/**
  * A broadcast router forwards the message it receives to all its routees.
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#broadcastpool-and-broadcastgroup
  */
object BroadcastRouterPoolApp extends App {
  val system = ActorSystem("BroadcastRouter")

  // from code
  // val router = system.actorOf(BroadcastPool(5).props(Props[Worker]),"broadcast-router-pool-code")

  // from conf
  val router = system.actorOf(FromConfig.props(Props[Worker]),"broadcast-router-pool-conf")

  (1 to 6).foreach(_ => router ! Work)
  Thread.sleep(100)
  system.terminate()
}

/*
broadcast-router-pool-conf
5 instances * 6 msg = 30 msg
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$b#-1191180436]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$c#2038678925]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$d#2031429021]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$d#2031429021]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$c#2038678925]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$a#819319715]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$c#2038678925]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$d#2031429021]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$a#819319715]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$c#2038678925]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$d#2031429021]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$a#819319715]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$d#2031429021]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$a#819319715]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$d#2031429021]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$a#819319715]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$a#819319715]
14:49:06.011 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$b#-1191180436]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$e#1240139783]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$b#-1191180436]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$e#1240139783]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$b#-1191180436]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$e#1240139783]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$b#-1191180436]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$e#1240139783]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$e#1240139783]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$b#-1191180436]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$c#2038678925]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$e#1240139783]
14:49:06.027 [BroadcastRouter-akka.actor.default-dispatcher-7] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-conf/$c#2038678925]
*/


/*
broadcast-router-pool-code
5 instances * 6 msg = 30 msg
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$d#1917439878]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$b#65576707]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$c#195931143]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$c#195931143]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$b#65576707]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$d#1917439878]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$b#65576707]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$c#195931143]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$d#1917439878]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$b#65576707]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$d#1917439878]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$c#195931143]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$b#65576707]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$e#-1596602742]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$d#1917439878]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$e#-1596602742]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$b#65576707]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$e#-1596602742]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$d#1917439878]
14:49:42.099 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$e#-1596602742]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$c#195931143]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$a#-1766685232]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$a#-1766685232]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$c#195931143]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$a#-1766685232]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$e#-1596602742]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$a#-1766685232]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$e#-1596602742]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$a#-1766685232]
14:49:42.114 [BroadcastRouter-akka.actor.default-dispatcher-5] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://BroadcastRouter/user/broadcast-router-pool-code/$a#-1766685232]
*/