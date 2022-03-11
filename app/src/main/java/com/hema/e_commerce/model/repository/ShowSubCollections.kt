package com.hema.e_commerce.model.repository

import android.content.Context
import android.provider.Settings.Global.getString
import com.hema.e_commerce.MainActivity
import com.hema.e_commerce.R
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections

class ShowSubCollections(var context:Context) {




    fun showSub(position: Int): ArrayList<SubCollections> {

        var subCollectionsListHome = arrayListOf<SubCollections>(
            //home 0
            SubCollections(context.getString(R.string.tshirt), R.drawable.tshirthome),
            SubCollections(context.applicationContext.getString(R.string.shoes), R.drawable.homeshoes),
            SubCollections(context.getString(R.string.accessories), R.drawable.saleaccessorie)
        )
        //kids 1
        var subCollectionsListKids = arrayListOf<SubCollections>(

            SubCollections(context.getString(R.string.tshirt), R.drawable.tshirtkids),
            SubCollections(context.getString(R.string.shoes), R.drawable.shoeskids),
            SubCollections(context.getString(R.string.accessories), R.drawable.accessoriesrightkids2)
        )
        //men 2
        var subCollectionsListMen = arrayListOf<SubCollections>(

            SubCollections(context.getString(R.string.tshirt), R.drawable.tshirtman),
            SubCollections(context.getString(R.string.shoes), R.drawable.shoesman),
            SubCollections(context.getString(R.string.accessories), R.drawable.accesoriesrightman)
        )
//sale 3

        var subCollectionsListSale = arrayListOf<SubCollections>(

            SubCollections(context.getString(R.string.tshirt), R.drawable.tshirthome),
            SubCollections(context.getString(R.string.shoes), R.drawable.homeshoes),
            SubCollections(context.getString(R.string.accessories), R.drawable.accessoriesrightwoman2)
        )

        //women  4
        var subCollectionsListWoman = arrayListOf<SubCollections>(

            SubCollections(context.getString(R.string.tshirt), R.drawable.tshirtwoman2),
            SubCollections(context.getString(R.string.shoes), R.drawable.womanshoes),
            SubCollections(context.getString(R.string.accessories), R.drawable.accessoriesrightwoman)
        )


        when (position) {
            0 -> return subCollectionsListHome
            1 -> return subCollectionsListKids
            2 -> return subCollectionsListMen
            3 -> return subCollectionsListSale
            4 -> return subCollectionsListWoman
            else -> return subCollectionsListHome


        }


    }


}