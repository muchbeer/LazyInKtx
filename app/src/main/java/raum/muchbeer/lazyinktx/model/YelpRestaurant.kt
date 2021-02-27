package raum.muchbeer.lazyinktx.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


data class YelpRestaurant(
    val name:String,
    val rating: Double,
    val price:String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String
    //Consider you have another data Class YelpCategory you also need to initiate Parcelable
  //  val YelpCategory: YelpCategory

) : Parcelable  {


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(name)
        parcel.writeDouble(rating)
        parcel.writeString(price)
        parcel.writeInt(numReviews)
        parcel.writeDouble(distanceInMeters)
        parcel.writeString(imageUrl)
    }



    override fun describeContents(): Int =0



    companion object CREATOR : Parcelable.Creator<YelpRestaurant> {
        override fun createFromParcel(parcel: Parcel): YelpRestaurant {
            return YelpRestaurant(parcel)
        }

        override fun newArray(size: Int): Array<YelpRestaurant?> {
            return arrayOfNulls(size)
        }
    }

    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles="%.2f".format(distanceInMeters*milesPerMeter)

        return "$distanceInMiles mi"  }
}
