package routing

import akka.actor.ActorSystem
import routing.Worker.Work

/*
14:31:56.069 [my-system-akka.actor.default-dispatcher-3] DEBUG routing.Router - I'm a router and I received a Message ....
14:31:56.069 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Router - I'm a router and I received a Message ....
14:31:56.069 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Router - I'm a router and I received a Message ....
14:31:56.069 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://my-system/user/router/worker-1#698687177]
14:31:56.069 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://my-system/user/router/worker-4#1073821725]
14:31:56.069 [my-system-akka.actor.default-dispatcher-4] DEBUG routing.Worker - I received a message Work and my ActorRef:Actor[akka://my-system/user/router/worker-1#698687177]
 */
object SimpleRouterApp extends App {
  val system = ActorSystem("my-system")
  val router = system.actorOf(Router.props, "router")

  router ! Work
  router ! Work
  router ! Work
  Thread.sleep(100)
  system.terminate()
}

