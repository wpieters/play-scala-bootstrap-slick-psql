package controllers.security

import jp.t2v.lab.play2.auth.AuthConfig
import models.Role.{Administrator, NormalUser}
import models.{Account, Role}
import play.api.mvc.RequestHeader
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.{ClassTag, classTag}

trait AuthConfigImpl extends AuthConfig {

  type Id = Account
  type User = Account
  type Authority = Role

  val idTag: ClassTag[Id] = classTag[Id]
  val sessionTimeoutInSeconds = 3600

  def resolveUser(id: Id)(implicit ctx: ExecutionContext) = Future.successful(Some(id))
  def authorize(user: User, authority: Authority)(implicit ctx: ExecutionContext) = Future.successful((Role.valueOf(user.role), authority) match {
    case (Administrator, _) => true
    case (NormalUser, NormalUser) => true
    case _ => false
  })

  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = throw new AssertionError("don't use application Login")
  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = throw new AssertionError("don't use application Logout")
  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful {
    Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="SECRET AREA"""")
  }
  def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit ctx: ExecutionContext) = Future.successful(Forbidden("no permission"))

  override lazy val idContainer = new BasicAuthIdContainer

  override lazy val tokenAccessor = new BasicAuthTokenAccessor

}