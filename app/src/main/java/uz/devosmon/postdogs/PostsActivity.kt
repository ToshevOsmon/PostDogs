package uz.devosmon.postdogs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_posts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.devosmon.postdogs.api.ApiService
import uz.devosmon.postdogs.model.PostModel
import uz.devosmon.postdogs.model.UserModel
import uz.devosmon.postretrofit.adapter.PostAdapter
import uz.devosmon.postretrofit.adapter.PostListener


class PostsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var user: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        supportActionBar?.hide()

        user = intent.getSerializableExtra("extra_data") as UserModel

        swipe.setOnRefreshListener(this)
        postTitle.text = user.firstName + " " + user.lastName

        loadUserPosts()


    }

    override fun onRefresh() {
        loadUserPosts()

    }

    fun loadUserPosts(){

        swipe.isRefreshing = true

        ApiService.apiClient().getUserPosts(user.id).enqueue(object :
            Callback<BaseResponse<List<PostModel>>> {
            override fun onFailure(call: Call<BaseResponse<List<PostModel>>>, t: Throwable) {
                swipe.isRefreshing = false
                Toast.makeText(this@PostsActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(
                call: Call<BaseResponse<List<PostModel>>>,
                response: Response<BaseResponse<List<PostModel>>>
            ) {
                swipe.isRefreshing = false
                recyclerPosts.layoutManager = LinearLayoutManager(this@PostsActivity)
                recyclerPosts.adapter = PostAdapter(response.body()?.data?: emptyList(),object :
                    PostListener
                {
                    override fun onClick(item: PostModel) {

                        var intent = Intent(this@PostsActivity,CommentActivity::class.java)
                        intent.putExtra("post_id",item)
                        startActivity(intent)


                    }
                })


            }
        })

    }
}
