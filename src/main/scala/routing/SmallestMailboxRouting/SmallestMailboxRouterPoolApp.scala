package routing.SmallestMailboxRouting

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, SmallestMailboxPool}
import routing.RandomRouting.RandomRouterGroupApp.system
import routing.Worker
import routing.Worker.{Work, WorkHard}

/**
  * A Router that tries to send to the non-suspended child routee with fewest messages in mailbox.
  * The selection is done in this order:
  * 1. pick any idle routee (not processing message) with empty mailbox
  * 2. pick any routee with empty mailbox
  * 3. pick routee with fewest pending messages in mailbox
  * 4. pick any remote routee, remote actors are consider lowest priority, since their mailbox size is unknown
  *
  * https://doc.akka.io/docs/akka/2.5/routing.html#smallestmailboxpool
  */
object SmallestMailboxRouterPoolApp extends App {
  val system = ActorSystem("SmallestMailboxRouter")
  (1 to 5).map{id => system.actorOf(Worker.props, s"w$id")}

  // from code
  // val router = system.actorOf(SmallestMailboxPool(5).props(Props[Worker]),"smallestmailbox-router-pool-code")

  // from conf
  val router = system.actorOf(FromConfig.props(Props[Worker]),"smallestmailbox-router-pool-conf")

  // TODO: come up with a better loading test
  (1 to 15).foreach(_ => router ! WorkHard)
  Thread.sleep(10000)
  system.terminate()
}

/*
smallestmailbox-router-pool-code
5 instances * 15 msg = 15 msg
21:39:53.822 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$e#1540632304]
21:39:53.822 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$c#-1712165597]
21:39:53.822 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$a#-1930786367]
21:39:53.822 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$b#-1100934801]
21:39:53.822 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$d#-1311035259]
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$a#-1930786367]
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$c#-1712165597]
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$b#-1100934801]
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$d#-1311035259]
21:39:55.823 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$e#1540632304]
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$a#-1930786367]
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$c#-1712165597]
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$b#-1100934801]
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$d#-1311035259]
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:57.836 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-code/$e#1540632304]
21:39:59.843 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:59.843 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:59.843 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:59.843 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
21:39:59.843 [SmallestMailboxRouter-akka.actor.default-dispatcher-8] DEBUG routing.Worker - WorkHard done!
 */

/*
smallestmailbox-router-pool-conf
5 instances * 15 msg = 15 msg
21:38:39.039 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$d#188776696]
21:38:39.039 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$a#1809993809]
21:38:39.039 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$e#-1949115441]
21:38:39.039 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$c#2112399345]
21:38:39.039 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$b#-112228202]
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$e#-1949115441]
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$d#188776696]
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$a#1809993809]
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$c#2112399345]
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:41.041 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$b#-112228202]
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$e#-1949115441]
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$a#1809993809]
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$d#188776696]
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$c#2112399345]
21:38:43.043 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:43.059 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - I received a message WorkHard and my ActorRef:Actor[akka://SmallestMailboxRouter/user/smallestmailbox-router-pool-conf/$b#-112228202]
21:38:45.044 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:45.044 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:45.044 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:45.044 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
21:38:45.044 [SmallestMailboxRouter-akka.actor.default-dispatcher-9] DEBUG routing.Worker - WorkHard done!
 */