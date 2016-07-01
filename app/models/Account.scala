package models

import org.mindrot.jbcrypt.BCrypt
import play.api.{Logger, Play}
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

case class Account(id: Long, email: String, password: String, name: String, role: String) {
  def withPassword(password: String) = copy(password = password)
}
case class AccountFormData(email: String, password: String, name: Option[String], role: String, test: Boolean)

object AccountForm {
  val form = Form(
    mapping(
      "Email" -> nonEmptyText,
      "Password" -> nonEmptyText,
      "Name" -> optional(text),
      "Role" -> nonEmptyText,
      "Test" -> boolean
    )(AccountFormData.apply)(AccountFormData.unapply)
  )
}

class AccountTableDef(tag: Tag) extends Table[Account](tag, "account") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def email = column[String]("email")
  def password = column[String]("password")
  def name = column[String]("name")
  def role = column[String]("role")

  override def * =
    (id, email, password, name, role) <>(Account.tupled, Account.unapply)
}

object Accounts {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val accounts = TableQuery[AccountTableDef]

  def authenticate(email: String, password: String): Option[Account] = {
    val r = Await.result(findByEmail(email), 10 seconds)
    r.filter(account => BCrypt.checkpw(password, account.password))
  }

  def findByEmail(email: String): Future[Option[Account]] = {
    dbConfig.db.run(accounts.filter(_.email === email).result.headOption)
  }

  def findById(id: Long): Future[Option[Account]] = {
    dbConfig.db.run(accounts.filter(_.id === id).result.headOption)
  }

  def create(account: Account): Future[String] = {
    val pass = BCrypt.hashpw(account.password, BCrypt.gensalt())
    dbConfig.db.run(accounts += account.withPassword(pass)).map(res => "Account successfully added").recover {
      case ex: Exception => Logger.error(ex.getCause.getMessage, ex.getCause);ex.getCause.getMessage
    }
  }

  def listAll: Future[Seq[Account]] = {
    dbConfig.db.run(accounts.result)
  }

}