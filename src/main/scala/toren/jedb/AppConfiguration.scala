package toren.jedb

import akka.actor.ActorSystem
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.scala.context.function.FunctionalConfiguration
import toren.jedb.target.SimulationActor

class AppConfiguration extends FunctionalConfiguration {
  /**
   * Load implicit context
   */
  implicit val ctx = beanFactory.asInstanceOf[ApplicationContext]

  /**
   * Actor system singleton for this application.
   */
  val actorSystem = bean() {
    val system = ActorSystem("AkkaScalaSpring")
    // initialize the application context in the Akka Spring Extension
    SpringExtentionImpl(system)
    system
  }


  import Config._

  val jedbService = bean("JedbService") {
     val service = new JedbService
     service.path = path
      Simulation.jedbService = service
      service
  }

  val simulationActor = bean("simulationActor", scope = BeanDefinition.SCOPE_PROTOTYPE) {
    val actor = new SimulationActor
    actor.jedbService = jedbService()
    actor
  }
}