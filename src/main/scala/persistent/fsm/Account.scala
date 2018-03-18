package persistent.fsm

import akka.persistence.fsm.PersistentFSM
import akka.persistence.fsm.PersistentFSM.FSMState
import persistent.fsm.Account._

import reflect._

object Account {

  // Account States
  sealed trait State extends FSMState

  case object Empty extends State {
    override def identifier: String = "Empty"
  }

  case object Active extends State {
    override def identifier: String = "Active"
  }

  // Account Data
  sealed trait Data {
    val amount: Float
  }

  case object ZeroBalance extends Data {
    override val amount: Float = 0.0f
  }

  case class Balance(override val amount: Float) extends Data


  // Domain Events
  sealed trait DomainEvent

  case class AcceptedTransaction(amount: Float,
                                 `type`: TransactionType)
    extends DomainEvent

  case class RejectedTransaction(amount: Float,
                                 `type`: TransactionType,
                                 reason: String)
    extends DomainEvent

  sealed trait TransactionType

  case object CR extends TransactionType

  case object DR extends TransactionType

  // Commands

  case class Operation(amount: Float, `type`: TransactionType)

}

class Account extends PersistentFSM[Account.State, Account.Data, Account.DomainEvent] {
  override def persistenceId: String = "account-id"

  override def domainEventClassTag: ClassTag[DomainEvent] = classTag[DomainEvent]

  override def applyEvent(domainEvent: Account.DomainEvent, currentData: Account.Data): Account.Data = {
    domainEvent match {
      case AcceptedTransaction(amount, CR) =>
        val newAmount = currentData.amount + amount
        log.debug(s"Your new balance is $newAmount")
        Balance(currentData.amount + amount)
      case AcceptedTransaction(amount, DR) =>
        val newAmount = currentData.amount - amount
        log.debug(s"Your new balance is $newAmount")
        if (newAmount > 0) Balance(newAmount) else ZeroBalance
      case RejectedTransaction(_, _, reason) =>
        log.debug(s"RejectedTransaction with reason $reason")
        currentData

    }
  }

  startWith(Empty, ZeroBalance)

  when(Empty) {
    case Event(Operation(amount, CR), _) =>
      log.debug("Hi, It's your first Credit Operation.")
      goto(Active) applying AcceptedTransaction(amount, CR)
    case Event(Operation(amount, DR), _) =>
      log.debug("Sorry, your account has zero balance.")
      stay applying RejectedTransaction(amount, DR, "Balance is zero")
  }

  when(Active) {
    case Event(Operation(amount, CR), _) =>
      stay applying AcceptedTransaction(amount, CR)
    case Event(Operation(amount, DR), balance) =>
      val newBalance = balance.amount - amount
      if (newBalance > 0)
        stay applying AcceptedTransaction(amount, DR)
      else if (newBalance == 0)
        goto(Empty) applying AcceptedTransaction(amount, DR)
      else
        stay applying RejectedTransaction(amount, DR, "balance doesn't cover this operation")
  }

}