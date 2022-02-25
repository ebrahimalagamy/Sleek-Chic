package com.hema.e_commerce.ui.category.categoryui.containerui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentContainerBinding
import com.hema.e_commerce.ui.category.repository.Repository
import com.hema.e_commerce.ui.category.testmodels.ModelContainer


class FragmentContainer : Fragment() {
    lateinit var binding: FragmentContainerBinding
    lateinit var adapter: ContainerAdapter
    lateinit var arr: ArrayList<ModelContainer>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_container, container, false)

        // Inflate the layout for this fragment
//        var bundle:Bundle?= arguments
//        if(bundle!=null){
//            Log.i("ssssssssssssssssss", "onCreateView: bundel ! null")
//
//           // if(bundle.getString("selected data")!=null) {
//              //  bundle.getString("selected data")
//             //   binding.test.text=bundle.getString("selected data")
//                Log.i("ssssssssssssssssss", "onCreateView: "+bundle.getString("selected data"))
//
//
////            }
////            else{
////
////                Log.i("ssssssssssssssssss", "onCreateView: empty string ")
////
////
////
////
////            }
//        }
//
//        else{
//
//            Log.i("ssssssssssssssssss", "onCreateView:bundel = null ")
//
//
//        }


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fragMan: FragmentManager? = fragmentManager

        arr = Repository().arrContainer
        adapter = ContainerAdapter(fragMan, arr, requireContext())
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recContainerItem.adapter = adapter
        binding.recContainerItem.layoutManager = layoutManager


    }


}