package com.geirolz.microservice.infra.route.endpoint.user

import com.geirolz.microservice.common.route.endpoint.VersionedEndpoint
import com.geirolz.microservice.infra.route.endpoint.user.contract.UserContract
import com.geirolz.microservice.infra.route.endpoint.user.UserEndpointApi.Errors.ErrorInfo
import com.geirolz.microservice.model.datatype.UserId

private[route] object UserEndpointApi {

  import com.geirolz.microservice.infra.route.endpoint.util.EndpointCommonCodecs._
  import io.circe.generic.auto._
  import sttp.tapir._
  import sttp.tapir.generic.auto._
  import sttp.tapir.json.circe._

  object Errors {
    sealed trait ErrorInfo
    case class UserNotFound(userId: UserId) extends ErrorInfo
  }

  private val user: Endpoint[Unit, ErrorInfo, Unit, Any] =
    VersionedEndpoint.v1.in("user").errorOut(jsonBody[ErrorInfo])

  val getById: Endpoint[UserId, ErrorInfo, UserContract, Any] =
    user.get
      .in(query[UserId]("id"))
      .out(jsonBody[UserContract])
}
