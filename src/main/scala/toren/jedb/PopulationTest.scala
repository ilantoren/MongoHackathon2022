package toren.jedb

import com.cra.figaro.language.Select

import scala.util.Random
import RandTest._
/**
 * Created by Owner on 8/6/14.
 */
object PopulationTest extends  App {
   val rnd: Random = Random
   def generateRandomness  = rnd.nextDouble()
   val tpl = priorProbs(0.4)
   val pop :Seq[String] = testSeq( 10000, tpl, rnd)
   var AA:Double = 0
   var Aa:Double = 0
   var aa:Double = 0
   for( p <- pop) p match {
    case "AA" => AA+=1
    case "Aa" => Aa+=1
    case "aa" => aa+=1
   }
   var tot = AA +Aa + aa
   println(s"AA ${AA/tot}   Aa ${Aa/tot}  aa ${aa/tot}")

  println( "heterozygote probs")
  AA = 0
  Aa = 0
  aa = 0
  for( i <- 1 to 10000 ) {
    Select( 0.25 -> "AA", 0.25 -> "aa", 0.50 -> "Aa").generateValue(generateRandomness) match {
      case "AA" => AA+=1
      case "Aa" => Aa+=1
      case "aa" => aa+=1
    }
  }
  tot = AA +Aa + aa
  println(s"AA ${AA/tot}   Aa ${Aa/tot}  aa ${aa/tot}")

}
