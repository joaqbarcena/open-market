package org.joaqbarcena.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.joaqbarcena.BuildConfig
import org.joaqbarcena.model.DescriptionResult
import org.joaqbarcena.model.PicturesResult
import org.joaqbarcena.model.SearchResult
import org.joaqbarcena.model.Site
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

val api = Retrofit.Builder()
    .baseUrl(BuildConfig.MELI_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build())
    .build()
    .create(MercadoLibreService::class.java)


//Retrofit Service class
interface MercadoLibreService {

    //TODO https://developers.mercadolibre.com.ar/es_ar/consideraciones-de-diseno#Reducción-de-respuestas
    @GET("/sites")
    fun availableSites(): Call<List<Site>>

    @GET("/sites/{site}/search")
    fun search(@Path("site") site: String,
               @Query("q") query: String,
               @Query("offset") offset: Number = 0,
               @Query("orders") sort: String = ""
               //,@Query("attributes") attr: String = "id,title,price,condition,thumbnail,shipping"
               ) : Call<SearchResult>

    @GET("/sites/{site}/search")
    fun searchAndFilter(@Path("site") site: String,
               @Query("q") query: String,
               @Query("offset") offset: Number = 0,
               @Query("orders") sort: String = "",
               @QueryMap filters: Map<String, String>
               ) : Call<SearchResult>

    //https://api.mercadolibre.com/items/MLA871632906 (El item de la publicacion con las pictures)
    //https://api.mercadolibre.com/items/MLA871632906?attributes=pictures (only pictures)
    //https://api.mercadolibre.com/items/MLA871632906/description (only description)

    @GET("/items/{item}?attributes=pictures")
    fun itemPictures(@Path("item") item: String): Call<PicturesResult>

    @GET("/items/{item}/description?attributes=plain_text")
    fun itemDescription(@Path("item") item: String): Call<DescriptionResult>

}

