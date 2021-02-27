package raum.muchbeer.lazyinktx.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import raum.muchbeer.lazyinktx.model.YelpRestaurant

    @BindingAdapter("foodName")
    fun TextView.foodName(food : YelpRestaurant?) {
        food?.let {
            text = it.name
        }
    }

    @BindingAdapter("foodPrice")
    fun TextView.foodPrice(food: YelpRestaurant?) {
        food?.let {
            text= it.price
        }
    }

    @BindingAdapter("foodReview")
    fun TextView.foodReview(food: YelpRestaurant?) {
        food?.let {
            text= it.numReviews.toString()
        }
    }

    @BindingAdapter("foodRating")
    fun RatingBar.foodRating(food: YelpRestaurant?) {
        food?.let {
         //  ratingBar.rating= it.rating.toFloat()
            rating = it.rating.toFloat()
        }
    }

    @BindingAdapter("foodDistance")
    fun TextView.foodDistance(food: YelpRestaurant?) {
        food?.let {
            text= it.displayDistance()
        }
    }

    @BindingAdapter("foodImage", "placeholder")
    fun ImageView.foodImage(food: YelpRestaurant?, placeholder: Drawable) {
        food?.let {
            if(!it.imageUrl.isNullOrEmpty()) {
             Glide.with(this.context).load(it.imageUrl)
                     .centerCrop()
                     .placeholder(android.R.drawable.stat_notify_error)
                     .into(this) }

            else {
                this.setImageDrawable(placeholder)
            }
        }
    }
