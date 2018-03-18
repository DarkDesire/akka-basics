package remoting

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object MemberService extends App {
  val config = ConfigFactory.load.getConfig("MemberService")
  val system = ActorSystem("MemberService", config)
  val worker = system.actorOf(Props[Worker], "remote-worker")

  println(s"Worker actor path is ${worker.path}")
}

object MemberServiceLookup extends App {
  val config = ConfigFactory.load.getConfig("MemberServiceLookup")
  val system = ActorSystem("MemberServiceLookup", config)
  val worker = system.actorSelection("akka.tcp://MemberService@127.0.0.1:2552/user/remote-worker")

  worker ! Worker.Work("Hi Remote Actor")
}

object MemberServiceRemoteCreation extends App {
  val config = ConfigFactory.load.getConfig("MemberServiceRemoteCreation")
  val system = ActorSystem("MemberServiceRemoteCreation", config)
  val workerActor = system.actorOf(Props[Worker], "workerActorRemote")

  println(s"The remote path of worker action is ${workerActor.path}")

  workerActor ! Worker.Work("Hi Remote Actor")
}