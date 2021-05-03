package net.claztec.sample.features.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.claztec.sample.api.RestaurantApi
import net.claztec.sample.data.Restaurant
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(api: RestaurantApi): ViewModel() {

    private val restaurantsLiveData = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = restaurantsLiveData

    init {
        viewModelScope.launch {
            val restaurants = api.getRestaurants()
            restaurantsLiveData.value = restaurants
        }
    }
}