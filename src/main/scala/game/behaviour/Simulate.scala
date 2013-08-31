package game.behaviour

import game.state._
import game.event._

object Simulate {
  // occurs at an instant, generally AI or player controls
  def decide(world:World, l:Living) : InstantEventSet = {

    // ai goes here

    null
  }

  // occurs over a period of time
  def simulate(world:World, e:Entity, time:Int) : SimulatedEventSet = {
    // move entity

    // animations etc. would go here (as collisions can depend on them)

    // check for collisions

    null
  }

  // the mutable part
  def applyEvents(e:Entity, events: Seq[StateTransition]) {

  }
}