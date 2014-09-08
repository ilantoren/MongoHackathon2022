package toren.jedb

import akka.actor._
import com.cra.figaro.language.Select
import org.springframework.scala.context.function._
import akka.pattern.ask
import scala.collection.mutable.{ArrayBuffer, Map}
import scala.util.{Failure, Success, Random}
import SimulationCollector._
import Config._

/*
   The starting point of the simulation.
 */
object Simulation extends App {
  // create a spring context
  implicit val ctx = FunctionalConfigApplicationContext(classOf[AppConfiguration])


  // get hold of the actor system
  val system = ctx.getBean(classOf[ActorSystem])
  val collector = system.actorOf(Props(new SimulationCollector), "collector")
  import java.io.FileWriter
  val fw : FileWriter = new FileWriter("test.csv")
  val tw: FileWriter = new FileWriter("breeders.txt")
  // Give time for Spring to fully start the Akka actors
  Thread.sleep(30000)
  // initial population
  val iPopSize: Int = 1000
  var jedbService: JedbService = _
  try {
    jedbService.start
    val population: ArrayBuffer[Individual] = ArrayBuffer[Individual]()

    println("Creating root individual")
    val individual: Individual = new Individual
    individual.id = 0
    individual.generation = 0
    individual.genotype = "AA"
    collector ! (individual :: List()).toArray
    println("saved root")


    import toren.jedb.RandTest._
    val rnd : Random = new Random
    val tpl = priorProbs(0.2)
    val list: Seq[String] = testSeq(iPopSize, tpl, rnd )
    var j: Int = 1

    // Generation 1 is special and represents the starting condition
    //   The prior probability is the relation between the dominant allele of gene A
    // vs the recessive allele.  In Hardy-Weinberg this is also commonly referred to p and q
    //   p+q = 1.  By the end of the simulation, due to the heterozygous advantage built in
    // the simulation p will approach q (0.5).
    for (i <- list) {
      val ind: Individual = new Individual
      ind.generation = 1
      ind.genotype = i
      ind.parent = 0
      ind.id = j
      j += 1
      population += ind
    }
    collector ! population

    // Start the simulation here
    val lastGen = 200
    for (gen <- 1 to lastGen) {
      val curGen: List[Individual] = Random.shuffle(population.toList)
      val size = curGen.length
      println(s"Generation $gen   Population size $size ")
      val (deaths, nextGen: List[Individual], breeders: List[Individual]) = deathsAndBreeders(curGen, rnd)
      report(gen, breeders)
      collector ! population.toArray
      printSummary( gen, getPercentage( population), fw)

      // create the next generation alive for interval n where n <-2 to end
      population.clear
      for (ind <- nextGen) {
        val indn: Individual = new Individual
        indn.generation = gen + 1
        indn.genotype = ind.genotype
        indn.parent = ind.id
        indn.id = j
        j += 1
        population += indn
      }

      // The proportion of breeders has been adjusted to be close to the number of deaths
      // so as to keep the population from exploding.
      val children: ArrayBuffer[Individual] = nextGeneration(breeders, rnd)
      println(s"deaths $deaths,  breeders ${breeders.size}  births ${children.size} ")
      for (ind <- children)
        population += ind
    }

    collector ! population.toArray
    collector ! TERMINATE


    var lc = 0
    var gate = true
    while ( gate && lc < 1) {
      Thread.sleep(2000)
      lc = askCollector
      println( lc )
      if ( lc == 1 ) gate = false
    }

/*
    // Final report
    for (i <- 1 to lastGen) {
      val lt: (Int, Int, Int, Int) = jedbService.findCountByGenerationAndGenotype(i)
      println(s"AA  ${lt._1} Aa ${lt._2}  aa ${lt._3}  total ${lt._4} ")
    }
*/
    fw.flush
    system.shutdown
    System.exit(0)
  }
  finally {
    if (jedbService != null)
      jedbService.shutdown

    fw.close
    println("shutdown")
  }

  def deathsAndBreeders(curGen: List[Individual], rnd :Random): ( Double, List[Individual], List[Individual]) = {
    var death: Double = 0
    val nextGen: ArrayBuffer[Individual] = ArrayBuffer[Individual]()
    val breeders: ArrayBuffer[Individual] = ArrayBuffer[Individual]()
    def generateRandomness = rnd.nextDouble()
    def fn( ind: Individual ) = {
      Select( 0.1 -> "death", 0.125 -> "breed",0.775 -> "continue" ).generateValue(generateRandomness) match {
        case "death" => death +=1
        case "breed" => {
          breeders+= ind
          nextGen += ind
        }
        case "continue" => nextGen += ind
      }
    }
    curGen.foreach( i => fn(i))

    (death, nextGen.toList, breeders.toList)
  }

