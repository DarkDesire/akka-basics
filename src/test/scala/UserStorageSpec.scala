import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestFSMRef, TestKit}
import behavior.fsm.UserStorage
import behavior.fsm.UserStorage._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

class UserStorageSpec extends TestKit(ActorSystem("test-system"))
  with ImplicitSender
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers {


  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "User Storage" should "Start with disconnected state and empty data" in {
    val storage = TestFSMRef(new UserStorage())

    storage.stateName must equal(Disconnected)
    storage.stateData must equal(EmptyData)

  }

  it should "be connected state if it receive a connect message" in {
    val storage = TestFSMRef(new UserStorage())

    storage ! Connect

    storage.stateName must equal(Connected)
    storage.stateData must equal(EmptyData)
  }

  it should "be still in disconnected state if it receive any other messages" in {
    val storage = TestFSMRef(new UserStorage())

    storage ! DBOperation.Create

    storage.stateName must equal(Disconnected)
    storage.stateData must equal(EmptyData)
  }

  it should "be switch to disconnected when it receive a disconnect message on Connected state" in {
    val storage = TestFSMRef(new UserStorage())

    storage ! Connect

    storage ! Disconnect

    storage.stateName must equal(Disconnected)
    storage.stateData must equal(EmptyData)
  }


  it should "be still on connected state if it receive any DB operations" in {
    val storage = TestFSMRef(new UserStorage())

    storage ! Connect

    storage ! DBOperation.Create

    storage.stateData must equal(EmptyData)
    storage.stateName must equal(Connected)
  }
}
