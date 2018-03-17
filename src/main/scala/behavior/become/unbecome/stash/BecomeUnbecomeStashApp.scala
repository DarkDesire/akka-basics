package behavior.become.unbecome.stash

import akka.actor.{ActorSystem, Props}
import behavior.become.unbecome.stash.UserStorage.{Connect, DBOperation, Disconnect, Operation}

object BecomeUnbecomeStashApp extends App {
  val system = ActorSystem("Hotswap-Become")
  val userStorage = system.actorOf(Props[UserStorage],"userStorage")

  userStorage ! "Something strange"
  userStorage ! Operation(DBOperation.Create, Some(User("eldar","oh.eldarkaa@gmail.com")))
  userStorage ! Connect
  userStorage ! Disconnect
  Thread.sleep(100)
  system.terminate()
}

/*
00:06:50.671 [Hotswap-Become-akka.actor.default-dispatcher-3] DEBUG behavior.become.unbecome.stash.UserStorage - UserStorage: connected to DB
00:06:50.671 [Hotswap-Become-akka.actor.default-dispatcher-3] ERROR behavior.become.unbecome.stash.UserStorage - Unknown message: Something strange
00:06:50.687 [Hotswap-Become-akka.actor.default-dispatcher-3] DEBUG behavior.become.unbecome.stash.UserStorage - UserStorage: received Create to do in user: Some(User(eldar,oh.eldarkaa@gmail.com))
00:06:50.688 [Hotswap-Become-akka.actor.default-dispatcher-3] DEBUG behavior.become.unbecome.stash.UserStorage - UserStorage: disconnected from DB
 */
