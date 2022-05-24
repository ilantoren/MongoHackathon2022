package hackathon

import com.google.cloud.functions.BackgroundFunction
import com.google.cloud.functions.Context
import com.google.type.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*


class PubSub : BackgroundFunction<PubSub.PubSubMessage> {
    private val logger: Logger = LoggerFactory.getLogger("hackathon")
    override fun accept(message: PubSubMessage, context: Context?) {
        val data =
            if (message.data != null) String(Base64.getDecoder().decode(message.data)) else "Trigger info missing"
        logger.info(data)
        Process()
    }

    class PubSubMessage {
        var data: String? = null
        var attributes: Map<String, String>? = null
        var messageId: String? = null
        var publishTime: String? = DateTime.getDefaultInstance().toString()
    }
}