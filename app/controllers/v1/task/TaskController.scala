package controllers.v1.task

import domains.task.Task
import infrastructures.repository.SlickTaskRepository
import play.api.libs.json.{Json, Writes}
import play.api.mvc.*

import javax.inject.*
import scala.concurrent.ExecutionContext

class TaskController @Inject()(repo: SlickTaskRepository,
                               cc: MessagesControllerComponents,
                              )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def index: Action[AnyContent] = Action.async { implicit request =>
    implicit val taskWrites: Writes[Task] = Writes { task =>
      Json.obj(
        "id" -> task.id.value, // idの値だけを出力
        "name" -> task.name.value // nameの値だけを出力
      )
    }

    repo.list.map { tasks =>
      Ok(Json.toJson(tasks))
    }
  }
}