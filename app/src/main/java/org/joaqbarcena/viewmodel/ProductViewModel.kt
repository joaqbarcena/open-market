package org.joaqbarcena.viewmodel

import android.util.Log.e
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.joaqbarcena.model.DescriptionResult
import org.joaqbarcena.model.Item
import org.joaqbarcena.model.PicturesResult
import org.joaqbarcena.net.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    val TAG = ProductViewModel::class.java.name

    val pictures: MutableLiveData<PicturesResult> = MutableLiveData()
    val description: MutableLiveData<DescriptionResult> = MutableLiveData()

    fun getPictureOf(item: Item) = api.itemPictures(item.id)
        .enqueue(object : Callback<PicturesResult> {
            override fun onResponse(call: Call<PicturesResult>,
                                    response: Response<PicturesResult>) {
                response.body()?.let {
                    if(it.error == null)
                        pictures.value = it
                    else
                        e(TAG, "${it.message}")
                }

            }
            override fun onFailure(call: Call<PicturesResult>, t: Throwable) {
                e(TAG, "Get pictures failed : ", t)
            }

        })

    fun getDescriptionOf(item: Item) = api.itemDescription(item.id)
        .enqueue(object : Callback<DescriptionResult> {
            override fun onResponse(call: Call<DescriptionResult>,
                                    response: Response<DescriptionResult>) {
                response.body()?.let {
                    if(it.error == null)
                        description.value = it
                    else
                        e(TAG, "${it.message}")
                }
            }
            override fun onFailure(call: Call<DescriptionResult>, t: Throwable) {
                e(TAG, "Get description failed : ", t)
            }

        })
}
