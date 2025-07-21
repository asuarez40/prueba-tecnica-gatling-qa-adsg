package simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class RegistroUsuario extends Simulation {
  val applicaion: String = "application/json"
  val httpConfig = http.baseUrl("https://reqres.in/api")
    .acceptHeader(applicaion)
    .contentTypeHeader(applicaion)

  val scenario1 = scenario("Test registros de usuarios")
    .repeat(1) {
      exec(
        http("Api Create user")
          .post("/users")
          .header("x-api-key", "reqres-free-v1")
          .body(RawFileBody(filePath = "data/bodyApiRegistroUsuario.json")).asJson
          .check(
            status.is(201),
            responseTimeInMillis.lte(500)
          )
          .check(bodyString.saveAs("response"))
      )
        .exec {
          session => println(session("response").as[String]); session
        }
    }

  setUp(
    scenario1.inject(
      rampUsers(100).during(20.seconds)
    ).protocols(httpConfig))

}