  // interim report
  def report(gen: Int, breeders: List[Individual]) {
    val map: Map[String, Int] = Map[String, Int] ( ("AA AA", 0 ),
              ("aa aa", 0), ("Aa Aa", 0), ("aa Aa", 0), ("AA Aa", 0), ("AA aa", 0))
    var nl = false
    var s : String =""
    tw.write("----- " + gen + " ------\n")
    for (k <- breeders) {
      if (nl) {
        tw.write(k.genotype + "\n")
        s+= k.genotype
        s match  {
          case "AA AA" =>  incrementKeyValue( "AA AA", map)
          case "aa aa" =>  incrementKeyValue( "aa aa", map)
          case "Aa Aa" =>  incrementKeyValue( "Aa Aa", map)
          case "Aa aa" =>  incrementKeyValue( "aa Aa", map)
          case "aa Aa" =>  incrementKeyValue( "aa Aa", map)
          case "AA Aa" =>  incrementKeyValue( "AA Aa", map)
          case "Aa AA" =>  incrementKeyValue( "AA Aa", map)
          case "aa AA" =>  incrementKeyValue( "AA aa", map)
          case "AA aa" =>  incrementKeyValue( "AA aa", map)
          case _  =>  println( s"unmatched $s")
        }
        nl = false
      }
      else {
        tw.write(k.genotype + " ")
        s = k.genotype + " "
        nl = true
      }
    }
    map.keys.foreach(i => print(s"$i  ${map(i)}  "))
    println("")
  }

  def nextGeneration(breeders: List[Individual], rnd :Random): ArrayBuffer[Individual] = {
    val children: ArrayBuffer[Individual] = ArrayBuffer[Individual]()
    val iterator = breeders.iterator

    while (iterator.hasNext) {
      val parentA = iterator.next()
      if (iterator.hasNext) {
        val parentB = iterator.next
        children ++= getOffspring(parentA, parentB, rnd)
      }
    } // while
    children
  }

  def incrementKeyValue( key: String, map : Map[String,Int]) {
    var value = map(key)
    value+=1
    map.update(key, value)
  }

  def getOffspring(pA: Individual, pB: Individual, rnd: Random): List[Individual] = {
    def generateRandomness = rnd.nextDouble()
    val list: ArrayBuffer[Individual] = ArrayBuffer[Individual]()
    val offspringCnt = if (pA.genotype == "Aa" | pB.genotype == "Aa") {
      2
    } else {
      1
    }
    for (i <- 1 to offspringCnt) {
      val genotype: String = if (pA.genotype == "AA" & pB.genotype == "AA") {
        "AA"
      }
      else if (pA.genotype == "aa" & pB.genotype == "aa") {
        "aa"
      }
      else if (pA.genotype == "Aa" & pB.genotype == "Aa") {
        Select(0.50 -> "Aa", 0.25 -> "aa", 0.25 -> "AA").generateValue(generateRandomness)
      }
      else if (pA.genotype == "AA") {
        // one is AA the other aa
        if (pB.genotype == "aa")
          "Aa"
        else
        // one is AA the other Aa
          Select(0.5 -> "Aa", 0.5 -> "AA").generateValue(generateRandomness)
      }
      else if (pA.genotype == "aa") {
        if (pB.genotype == "AA")
          "Aa"
        else
          Select(0.5 -> "Aa", 0.5 -> "aa").generateValue(generateRandomness)

      }
      else if ( pA.genotype == "Aa") {
        if ( pB.genotype == "aa")
          Select( 0.5 -> "Aa", 0.5-> "aa").generateValue(generateRandomness)
        else
          Select(0.5-> "Aa", 0.5->"AA").generateValue(generateRandomness)
      }
      else {
          println(s"ERROR  ${pA.genotype}   ${pB.genotype}")
        " "
      }

      list += Individual.createNew(genotype, pA.generation + 1, pA.id)

    }
    list.toList
  }

  def getPercentage(buf: ArrayBuffer[Individual]) : (Double,Double,Double,Double) = {
    val AA :Double = buf.count(_.genotype == "AA")
    val Aa : Double = buf.count(_.genotype == "Aa")
    val aa :Double= buf.count(_.genotype == "aa")
    val tot :Double= buf.size
    (AA/tot, Aa/tot, aa/tot, tot)
  }
  def printSummary( gen: Int, tpl: (Double,Double,Double, Double), fw: FileWriter) {
    val a  = Array( gen, tpl._1, tpl._2, tpl._3, tpl._4)
    val sf =  s"$gen,  ${tpl._1},  ${tpl._2}, ${tpl._3}, ${tpl._4}".toString
    fw.write( sf  + "\n")
  }
  def askCollector: Int = {
    val result = (collector ? GET).mapTo[Int]
    var ret = -1
    result onComplete {
      case Success(result) => {
        result match {
          case 1 => {
            ret = result
            println( "message to finish")
          }
          case _ => ret = result
        }
      }
      case Failure(failure) => {
        println(s"Got an exception $failure")
        ret = 2
      }

    }
    ret
  }
}
