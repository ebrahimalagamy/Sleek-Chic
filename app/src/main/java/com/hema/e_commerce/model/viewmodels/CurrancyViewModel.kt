package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.hema.e_commerce.model.dataclass.currancy.CurrancyResponse
import com.hema.e_commerce.model.remote.currancynetwork.CurrancyRepository
import com.hema.e_commerce.model.repository.Repository

class CurrancyViewModel(private val repo: CurrancyRepository,app: Application) : AndroidViewModel(app) {

    var currancyLiveData = MutableLiveData<CurrancyResponse>()

    fun changeCurrancy(to:String,amount:Double) {
        repo.getCurrancy(to,amount)
        currancyLiveData = repo.currancyLiveData

    }


}