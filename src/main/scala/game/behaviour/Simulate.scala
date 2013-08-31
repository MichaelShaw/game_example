package game.behaviour

import game.state._
import game.event._

object Simulate {
  // occurs at an instant, generally AI or player controls
  def decide(world:World, l:Living) : DecisionOutput = {

    // ai goes here

    DecisionOutput(Seq.empty, Seq.empty)
  }

  // occurs over a period of time
  def simulate(world:World, e:Entity) : SimulationOutput = {
    // move entity

    // animations etc. would go here (as collisions can depend on them)

    // check for collisions

    SimulationOutput(Seq.empty, Seq.empty)
  }
}