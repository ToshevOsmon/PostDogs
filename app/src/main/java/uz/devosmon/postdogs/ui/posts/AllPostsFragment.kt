package uz.devosmon.postdogs.ui.posts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uz.devosmon.postdogs.PostActivity
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.UserProfileActivity
import uz.devosmon.postdogs.adapter.ItemClick
import uz.devosmon.postdogs.adapter.ItemPostClick
import uz.devosmon.postdogs.adapter.PaginationPostsAdapter
import uz.devosmon.postdogs.adapter.PaginationUsersAdapter
import uz.devosmon.postdogs.model.PostModel
import uz.devosmon.postdogs.model.Posts
import uz.devosmon.postdogs.model.UserModel
import uz.devosmon.postdogs.model.Users
import uz.devosmon.postdogs.retrofit2.ApiInterface
import uz.devosmon.postdogs.utels.PaginationScrollListener

class AllPostsFragment : Fragment() {


    lateinit var paganaitonAdapter: PaginationPostsAdapter
    var posts_recycler: RecyclerView? = null

    var PAGE_START = 1
    var TOTAL_PAGE = 10
    var CURRENT_PAGE = PAGE_START
    var isLastPage = false
    var isLoading = false

    lateinit var retrofit: Retrofit
    lateinit var create: ApiInterface

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_posts, container, false)



        posts_recycler = root.findViewById(R.id.posts_recycler)

        retrofit = uz.devosmon.postdogs.retrofit2.ApiService().getRetrofit()
        create = retrofit.create(ApiInterface::class.java)

        val linearLayoutManager = LinearLayoutManager(context)

        posts_recycler?.layoutManager = linearLayoutManager
        paganaitonAdapter = PaginationPostsAdapter( object : ItemPostClick {
            override fun onClick(postModel: PostModel) {

                var intent = Intent(activity, PostActivity::class.java)
                intent.putExtra("data", postModel)
                startActivity(intent)

            }
        })
        posts_recycler?.adapter = paganaitonAdapter


        posts_recycler?.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {

                isLoading = true
                CURRENT_PAGE++


                Handler().postDelayed({ loadNextPage() }, 1000)
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGE
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        loadingFirstPage()

        return root
    }


    private fun loadingFirstPage() {


        create.getPosts(CURRENT_PAGE).enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {

                val list: ArrayList<PostModel> = ArrayList()

                if (response.isSuccessful) {

                    list.addAll(response.body()?.data as ArrayList<PostModel>)
                    paganaitonAdapter.addPostModel(list)
                    paganaitonAdapter.setLoading(true)

                }
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                Log.d("TTTT", "" + t.localizedMessage)
            }
        })

    }

    private fun loadNextPage() {

        if (CURRENT_PAGE <= TOTAL_PAGE) {
            create.getPosts(CURRENT_PAGE).enqueue(object : Callback<Posts> {
                override fun onResponse(call: Call<Posts>, response: Response<Posts>) {

                    if (response.isSuccessful) {
                        isLoading = false

                        paganaitonAdapter.addPostModel(response.body()?.data as ArrayList<PostModel>)
                    }

                }

                override fun onFailure(call: Call<Posts>, t: Throwable) {
                    Log.d("TTTT", "" + t.localizedMessage)
                }
            })
        } else {
            paganaitonAdapter.setLoading(false)
            paganaitonAdapter.notifyDataSetChanged()
        }

    }

}
