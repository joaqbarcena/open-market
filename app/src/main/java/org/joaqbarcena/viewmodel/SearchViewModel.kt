package org.joaqbarcena.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.joaqbarcena.model.SearchResult
import org.joaqbarcena.net.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val TAG = SearchViewModel::class.java.name
    val searchResult: MutableLiveData<SearchResult> = MutableLiveData()

    fun search(query: String?, siteId: String = "MLA", offset:Int = 0) {
        query?.let {
            api.search(siteId, it)
                .enqueue(object : Callback<SearchResult> {

                    override fun onResponse(call: Call<SearchResult>,
                                            response: Response<SearchResult>){
                        response.body()?.run {
                            searchResult.value = this
                        }

                    }

                    override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                        Log.e(TAG, "Get sites failed : ", t)
                    }
                })
        }
    }

    // TODO: Implement the ViewModel
}
