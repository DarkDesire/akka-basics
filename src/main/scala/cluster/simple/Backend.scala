package cluster.simple

import akka.actor.{Actor, ActorSystem, Props, RootActorPath}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.MemberUp
import cluster.commons.{Add, BackendRegistration}
import com.typesafe.config.ConfigFactory

class Backend extends Actor {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, MemberUp
  // re-subscribe when restart
  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberUp])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  def receive: Receive = {
    case Add(_, _) =>
      println(s"I'm a backend with path: $self and I received add operation.")
    case MemberUp(member) =>
      if(member.hasRole("frontend")){
        context.actorSelection(RootActorPath(member.address) / "user" / "frontend") !
          BackendRegistration
      }
  }

}

object Backend {
  def initiate(port: Int): Unit = {
    val config = ConfigFactory.load("cluster/cluster").getConfig("Backend")
      .withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port"))

    val system = ActorSystem("ClusterSystem", config)

    val Backend = system.actorOf(Props[Backend], name = "Backend")
  }
}