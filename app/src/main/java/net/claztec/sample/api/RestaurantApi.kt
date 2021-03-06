package net.claztec.sample.api

import net.claztec.sample.data.Restaurant
import retrofit2.http.GET

interface RestaurantApi {

    companion object {
        const val BASE_URL = "https://random-data-api.com/api/"
    }

    @GET("restaurant/random_restaurant?size=20")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurant/random_restaurant?size=10")
    suspend fun getRestaurants2(): List<Restaurant>
}