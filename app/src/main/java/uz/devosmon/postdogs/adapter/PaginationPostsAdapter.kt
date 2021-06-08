package uz.devosmon.postdogs.adapter


import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.loading_item_layout.view.*
import kotlinx.android.synthetic.main.post_item_layout.view.*
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.model.PostModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


interface ItemPostClick{
    fun onClick(postModel: PostModel)
}

class PaginationPostsAdapter(val itemListener:ItemPostClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lists: ArrayList<PostModel>
    var ITEM = 0
    var LOADING = 1
    private var isLoading = false

    init {
        lists = ArrayList()
    }

    class PostViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(postModel: PostModel) {

            var myDate: String = postModel.publishDate
            var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date: Date = dateFormat.parse(myDate)

            var formatDate:String = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
            } else ({

            }).toString()

            view.postTitle.text = postModel.text
            view.postDate.text = formatDate
            view.likeCount.text = postModel.likes.toString()
            Glide.with(view.context).load(postModel.image).into(view.postImage)
        }
    }
    class LoadingViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun onBind() {
            itemView.progress_circular.visibility = View.VISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        var layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {

            ITEM -> {
                viewHolder = PostViewHolder(
                    layoutInflater.inflate(
                        R.layout.post_item_layout,
                        parent,
                        false
                    )
                )
            }
            LOADING -> {
                viewHolder = LoadingViewHolder(
                    layoutInflater.inflate(
                        R.layout.loading_item_layout,
                        parent,
                        false
                    )
                )
            }
        }
        return viewHolder!!
    }


    override fun getItemViewType(position: Int): Int {

        if (lists.size - 1 == position && isLoading) {
            return LOADING
        } else {
            return ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var postModel: PostModel = lists[position]




        when (getItemViewType(position)) {
            ITEM -> {
                var userViewHolder: PostViewHolder = holder as PostViewHolder
                userViewHolder.onBind(postModel)

                holder.itemView.setOnClickListener {
                    itemListener.onClick(postModel)
                }

            }
            LOADING -> {
                var loadingViewHolder: LoadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.onBind()
            }
        }
    }

    override fun getItemCount(): Int {

        if (lists == null) {
            return 0
        } else {
            return lists.size
        }
    }

    fun addPostModel(postModel: ArrayList<PostModel>) {

        lists.addAll(postModel)
        notifyDataSetChanged()
    }

    fun setLoading(b: Boolean) {
        isLoading = b
    }


}