package com.hema.e_commerce.model.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections

class SubCollectionsRepo(var context: Context) {
    //sub collections
    var subCollectionProductsLiveData = MutableLiveData<ArrayList<SubCollections>>()
    fun getSubCollections(position: Int) {
        subCollectionProductsLiveData.value = ShowSubCollections(context).showSub(position)
    }
}