import play.api.inject.guice.GuiceApplicationBuilder

/**
  * Created by duhblinn on 2016/06/26.
  */
trait InMemoryDB {
  def inMemoryDatabaseSettings = Map(
    "slick.dbs.default.db.driver" -> "org.h2.Driver",
    "slick.dbs.default.db.url" -> "jdbc:h2:mem:postgres;MODE=PostgreSQL;LOCK_MODE=0",
    "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
    "slick.dbs.default.user" -> "sa",
    "slick.dbs.default.password" -> ""
  )

  def appWithMemoryDatabase = new GuiceApplicationBuilder().configure(inMemoryDatabaseSettings).build()
}
