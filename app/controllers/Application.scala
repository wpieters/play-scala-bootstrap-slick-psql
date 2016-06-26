package controllers

import controllers.security.AuthConfigImpl
import jp.t2v.lab.play2.auth.{AuthElement, LoginLogout}
import models.Role.{Administrator, NormalUser}
import models._
import play.api.mvc._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import scala.concurrent.Future
import services.{AccountsService, PersonService}

import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller with AuthElement with AuthConfigImpl {

  def index = Action {
    Ok(views.html.index("Play Scala Tutorials application is ready."))
  }

  def users = AsyncStack(AuthorityKey -> NormalUser) { implicit request =>
    PersonService.listAllUsers map { users =>
      Ok(views.html.persons(PersonForm.form, users))
    }
  }

  def addUser() = AsyncStack(AuthorityKey -> Administrator) { implicit request =>
    PersonForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => Future.successful(Ok(views.html.persons(errorForm, Seq.empty[Person]))),
      data => {
        val newPerson = Person(0, data.firstName, data.lastName, data.mobile, data.email)
        PersonService.addUser(newPerson).map(res =>
          Redirect(routes.Application.users())
        )
      })
  }

  def deleteUser(id: Long) = AsyncStack(AuthorityKey -> Administrator) { implicit request =>
    PersonService.deleteUser(id) map { res =>
      Redirect(routes.Application.users())
    }
  }

  def account = StackAction(AuthorityKey -> NormalUser)  { implicit request =>
    Ok(views.html.accounts(AccountForm.form))
  }

  def addAccount() = AsyncStack(AuthorityKey -> NormalUser) { implicit request =>
    AccountForm.form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.accounts(formWithErrors))),
      data => {
        val newAccount = Account(0, data.email, data.password, data.name, data.role)
        AccountsService.addUser(newAccount).map(res =>
          Redirect(routes.Application.account())
        )
      })
  }

}