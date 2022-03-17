package com.hema.e_commerce.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.currancy.CurrancyResponse
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.room.orderroom.OrderData
import kotlinx.coroutines.*

class Repository(private val db: RoomData) {
    //Aya
    //val productDetails = MutableLiveData<ProductItem>()

    val collectionsLiveData = MutableLiveData<CustomCollectionsResponse>()

    fun getCollections() {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = RetrofitInstance.api.getAllCollections()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        collectionsLiveData.value = it
                        Log.i("TAG", "getCollections: " + it.custom_collections.size)
                    }
                } else {


                }
            }

        }
    }

    val collectionProductsLiveData = MutableLiveData<ProductsResponse>()
    fun getSubCollectionsProducts(categoryId: Long) {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = RetrofitInstance.api.getCollectionProducts(categoryId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        collectionProductsLiveData.value = it
                    }
                } else {


                }
            }

        }
    }



    //Single product
    val singleProductsLiveData = MutableLiveData<ProductCollectionResponse>()
    fun getSingleProduct(productId: Long) {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = RetrofitInstance.api.getSingleProduct(productId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        singleProductsLiveData.value = it

                    }
                } else {

                }
            }

        }
    }



    // currancy

//    val currancyLiveData = MutableLiveData<CurrancyResponse>()
//    fun getCurrancy(to:String,amount:Double) {
//
//
//        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
//            val response = RetrofitInstance.api
//                .changeCurrancy(to,amount)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        Log.d("currancy", "onResponse: ${it}")
//                        Log.i("currancy", "getCurrancy: " + response.body().toString())
//                        currancyLiveData.value = it
//                    }
//                } else {
//                    Log.i("currancy", "getCurrancy: error " + response.errorBody())
//
//
//                }
//            }
//
//        }
//    }


    //////////////////////////////////////////
//Mohamed

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
//Live data
    val brandsLiveData = MutableLiveData<BrandsResponce>()
    //val onSaleProductsList = MutableLiveData<ProductsResponse>()
    //val onHomeProductsList = MutableLiveData<ProductsResponse>()
    val allProduct = MutableLiveData<ProductsResponse>()

    //Remote function
    private suspend fun getBrands() = RetrofitInstance.api.getBrands()

    private suspend fun getProducts() = RetrofitInstance.api.getProducts()

    private suspend fun getOnSaleProductsList() = RetrofitInstance.api.getOnSaleProductsList()

    private suspend fun getOnHomeProductsList() = RetrofitInstance.api.getOnHomeProductsList()

    //room
    private suspend fun fetchFav(favoriteProduct: FavoriteProduct) =
        db.getFavoriteData().insert(favoriteProduct)

    private suspend fun deleteFav(favoriteProduct: FavoriteProduct) =
        db.getFavoriteData().delete(favoriteProduct)

   // private suspend fun deleteAll() = db.getFavoriteData().deleteAll()

    private suspend fun deleteById(id: Long) = db.getFavoriteData().deleteById(id)


    fun getAllFav(customerId:Long) = db.getFavoriteData().getAllFav(customerId)

    fun getOneItem(id: Long,customerId:Long) = db.getFavoriteData().getOneItem(id,customerId)


    //Methods use in viewModel
    fun getBrand() {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = getBrands()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        brandsLiveData.value = it
                    }
                } else {


                }
            }
        }
    }

   /* fun getOnSaleProducts() {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = getOnSaleProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSaleProductsList.value = it
                    }
                } else {
                }
            }

        }
    }*/

  /*  fun getOnHomeProducts() {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = getOnHomeProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onHomeProductsList.value = it

                    }
                } else {
                }
            }

        }
    }*/

    fun getAllProduct() {
        GlobalScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = getProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allProduct.value = it
                    }
                } else {
                }
            }

        }

    }




    fun insert(favoriteProduct: FavoriteProduct) {
        GlobalScope.launch(Dispatchers.IO) {
            fetchFav(favoriteProduct)
        }
    }

    fun deleteOnItemFromFavByID(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            deleteById(id)
        }
    }

    fun deleteOnItemFromFav(favoriteProduct: FavoriteProduct) {
        GlobalScope.launch(Dispatchers.IO) {
            deleteFav(favoriteProduct)
        }
    }

 /*   fun deleteAllFromFav() {
        GlobalScope.launch(Dispatchers.IO) {
            deleteAll()
        }
    }*/


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    //Local database
    private suspend fun saveCartList(cartList: CartProductData) {
        db.getLocalDataObject().saveAllCartList(cartList)
    }

    private suspend fun updateCount(cartList: CartProductData) {
        db.getLocalDataObject().getCountUpdate(cartList)
    }

    fun getAllCartProduct(customerId:Long): LiveData<List<CartProductData>> {
        return db.getLocalDataObject().getAllCartList(customerId)

    }

    private suspend fun deleteOneItemOnCart(cartList: CartProductData) {
        db.getLocalDataObject().deleteOnCartItem(cartList)
    }

    private suspend fun deleteAll(customerId:Long) {
        db.getLocalDataObject().deleteAll(customerId)
    }

    //Method to handle data
    fun insert(cartList: CartProductData) {
        GlobalScope.launch(Dispatchers.IO) {
            saveCartList(cartList)
        }
    }

    fun updateCountChange(cartList: CartProductData) {
        GlobalScope.launch(Dispatchers.IO) {
            updateCount(cartList)
        }
    }

    fun deleteOneCartItem(cartList: CartProductData) {
        GlobalScope.launch(Dispatchers.IO) {
            deleteOneItemOnCart(cartList)
        }
    }
    fun deleteALL(customerId:Long) {
        GlobalScope.launch(Dispatchers.IO) {
            deleteAll(customerId)
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
//order
     fun addOrder(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().addOrder(orderData)
        }
    }



     fun updateOrder(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().updateState(orderData)
        }

    }

   /* fun updateState(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().updateState(orderData)
        }
    }*/

   /* fun getAllOrders():LiveData<List<OrderData>> {
       return db.getOrder().getAllOrders()
    }*/

     fun deleteOrder(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().deleteOrder(orderData)
        }
    }

   /*  fun deleteAllOrders() {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().deleteAllOrders()
        }
    }*/

    fun getOrdersFromState(state: String,customerId:Long):LiveData<List<OrderData>> {
     return   db.getOrder().getOrdersFromState(state,customerId)
    }




//



    //
}