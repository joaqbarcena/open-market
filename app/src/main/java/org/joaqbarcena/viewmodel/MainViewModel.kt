package org.joaqbarcena.viewmodel

import android.app.Application
import android.util.Log.e
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.joaqbarcena.model.Item
import org.joaqbarcena.model.Site
import org.joaqbarcena.net.api
import org.joaqbarcena.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = MainViewModel::class.java.name
    private val repository: Repository by lazy {
        Repository(application.applicationContext)
    }

    val availableSites: MutableLiveData<List<Site>> by lazy {
        MutableLiveData<List<Site>>().also {
            api.availableSites().enqueue(object : Callback<List<Site>> {

                override fun onResponse(call: Call<List<Site>>, response: Response<List<Site>>){
                    response.body()?.run {
                        it.value = this.sortedBy { it.name }
                    }
                }

                override fun onFailure(call: Call<List<Site>>, t: Throwable) {
                    e(TAG, "Get sites failed : ", t)
                }

            })
        }
    }

    var site: Site? = null
        get() = if(field != null) field else repository.loadSite()
        set(value) {
            if(value != null && value != field){
                field = value
                repository.saveSite(value)
            }
        }

    var item: Item? = null
}
