package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Terminated, Props}
import play.libs.Akka
import scala.collection.mutable._
import javax.inject._


class HandlerActor extends Actor {
  import HandlerActor._
  var users = ListBuffer[User]()
  def receive = {
    case user: User =>
      users += user
      users.filterNot(_ == user).foreach( _.ref ! Message("New User connected")  )
    case message: String =>
      users.filterNot(_.ref == sender ).foreach( _.ref ! Message(message) )
    case Disconnect =>

      users.filter(_.ref ==  sender).foreach( users -= _)
      users.foreach( _.ref ! Message("User Disconnect"))

  }
}

object HandlerActor  {
  val prop = Props[HandlerActor]
  object Disconnect
  case class User(ref : ActorRef)
  case class Message(message: String)
}
