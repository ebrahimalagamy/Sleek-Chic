package com.hema.e_commerce.ui.category.categoryui.typelistofproduct

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentTypeListProductBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.ui.category.testmodels.TypeModelList
import java.util.*


class TypeListProductsFragment : Fragment() {
    lateinit var binding: FragmentTypeListProductBinding
    lateinit var adapter: TypeListAdapter
    lateinit var arr: ArrayList<TypeModelList>
    lateinit var tempArray: ArrayList<TypeModelList>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_type_list_product, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arr = Repository().arrTypeList
        tempArray = Repository().arrTypeList
        tempArray.addAll(arr)
        adapter = TypeListAdapter(tempArray, requireContext())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recListProduct.adapter = adapter
        binding.recListProduct.layoutManager = layoutManager


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu, menu)

        val item = menu?.findItem(R.id.app_bar_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "type here to search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArray.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {

                    arr.forEach {

                        if (it.desc.toLowerCase(Locale.getDefault()).contains(searchText)) {


                            tempArray.add(it)


                        }


                    }
                    binding.recListProduct.adapter!!.notifyDataSetChanged()


                } else {

                    tempArray.clear()
                    tempArray.addAll(arr)

                    binding.recListProduct.adapter!!.notifyDataSetChanged()


                }

                return false
            }


        })
        return super.onCreateOptionsMenu(menu, inflater)


    }


}