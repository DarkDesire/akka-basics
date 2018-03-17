package actorsystem

import actorsystem.Recorder.NewUser
import actorsystem.Storage.GetAllUsers
import akka.actor.{ActorRef, ActorSystem, Props}


object ActorSystemApp extends App {
  val system = ActorSystem("talk-to-actor")

  val checker: ActorRef = system.actorOf(Props[Checker], "checker")
  val storage: ActorRef = system.actorOf(Props[Storage], "storage")
  val recorder: ActorRef = system.actorOf(Recorder.props(checker,storage), "recorder")

  recorder ! NewUser(User("eldar","oh.eldarkaa@gmail.com"))
  recorder ! NewUser(User("Obi-wan", "obi-wan@kenobi.com"))

  Thread.sleep(200)

  storage ! GetAllUsers

  recorder ! NewUser(User("one","one@gmail.com"))
  recorder ! NewUser(User("two", "two@gmail.com"))

  Thread.sleep(200)

  storage ! GetAllUsers

  system.terminate()
}