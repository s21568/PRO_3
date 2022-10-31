package pl.edu.pja.pro_3.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pl.edu.pja.pro_3.activities.NewsDetilsActivity
import pl.edu.pja.pro_3.database.FeedDTo
import pl.edu.pja.pro_3.database.FeedDb
import pl.edu.pja.pro_3.database.UserDto
import pl.edu.pja.pro_3.databinding.NewsItemCardBinding
import kotlin.concurrent.thread

var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

class FeedAdapter(private val db: FeedDb) : RecyclerView.Adapter<FeedVh>() {

    private var feedList = listOf<FeedDTo>()
    private lateinit var user: UserDto
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVh {

        val view = NewsItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FeedVh(view).also { holder ->

            context = holder.itemView.context
            view.root.setOnClickListener {
                val url = feedList[holder.layoutPosition].link
                val intent = Intent(
                    holder.itemView.context,
                    NewsDetilsActivity::class.java
                ).putExtra("url", url)
                holder.itemView.context.startActivity(intent)
            }
        }
    }


    override fun onBindViewHolder(holder: FeedVh, position: Int) {
        holder.bind(feedList[position], user)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun load() = thread {
        feedList = db.feed.getall() as MutableList<FeedDTo>
        user = db.user.getWithEmail(mAuth.currentUser?.email.toString())[0]
        mainHandler.post { notifyDataSetChanged() }
    }

}