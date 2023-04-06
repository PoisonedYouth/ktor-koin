package com.poisonedyouth

import org.junit.jupiter.api.Test
import org.koin.ksp.generated.defaultModule
import org.koin.test.verify.verify

class DependencyConfigurationTest {

    @Test
    fun verifyDependencyInjectionConfiguration() {

        // Verify the dependency configuration
        defaultModule.verify()
    }
}