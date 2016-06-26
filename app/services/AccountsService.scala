package services

import models.{Account, Accounts}

import scala.concurrent.Future

/**
  * Created by duhblinn on 2016/06/26.
  */
object AccountsService {

  def addUser(account: Account): Future[String] = {
    Accounts.create(account)
  }

  def listAllAccounts: Future[Seq[Account]] = {
    Accounts.listAll
  }
}
