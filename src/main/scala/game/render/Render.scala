package game.render

import game.state.World

object Render {
  def render(world:World, step:Int) {
    (1 to 5).foreach { _ => println("") }

    val drawBuffer = world.blocks.map { b =>
      b match {
        case 0 => ' '
        case 1 => (0x2593).asInstanceOf[Char]
        case _ => '.'
      }
    }

    // applies entity to drawbuffer
    for(entity <- world.entities.values) {
      val i = world.size.y * entity.position.x + entity.position.y
      drawBuffer(i) = entity.glyph
    }

    println(s"== Step $step ==")
    for(line <- drawBuffer.grouped(world.size.x)) {
      println(line.mkString(" "))
    }
  }
}

