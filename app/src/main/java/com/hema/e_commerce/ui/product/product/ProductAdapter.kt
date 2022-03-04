package com.hema.e_commerce.ui.product.product
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CategoriesProductsItemsBinding
import com.hema.e_commerce.databinding.FragmentProductBinding
import com.hema.e_commerce.databinding.ItemSliderViewBinding
import com.hema.e_commerce.model.dataclass.allProducts.Image
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollection
import com.hema.e_commerce.ui.category.categoryui.containerui.FragmentContainer




class ProductAdapter(
    var context:Context, var images: List<Image>


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




