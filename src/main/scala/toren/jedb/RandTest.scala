package toren.jedb

import com.cra.figaro.language.Select

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by Owner on 8/4/14.
 */
object RandTest extends App {
  val rnd: Random = new Random
  var x1: Float = _
  var min: Float = _
  var max: Float = _
  for (i <- 1 to 1000) {
    x1 = rnd.nextFloat()
    if (x1 < min) min = x1
    if (x1 > max) max = x1
    println(rnd.nextFloat())
  }
  println(s"min  $min  max $max")
  var AA: Double = 0
  var Aa: Double = 0
  var aa: Double = 0
  priorProbs(0.5) match {
    case tpl: (Double, Double, Double) => {
      println(s"AA ${tpl._1}  Aa ${tpl._2}  aa ${tpl._3} ")
      AA = tpl._1
      Aa = AA + tpl._2
      aa = AA + tpl._3
    }
    case _ => println("error")
  }

  val prob: Double = 0.5
  for (j <- 1 to 30) {
    AA = 0
    Aa = 0
    aa = 0
    testSeq(1000, priorProbs(prob), rnd).foreach(i => summarize(prob, i))
    printf("With prob of %f  AA %d  Aa %d  aa %d\n", prob.toFloat, AA.toInt, Aa.toInt, aa.toInt)
  }

  def summarize(prob: Double, genotype: String) {
    genotype match {
      case "AA" => AA += 1
      case "Aa" => Aa += 1
      case "aa" => aa += 1
    }
  }

  println("finished")

  def priorProbs(p: Double): (Double, Double, Double) = {
    import Math._
    val q = 1 - p;
    (pow(p, 2), (2 * p * q), pow(q, 2))
  }

  def testSeq(count: Integer, tpl: (Double, Double, Double), rnd: Random): Seq[String] = {
    def generateRandomness = rnd.nextDouble()
    var list: ArrayBuffer[String] = new ArrayBuffer[String]
    for (i <- 1 to count) {
      val x2 = Select(tpl._1 -> "AA", tpl._2 -> "Aa", tpl._3 -> "aa").generateValue(generateRandomness)
      list.append(x2)
    }

    list.toSeq
  }
}
