package toren.jedb

import java.io.File

import com.sleepycat.je.{Environment, EnvironmentConfig}
import com.sleepycat.persist.{EntityStore, StoreConfig}

/**
 * Created by Owner on 7/22/14.
 */
class JedbEnv (val envHome: File, val readOnly: Boolean){

    protected val myEnvConfig = getEnvConfig
    protected val storeConfig = getStoreConfig

    protected def getEnvConfig = {
      val ec = new EnvironmentConfig()
      ec.setReadOnly(readOnly)
      ec.setAllowCreate(!readOnly)
      ec
    }

    protected def getStoreConfig = {
      val sc = new StoreConfig()
      sc.setReadOnly(readOnly)
      sc.setAllowCreate(!readOnly)
      sc
    }

    val myEnv: Environment = new Environment(envHome, myEnvConfig)
    val store: EntityStore = new EntityStore(myEnv, "EntityStore", storeConfig)

    def close() {
      store.close()
      myEnv.close()
    }

}
