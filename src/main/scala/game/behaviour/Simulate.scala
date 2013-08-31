package game.behaviour

import game.state._
import game.event._

object Simulate {
  def update(world:World, time:Int) {
    for((id, e) <- world.entities) {
      // handle outstanding events
      // call decide

      // call simulate
    } // accumulate events
  }

  // occurs at an instant
  def decide(world:World, l:Living) : InstantEventSet = {

    null

  }

  // occurs over a period of time
  def simulate(e:Entity, time:Int) : SimulatedEventSet = {

    null
  }

  def applyEvents(e:Entity, events: Seq[StateTransition]) {

  }
}