import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun defaultEngineFactory(): HttpClientEngineFactory<*> = OkHttp