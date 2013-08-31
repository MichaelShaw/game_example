package game

import game.behaviour._
import game.render._
import game.state._
import game.event._

import collection.mutable

object SinglePlayerGame {
  def run(world:World, timesteps:Int) {
    for(i <- 1 to timesteps) {
      update(world)
      Render.render(world, i)
    }
  }

  def update(world:World) {
    def routeEntityEvent(tee:ToEntityEvent) {
      world.entities(tee.to).events += tee
    }

    val events = (for((id, e) <- world.entities) yield {
      e match {
        case living:Living => updateLiving(world, living, id)
        case item:Item => updateItem(world, item, id)
      }
    }).flatten // accumulate events and route them out

    val zoneEvents = new mutable.ArrayBuffer[ZoneEvent]()

    for(event <- events) {
      event match {
        case tee:ToEntityEvent => routeEntityEvent(tee)
        case ze:ZoneEvent => zoneEvents += ze
      }
    }

    // mutex over the zone events
  }

  def updateLiving(world:World, living:Living, id:Int) : Seq[Event] = {
    val replyEvents = living.events.flatMap { ev =>
      applyLivingEvent(living, ev, id)
    }

    val decision = Simulate.decide(world, living)

    applyStateTransitionToLiving(living, decision.transitions)

    // apply decision events
    val simulate = Simulate.simulate(world, living)

    applyStateTransitionToLiving(living, simulate.transitions)

    replyEvents ++ decision.events ++ simulate.events
  }

  def applyStateTransitionToLiving(living:Living, transitions:Seq[StateTransition[Living]]) : Seq[Event] = {
    Seq.empty
  }

  def applyStateTransitionToEntity(entity:Entity, transitions:Seq[StateTransition[Entity]]) : Seq[Event] = {
    Seq.empty
  }

  def updateItem(world:World, item:Item, id:Int) : Seq[Event] = {
    val replyEvents = item.events.flatMap { ev =>
      applyItemEvent(item, ev, id)
    }

    val simulate = Simulate.simulate(world, item)

    applyStateTransitionToEntity(item, simulate.transitions)

    replyEvents ++ simulate.events
  }

  def applyItemEvent(item:Item, ev:Event, id:Int) : Seq[Event] = {
    ev match {
      case PickupItem(_, from) => {
        if(item.alive) {
          Seq(SuccessfulPickup(item.itemType, from))
        } else {
          Seq(FailedPickup(id, from))
        }
      }
    }
  }

  def applyLivingEvent(living:Living, ev:Event, id:Int) : Seq[Event] = {
    ev match {
      case PlaceItemSucceeded(itemType, _) =>
      case PlaceItemFailed(itemType, _) => living.itemInHand = Some(itemType)
      case SuccessfulPickup(itemType, _) => living.itemInHand = Some(itemType)
      case FailedPickup(entityId, _) =>
      case TakeDamage(damage, from, _) => {
        living.health -= damage
        living.alive = living.health > 0
      }
    }
    Seq.empty
  }
}