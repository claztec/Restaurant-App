package net.claztec.sample.features.restaurant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.claztec.sample.data.Restaurant
import net.claztec.sample.data.RestaurantRepository
import net.claztec.sample.util.Resource
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {

    private fun upper(restaurants: List<Restaurant>?): List<Restaurant> {
        for (restaurant in restaurants!!) {
            restaurant.address = restaurant.address.toUpperCase()
        }

        return restaurants
    }

    private var _restaurant = repository.getRestaurants().map {
        delay(100)
//        for (item in it.data!!) {
//            item.name = item.name.toUpperCase()
//        }
//        it
        if (it.data != null) {
            val aaaa = upper(it.data)
            Resource.Success(aaaa)
        } else {
            it
        }
    }.asLiveData(

    )

    val restaurants: LiveData<Resource<out List<Restaurant>>>
        get() = _restaurant

    fun deleteItem() {
//        val value = _restaurant.value
//        value?.data?.subList(0, value?.data?.size?:0)
//        restaurants.value = Resource.Success()
//        _restaurant = repository.delete().asLiveData()
//        _restaurant = repository.delete().asLiveData()

        viewModelScope.launch {
            _restaurant = repository.delete().asLiveData()
            Log.d("뷰모델", "리파지토리 딜리트 ")
        }
    }
}