package com.poisonedyouth.plugins

import com.poisonedyouth.CustomerController
import com.poisonedyouth.CustomerDto
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    val controller = CustomerController()

    routing {
        post("/api/v1/customer") {
            val customer = call.receive<CustomerDto>()
            val result = controller.addNewCustomer(customer)
            call.respond(result.first, result.second)
        }
    }
}
