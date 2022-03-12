package com.hema.e_commerce.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.MainActivity
import com.hema.e_commerce.model.dataclass.allProducts.ProductItem
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.getOrder.GetOrderResponce
import com.hema.e_commerce.model.dataclass.getOrder.Order
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.room.orderroom.OrderData
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val db: RoomData) {
    //Aya
    val productDetails = MutableLiveData<ProductItem>()

    val collectionsLiveData = MutableLiveData<CustomCollectionsResponse>()

    fun getCollections() {
        GlobalScope.launch(Dispatchers.IO) {
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
        GlobalScope.launch(Dispatchers.IO) {
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
        GlobalScope.launch(Dispatchers.IO) {
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

    //////////////////////////////////////////
//Mohamed
//Live data
    val brandsLiveData = MutableLiveData<BrandsResponce>()
    val onSaleProductsList = MutableLiveData<ProductsResponse>()
    val onHomeProductsList = MutableLiveData<ProductsResponse>()
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

    private suspend fun deleteAll() = db.getFavoriteData().deleteAll()

    private suspend fun deleteById(id: Long) = db.getFavoriteData().deleteById(id)


    fun getAllFav() = db.getFavoriteData().getAllFav()

    fun getOneItem(id: Long) = db.getFavoriteData().getOneItem(id)


    //Methods use in viewModel
    fun getBrand() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = getBrands()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        brandsLiveData.value = it
                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody())
                }
            }
        }
    }

    fun getOnSaleProducts() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = getOnSaleProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSaleProductsList.value = it
                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody())
                }
            }

        }
    }

    fun getOnHomeProducts() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = getOnHomeProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onHomeProductsList.value = it

                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody())
                }
            }

        }
    }

    fun getallProduct() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = getProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allProduct.value = it
                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody())
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

    fun deleteAllFromFav() {
        GlobalScope.launch(Dispatchers.IO) {
            deleteAll()
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    //Local database
    suspend fun saveCartList(cartlist: CartProductData) {
        db.getLocalDataObject().saveAllCartList(cartlist)
    }

    suspend fun updateCount(cartlist: CartProductData) {
        db.getLocalDataObject().getCountUpdate(cartlist)
    }

    fun getAllCartProduct(): LiveData<List<CartProductData>> {
        return db.getLocalDataObject().getAllCartList()

    }

    suspend fun deleteOneItemOnCart(cartlist: CartProductData) {
        db.getLocalDataObject().deleteOnCartItem(cartlist)
    }

    //Method to handle data
    fun insert(cartlist: CartProductData) {
        GlobalScope.launch(Dispatchers.IO) {
            saveCartList(cartlist)
        }
    }

    fun updateCountChange(cartlist: CartProductData) {
        GlobalScope.launch(Dispatchers.IO) {
            updateCount(cartlist)
        }
    }

    fun deleteOneCartItem(cartlist: CartProductData) {
        GlobalScope.launch(Dispatchers.IO) {
            deleteOneItemOnCart(cartlist)
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
//order
     fun addOrder(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().addOrder(orderData)
        }
    }

    fun getCanceledOrder(state:String) {
            db.getOrder().getCanceledOrder(state)
    }

/*     fun updateOrder(id: Long, state: String) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().updateOrder(id, state)
        }

    }*/

     fun updateOrder(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().updateState(orderData)
        }

    }

     fun updateState(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().updateState(orderData)
        }
    }

    fun getAllOrders():LiveData<List<OrderData>> {
       return db.getOrder().getAllOrders()
    }

     fun deleteOrder(orderData: OrderData) {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().deleteOrder(orderData)
        }
    }

     fun deleteAllOrders() {
        GlobalScope.launch(Dispatchers.IO) {
            db.getOrder().deleteAllOrders()
        }
    }
    fun getOrdersFromState(state: String):LiveData<List<OrderData>> {
     return   db.getOrder().getOrdersFromState(state)
    }




}