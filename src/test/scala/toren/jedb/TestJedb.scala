package toren.jedb

/**
 * Created by Owner on 7/30/14.
 */



import org.junit.runner._
import org.specs2.mutable.Specification
import org.specs2.runner._
import org.specs2.io.Path


@RunWith(classOf[JUnitRunner])
class TestJedb extends Specification {

/*
  def setup {
    val path: Path = Path("tmp_test")
    path.deleteRecursively()
    path.createDirectory()
  }
*/
  "Read-write test" should {

    "Test JedbService" in {

      import Config._
      // get hold of the actor system
     val mpath: Path = Config.path
      val result = mpath.fileName
      result must beEqualTo("test2")


    }
  }
}



