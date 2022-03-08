package com.hema.e_commerce.adapter.singleProduct
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.navigation.NavController
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.hema.e_commerce.R


class ProductAdapter(
    var context:Context, var images: List<com.hema.e_commerce.model.dataclass.singleproduct.Image>


) : PagerAdapter (){

lateinit var inflater:LayoutInflater

    override fun getCount(): Int {
return images.size   }

    override fun isViewFromObject(view: View, `object`: Any): Boolean =view== `object` as RelativeLayout

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
var  image:ImageView

    inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var view:View=inflater.inflate(R.layout.item_slider_view,container,false)
    image=view.findViewById(R.id.myimage)
      //  image.setBackgroundResource(images.get(position))
        Glide.with(context).load(images.get(position).src).into(image)


        container.addView(view)
return  view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
container.removeView(`object` as RelativeLayout)
    }
}




