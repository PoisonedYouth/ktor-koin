package com.poisonedyouth.plugins

import com.poisonedyouth.CustomerApplicationService
import com.poisonedyouth.CustomerController
import com.poisonedyouth.CustomerDto
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.receive

fun Application.configureRouting() {
    val service = CustomerController()

    routing {
        post("/api/v1/customer") {
            val customer = call.receive<CustomerDto>()
            val result = service.addNewCustomer(customer)
            call.respond(result.first, result.second)
        }
    }
}
