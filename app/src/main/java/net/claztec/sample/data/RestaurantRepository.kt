package net.claztec.sample.data

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.claztec.sample.api.RestaurantApi
import net.claztec.sample.util.Resource
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val api: RestaurantApi
) {
    fun getRestaurants(): Flow<Resource<out List<Restaurant>>> = flow {
        emit(Resource.Loading(null))
        try {
            val restaurants = api.getRestaurants()
            delay(100)
            emit(Resource.Success(restaurants))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable, null))
        }
    }

    suspend fun getRestaurants1(): List<Restaurant> {
        return api.getRestaurants()
    }


    fun delete(): Flow<Resource<out List<Restaurant>>> = flow {
        Log.d("리파지토리", "딜리트")
        emit(Resource.Loading(null))
        try {
            val restaurants = api.getRestaurants2()
//            restaurants.subList(0, 3)
            Log.d("리파지토리", restaurants.toString())
//            delay(100)
            emit(Resource.Success(restaurants))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable, null))
        }
    }

}