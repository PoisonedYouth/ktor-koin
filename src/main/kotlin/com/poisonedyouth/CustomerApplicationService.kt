package com.poisonedyouth

import org.koin.java.KoinJavaComponent.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CustomerApplicationService(
    private val addressRepository: AddressRepository,
    private val customerRepository: CustomerRepository
) {
    private val accessCounter by inject<AccessCounter>(AccessCounter::class.java)
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun addNewCustomer(customerDto: CustomerDto): ApiResult<Long> {
        logger.info("Current in CustomerApplicationService counter: ${accessCounter.getCurrentCount()}")
        accessCounter.increaseCounter()
        try {
            val customer = mapCustomerDtoToCustomer(customerDto)

            if (customerRepository.existsCustomerByEmail(customer.email)) {
                return Failure(ErrorCode.DUPLICATE_EMAIL, "For the email '${customer.email}' a customer already exists!")
            }

            val address = addressRepository.findAddressByZipCode(customer.address.zipCode)
            val customerToPersist = if (address == null) {
                customer
            } else {
                customer.copy(address = address)
            }

            return Success(customerRepository.createCustomer(customerToPersist))

        } catch (e: DateTimeParseException) {
            return Failure(
                ErrorCode.INVALID_DATE,
                "The birthdate '${customerDto.birthdate}' is not in expected format (dd.MM.yyyy)!"
            )
        } catch (e: Exception) {
            return Failure(ErrorCode.GENERAL_ERROR, "An unexpected error occurred (${e.message}!")
        }
    }

    private fun mapCustomerDtoToCustomer(customerDto: CustomerDto): Customer {
        return Customer(
            firstName = customerDto.firstName,
            lastName = customerDto.lastName,
            birthDate = LocalDate.parse(customerDto.birthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            email = customerDto.email,
            address = Address(
                id = null,
                street = customerDto.address.street,
                number = customerDto.address.number,
                zipCode = customerDto.address.zipCode,
                city = customerDto.address.city,
                country = customerDto.address.country
            ),
            accounts = customerDto.accounts.map {
                Account(
                    number = it.number,
                    balance = it.balance
                )
            }.toSet()
        )
    }
}

sealed interface ApiResult<out T>

internal data class Failure(val errorCode: ErrorCode, val errorMessage: String) : ApiResult<Nothing>
internal data class Success<T>(val value: T) : ApiResult<T>

enum class ErrorCode {
    DUPLICATE_EMAIL,
    INVALID_DATE,
    GENERAL_ERROR,
}
