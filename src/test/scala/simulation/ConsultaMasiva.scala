package simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class ConsultaMasiva extends Simulation {
  val applicaion: String = "application/json"
  val httpConfig = http.baseUrl("https://reqres.in/api")
    .acceptHeader(applicaion)
    .contentTypeHeader(applicaion)

  val scenario1 = scenario("Test consulta masiva de usuarios")
    .repeat(1) {
      exec(
        http("Api get all users")
          .get("/users?page=2")
          .header("x-api-key", "reqres-free-v1")
          .check(
            status.is(200),
            responseTimeInMillis.lte(500),
            jsonPath("$.data").exists,
            jsonPath("$.data").find.saveAs("jsonData"),
            jsonPath("$.data[0].email").exists
          )
          .check(bodyString.saveAs("response"))
      )
        .exec {
          session => {
            println(session("response").as[String])
            val data = session("jsonData").asOption[String]
            println(s" JSON recibido: ${data.getOrElse("NULO")}")
            println(session("jsonData").asOption[String].getOrElse("NULO"))
            session
          }
        }
    }

  setUp(
    scenario1.inject(
      constantConcurrentUsers(200).during(30.seconds)
    ).protocols(httpConfig))

}
