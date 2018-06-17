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
    sender.expectMsgType[Int]
  }
  it should "handle Increment message" in {
    val counter = system.actorOf(Props[Counter])
    counter ! Cmd(Counter.Increment(1))
    expectNoMessage(1.seconds)
  }
  it should "handle Decrement message" in {
    val counter = system.actorOf(Props[Counter])
    counter ! Cmd(Counter.Decrement(1))
    expectNoMessage(1.seconds)
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
16:27:50.762 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Starting ...
16:27:51.199 [test-system-akka.actor.default-dispatcher-7] DEBUG persistent.counter.Counter - Counter: receive snapshot with data State(5) on recovering mood
16:27:51.528 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Increment(4)) on recovering mood
16:27:51.528 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Decrement(3)) on recovering mood
16:27:51.529 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Increment(1)) on recovering mood
16:27:51.530 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Decrement(1)) on recovering mood
16:27:51.530 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Increment(2)) on recovering mood
16:27:51.531 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Increment(4)) on recovering mood
16:27:51.532 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Counter: receive Evt(Decrement(3)) on recovering mood
16:27:51.533 [test-system-akka.actor.default-dispatcher-3] DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
16:27:52.568 [test-system-akka.actor.default-dispatcher-15] DEBUG persistent.counter.Counter - Starting ...
16:27:52.582 [test-system-akka.actor.default-dispatcher-14] DEBUG persistent.counter.Counter - Counter: receive snapshot with data State(10) on recovering mood
16:27:52.584 [test-system-akka.actor.default-dispatcher-14] DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
16:27:52.584 [test-system-akka.actor.default-dispatcher-14] DEBUG persistent.counter.Counter - Counter: receive Cmd(Decrement(1))
16:27:53.583 [test-system-akka.actor.default-dispatcher-13] DEBUG persistent.counter.Counter - Starting ...
16:27:53.589 [test-system-akka.actor.default-dispatcher-12] DEBUG persistent.counter.Counter - Counter: receive snapshot with data State(10) on recovering mood
16:27:53.594 [test-system-akka.actor.default-dispatcher-13] DEBUG persistent.counter.Counter - Counter: receive Evt(Decrement(1)) on recovering mood
16:27:53.595 [test-system-akka.actor.default-dispatcher-13] DEBUG persistent.counter.Counter - Recovery completed and now I'll switch to normal receiving mode
16:27:53.597 [test-system-akka.actor.default-dispatcher-13] DEBUG persistent.counter.Counter - Counter: receive Cmd(Increment(2))
16:27:53.603 [test-system-akka.actor.default-dispatcher-12] DEBUG persistent.counter.Counter - Counter: receive Cmd(Increment(4))
16:27:53.605 [test-system-akka.actor.default-dispatcher-12] DEBUG persistent.counter.Counter - Counter: receive Cmd(Decrement(3))
*/