package infrastructures.repository

import domains.user._
import domains._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.util.Try

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Success, Failure}
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class SlickUserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends UserRepository {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  private type UserType = (String, String)

  import dbConfig.*
  import profile.api.*

  val user = TableQuery[UserTable]

  def save(userAggregate: User)(implicit uof: UnitOfWork): Try[User] = Try {
    db.run {
      user += (userAggregate.id.toString, userAggregate.name.value)
    }
    userAggregate
  }

  def list(): Future[Seq[User]] = db.run {
    user.result.map { results =>
      results.map { case (id, name) =>
        User(UserId(id), UserName(name), EntityMetaDataImpl(1))
      }
    }
  }


  def get(id: UserId)(implicit uow: UnitOfWork): Try[Option[User]] = {
    val futureResult: Future[Option[User]] = db.run {
      user.filter(_.id === id.toString).result.headOption.map {
        case Some((id, name)) => Some(User(UserId(id), UserName(name), EntityMetaDataImpl(1)))
        case None => None
      }
    }

    try {
      val result = Await.result(futureResult, 10.seconds) // 10秒間待つ
      Success(result)
    } catch {
      case e: Exception => Failure(e)
    }
  }

  class UserTable(tag: Tag) extends Table[UserType](tag, "users") {

    def id = column[String]("id", O.PrimaryKey)

    def * = (id, name)

    def name = column[String]("name")
  }
}