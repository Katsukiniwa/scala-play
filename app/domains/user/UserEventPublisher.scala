package domains.user

import domains.UnitOfWork

import scala.util.Try

trait UserEventPublisher {
  def publish[EVENT <: UserEvent](event: EVENT)(implicit uow: UnitOfWork): Try[EVENT]
}