package raum.muchbeer.lazyinktx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.lazyinktx.databinding.YelpItemBinding
import raum.muchbeer.lazyinktx.model.YelpCategory
import raum.muchbeer.lazyinktx.model.YelpRestaurant
import java.util.*

class RestaurantAdapter(val clickListener : FoodListener) :
                    ListAdapter<YelpRestaurant, RestaurantAdapter.RestaurantVH>(RestaurantVH) {

    var unfilteredList: List<YelpRestaurant>? = null

    //recommended way to use List:
  /*  var foodListAlt = listOf<YelpRestaurant>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }*/

    //Using binding Adapter to represent the view to load the data



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantVH {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding = YelpItemBinding.inflate(layoutInflater, parent, false)
       // val view = layoutInflater.inflate(R.layout.yelp_item, parent, false)
        return RestaurantVH(binding)
    }

    override fun onBindViewHolder(holder: RestaurantVH, position: Int) {
     //  val restaurant = getItem(position)
        val restaurant = unfilteredList!![position]
        holder.bind(restaurant, clickListener)
    }

    fun modifyList(list : List<YelpRestaurant>) {
        unfilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<YelpRestaurant>()

        // perform the data filtering
        if(!query.isNullOrEmpty()) {
            list.addAll(unfilteredList!!.filter {
                it.name.toLowerCase(Locale.getDefault()).contains(query.toString().toLowerCase(Locale.getDefault()))
               /*         ||
                it.price.toLowerCase(Locale.getDefault()).contains(query.toString().toLowerCase(Locale.getDefault())) */

            })
        } else {
            list.addAll(unfilteredList!!)
        }


        submitList(list)

    }


    //We dont getItemCount because the listAdapter can figure it for us
  //  override fun getItemCount(): Int = foodList.size
    class RestaurantVH(val binding: YelpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: YelpRestaurant, clickListener: FoodListener) {

            binding.getRestaurant = restaurant
            binding.executePendingBindings()
            binding.clickListenerBinding = clickListener
          /*  binding.txtName.text = restaurant.name
            binding.txtPrice.text = restaurant.price
            binding.txtReview.text = restaurant.numReviews.toString()
            binding.txtDistance.text = restaurant.displayDistance()
            binding.ratingBar.rating = restaurant.rating.toFloat()
            Glide.with(itemView.context).load(restaurant.imageUrl).into(binding.imageView)*/

            //Other option of getting replace data into the view
        /*    qualityImage.setImageResource(when(foodModel.image) {

                food->R.drawable.ic_food
                drink->R.drawable.ic_drink
                snacks->R.drawable.ic_snacks
                else -> R.drawable.ic_other })*/
        }

companion object    diffUtil : DiffUtil.ItemCallback<YelpRestaurant>()  {
    override fun areItemsTheSame(oldItem: YelpRestaurant, newItem: YelpRestaurant): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: YelpRestaurant, newItem: YelpRestaurant): Boolean {
        return oldItem==newItem
      }

   }
}
    class FoodListener(val clickListener : (foodName: YelpRestaurant)->Unit) {
        fun foodClick(food: YelpRestaurant) = clickListener(food)
    }

}
