package raum.muchbeer.lazyinktx.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class YelpInstance {

    companion object {
      private const val BASE_URL= "https://api.yelp.com/v3/"
        const val API_KEY = "uNWCh1LywfAZ3wvadwE-JKo9AOT9SR6loHIhFMi21nNfEhTgOPLz7D0YtV_i6nXYPJ41JdzdbyfYIGkLV8G2XQFCI2PGg8U2fAymsfMlTakGVOcMEEE2QIBaKqw0YHYx"

        val httpLogger = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(httpLogger)
                .connectTimeout(7, TimeUnit.SECONDS) }.build()

        fun yelpInstance() : YelpService {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(YelpService::class.java)
        }
    }
}