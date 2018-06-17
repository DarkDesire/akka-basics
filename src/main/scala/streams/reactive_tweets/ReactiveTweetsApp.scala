package streams.reactive_tweets

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import twitter4j.Status

import scala.concurrent.ExecutionContextExecutor

object ReactiveTweetsApp extends App {
  implicit val system: ActorSystem = ActorSystem("reactive-tweets")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val source = Source.fromIterator(() => TwitterClient.retrieveTweets("#Akka"))

  val normalize = Flow[Status].map{ t =>
    Tweet(Author(t.getUser().getName()), t.getText())
  }

  val sink = Sink.foreach[Tweet](tw => println(s"tweet:\n$tw\n\n"))

  source.via(normalize).runWith(sink).andThen{
    case _ =>
      system.terminate()
  }
}