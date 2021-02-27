package raum.muchbeer.lazyinktx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_food_detail.*
import raum.muchbeer.lazyinktx.MainActivity.Companion.RESTAURANT_KEY
import raum.muchbeer.lazyinktx.model.YelpRestaurant

class FoodDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        val foodName = intent.getParcelableExtra<YelpRestaurant>(RESTAURANT_KEY)
        txtDetailName.text = foodName.name

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}