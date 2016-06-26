package services

import models.{Person, Persons}
import scala.concurrent.Future

object PersonService {

  def addUser(user: Person): Future[String] = {
    Persons.add(user)
  }

  def deleteUser(id: Long): Future[Int] = {
    Persons.delete(id)
  }

  def getUser(id: Long): Future[Option[Person]] = {
    Persons.get(id)
  }

  def listAllUsers: Future[Seq[Person]] = {
    Persons.listAll
  }
}
