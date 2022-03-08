package com.hema.e_commerce.adapter.singleProduct

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ReviewsItemBinding
import com.hema.e_commerce.ui.category.reviews.ReviewsModel


class ReviewsAdapter(private val reviewsList: List<ReviewsModel>) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.reviews_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val review = reviewsList[position]
       holder.itemBinding.tvUser.text = review.user
        holder.itemBinding.tvComment.text = review.comment
        holder.itemBinding.profileImage.setImageResource(review.image)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }


    class ViewHolder(itemBinding: ReviewsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: ReviewsItemBinding = itemBinding

    }


}