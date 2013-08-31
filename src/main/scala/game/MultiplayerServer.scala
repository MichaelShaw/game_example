package game

import game.behaviour._
import game.render._
import game.state._
import game.event._

abstract class ClientConnection {
  // send
  def sendPlayerEvents(event:Seq[Event], stateTransitions:Seq[Event])

  // receive
  def receive : (Seq[Event], Seq[StateTransition])
}

object MultiplayerServer { // server is arg
  def run(world:World, timesteps:Int, clients:Map[Int, ClientConnection]) {
    for(i <- 1 to timesteps) {

    }
  }
}