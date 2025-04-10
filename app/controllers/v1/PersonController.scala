package controllers.v1

import dao.PersonDAO
import infrastructures.repository.SlickPersonRepository
import models.*
import play.api.libs.json.Json
import play.api.mvc.*

import javax.inject.*
import scala.concurrent.ExecutionContext

class PersonController @Inject()(repo: SlickPersonRepository,
                                 cc: MessagesControllerComponents,
                                 personDAO: PersonDAO,
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def index: Action[AnyContent] = Action.async { implicit request =>
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }

  /**
   * The add person action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on SlickPersonRepository.
   */
  def create: Action[AnyContent] = Action.async { implicit request =>
    val json = request.body.asJson.get
    val person = json.as[Person]
    repo.create(person.name, person.age).map { _ =>
      Ok(Json.obj("message" -> "success"))
    }
  }
}