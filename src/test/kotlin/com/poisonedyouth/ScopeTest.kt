package com.poisonedyouth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension

@ExtendWith(CleanDatabaseExtension::class)
@Disabled
class ScopeTest : KoinTest {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            com.poisonedyouth.plugins.main
        )
    }

    @Test
    fun `test custom scope`() {
        val scope = getKoin().createScope("CustomScope", named("Custom"))

        fun useScope() {
            val accessCounter = scope.get<AccessCounter>()
            accessCounter.increaseCounter()
            assertThat(accessCounter.getCurrentCount()).isEqualTo(1)
        }

        fun useScopeAgain() {
            val scopeInner = getKoin().getScope("CustomScope")
            val accessCounter = scopeInner.get<AccessCounter>()
            accessCounter.increaseCounter()
            assertThat(accessCounter.getCurrentCount()).isEqualTo(2)
        }



        scope.close()

        val accessCounterNew = scope.get<AccessCounter>()

    }


}