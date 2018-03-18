package persistent.doc
import ExamplePersistentActor._

case class ExampleState(events: List[String] = Nil) {
  def updated(evt: Evt): ExampleState = copy(evt.data :: events)
  def size: Int = events.length
  override def toString: String = events.reverse.toString
  def printLast5: String = events.take(5).reverse.toString
}