package net.claztec.sample.features.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.claztec.sample.data.Restaurant
import net.claztec.sample.data.RestaurantRepository
import net.claztec.sample.util.Resource
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    private val _loadingFlag = MutableLiveData<Boolean>(false)
    val restaurants: LiveData<List<Restaurant>> = _restaurants
    val loadingFlag: LiveData<Boolean> = _loadingFlag


    init {
        viewModelScope.launch {
            repository.getRestaurants().collect { result ->
                if (result is Resource.Success) {
                    _restaurants.postValue(result.data!!)
                    _loadingFlag.value = false
                } else if (result is Resource.Loading) {
                    _loadingFlag.value = true
                }
            }
        }

    }

    fun deleteItem() {
        viewModelScope.launch {
            val data = _restaurants.value
            val subList = data?.subList(0, (data.size / 2).toInt())
            _restaurants.postValue(subList!!)
        }
    }
}