import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.duration._

import scala.concurrent.Await

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  def appWithMemoryDatabase = new GuiceApplicationBuilder().configure(inMemoryDatabase("postgres")).build()

  "Application" should {

    "send 404 on a bad request" in new WithApplication(appWithMemoryDatabase) {
      val error = Await.result(route(FakeRequest(GET, "/boum")).get, 10 seconds)
      error.header.status shouldEqual 404
    }

    "render the index page" in new WithApplication(appWithMemoryDatabase) {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Play Scala Tutorials")
    }
  }
}
