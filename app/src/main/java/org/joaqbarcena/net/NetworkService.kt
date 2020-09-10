package org.joaqbarcena.net

import org.joaqbarcena.BuildConfig
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
               ) : Call<SearchResult>

    @GET("/sites/{site}/search")
    fun searchAndFilter(@Path("site") site: String,
               @Query("q") query: String,
               @Query("offset") offset: Number = 0,
               @Query("orders") sort: String = "",
               @QueryMap filters: Map<String, String>
               ) : Call<SearchResult>
}