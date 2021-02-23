package raum.muchbeer.lazyinktx.repository

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import raum.muchbeer.lazyinktx.api.YelpInstance
import raum.muchbeer.lazyinktx.api.YelpInstance.Companion.API_KEY
import raum.muchbeer.lazyinktx.model.YelpRestaurant
import raum.muchbeer.lazyinktx.model.YelpSearchResult
import raum.muchbeer.lazyinktx.utility.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YelpRepository() {

   suspend fun retrieveLiveDataTrial() : Response<YelpSearchResult>{

      return  YelpInstance.yelpInstance().searchRestaurants(
            "Bearer ${API_KEY}",
            "Avocado",
            "Texas"
        )
    }
}
/*    fun retrieveYelpResponse() : Response<YelpSearchResult> {


        YelpInstance.yelpInstance().searchRestaurants(
            "Bearer ${API_KEY}",
             "Avocado",
            "Texas"
        ).enqueue( object : Callback<YelpSearchResult> {
            override fun onResponse(
                call: Call<YelpSearchResult>,
                response: Response<YelpSearchResult>
            ) {
                val  response : List<YelpRestaurant> = response.body()!!.restaurants

                if(response.isNullOrEmpty()) {
                    Log.i("Repository", "error caused ")
                } else {
                        //foodList.addAll(response)
                       for(food in response) {
                        Log.i("Repository", "FoodSearch is : ${food.name}")
                    }
                }


            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i("Repository", "Error given is : ${t.message}")
            }
        })

    }*/
