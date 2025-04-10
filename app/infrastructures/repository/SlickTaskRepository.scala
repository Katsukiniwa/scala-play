package infrastructures.repository

import domains.EntityMetaDataImpl
import domains.task.*
import domains.user.*
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SlickTaskRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends TaskRepository {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  private type TaskType = (String, String, String, String)

  import dbConfig.*
  import profile.api.*

  val task = TableQuery[TaskTable]

  def save(taskAggregate: Task): Future[Task] = {
    db.run {
      (task += (
        taskAggregate.id.toString,
        taskAggregate.name.toString,
        taskAggregate.state.toString,
        taskAggregate.authorId.toString
      )).map(_ => taskAggregate)
    }
  }

  def list: Future[Seq[Task]] = {
    db.run(task.result).map { results =>
      results.map { case (id, name, state, authorId) =>
        Task(
          id = TaskId(id),
          name = TaskName(name),
          state = TaskState.valueOf(state),
          authorId = UserId(authorId),
          comments = Comments.nothing,
          metaData = EntityMetaDataImpl(1),
        )
      }
    }
  }

  class TaskTable(tag: Tag) extends Table[TaskType](tag, "tasks") {
    def author = foreignKey("author_fk", authorId, task)(_.id)

    def id = column[String]("task_id", O.PrimaryKey)

    def authorId = column[String]("author_id")

    def * = (id, name, state, authorId)

    def name = column[String]("name")

    def state = column[String]("state")
  }
}