package modules
import play.api.libs.concurrent.{AkkaGuiceSupport}

import com.google.inject.AbstractModule
import actors._
class HandlerModule extends AbstractModule with AkkaGuiceSupport {
  def configure = {
    bindActor[HandlerActor]("Handler-Actor")
  }
}
