package dao

import models.Person
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PersonDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api.*

  private val Persons = TableQuery[PersonsTable]

  def all(): Future[Seq[Person]] = db.run(Persons.result)

  def insert(person: Person): Future[Unit] = db.run(Persons += person).map { _ => () }

  private class PersonsTable(tag: Tag) extends Table[Person](tag, "PERSON") {

    private def name = column[String]("NAME", O.PrimaryKey)
    private def age = column[Int]("AGE")

    def * = (name, age) <> (Person.tupled, Person.unapply)
  }
}