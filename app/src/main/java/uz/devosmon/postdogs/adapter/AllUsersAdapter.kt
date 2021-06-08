package uz.devosmon.postdogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.all_users_layout.view.*
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.model.UserModel


interface AllUsersListener{
    fun onClick(item: UserModel)
}

interface UserPostsListener{
    fun onClick(item: UserModel)
}

class AllUsersAdapter(val items:List<UserModel>,val listener: AllUsersListener,val postsListener: UserPostsListener) :
    RecyclerView.Adapter<AllUsersAdapter.ItemHolder>() {


    inner class ItemHolder(val view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.all_users_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val item  = items[position]

        holder.itemView.usersName.text = item.firstName +" "+ item.firstName
holder.itemView.users_email.text = item.email


        holder.itemView.getFullProfile.setOnClickListener {
            listener.onClick(item)
        }

        holder.itemView.getPostsList.setOnClickListener {
            postsListener.onClick(item)
        }

        Glide.with(holder.itemView.context)
            .load(item.picture)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.itemView.usersImage)

    }

    override fun getItemCount(): Int {
        return items.count()
    }

}