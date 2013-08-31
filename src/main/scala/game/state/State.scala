package game.state

import collection.mutable

case class World(val size:Vec2i,
                 val entities:mutable.HashMap[Int, Entity] = new mutable.HashMap[Int, Entity]()) {
  val blocks = new Array[Byte](size.product)

  def hasBlockAt(x:Int, y:Int) = getBlockAt(x, y) > 0

  def getBlockAt(x:Int, y:Int) : Byte = {
    blocks(blockPosition(x, y))
  }

  def setBlockAt(x:Int, y:Int, b:Byte) {
    blocks(blockPosition(x, y)) = b
  }

  private def blockPosition(x:Int, y:Int) : Int = {
    size.y * x + y
  }
}

object World {
  def assignBorders(world:World, b:Byte) {
    for {
      x <- 0 until world.size.x
    } {
      world.setBlockAt(x, 0 , b)
      world.setBlockAt(x, world.size.y - 1, b)
    }

    for {
      y <- 0 until world.size.y
    } {
      world.setBlockAt(0, y, b)
      world.setBlockAt(world.size.x - 1, y, b)
    }
  }
}

abstract class Entity(val position:Vec2i) {
  val events = new mutable.Queue[game.event.ToEntityEvent]()

  var alive = true // needed to track an object being picked up while still processing all of it's events

  def glyph : Char

  val velocity = Vec2i(0, 0)
}

class Living(pos:Vec2i) extends Entity(pos) {
  def glyph = 0x263a

  var health = 100

  var itemInHand : Option[Int] = None

  def holdingItem = itemInHand.isDefined

  val facing = Vec2i(1, 0)

  var weaponDrawn = false
  var attacking = false
}

class Item(pos:Vec2i, val itemType:Int) extends Entity(pos) {
  def glyph = 0x2663

}

object Item {
  val sword = 0
  val crate = 1
}

case class Vec2i(var x:Int, var y:Int) {
  def product = x * y
  def :=(other:Vec2i) {
    x = other.x;
    y = other.y
  }
}