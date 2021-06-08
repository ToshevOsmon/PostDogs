package uz.devosmon.postdogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_comment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.devosmon.postdogs.adapter.CommentAdapter
import uz.devosmon.postdogs.api.ApiService
import uz.devosmon.postdogs.model.CommentModel
import uz.devosmon.postdogs.model.PostModel

class CommentActivity : AppCompatActivity() {

    lateinit var postId: PostModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        supportActionBar?.hide()

        postId = intent.getSerializableExtra("post_id") as PostModel
        Log.d("TTTT", "postId: " + postId.id)
        getLoadComment(postId.id)


        postTitleId.text = postId.owner.firstName + " "+ postId.owner.lastName

    }

    private fun getLoadComment(id: String) {

        Log.d("TTTT", "Id = : " + id)
        ApiService.apiClient().getPostComments(id).enqueue(object :
            Callback<BaseResponse<List<CommentModel>>> {
            override fun onResponse(

                call: Call<BaseResponse<List<CommentModel>>>,
                response: Response<BaseResponse<List<CommentModel>>>
            ) {
                if (response.isSuccessful && !response.body()?.data.isNullOrEmpty()) {

                    Log.d("TTTT", "response successful")
                    comment_recycler.layoutManager = LinearLayoutManager(this@CommentActivity)
                    Log.d("TTTT", "rv layoutManager worked")
                    comment_recycler.adapter = CommentAdapter(response.body()?.data ?: emptyList())
                    Log.d("TTTT", "rv adapter worked")
                } else {
                    Toast.makeText(
                        this@CommentActivity,
                        "Hech qanday comment yo'q ",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }


            }

            override fun onFailure(call: Call<BaseResponse<List<CommentModel>>>, t: Throwable) {
                Toast.makeText(this@CommentActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }
}
