package raum.muchbeer.lazyinktx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import raum.muchbeer.lazyinktx.R
import raum.muchbeer.lazyinktx.databinding.YelpItemBinding
import raum.muchbeer.lazyinktx.model.YelpRestaurant
import java.lang.ClassCastException
import java.util.*

private const val ITEM_VIEW_TYPE_HEADER=0
private const val ITEM_VIEW_TYPE_ITEM=1

class RestaurantMultipleViewAdapter(val clickListener : FoodListener) :
                    ListAdapter<RestaurantMultipleViewAdapter.FoodItem, RecyclerView.ViewHolder>(RestaurantVH) {

    private val adapterScope= CoroutineScope(Dispatchers.Default)

    var unfilteredList: List<YelpRestaurant>? = null

     override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding = YelpItemBinding.inflate(layoutInflater, parent, false)
        val viewHolder = layoutInflater.inflate(R.layout.yelp_item, parent, false)
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER->TextHeaderVH(binding)
            ITEM_VIEW_TYPE_ITEM-> RestaurantVH(binding)
            else -> throw ClassCastException("Unknown ViewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     //  val restaurant = getItem(position)
      //  val restaurant = unfilteredList!![position]

        when(holder) {
            is RestaurantVH -> {
                val restaurant = getItem(position) as FoodItem.RestaurantItem
                holder.bind(restaurant, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when(getItem(position)) {
            is FoodItem.Header-> ITEM_VIEW_TYPE_HEADER
            is FoodItem.RestaurantItem-> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<YelpRestaurant>?) {
        //get list in the back end thread
            adapterScope.launch {
                val items = when(list) {
                    null-> listOf(FoodItem.Header)
                    else-> listOf(FoodItem.Header) + list.map {FoodItem.RestaurantItem(it) }
                }
        //run in the ui thread update the UI
                withContext(Dispatchers.Main) {
                    submitList(items)
                }
            }
    }

    fun modifyList(list : List<YelpRestaurant>) {
        unfilteredList = list
      //  submitList(list)
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


      //  submitList(list)

    }

    class TextHeaderVH(val binding: YelpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: YelpRestaurant) {
            binding.getRestaurant = restaurant
        }
    }

    class RestaurantVH(val binding: YelpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: FoodItem.RestaurantItem, clickListener: FoodListener) {

         /*   binding.getRestaurant = restaurant
            binding.executePendingBindings()
            binding.clickListenerBinding = clickListener*/

        }


companion object    diffUtil : DiffUtil.ItemCallback<FoodItem>()  {
    override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem.foodName == newItem.foodName
    }

    override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem==newItem
      }

   }
}
    class FoodListener(val clickListener : (foodName: YelpRestaurant)->Unit) {
        fun foodClick(food: YelpRestaurant) = clickListener(food)
    }

    sealed class FoodItem {
        data class RestaurantItem(val restaurant: YelpRestaurant) : FoodItem() {
            override val foodName = restaurant.name
        }
        object Header: FoodItem() {
            override val foodName = "sweetgreen"
        }

        abstract val foodName: String
    }

}
