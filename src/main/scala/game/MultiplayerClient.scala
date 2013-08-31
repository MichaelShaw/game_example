package game

import game.behaviour._
import game.render._
import game.state._
import game.event._

abstract class ServerConnection {
  // send
  def playerUpdate(events:Seq[Event], stateTransitions:Seq[StateTransition[Living]])

  // receive
  def receivePlayer : (Seq[ToEntityEvent], Seq[StateTransition[Living]])
  def receiveNpcStateTransitions : Map[Int, Seq[StateTransition[Living]]]
}

object MultiplayerClient { // server is arg
  def run(world:World, timesteps:Int, server:ServerConnection) {
    for(i <- 1 to timesteps) {

    }
  }
}