package com.hema.e_commerce.ui.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hema.e_commerce.model.repository.Repository

class OrderFragmentViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {
fun getActiveStateOrder(state:String)=repo.getOrdersFromState(state)



}
