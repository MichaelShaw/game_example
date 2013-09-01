package game.state

import collection.mutable


class World(val size:Vec2i) {
  val blocks = new Array[Byte](size.product)
  val entities = new mutable.HashMap[Int, Entity]()

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
  def takeDamage(damage:Int)(l:Living) : Living = {
    l.copy(health = floor(l.health - damage, 0, 100))
  }

  def floor(n:Int, min:Int, max:Int) = {
    if(n < min) {
      min
    } else if (n > max) {
      max
    } else {
      n
    }
  }


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
  var alive = true // needed to track an object being picked up while still processing all of it's events

  val events = new mutable.Queue[game.event.ToEntityEvent]()

  def glyph : Char
}

case class Living(pos:Vec2i, var health:Int = 100) extends Entity(pos) {
  val facing = Vec2i(1, 0)
  var weaponDrawn = false
  var attacking = false

  var itemInHand : Option[Int] = None

  def glyph = 0x263a

  def holdingItem = itemInHand.isDefined




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