package game.event

import game.state.{Vec2i, Living, Entity}

trait Event

trait FromEntityEvent extends Event {
  def from:Int
} // all events are routed
trait ToEntityEvent extends Event { def to:Int }

// doesn't cause a state transition, just a flag to hold on to it rather than mutate
// mainly used for failures, or any event that doesn't transition state

trait ZoneEvent extends Event // routed to event

case class DecisionOutput(events:Seq[Event], transitions: Seq[InstantStateTransition[Living]])
case class SimulationOutput(events:Seq[Event], transitions: Seq[StateTransition[Entity]])


// ROUTED EVENTS
case class PlaceItemAt(at:Vec2i, itemType:Int, from:Int) extends FromEntityEvent with ZoneEvent {
  def failure = PlaceItemFailed(itemType, from)
}
  case class PlaceItemFailed(itemType:Int, to:Int) extends ToEntityEvent
  case class PlaceItemSucceeded(itemType:Int, to:Int) extends ToEntityEvent

case class RequestPath(fromPosition:Vec2i, toPosition:Vec2i, from:Int) extends FromEntityEvent {
  def failure = PathRequestFailed(from)
}

  case class PathRequestSuccessful(path:Seq[Int], to:Int) extends ToEntityEvent
  case class PathRequestFailed(to:Int) extends ToEntityEvent

case class PickupItem(to:Int, from:Int) extends FromEntityEvent with ToEntityEvent {
  def failure = FailedPickup(to, from)
}
  case class SuccessfulPickup(itemType:Int, to:Int) extends ToEntityEvent
  case class FailedPickup(from:Int, to:Int) extends ToEntityEvent

case class DropItem(itemType:Int, from:Int) extends FromEntityEvent with ZoneEvent // unsure if this is needed

case class TakeDamage(damage:Int, from:Int, to:Int) extends FromEntityEvent with ToEntityEvent

// STATE TRANSITIONS
trait StateTransition[-T] {
  def applyTo(entity: T)
}

  trait InstantStateTransition[T] extends StateTransition[T] // decide can only return instant state transitions
    case object DrawWeapon extends InstantStateTransition[Living] {
      def applyTo(living:Living) {
        living.weaponDrawn = true
      }
    }
    case object PutAwayWeapon extends InstantStateTransition[Living] {
      def applyTo(living:Living) = {
        living.weaponDrawn = false
      }
    }
    case object BeginAttack extends InstantStateTransition[Living] {
      def applyTo(living:Living) = {
        living.attacking = true
      }
    }
    case class ChangeFacing(facing:Vec2i) extends InstantStateTransition[Living] {
      def applyTo(living:Living) = {
        living.facing := facing
      }
    }
    case object Death extends InstantStateTransition[Entity] {
      def applyTo(entity:Entity) {
        entity.alive = false
      }
    }

  trait SimulatedStateTransition[T] extends StateTransition[T] // only update is allowed to return simulated state transitions (it can also return instants)
    case class ChangePosition(position:Vec2i) extends SimulatedStateTransition[Entity] {
      def applyTo(entity:Entity) {
        entity.position := position
      }
    }
