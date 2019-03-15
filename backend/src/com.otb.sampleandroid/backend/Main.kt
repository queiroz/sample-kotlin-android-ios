package com.otb.sampleandroid.backend

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.util.error
import io.ktor.websocket.WebSockets

internal fun Application.main() {

    install(DefaultHeaders)
    install(WebSockets)
    install(StatusPages) {
        exception<ServiceUnavailable> {
            call.respond(HttpStatusCode.ServiceUnavailable)
        }
        exception<BadRequest> {
            call.respond(HttpStatusCode.BadRequest)
        }
        exception<Unauthorized> {
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<NotFound> {
            call.respond(HttpStatusCode.NotFound)
        }
        exception<SecretInvalidError> {
            call.respond(HttpStatusCode.Forbidden)
        }
        exception<Throwable> { cause ->
            environment.log.error(cause)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
    install(Routing) {
        api()
    }

}