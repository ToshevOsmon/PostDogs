package uz.devosmon.postretrofit.adapter

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.post_item_layout.view.*
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.model.PostModel
import java.text.SimpleDateFormat
import java.util.*


interface PostListener {
    fun onClick(item: PostModel)
}

class PostAdapter(val items: List<PostModel>, val listener: PostListener) :
    RecyclerView.Adapter<PostAdapter.ItemHolder>() {


    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position];

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        var myDate: String = item.publishDate
        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date: Date = dateFormat.parse(myDate)

        var formatDate:String = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
        } else ({

            Toast.makeText(holder.itemView.context,"Not Date",Toast.LENGTH_SHORT).show()

        }).toString()



        holder.itemView.postTitle.text = item.text
        holder.itemView.postDate.text = formatDate
        holder.itemView.likeCount.text = item.likes.toString()
        Glide.with(holder.itemView.context).load(item.image)
            .centerCrop()
            .into(holder.itemView.postImage)


    }
}