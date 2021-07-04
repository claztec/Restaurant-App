package net.claztec.sample.features.restaurant

import android.util.Log
import androidx.lifecycle.*
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

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants:LiveData<List<Restaurant>> = _restaurants

    init {
        viewModelScope.launch {
            val data = repository.getRestaurants1()
            _restaurants.postValue(data)
        }

    }

    fun deleteItem() {
        viewModelScope.launch {
            val data = _restaurants.value
            val subList = data?.subList(0, (data.size / 2).toInt())
            _restaurants.postValue(subList!!)
        //            val aa = repository.getRestaurants1()
//            _restaurants.postValue(aa)
//            restaurants = repository.delete().map{it.data}.asLiveData()
//            Log.d("뷰모델", "리파지토리 딜리트 ")
//        val emptyList = List<Restaurant>()
//        restaurants.value =
        }
    }
}