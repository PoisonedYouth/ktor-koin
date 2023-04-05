package com.poisonedyouth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

@ExtendWith(CleanDatabaseExtension::class)
class AddressRepositoryTest : KoinTest {

    private val addressRepository by inject<AddressRepository>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            com.poisonedyouth.plugins.main
        )
    }

    @Test
    fun `createNewAddress persists new address`() {
        // given
        val address = Address(
            street = "Main Street",
            number = "13A",
            zipCode = 90001,
            city = "Los Angeles",
            country = "US"
        )

        // when
        val id = addressRepository.createAddress(address)

        // then
        assertThat(addressRepository.getAddressById(id)).isNotNull
    }
}