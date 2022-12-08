package com.example.android.marsphotos.network
import androidx.lifecycle.LiveData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL =
    "https://mars.udacity.com/"
enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"),
    SHOW_BUY("buy"), SHOW_ALL("all") }
/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


/**
 * A public interface that exposes the [getPhotos] method
 */
interface MarsApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */    @GET("realestate")
    suspend fun getProperty(@Query("filter") type: String): List<NetworkMarsProperty>
}
/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi{
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}