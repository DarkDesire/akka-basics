package routing

import akka.actor.{ActorSystem, Props}
import routing.Worker.Work

/*
14:30:39.922 [my-system-akka.actor.default-dispatcher-7] DEBUG routing.RouterGroup - RouterGroup: receive a message work
14:30:39.937 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.RouterGroup - RouterGroup: receive a message work
14:30:39.937 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.RouterGroup - RouterGroup: receive a message work
14:30:39.937 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://my-system/user/w3#70668868]
14:30:39.937 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://my-system/user/w5#225560679]
14:30:39.937 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://my-system/user/w1#-1799764392]
 */
object SimpleRouterGroupApp extends App {
  val system = ActorSystem("my-system")
  (1 to 5).map{id => system.actorOf(Worker.props, s"w$id")}
  val workers: List[String] = (1 to 5).map{id => s"/user/w$id"}.toList
  val routerGroup = system.actorOf(Props(classOf[RouterGroup], workers),"router-group")

  routerGroup ! Work
  routerGroup ! Work
  routerGroup ! Work
  Thread.sleep(100)
  system.terminate()
}