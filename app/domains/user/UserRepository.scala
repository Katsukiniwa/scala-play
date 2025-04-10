package domains.user

import domains.UnitOfWork

import scala.concurrent.Future
import scala.util.Try

trait UserRepository {
  def get(id: UserId)(implicit uow: UnitOfWork): Try[Option[User]] 

  def save(user: User)(implicit uof: UnitOfWork): Try[User]

//  def delete(task: User)(implicit uof: UnitOfWork): Try[User]
}
