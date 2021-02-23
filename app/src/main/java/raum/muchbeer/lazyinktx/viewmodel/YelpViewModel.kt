package raum.muchbeer.lazyinktx.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.Dispatchers
import raum.muchbeer.lazyinktx.model.YelpRestaurant
import raum.muchbeer.lazyinktx.repository.YelpRepository
import java.lang.Exception

class YelpViewModel(app:Application) : AndroidViewModel(app) {

    private val repository = YelpRepository()

    fun retrieveResponse() = liveData(Dispatchers.IO) {
        emit(raum.muchbeer.lazyinktx.utility.Resource.loading(null))

        try {
            lateinit var foodList: List<YelpRestaurant>
           foodList = repository.retrieveLiveDataTrial().body()!!.restaurants
            emit(raum.muchbeer.lazyinktx.utility.Resource.success(foodList))
            foodList.forEach {
                Log.i("YelpModel", "List of Restaurant are : ${it.name}")
            }
        } catch (e: Exception) {
            emit(raum.muchbeer.lazyinktx.utility.Resource.error(null, e.message ?: "Unknown Error"))
        }
    }
}