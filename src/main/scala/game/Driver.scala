package game

import game.state._

object Driver extends App {
  val world = {
    val w = new World(size = Vec2i(16, 16))
    w.entities += 1 -> new Living(Vec2i(1, 1))
    w.entities += 2 -> new Item(Vec2i(10, 10), Item.sword)
    World.assignBorders(w, 1)
    w
  }

  SinglePlayerGame.run(world, timesteps = 1)
}