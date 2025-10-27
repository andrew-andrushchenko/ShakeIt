package com.andrii_a.shakeit.data.remote.service

import com.andrii_a.shakeit.data.remote.dto.IngredientsResponseDto
import com.andrii_a.shakeit.data.util.Endpoints
import com.andrii_a.shakeit.data.util.backendRequest
import com.andrii_a.shakeit.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class IngredientServiceImpl(private val httpClient: HttpClient) : IngredientService {

    override suspend fun getIngredients(
        page: Int,
        perPage: Int,
        isAlcoholic: String?
    ): Resource<IngredientsResponseDto> {
        return backendRequest {
            httpClient.get(urlString = Endpoints.Ingredients.url) {
                parameter("page", page)
                parameter("perPage", perPage)
                if (isAlcoholic != null) {
                    parameter("alcohol", isAlcoholic)
                }
            }
        }
    }
}