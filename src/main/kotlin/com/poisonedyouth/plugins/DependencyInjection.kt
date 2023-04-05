package com.poisonedyouth.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val main = module {
    // Place for component definition
}

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger()
        modules(main)
    }
}