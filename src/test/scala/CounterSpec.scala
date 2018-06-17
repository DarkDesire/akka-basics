import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}
import persistent.counter.Counter
import persistent.counter.Counter.{Cmd, GetCount}

import scala.concurrent.duration._

class CounterSpec extends TestKit(ActorSystem("test-system"))
  with ImplicitSender
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Counter Actor" should "handle GetCount message with using TestProbe" in {
    val sender = TestProbe("testProbe")
    val counter = system.actorOf(Props[Counter])

    sender.send(counter, Counter.GetCount)

    val state = sender.expectMsgType[Int]

    state mustEqual 0
  }
  it should "handle Increment message" in {
    val counter = system.actorOf(Props[Counter])
    counter ! Cmd(Counter.Increment(1))
    expectNoMessage(1.seconds)
  }
  it should "handle GetCount message" in {
    val counter = system.actorOf(Props[Counter])
    counter ! GetCount
    expectMsg(1)
  }
  it should "handle Decrement message" in {
    val counter = system.actorOf(Props[Counter])
    counter ! Cmd(Counter.Decrement(1))
    expectNoMessage(1.seconds)
  }
  it should "handle GetCount message #2" in {
    val counter = system.actorOf(Props[Counter])
    counter ! GetCount
    expectMsg(0)
  }
  it should "handle sequence of messages" in {
    val sender = TestProbe("testProbe")
    val counter = system.actorOf(Props[Counter])
    sender.send(counter, GetCount)
    val state = sender.expectMsgType[Int]

    sender.send(counter, Cmd(Counter.Increment(2)))
    sender.send(counter, Cmd(Counter.Increment(4)))
    sender.send(counter, Cmd(Counter.Decrement(3)))

    sender.send(counter, GetCount)
    val state2 = sender.expectMsgType[Int]
    state2 mustEqual(state+3)
  }
}

/*
16:09:24.811 DEBUG persistent.counter.Counter - Starting ...
16:09:25.512 DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
16:09:26.560 DEBUG persistent.counter.Counter - Starting ...
16:09:26.614 DEBUG persistent.counter.Counter - Counter: receive Evt(Increment(1)) on recovering mood
16:09:26.614 DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
16:09:26.620 DEBUG persistent.counter.Counter - Starting ...
16:09:26.628 DEBUG persistent.counter.Counter - Counter: receive Evt(Increment(1)) on recovering mood
16:09:26.629 DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
16:09:26.630 DEBUG persistent.counter.Counter - Counter: receive Cmd(Decrement(1))
16:09:27.642 DEBUG persistent.counter.Counter - Starting ...
16:09:27.658 DEBUG persistent.counter.Counter - Counter: receive snapshot with data State(0) on recovering mood
16:09:27.660 DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
 */