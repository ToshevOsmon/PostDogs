package uz.devosmon.postdogs.retrofit2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.devosmon.postdogs.model.Posts
import uz.devosmon.postdogs.model.Users

interface ApiInterface {

    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("user")
    fun getUser(@Query("page") page:Int,
                @Query("limit") limit:Int=10
    ): Call<Users>

    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("post")
    fun getPosts(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Call<Posts>

}