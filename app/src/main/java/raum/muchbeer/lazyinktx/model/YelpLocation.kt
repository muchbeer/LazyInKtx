package raum.muchbeer.lazyinktx.model

import com.google.gson.annotations.SerializedName

data class YelpLocation(
    @SerializedName("address1") val address: String
)
