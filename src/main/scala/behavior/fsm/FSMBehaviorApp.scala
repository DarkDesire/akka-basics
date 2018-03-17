package behavior.fsm

import akka.actor.{ActorSystem, Props}
import behavior.fsm.UserStorage.{Connect, DBOperation, Disconnect, Operation}

object FSMBehaviorApp extends App {
  val system = ActorSystem("FSM")
  val userStorage = system.actorOf(Props[UserStorage],"userStorage")

  userStorage ! "Something strange"
  userStorage ! Operation(DBOperation.Create, Some(User("eldar","oh.eldarkaa@gmail.com")))
  userStorage ! Connect
  userStorage ! Disconnect
  Thread.sleep(100)
  system.terminate()
}
/*
00:22:45.380 [Hotswap-Become-akka.actor.default-dispatcher-4] DEBUG behavior.fsm.UserStorage - UserStorage: connected to DB
00:22:45.395 [Hotswap-Become-akka.actor.default-dispatcher-4] ERROR behavior.fsm.UserStorage - Unknown message: Something strange
00:22:45.395 [Hotswap-Become-akka.actor.default-dispatcher-4] DEBUG behavior.fsm.UserStorage - UserStorage: received Create to do in user: Some(User(eldar,oh.eldarkaa@gmail.com))
00:22:45.395 [Hotswap-Become-akka.actor.default-dispatcher-4] ERROR behavior.fsm.UserStorage - Unknown message: Disconnect
 */
