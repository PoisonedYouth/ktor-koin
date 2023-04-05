package com.poisonedyouth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CleanDatabaseExtension::class)
class AddressRepositoryTest  {

    private val addressRepository = AddressRepository()

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
        val id= addressRepository.createNewAddress(address)

        // then
        assertThat(addressRepository.getAddressById(id)).isNotNull
    }
}