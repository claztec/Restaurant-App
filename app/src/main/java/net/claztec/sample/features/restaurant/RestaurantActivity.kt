package net.claztec.sample.features.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.claztec.sample.data.Restaurant
import net.claztec.sample.databinding.ActivityRestaurantBinding
import net.claztec.sample.util.Resource

@AndroidEntryPoint
class RestaurantActivity : AppCompatActivity() {

    private val viewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantAdapter = RestaurantAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = restaurantAdapter
                layoutManager = LinearLayoutManager(this@RestaurantActivity)
            }

            viewModel.restaurants.observe(this@RestaurantActivity) { result ->
                    restaurantAdapter.submitList(result)
                    restaurantAdapter.notifyDataSetChanged()

            }

            viewModel.loadingFlag.observe(this@RestaurantActivity) { flag ->
                if (flag) {
                    binding.progreeBar.visibility = View.VISIBLE
                } else {
                    binding.progreeBar.visibility = View.INVISIBLE
                }
            }

            buttonDeleteItem.setOnClickListener {
                Log.d("액티비티", "딜리트 버튼 클릭")
                viewModel.deleteItem()
//                restaurantAdapter.notifyDataSetChanged()
            }


        }

    }
}