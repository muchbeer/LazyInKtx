package raum.muchbeer.lazyinktx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.yelp_item.view.*
import raum.muchbeer.lazyinktx.model.YelpRestaurant

class RestaurantAdapter(val context: Context, private var foodList: List<YelpRestaurant>) :
                    RecyclerView.Adapter<RestaurantAdapter.RestaurantVH>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantAdapter.RestaurantVH {
       val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.yelp_item, parent, false)
        return RestaurantVH(view)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.RestaurantVH, position: Int) {
       val restaurant = foodList[position]
        holder.bind(restaurant)
    }

    override fun getItemCount(): Int = foodList.size

  inner class RestaurantVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurant) {
            itemView.txtName.text = restaurant.name
            itemView.txtPrice.text = restaurant.price
            itemView.txtReview.text = restaurant.numReviews.toString()
            itemView.txtDistance.text = restaurant.displayDistance()
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            Glide.with(context).load(restaurant.imageUrl).into(itemView.imageView)
        }

    }


}
