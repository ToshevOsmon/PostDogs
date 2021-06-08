package uz.devosmon.postdogs.adapter

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_comment_layout.view.*
import uz.devosmon.postdogs.R
import uz.devosmon.postdogs.model.CommentModel
import java.text.SimpleDateFormat
import java.util.*


class CommentAdapter(val items: List<CommentModel>) :
    RecyclerView.Adapter<CommentAdapter.ItemHolder>() {


    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment_layout, parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        Log.d("TTTT", "onBindViewHolder work")
        holder.itemView.userNameId.text = item.owner.firstName + " " + item.owner.lastName
        holder.itemView.textComment.text = item.message

        Glide.with(holder.itemView.context).load(item.owner.picture).into(holder.itemView.userImgId)

        var myDate: String = item.publishDate
        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date: Date = dateFormat.parse(myDate)

        var formatDate: String =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
            } else ({

            }).toString()

        holder.itemView.dateId.text = formatDate

        Log.d("TTTT", "onBindViewHolder worked")

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}