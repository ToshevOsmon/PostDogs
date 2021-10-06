package uz.devosmon.postdogs.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import uz.devosmon.postdogs.BaseResponse
import uz.devosmon.postdogs.model.*


interface Api {

    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("user")
    fun getUsers(): Call<BaseResponse<List<UserModel>>>


    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("post")
    fun getPosts(): Call<BaseResponse<List<PostModel>>>


    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("user/{user_id}/post")
    fun getUserPosts(@Path("user_id") id: String): Call<BaseResponse<List<PostModel>>>


    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("user/{user_id}")
    fun getUsersById(@Path("user_id") id: String): Call<AllUserProfileModel>


    @Headers("app-id:615df7bc37c0e25db0c15f1c")
    @GET("post/{post_id}/comment")
    fun getPostComments(@Path("post_id")id:String): Call<BaseResponse<List<CommentModel>>>


}