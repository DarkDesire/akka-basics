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

/* MemberService
[INFO] [03/18/2018 16:56:18.272] [main] [akka.remote.Remoting] Starting remoting
[INFO] [03/18/2018 16:56:18.734] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://MemberService@127.0.0.1:2552]
[INFO] [03/18/2018 16:56:18.734] [main] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://MemberService@127.0.0.1:2552]
Worker actor path is akka://MemberService/user/remote-worker
*/

/* MemberServiceLookup
[INFO] [03/18/2018 16:56:27.454] [main] [akka.remote.Remoting] Starting remoting
[INFO] [03/18/2018 16:56:27.903] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://MemberServiceLookup@127.0.0.1:2553]
[INFO] [03/18/2018 16:56:27.919] [main] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://MemberServiceLookup@127.0.0.1:2553]
[WARN] [SECURITY][03/18/2018 16:56:28.235] [MemberServiceLookup-akka.remote.default-remote-dispatcher-6] [akka.serialization.Serialization(akka://MemberServiceLookup)] Using the default Java serializer for class [remoting.Worker$Work] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
 */

/* MemberService
[INFO] [03/18/2018 16:56:28.304] [MemberService-akka.actor.default-dispatcher-3] [akka.tcp://MemberService@127.0.0.1:2552/user/remote-worker] I received Work message: Hi Remote Actor and my ActorRef: Actor[akka://MemberService/user/remote-worker#-2096576179]
 */

////////////////////////////////////////////////////////////////

/* MemberService
[INFO] [03/18/2018 17:06:23.230] [main] [akka.remote.Remoting] Starting remoting
[INFO] [03/18/2018 17:06:23.678] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://MemberService@127.0.0.1:2552]
[INFO] [03/18/2018 17:06:23.678] [main] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://MemberService@127.0.0.1:2552]
Worker actor path is akka://MemberService/user/remote-worker
 */

/* MemberServiceRemoteCreation
[INFO] [03/18/2018 17:06:41.106] [main] [akka.remote.Remoting] Starting remoting
[INFO] [03/18/2018 17:06:41.541] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://MemberServiceRemoteCreation@127.0.0.1:2558]
[INFO] [03/18/2018 17:06:41.541] [main] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://MemberServiceRemoteCreation@127.0.0.1:2558]
The remote path of worker action is akka.tcp://MemberService@127.0.0.1:2552/remote/akka.tcp/MemberServiceRemoteCreation@127.0.0.1:2558/user/workerActorRemote
[WARN] [SECURITY][03/18/2018 17:06:41.942] [MemberServiceRemoteCreation-akka.remote.default-remote-dispatcher-8] [akka.serialization.Serialization(akka://MemberServiceRemoteCreation)] Using the default Java serializer for class [remoting.Worker$Work] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
 */

/* MemberService
[INFO] [03/18/2018 17:06:42.026] [MemberService-akka.actor.default-dispatcher-14] [akka.tcp://MemberService@127.0.0.1:2552/remote/akka.tcp/MemberServiceRemoteCreation@127.0.0.1:2558/user/workerActorRemote] I received Work message: Hi Remote Actor and my ActorRef: Actor[akka://MemberService/remote/akka.tcp/MemberServiceRemoteCreation@127.0.0.1:2558/user/workerActorRemote#1514808156]
 */