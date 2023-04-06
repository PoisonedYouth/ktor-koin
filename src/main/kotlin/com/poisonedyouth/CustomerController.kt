package com.poisonedyouth

import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Single

interface CustomerApi {
    fun addNewCustomer(customerDto: CustomerDto): Pair<HttpStatusCode, CustomerController.ResponseDto>
}

@Single(createdAtStart = true)
class CustomerController(
    private val customerApplicationService: CustomerApplicationService
) : CustomerApi {

    override fun addNewCustomer(customerDto: CustomerDto): Pair<HttpStatusCode, ResponseDto> {
        return customerApplicationService.addNewCustomer(customerDto).let { result ->
            when (result) {
                is Success -> handleSuccess(result)
                is Failure -> handleFailure(result)
            }

        }
    }

    private fun handleSuccess(result: Success<Long>): Pair<HttpStatusCode, SuccessDto> {
        return HttpStatusCode.Created to SuccessDto(result.value)
    }

    private fun handleFailure(
        result: Failure
    ): Pair<HttpStatusCode, ErrorDto> {

        val code = result.errorCode
        val status = when (code) {
            ErrorCode.INVALID_DATE,
            ErrorCode.DUPLICATE_EMAIL -> HttpStatusCode.BadRequest

            ErrorCode.GENERAL_ERROR -> HttpStatusCode.InternalServerError
        }
        return status to ErrorDto(code.name, result.errorMessage)
    }

    sealed interface ResponseDto

    data class ErrorDto(val errorCode: String, val errorMessage: String) : ResponseDto
    data class SuccessDto(val customerId: Long) : ResponseDto
}
