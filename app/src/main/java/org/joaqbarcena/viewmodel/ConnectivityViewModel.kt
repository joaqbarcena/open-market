package org.joaqbarcena.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joaqbarcena.Status

class ConnectivityViewModel(application: Application) : AndroidViewModel(application) {

    val status: MutableLiveData<Status> by lazy {
        MutableLiveData<Status>().apply {
            value = Status.START
        }
    }

    init {
        startConnectivityCheck()
    }

    @SuppressLint("CheckResult")
    private fun startConnectivityCheck() {
        ReactiveNetwork
            .observeNetworkConnectivity(getApplication<Application>().applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                status.value = if(connectivity.available()) Status.CONNECTED else Status.NO_CONNECTED
            }
    }
}