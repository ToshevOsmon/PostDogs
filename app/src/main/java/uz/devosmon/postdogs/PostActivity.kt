package uz.devosmon.postdogs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_post.*
import uz.devosmon.postdogs.model.PostModel

class PostActivity : AppCompatActivity() {

    lateinit var post: PostModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        supportActionBar?.hide()


        post = intent.getSerializableExtra("data") as PostModel

        Glide.with(this).load(post.owner.picture).into(imageUser)

        tvPostTitle.text = post.owner.firstName + " " + post.owner.lastName
        tvPostName.text = post.owner.title + ": " + post.owner.lastName
        userPostTitle.text = post.text
        Glide.with(this).load(post.image).into(userPostImage)
        likeCount.text = post.likes.toString()
        postLink.text = post.link
        userEmail.text = post.owner.email


        userEmail.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_SEND)
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(userEmail.text.toString()))
            //put the Subject in the intent
            mIntent.putExtra(Intent.EXTRA_SUBJECT, userPostTitle.text.toString())
            try {
                //start email intent
                startActivity(Intent.createChooser(mIntent, "Choose Gmail Client..."))
            } catch (e: Exception) {
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }


        }
        postLink.setOnClickListener {
            if (postLink.text.toString().isEmpty()) {
                Toast.makeText(this, "Uri link is empty", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(postLink.text.toString()))
                startActivity(intent)
            }


        }


        post_layout.setOnClickListener {

            var intent = Intent(this,CommentActivity::class.java)
            intent.putExtra("post_id",post)
            startActivity(intent)

        }

    }
}
