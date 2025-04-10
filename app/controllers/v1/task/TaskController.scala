package controllers.v1.task

import domains.task.{Task, TaskName}
import domains.user.*
import infrastructures.repository.SlickTaskRepository
import play.api.libs.json.{Json, Writes}
import play.api.mvc.*
import usecases.task.TaskUseCase

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

class TaskController @Inject()(repo: SlickTaskRepository,
                               cc: MessagesControllerComponents,
                               taskService: TaskUseCase,
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

  /**
   * The add task action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on SlickTaskRepository.
   */
  def create(): Action[AnyContent] = Action.async { implicit request =>
    val json = request.body.asJson.get
    type TaskRequest = (String, String)
    val taskRequest = json.as[TaskRequest]
    val createdTask = taskService.createNewTask(TaskName(taskRequest._1), UserId(taskRequest._2))
    Future.successful(Ok(Json.obj("message" -> "success")))
  }
}