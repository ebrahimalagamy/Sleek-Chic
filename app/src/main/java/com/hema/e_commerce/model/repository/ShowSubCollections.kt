package com.hema.e_commerce.model.repository

import com.hema.e_commerce.R
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections

class ShowSubCollections {

    fun showSub(position: Int): ArrayList<SubCollections> {

        var subCollectionsListHome = arrayListOf<SubCollections>(

            //home 0
            SubCollections("T-SHIRTS", R.drawable.tshirthome),
            SubCollections("SHOES", R.drawable.homeshoes),
            SubCollections("ACCESSORIES", R.drawable.saleaccessorie)
        )
        //kids 1
        var subCollectionsListKids = arrayListOf<SubCollections>(

            SubCollections("T-SHIRTS", R.drawable.tshirtkids),
            SubCollections("SHOES", R.drawable.shoeskids),
            SubCollections("ACCESSORIES", R.drawable.accessoriesrightkids2)
        )
        //men 2
        var subCollectionsListMen = arrayListOf<SubCollections>(

            SubCollections("T-SHIRTS", R.drawable.tshirtman),
            SubCollections("SHOES", R.drawable.shoesman),
            SubCollections("ACCESSORIES", R.drawable.accesoriesrightman)
        )
//sale 3

        var subCollectionsListSale = arrayListOf<SubCollections>(

            SubCollections("T-SHIRTS", R.drawable.tshirthome),
            SubCollections("SHOES", R.drawable.homeshoes),
            SubCollections("ACCESSORIES", R.drawable.accessoriesrightwoman2)
        )

        //women  4
        var subCollectionsListWoman = arrayListOf<SubCollections>(

            SubCollections("T-SHIRTS", R.drawable.tshirtwoman2),
            SubCollections("SHOES", R.drawable.womanshoes),
            SubCollections("ACCESSORIES", R.drawable.accessoriesrightwoman)
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