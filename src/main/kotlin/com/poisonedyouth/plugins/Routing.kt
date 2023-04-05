package com.poisonedyouth.plugins

import com.poisonedyouth.CustomerApi
import com.poisonedyouth.CustomerDto
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val customerApi by inject<CustomerApi>()

    routing {
        post("/api/v1/customer") {
            val customer = call.receive<CustomerDto>()
            val result = customerApi.addNewCustomer(customer)
            call.respond(result.first, result.second)
        }
    }
}
