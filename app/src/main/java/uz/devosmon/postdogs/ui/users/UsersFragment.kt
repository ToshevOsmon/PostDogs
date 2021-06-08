package uz.devosmon.postdogs.ui.users

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_users.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.UserProfileActivity
import uz.devosmon.postdogs.adapter.ItemClick
import uz.devosmon.postdogs.adapter.PaginationUsersAdapter
import uz.devosmon.postdogs.model.UserModel
import uz.devosmon.postdogs.model.Users
import uz.devosmon.postdogs.retrofit2.ApiInterface
import uz.devosmon.postdogs.utels.PaginationScrollListener


class UsersFragment : Fragment() {


    lateinit var paganaitonAdapter: PaginationUsersAdapter
    var users_recycler: RecyclerView? = null

    var PAGE_START = 1
    var TOTAL_PAGE = 10
    var CURRENT_PAGE = PAGE_START
    var isLastPage = false
    var isLoading = false

    lateinit var retrofit: Retrofit
    lateinit var create: ApiInterface


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_users, container, false)

        users_recycler = root.findViewById(R.id.users_recycler)

        retrofit = uz.devosmon.postdogs.retrofit2.ApiService().getRetrofit()
        create = retrofit.create(ApiInterface::class.java)

        val linearLayoutManager = LinearLayoutManager(context)

        users_recycler?.layoutManager = linearLayoutManager
        paganaitonAdapter = PaginationUsersAdapter(context!!, object : ItemClick {
            override fun onClick(userModel: UserModel) {

                var intent = Intent(activity, UserProfileActivity::class.java)
                intent.putExtra("id", userModel)
                startActivity(intent)
            }
        })
        users_recycler?.adapter = paganaitonAdapter


        users_recycler?.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {

                isLoading = true
                CURRENT_PAGE++


                Handler().postDelayed({ loadNextPage() }, 1500)
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


        create.getUser(CURRENT_PAGE).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {

                val list: ArrayList<UserModel> = ArrayList()

                if (response.isSuccessful) {

                    list.addAll(response.body()?.data as ArrayList<UserModel>)
                    paganaitonAdapter.addUserModel(list)
                    paganaitonAdapter.setLoading(true)

                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.d("TTTT", "" + t.localizedMessage)
            }
        })

    }

    private fun loadNextPage() {

        if (CURRENT_PAGE <= TOTAL_PAGE) {
            create.getUser(CURRENT_PAGE).enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {

                    if (response.isSuccessful) {
                        isLoading = false

                        paganaitonAdapter.addUserModel(response.body()?.data as ArrayList<UserModel>)
                    }

                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    Log.d("TTTT", "" + t.localizedMessage)
                }
            })
        } else {
            paganaitonAdapter.setLoading(false)
            paganaitonAdapter.notifyDataSetChanged()
        }

    }


}
