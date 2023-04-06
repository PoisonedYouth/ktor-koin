package com.poisonedyouth

import com.poisonedyouth.plugins.configureRouting
import com.poisonedyouth.plugins.configureSerialization
import com.poisonedyouth.plugins.installKoin
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.io.File

@ExtendWith(CleanDatabaseExtension::class)
class ScopeTest : KoinTest {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            com.poisonedyouth.plugins.main
        )
    }

   @Test
   fun `test custom scope`(){
       val scope = getKoin().createScope("CustomScope", named("Custom"))

       fun useScope(){
           val accessCounter = scope.get<AccessCounter>()
           accessCounter.increaseCounter()
           assertThat(accessCounter.getCurrentCount()).isEqualTo(1)
       }

       fun useScopeAgain(){
           val scopeInner = getKoin().getScope("CustomScope")
           val accessCounter = scopeInner.get<AccessCounter>()
           accessCounter.increaseCounter()
           assertThat(accessCounter.getCurrentCount()).isEqualTo(2)
       }



       scope.close()

       val accessCounterNew = scope.get<AccessCounter>()

   }


}