package domains.user

import domains.*

trait UserEvent extends DomainEvent {
  val userId: UserId
}

case class UserCreated(
                        userId: UserId,
                        userName: UserName
                      ) extends UserEvent

case class UserModified(
                         userId: UserId,
                         userName: UserName
                       ) extends UserEvent