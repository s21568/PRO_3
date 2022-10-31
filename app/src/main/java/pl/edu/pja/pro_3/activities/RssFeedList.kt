package pl.edu.pja.pro_3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import pl.edu.pja.pro_3.adapters.FeedAdapter
import pl.edu.pja.pro_3.adapters.FeedItemCallback
import pl.edu.pja.pro_3.database.FeedDb
import pl.edu.pja.pro_3.databinding.ActivityRssFeedListBinding

class RssFeedList : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    private val view by lazy { ActivityRssFeedListBinding.inflate(layoutInflater) }
    private val feedAdapter by lazy { FeedAdapter(FeedDb.open(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(view.root)
        view.logOutButton.setOnClickListener { signOut() }
        setUpTaskList()
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.reload()
    }

    private fun signOut() {
        mAuth.signOut()
        finish()
    }

    override fun onResume() {
        super.onResume()
        feedAdapter.load()
    }

    private fun setUpTaskList() {
        val itemSwitch = ItemTouchHelper(FeedItemCallback())
        view.feedList.apply {
            adapter =feedAdapter
                layoutManager = LinearLayoutManager(context)
            itemSwitch.attachToRecyclerView(this)
        }
        feedAdapter.load()
    }
}