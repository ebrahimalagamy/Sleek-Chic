package com.hema.e_commerce.ui.category.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.singleProduct.ReviewsAdapter
import com.hema.e_commerce.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {
    lateinit var   reviewAdapter: ReviewsAdapter
    lateinit var binding:FragmentReviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)

return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var arrayList= arrayListOf<ReviewsModel>(ReviewsModel(R.drawable.aya,"Aya Alaa",getString(
                    R.string.ayareview)),
            ReviewsModel(R.drawable.doaa,"Doaa Abdelnasser",getString(R.string.doaareview)),
            ReviewsModel(R.drawable.mohammed,"Mohammed kamal",getString(R.string.mohammedreview)),
            ReviewsModel(R.drawable.ebrahim,"Ebrahim Elagamy",getString(R.string.ibrahimreview)),
        ReviewsModel(R.drawable.doaa,"Enas mohammed",getString(R.string.enasreview)),
        ReviewsModel(R.drawable.aya,"Aya Abdelaziz",getString(R.string.ayahreview)),
        ReviewsModel(R.drawable.doaa,"fayza ",getString(R.string.fayzareview)))



        reviewAdapter=ReviewsAdapter(arrayList)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.reviewRec.adapter = reviewAdapter
        binding.reviewRec.layoutManager = layoutManager









    }




}