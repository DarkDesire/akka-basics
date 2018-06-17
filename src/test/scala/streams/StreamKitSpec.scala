package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.stream.testkit.scaladsl._
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

import scala.concurrent.ExecutionContextExecutor

class StreamKitSpec extends TestKit(ActorSystem("test-system"))
  with ImplicitSender
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val flowMaterializer: ActorMaterializer = ActorMaterializer()

  "With Stream Test Kit" should "use a TestSink to test a source" in {
    val sourceUnderTest = Source(1 to 4).filter(_ < 3).map(_ * 2)

    sourceUnderTest.runWith(TestSink.probe[Int])
      .request(2)
      .expectNext(2, 4)
  }

  it should "use a TestSource to test a sink" in {
    val sinkUnderTest = Sink.cancelled

    TestSource.probe[Int]
      .toMat(sinkUnderTest)(Keep.left)
      .run()
      .expectCancellation()

  }

}