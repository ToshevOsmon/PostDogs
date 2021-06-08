package uz.devosmon.postdogs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_profile_layout.view.*
import kotlinx.android.synthetic.main.loading_item_layout.view.*
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.model.UserModel


interface ItemClick {
    fun onClick(userModel: UserModel)
    fun onPostClick(userModel: UserModel)

}


class PaginationUsersAdapter(val context: Context, val itemListener: ItemClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lists: ArrayList<UserModel>
    var ITEM = 0
    var LOADING = 1
    private var isLoading = false


    init {
        lists = ArrayList()
    }


    class UserViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(userModel: UserModel) {

            view.profileName.text = userModel.title + " " + userModel.firstName+" "+userModel.lastName
            view.profileEmail.text = userModel.email
            Glide.with(view.context).load(userModel.picture).into(view.profileImage)


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
                viewHolder = UserViewHolder(
                    layoutInflater.inflate(
                        R.layout.item_profile_layout,
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

        var userModel: UserModel = lists[position]




        when (getItemViewType(position)) {
            ITEM -> {
                var userViewHolder: UserViewHolder = holder as UserViewHolder
                userViewHolder.onBind(userModel)

                userViewHolder.view.profileUser.setOnClickListener {
                    itemListener.onClick(userModel)
                }

                userViewHolder.view.profilePosts.setOnClickListener {

                    itemListener.onPostClick(userModel)
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

    fun addUserModel(userModel: ArrayList<UserModel>) {

        lists.addAll(userModel)
        notifyDataSetChanged()
    }

    fun setLoading(b: Boolean) {
        isLoading = b
    }


}