package com.hema.e_commerce.adapter.home

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.hema.e_commerce.R

class WishListNotificationAdapter(view: View) {
    var wishListCounter : TextView = view.findViewById(R.id.favouriteItems)
    var Button : ImageButton = view.findViewById(R.id.favouriteButton)
    fun updateView(number : Int){
        if (number> 0){
            wishListCounter.text = number.toString()
            wishListCounter.visibility = View.VISIBLE
        }
        else
            wishListCounter.visibility = View.INVISIBLE
    }

    fun hideNumber(){
            wishListCounter.visibility = View.INVISIBLE
    }

}