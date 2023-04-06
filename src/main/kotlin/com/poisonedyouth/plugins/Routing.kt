package com.poisonedyouth.plugins

import com.poisonedyouth.AccessCounter
import com.poisonedyouth.CustomerApi
import com.poisonedyouth.CustomerDto
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val customerApi by inject<CustomerApi>()
    val accessCounter by inject<AccessCounter>()

    val logger = LoggerFactory.getLogger(this::class.java)

    routing {
        post("/api/v1/customer") {
            logger.info("Current in Routing counter: ${accessCounter.getCurrentCount()}")
            accessCounter.increaseCounter()
            val customer = call.receive<CustomerDto>()
            val result = customerApi.addNewCustomer(customer)
            call.respond(result.first, result.second)
        }
    }
}
