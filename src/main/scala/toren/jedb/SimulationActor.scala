package toren.jedb

/**
 * Created by Owner on 8/7/14.
 */

import akka.actor.{Actor, ActorLogging}


class SimulationActor extends Actor with ActorLogging {

  var jedbService: JedbService = _

  def receive = {
    case x : Individual => {
        jedbService.save(x)
    }
    case _  => log.info("bad message")
  }


}