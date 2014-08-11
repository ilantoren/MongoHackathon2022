package toren.jedb

/**
 * Created by Owner on 7/24/14.
 */


import akka.actor._
import org.springframework.scala.context.function._
import akka.routing.{Broadcast, RoundRobinRouter}
import akka.pattern.ask
import scala.util._
import scala.annotation.tailrec
import scala.concurrent.duration._

object SimulationCollector {
  object GET
  object TERMINATE
}


class SimulationCollector extends Actor with ActorLogging {

  import SimulationCollector._

  var isDead = 0;
  // create a spring context
  implicit val ctx = FunctionalConfigApplicationContext(classOf[AppConfiguration])

  // get hold of the actor system
  val system = ctx.getBean(classOf[ActorSystem])

  // use the Spring Extension to create props for a named actor bean
  val prop = SpringExtentionImpl(system).props("simulationActor")
  val router = system.actorOf(prop.withRouter(RoundRobinRouter(nrOfInstances = 5)))


  //  Death watch call back on routers
  context.watch(router)


  // call backs
  def receive = {

    case msg: String => log.info(msg)

    case Terminated(corpse) => {
      if (corpse == router) {
        log.info("all first stage routees are dead")
        isDead = 1
        system.shutdown()
      }
    }

    case list: Array[Individual] => {
      log.info( "Processing a List[Individual] " )
      for (ind <- list) {
          router ! ind
      }
    }


    case GET => {
      // will see this message until system shuts down
      log.info(s"Sending isDead status $isDead")
      sender ! isDead
    }
    case TERMINATE => router ! Broadcast(PoisonPill)
    case _ => {
      // should never get here
      log.error("no match:")
    }
  }
}