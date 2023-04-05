package com.poisonedyouth

import com.poisonedyouth.plugins.configureRouting
import com.poisonedyouth.plugins.configureSerialization
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
import java.io.File

@ExtendWith(CleanDatabaseExtension::class)
class CustomerEntityAcceptanceTest {

    @Test
    fun `scenario save customer is successful`() = testApplication {
        // given
        environment {
            config = ApplicationConfig("application-test.conf")
        }
        application {
            configureSerialization()
            configureRouting()
        }
        // when
        client.post("/api/v1/customer"){
            setBody(File("src/test/resources/payload.json").readText())
            contentType(ContentType.Application.Json)
        }.apply {
            assertThat(status).isEqualTo(HttpStatusCode.Created)
            assertThat(bodyAsText()).containsPattern("\"customerId\" : \\d{5}")
        }
    }

}