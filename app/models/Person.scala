package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

case class Person(id: Long, firstName: String, lastName: String, mobile: Long, email: String)

case class PersonFormData(firstName: String, lastName: String, mobile: Long, email: String)

object PersonForm {

  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "mobile" -> longNumber,
      "email" -> email
    )(PersonFormData.apply)(PersonFormData.unapply)
  )
}

class PersonTableDef(tag: Tag) extends Table[Person](tag, "person") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def mobile = column[Long]("mobile")
  def email = column[String]("email")

  override def * =
    (id, firstName, lastName, mobile, email) <>(Person.tupled, Person.unapply)
}

object Persons {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val persons = TableQuery[PersonTableDef]

  def add(person: Person): Future[String] = {
    dbConfig.db.run(persons += person).map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(persons.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Person]] = {
    dbConfig.db.run(persons.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Person]] = {
    dbConfig.db.run(persons.result)
  }

}
