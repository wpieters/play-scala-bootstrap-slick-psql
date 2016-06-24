package controllers

import models.{User, UserForm}
import play.api.mvc._
import scala.concurrent.Future
import services.UserService
import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {

//  def index = Action {
//    Ok(views.html.index("Play Scala Tutorials application is ready."))
//  }

  def index = Action.async { implicit request =>
    UserService.listAllUsers map { users =>
      Ok(views.html.users(UserForm.form, users))
    }
  }

  def addUser() = Action.async { implicit request =>
    UserForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => Future.successful(Ok(views.html.users(errorForm, Seq.empty[User]))),
      data => {
        val newUser = User(0, data.firstName, data.lastName, data.mobile, data.email)
        UserService.addUser(newUser).map(res =>
          Redirect(routes.Application.index())
        )
      })
  }

  def deleteUser(id: Long) = Action.async { implicit request =>
    UserService.deleteUser(id) map { res =>
      Redirect(routes.Application.index())
    }
  }

}