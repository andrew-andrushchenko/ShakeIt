package com.andrii_a.shakeit.data.remote.service

import com.andrii_a.shakeit.data.remote.dto.CocktailResponseDto
import com.andrii_a.shakeit.data.remote.dto.CocktailsResponseDto
import com.andrii_a.shakeit.data.util.Endpoints
import com.andrii_a.shakeit.data.util.backendRequest
import com.andrii_a.shakeit.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

class CocktailServiceImpl(private val httpClient: HttpClient) : CocktailService {

    override suspend fun getCocktails(
        page: Int,
        perPage: Int,
        name: String,
        category: String,
        hasAlcohol: String,
        glass: String,
        ingredientId: Int?,
        ingredients: Boolean
    ): Resource<CocktailsResponseDto> {
        return backendRequest {
            httpClient.get(urlString = Endpoints.Cocktails.url) {
                parameter("page", page)
                parameter("perPage", perPage)
                if (name.isNotEmpty()) {
                    parameter("name", name)
                }
                if (category.isNotEmpty()) {
                    parameter("category", category)
                }
                if (hasAlcohol.isNotEmpty()) {
                    parameter("alcoholic", hasAlcohol)
                }
                if (glass.isNotEmpty()) {
                    parameter("glass", glass)
                }
                if (ingredientId != null) {
                    parameter("ingredientId", ingredientId)
                }
                parameter("ingredients", ingredients)
            }
        }
    }

    override suspend fun getCocktail(id: Int): Resource<CocktailResponseDto> {
        return backendRequest {
            httpClient.get(urlString = Endpoints.Cocktails.url) {
                url {
                    appendPathSegments(id.toString())
                }
            }
        }
    }
}