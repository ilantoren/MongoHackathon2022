package toren.jedb

import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.mutable
import scala.util.Random

/**
 * Created by Owner on 8/4/14.
 */
object  RandTest extends App{
  val rnd : Random  = new Random
  var x1 : Float  = _
  var min : Float = _
  var max : Float =_
  for( i <- 1 to 1000) {
    x1 = rnd.nextFloat()
     if (x1 < min) min = x1
     if  (x1 > max ) max = x1
    println(rnd.nextFloat())
     }
  println( s"min  $min  max $max")
  var AA : Double= 0
  var Aa : Double= 0
  var aa :Double = 0
   priorProbs( 0.4  ) match {
     case tpl : (Double,Double,Double) => {
       println(s"AA ${tpl._1}  Aa ${tpl._2}  aa ${tpl._3} ")
       AA = tpl._1
       Aa = AA + tpl._2
       aa = AA + tpl._3
     }
     case _  => println( "error")
   }

  val list  = testSeq(1000, priorProbs(0.4))
  for( i <- list) println( i)
   println("finished")

  def priorProbs( p : Double ) :( Double , Double, Double ) = {
    import Math._
    val q = 1 - p;
    (pow(p, 2), (2 * p * q), pow(q, 2))
  }
  def testSeq( count: Integer, tpl: (Double, Double, Double)) :  Seq[String]  ={
    var list : ArrayBuffer[String]  =  new ArrayBuffer[String]
    val rnd: Random  = new Random
    for( i <- 1 to count) {
      x1 = rnd.nextFloat()
      val x2 = if ( x1 < tpl._1 ) {
        "AA"
      }else if  (x1 < tpl._2) {
        "Aa"
      }else {
        "aa"
      }
     list.append(x2)
    }

    list.toSeq
  }
}
