package com.hema.e_commerce.ui.category.repository

import com.hema.e_commerce.R
import com.hema.e_commerce.ui.category.testmodels.ModelContainer
import com.hema.e_commerce.ui.category.testmodels.TypeModelList
import com.hema.e_commerce.ui.category.testmodels.model

class Repository {
// for side recycler
    val arr= arrayListOf<model>(model("man"),
    model("woman"),
    model("child"),
    model("bags"),
    model("T-shirt"),
    model("jacket"),
    model("man"),
    model("woman"),model("child"),
    model("man"),
    model("woman"),
    model("child"),
    model("man"),
    model("woman"),
    model("child"),
    model("man"))

// for container
    val arrContainer= arrayListOf<ModelContainer>(ModelContainer("man", R.drawable.test2),
        ModelContainer("woman", R.drawable.test2), ModelContainer("bags", R.drawable.test2),
        ModelContainer("dresses", R.drawable.test2), ModelContainer("suits", R.drawable.test2)
        , ModelContainer("shose", R.drawable.test2)
        , ModelContainer("accessories", R.drawable.test2)
        , ModelContainer("hair", R.drawable.test2))

// for type list



    val arrTypeList= arrayListOf<TypeModelList>(TypeModelList("man","shirt black red ", R.drawable.test2),
        TypeModelList("woman","dress black red ", R.drawable.test2), TypeModelList("bags","bag black red", R.drawable.test2),
        TypeModelList("dresses","dress black red ", R.drawable.test2), TypeModelList("suits","suits black red", R.drawable.test2)
        , TypeModelList("shose","shose black red ", R.drawable.test2)
        , TypeModelList("accessories","accessories black red", R.drawable.test2)
        , TypeModelList("hair","hair black red", R.drawable.test2))



}