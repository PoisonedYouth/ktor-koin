package com.poisonedyouth.plugins

import com.poisonedyouth.AccessCounter
import com.poisonedyouth.AddressRepository
import com.poisonedyouth.AddressRepositoryImpl
import com.poisonedyouth.CustomerApi
import com.poisonedyouth.CustomerApplicationService
import com.poisonedyouth.CustomerController
import com.poisonedyouth.CustomerRepository
import com.poisonedyouth.CustomerRepositoryImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.component.getScopeName
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


val main = module {
    // Place for component definition
    singleOf(::AddressRepositoryImpl) bind AddressRepository::class
    singleOf(::CustomerRepositoryImpl) bind CustomerRepository::class
    singleOf(::CustomerApplicationService)
    singleOf(::CustomerController) bind CustomerApi::class

    factoryOf(::AccessCounter)

    scope(named("Custom")){
        scopedOf(::AccessCounter)
    }
}

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger()
        modules(main)
    }
}