package game.event

import game.state.Vec2i

trait Event

trait FromEntityEvent { def from:Int } // all events are routed
trait ToEntityEvent { def to:Int }

case class PlaceBlockAt(at:Vec2i, b:Byte, from:Int) extends FromEntityEvent
  case class PlaceBlockFailed(b:Byte, to:Int) extends ToEntityEvent
  case class PlaceBlockSucceeded(b:Byte, to:Int) extends ToEntityEvent

case class RequestPath(fromPosition:Vec2i, toPosition:Vec2i, from:Int) extends FromEntityEvent
  case class PathRequestSuccessful(path:Seq[Int], to:Int) extends ToEntityEvent
  case class PathRequestFailed(to:Int) extends ToEntityEvent

case class PickupItem(to:Int, from:Int) extends FromEntityEvent with ToEntityEvent
  case class SuccessfulPickup(itemType:Int, to:Int) extends ToEntityEvent
  case class FailedPickup(from:Int, to:Int) extends FromEntityEvent with ToEntityEvent

case class InstantEventSet(events:Seq[Event], transitions: Seq[InstantStateTransition])
case class SimulatedEventSet(events:Seq[Event], transitions: Seq[StateTransition])

case class TakeDamage(damage:Int, to:Int, from:Int) extends FromEntityEvent with ToEntityEvent

trait StateTransition
trait InstantStateTransition extends StateTransition // decide can only return instant state transitions
case object DrawWeapon extends InstantStateTransition
case object PutAwayWeapon extends InstantStateTransition
case object BeginAttack extends InstantStateTransition
case class ChangeFacing(facing:Vec2i) extends InstantStateTransition
case object Death extends InstantStateTransition

trait SimulatedStateTransition extends StateTransition // only update is allowed to return simulated state transitions (it can also return instants)
case class ChangePosition(position:Vec2i) extends StateTransition





