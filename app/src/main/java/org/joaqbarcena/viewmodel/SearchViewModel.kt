package org.joaqbarcena.viewmodel

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.joaqbarcena.model.Item
import org.joaqbarcena.model.SearchResult
import org.joaqbarcena.net.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val TAG = SearchViewModel::class.java.name
    val searchResult: MutableLiveData<SearchResult> = MutableLiveData()

    fun search(query: String?, siteId: String = "MLA", offset:Int = 0) {
        query?.let { qry ->
            api.search(siteId, qry)
                .enqueue(object : Callback<SearchResult> {
                    override fun onResponse(call: Call<SearchResult>,
                                            response: Response<SearchResult>){
                        response.body()?.let {
                            if(it.error == null)
                                searchResult.value = it
                            else
                                Log.e(TAG, "${it.message}")
                        }

                    }

                    override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                        Log.e(TAG, "Get items failed : ", t)
                    }
                })
        }
    }
}
