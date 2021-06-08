package uz.devosmon.postdogs.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.devosmon.postdogs.BaseResponse
import uz.devosmon.postdogs.PostActivity
import uz.devosmon.postdogs.PostsActivity
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.api.ApiService
import uz.devosmon.postdogs.model.PostModel
import uz.devosmon.postdogs.model.UserModel
import uz.devosmon.postretrofit.adapter.PostAdapter
import uz.devosmon.postretrofit.adapter.PostListener
import uz.devosmon.postretrofit.adapter.UserAdapter
import uz.devosmon.postretrofit.adapter.UserListener


class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

var swipe:SwipeRefreshLayout? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

swipe = root.findViewById(R.id.swipe)

        swipe?.setOnRefreshListener(this)

        swipe?.isRefreshing = true
        loadUsers()
        loadPosts()
        return root
    }


    fun loadUsers() {
        ApiService.apiClient().getUsers().enqueue(object : Callback<BaseResponse<List<UserModel>>> {
            override fun onFailure(call: Call<BaseResponse<List<UserModel>>>, t: Throwable) {
                swipe?.isRefreshing = false
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<BaseResponse<List<UserModel>>>,
                response: Response<BaseResponse<List<UserModel>>>
            ) {

                recyclerUser.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recyclerUser.adapter =
                    UserAdapter(response.body()?.data ?: emptyList(), object : UserListener {
                        override fun onClick(item: UserModel) {
                            var intent = Intent(activity, PostsActivity::class.java)
                            intent.putExtra("extra_data", item)
                            startActivity(intent)
                        }
                    })
                swipe?.isRefreshing = false
            }
        })
    }


    fun loadPosts() {

        ApiService.apiClient().getPosts().enqueue(object : Callback<BaseResponse<List<PostModel>>> {
            override fun onFailure(call: Call<BaseResponse<List<PostModel>>>, t: Throwable) {

                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<BaseResponse<List<PostModel>>>,
                response: Response<BaseResponse<List<PostModel>>>
            ) {
                recyclerPost.layoutManager = LinearLayoutManager(activity)
                recyclerPost.adapter =
                    PostAdapter(response.body()?.data ?: emptyList(), object : PostListener {
                        override fun onClick(item: PostModel) {

                            var intent = Intent(activity, PostActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)

                        }
                    })
            }
        })
    }

    override fun onRefresh() {

        loadUsers()
        loadPosts()

    }

}
