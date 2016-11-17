package actors

import akka.actor._


class MyWebSocketActor(out: ActorRef) extends Actor  with ActorLogging{
  import HandlerActor._
  val handlerActor = context.actorSelection("akka://application/user/Handler-Actor")

  override def preStart ={
    super.preStart
    println(s"Connected     $out")
    handlerActor ! User(self)
  }
  def receive = {
    case message: Message =>
      out ! message.message
    case msg: String =>
      handlerActor ! msg
  }

  override def postStop = {
    handlerActor ! Disconnect
  }
}
object MyWebSocketActor {
  def props(out: ActorRef) = Props(classOf[MyWebSocketActor],out)
  // def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}


