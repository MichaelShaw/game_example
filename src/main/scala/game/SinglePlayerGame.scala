package game

import game.behaviour._
import game.render._
import game.state._

object SinglePlayerGame {
  def run(world:World, timesteps:Int) {
    for(i <- 1 to timesteps) {
      update(world, 1)
      Render.render(world, i)
    }
  }

  def update(world:World, time:Int) {
    for((id, e) <- world.entities) {
      // handle outstanding events

      // call decide

      // call simulate
    } // accumulate events
  }
}