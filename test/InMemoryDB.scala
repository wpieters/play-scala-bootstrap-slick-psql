import play.api.inject.guice.GuiceApplicationBuilder

/**
  * Created by duhblinn on 2016/06/26.
  */
trait InMemoryDB {
  val inMemoryDatabaseSettings = Map(
    "slick.dbs.default.db.driver" -> "org.h2.Driver",
    "slick.dbs.default.db.url" -> "jdbc:h2:mem:postgres;MODE=PostgreSQL;LOCK_MODE=0;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
    "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
    "slick.dbs.default.user" -> "sa",
    "slick.dbs.default.password" -> ""
  )

  val appWithMemoryDatabase = new GuiceApplicationBuilder().configure(inMemoryDatabaseSettings).build()
}
