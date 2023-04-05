package com.poisonedyouth

import io.ktor.http.HttpStatusCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CleanDatabaseExtension::class)
internal class CustomerControllerTest {

    private val customerController = CustomerController()

    @Test
    fun `returns SuccessDto with correct http status`() {
        // given
        val address = AddressDto(
            street = "Main Street",
            number = "13",
            zipCode = 90001,
            city = "Los Angeles",
            country = "US"
        )
        val customer = CustomerDto(
            firstName = "John",
            lastName = "Doe",
            birthdate = "10.05.2000",
            email = "john.doe@mail.com",
            address = address,
            accounts = setOf(
                AccountDto(
                    number = 12345,
                    balance = 200
                ),
                AccountDto(
                    number = 12346,
                    balance = -150
                )
            )
        )


        // when
        val actual = customerController.addNewCustomer(customer)


        // then
        assertThat(actual.first).isEqualTo(HttpStatusCode.Created)
        assertThat(actual.second.toString()).matches("SuccessDto\\(customerId=\\d{5}\\)")
    }

    @Test
    fun `returns ErrorDto with correct http status`() {
        // given
        val address = AddressDto(
            street = "Main Street",
            number = "13",
            zipCode = 90001,
            city = "Los Angeles",
            country = "US"
        )
        val customer = CustomerDto(
            firstName = "John",
            lastName = "Doe",
            birthdate = "10.05.2010",
            email = "john.doe@mail.com",
            address = address,
            accounts = setOf(
                AccountDto(
                    number = 12345,
                    balance = 200
                ),
                AccountDto(
                    number = 12346,
                    balance = -150
                )
            )
        )


        // when
        val actual = customerController.addNewCustomer(customer)


        // then
        assertThat(actual.first).isEqualTo(HttpStatusCode.InternalServerError)
        assertThat(actual.second.toString()).isEqualTo("ErrorDto(errorCode=GENERAL_ERROR, errorMessage=An unexpected error occurred (Age must be between 18 and 100!!)")
    }
}