package com.hema.e_commerce.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.R
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.getOrder.GetOrderResponce
import com.hema.e_commerce.model.dataclass.getOrder.Order
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.ui.cart.CartData
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.cartroom.LocalDataDao
import com.hema.e_commerce.model.room.orderroom.OrderDao
import com.hema.e_commerce.model.room.orderroom.OrderData
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository {
    //Aya
    private val TAG = "CollectionsRepo"
    val collectionsLiveData = MutableLiveData<CustomCollectionsResponse>()
    fun getCollections() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getAllCollections()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse: $it")
                        Log.i(TAG, "getCollections:size " + it.custom_collections.size)
                        collectionsLiveData.value = it
                        Log.i("TAG", "getCollections: " + it.custom_collections.size)

                    }
                } else {
                    Log.i(TAG, "getCollections: error " + response.errorBody())


                }
            }

        }
    }


    val collectionProductsLiveData = MutableLiveData<ProductsResponse>()
    fun getSubCollectionsProducts(categoryId: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getCollectionProducts(categoryId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse: $it")
                        collectionProductsLiveData.value = it

                    }
                } else {
                    Log.i(TAG, "getProducts: error " + response.errorBody())


                }
            }

        }
    }

//sub collections
    var subCollectionProductsLiveData = MutableLiveData<ArrayList<SubCollections>>()
    fun getSubCollections(position: Int) {
        subCollectionProductsLiveData.value = ShowSubCollections().showSub(position)
    }

//Single product
    val singleProductsLiveData = MutableLiveData<ProductCollectionResponse>()
    fun getSingleProduct(productId: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getSingleProduct(productId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse single: $it")
                        singleProductsLiveData.value = it

                    }
                } else {
                    Log.i(TAG, "getProduct: error " + response.errorBody())

                }
            }

        }
    }
    //for cart adapter
    //class CartData(var cartIcon:Int,var cartDesc:String,var cartPrice:String,var cartCopoun:String)
    val arrayList = arrayListOf(
        CartData(R.drawable.dress, "dress", "33$", "Deliver for free"),
        CartData(R.drawable.dress, "dress", "43$", "Deliver for free"),
        CartData(R.drawable.dress, "dress", "55$", "Deliver for free")

    )
//////////////////////////////////////////
//Mohamed
//Live data
val brandsLiveData = MutableLiveData<BrandsResponce>()
    val onSaleProductsList = MutableLiveData<ProductsResponse>()
    val onHomeProductsList = MutableLiveData<ProductsResponse>()
    val allProduct = MutableLiveData<ProductsResponse>()

    //Remote function
    private suspend fun getBrands() = RetrofitInstance.api.getBrands()

    private suspend fun getProducts()=RetrofitInstance.api.getProducts()

    private suspend  fun getOnSaleProductsList()=RetrofitInstance.api.getOnSaleProductsList()

    private suspend  fun getOnHomeProductsList()=RetrofitInstance.api.getOnHomeProductsList()

    //Methods use in viewModel
    fun getBrand() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = getBrands()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("checkkkk", "onResponse: $it")
                        Log.i("checkkkk", "getBrands " + it.smart_collections.size)
                        brandsLiveData.value = it
                        Log.i("checkkkk", "getBrands: " + it.smart_collections[0].title)
                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }
        }
    }
    fun getOnSaleProducts(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = getOnSaleProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("checkkkk", "onResponse: $it")
                        Log.i("checkkkk", "getBrands " + it.products.size)
                        onSaleProductsList.value = it
                        Log.i("checkkkk", "getBrands: " + it.products[0].title)

                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }

        }
    }
    fun getOnHomeProducts(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = getOnHomeProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("checkkkk", "onResponse: $it")
                        Log.i("checkkkk", "getBrands " + it.products.size)
                        onHomeProductsList.value = it
                        Log.i("checkkkk", "getBrands: " + it.products[0].title)

                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }

        }

    }
    fun getallProduct(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = getProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allProduct.value = it
                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }

        }

    }
    val oneOrder= MutableLiveData<Order>()
    fun getcreatAnOrder(){

        GlobalScope.launch(Dispatchers.IO) {

            val response=RetrofitInstance.api.creatAnOrder()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        Log.d(TAG,"OnResponce Order:${it}")
                        oneOrder.value= it
                    }
                }else{

                    Log.i(TAG, "postOrder: error " + response.errorBody())


                }

            }
        }


    }
    val listOfOrder= MutableLiveData<GetOrderResponce>()

    fun getListOfOrderOpen(){
        GlobalScope.launch(Dispatchers.IO) {

            val response=RetrofitInstance.api.getListOfOrderOpen()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        Log.d(TAG,"OnResponce Order:${it}")
                        listOfOrder.value= it
                    }
                }else{

                    Log.i(TAG, "getOrder: error " + response.errorBody())


                }

            }
        }


    }
    val getOneOrder= MutableLiveData<Order>()

    fun getAnOrder(orderId:Long){

        GlobalScope.launch(Dispatchers.IO) {

            val response=RetrofitInstance.api.getAnOrder(orderId)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        Log.d(TAG,"OnResponce Order:${it}")
                        getOneOrder.value= it
                    }
                }else{

                    Log.i(TAG, "getAnOrder: error " + response.errorBody())


                }

            }
        }

    }
    ///////////////////
    lateinit var localDaoCart: LocalDataDao
    lateinit var localDaoOrder: OrderDao
    lateinit var cartProduct: CartProductData

    fun saveCartList(){
        localDaoCart.saveAllCartList(cartProduct)

    }
    fun getAllCartProduct(): LiveData<List<CartProductData>> {
        return localDaoCart.getAllCartList()

    }

    fun deleteOnCartItem(cartProduct: CartProductData){
        localDaoCart.deleteOnCartItem(cartProduct)
    }

    fun deleteAllFromCart(){
        localDaoCart.deleteAllFromCart()
    }

    ///order//
    fun getAllOrderList(): LiveData<List<OrderData>> {
        return  localDaoOrder.getAllOrderList()
    }
}
