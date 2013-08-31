package game

import game.behaviour._
import game.render._
import game.state._
import game.event._

abstract class ServerConnection {
  // send
  def playerUpdate(events:Seq[Event], stateTransitions:Seq[StateTransition])

  // receive
  def receivePlayer : (Seq[ToEntityEvent], Seq[StateTransition])
  def receiveNpcStateTransitions : Map[Int, Seq[StateTransition]]
}

object MultiplayerClient { // server is arg
  def run(world:World, timesteps:Int, server:ServerConnection) {
    for(i <- 1 to timesteps) {

    }
  }
}