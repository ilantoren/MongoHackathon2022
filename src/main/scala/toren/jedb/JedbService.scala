package toren.jedb

/**
 * Created by Owner on 7/22/14.
 */

import java.io.File

import com.sleepycat.persist.{EntityCursor, EntityStore}
import org.specs2.io.Path
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConversions
import scala.collection.mutable.ArrayBuffer


class JedbService() {

  var da : DataAccessor = _
  var  env : JedbEnv  = _
  @Autowired
  var path : Path = _

  def start {
      env = new JedbEnv(new  File(path.fileName), false)
      da = new DataAccessor( env.store )
  }


  def shutdown {
     env.store.sync()
     env.store.close()
     env.close()
  }



  def createChild( parent : Individual, genotype : String)  {
    val indObj = IndividualObj( 0,parent.id, genotype, parent.generation+1 )
    val individual = Individual.fromIndividualObj(indObj)
    da.individualById.put(individual)
  }

 def createNew( genotype: String, generation: Integer, parent:Integer ) {
   val indivdual = Individual.createNew(genotype,generation,parent)
   da.individualById.put(indivdual)
 }

  def save(obj: IndividualObj ) {

      val individual = Individual.fromIndividualObj( obj )
      da.individualById.put( individual )
  }

  def save( individual: Individual) {
    da.individualById.put(individual)
  }

  def findAll() : List[Individual] = {
    val cursor : EntityCursor[Individual] = da.individualById.entities()
    val list : List[Individual] = JavaConversions.asScalaIterator(cursor.iterator()).toList
    cursor.close()
     list
  }

  def findById( inList: Seq[Integer] ) : List[Individual]  = {
    val buff: ArrayBuffer[Individual]  = new ArrayBuffer[Individual]
    for( id <- inList) {
      val individual: Individual = da.individualById.get( id )
      buff.append(individual)
    }
    buff.toList
  }


  def findByGeneration( generation: Int ): List[Individual] =   {
       val cursor : EntityCursor[Individual] = da.individualByGeneration.subIndex(generation).entities
       val list : List[Individual] = JavaConversions.asScalaIterator(cursor.iterator()).toList
       cursor.close()
        list
  }

  def findCountByGenerationAndGenotype( generation: Int ) : (Int, Int, Int, Int) = {
    val cursor : EntityCursor[Individual] = da.individualByGeneration.subIndex(generation).entities
    val list : List[Individual] = JavaConversions.asScalaIterator(cursor.iterator()).toList
    cursor.close
    val cntAA = list.filter( _.genotype == "AA"  ).size
    val cntAa = list.filter( _.genotype == "Aa").size
    val cntaa = list.filter( _.genotype == "aa").size
    ( cntAA, cntAa, cntaa, list.size)

  }


  def findByGenotype(genotype: String) : List[Individual] = {
    val cursor : EntityCursor[Individual] = da.individualByGenotype.subIndex(genotype).entities
    val list : List[Individual] = JavaConversions.asScalaIterator(cursor.iterator()).toList
    cursor.close()
     list
  }

  def findByParent( parent: Individual ) : List[Individual] ={
    val cursor : EntityCursor[Individual] = da.individualByParent.subIndex(parent.id).entities
    val list : List[Individual] = JavaConversions.asScalaIterator(cursor.iterator()).toList
    cursor.close()
     list
  }
}

class DataAccessor(val store: EntityStore) {
  val individualById = store.getPrimaryIndex(classOf[Integer], classOf[Individual])
  val individualByParent = store.getSecondaryIndex(individualById, classOf[Integer], "parent")
 // val childrenByParent = store.getSecondaryIndex(individualById, classOf[Integer], "children")
  val individualByGeneration = store.getSecondaryIndex( individualById, classOf[Integer], "generation")
  val individualByGenotype = store.getSecondaryIndex(individualById, classOf[java.lang.String], "genotype")
}

