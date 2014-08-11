package toren.jedb

/**
 * Created by Owner on 7/22/14.
 */
  import com.sleepycat.persist.model.Relationship._
  import com.sleepycat.persist.model.{Entity, PrimaryKey, SecondaryKey}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

@Entity
  class Individual {



    def createNew( genotype: String, generation: Integer, parent: Integer ) {
      this.genotype = genotype
      this.generation = generation
      this.parent = parent
    }


    @PrimaryKey(sequence = "individual_seq")
    var id: Integer = _
    @SecondaryKey( relate=MANY_TO_ONE)
    var genotype: java.lang.String = _
    @SecondaryKey( relate = MANY_TO_ONE )
    var generation: Integer = _
    @SecondaryKey(relate=MANY_TO_ONE)
    var parent: Integer = _
    @SecondaryKey(relate=ONE_TO_MANY)
    var children: java.util.Set[Integer] = new java.util.HashSet[Integer]
  }

  object Individual {
    def createNew( genotype:String, generation: Integer , parent: Integer ) : Individual  ={
      val individual = new Individual
      individual.genotype = genotype
      individual.generation = generation
      individual.parent = parent
      individual
    }

    def fromIndividualObj( obj : IndividualObj ) : Individual = {
      val ind = new Individual

      if ( obj.id > 0)
        ind.id = obj.id

      ind.genotype = obj.genotype
      ind.generation = obj.generation
      ind.parent = obj.parent
      ind
    }
  }
case class IndividualObj( id: Int, parent:Int, genotype: String, generation:Int )
