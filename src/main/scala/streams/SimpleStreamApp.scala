package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}

import scala.concurrent.Future

object SimpleStreamApp extends App {
  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val source = Source(1 to 10)
  val multiply = Flow[Int].map(_ * 4)
  val sink = Sink.fold[Int, Int](0)(_ + _)

  // connect the Source to the Sink, obtaining a RunnableGraph
  val runnable = source.via(multiply).toMat(sink)(Keep.right)

  // materialize the flow and get the value of the FoldSink
  val sum: Future[Int] = runnable.run()

  sum.map { value =>
    println(s"Value: $value")
    system.terminate()
  }
}
