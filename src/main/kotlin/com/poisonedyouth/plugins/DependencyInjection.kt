package com.poisonedyouth.plugins

import com.poisonedyouth.AddressRepository
import com.poisonedyouth.AddressRepositoryImpl
import com.poisonedyouth.CustomerApi
import com.poisonedyouth.CustomerApplicationService
import com.poisonedyouth.CustomerController
import com.poisonedyouth.CustomerRepository
import com.poisonedyouth.CustomerRepositoryImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val main = module {
    // Place for component definition
    single<AddressRepository> { AddressRepositoryImpl() }
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<CustomerApplicationService> { CustomerApplicationService(get(), get()) }
    single<CustomerApi> { CustomerController(get()) }
}

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger()
        modules(main)
    }
}