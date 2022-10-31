package pl.edu.pja.pro_3.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import pl.edu.pja.pro_3.database.FeedDTo
import pl.edu.pja.pro_3.database.FeedDb
import pl.edu.pja.pro_3.databinding.ActivityMainBinding
import pl.edu.pja.pro_3.models.RSSFeedModel
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    private val view by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(view.root)
        view.loginButton.setOnClickListener { signInWithEmailAndPassword() }
        view.registerLinkDesc.setOnClickListener {
            startActivity(Intent(this, RegisterNewUserActivity::class.java))
        }
        loadData()
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.reload()

    }

    private fun loadData()= thread {
        val db = FeedDb.open(this)
        val stringFromURL =
            URL("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fmoxie.foxnews.com%2Ffeedburner%2Fscitech.xml").readText()
        val tmpObj: RSSFeedModel =
            listOf(Gson().fromJson(stringFromURL, RSSFeedModel::class.java))[0]
        val listItem: List<FeedDTo> = tmpObj.items.distinct()
        if (db.feed.getalltitles().isEmpty()) {
            listItem.forEach {
                db.feed.insert(it)
            }
        }
        db.close()
    }

    private fun signInWithEmailAndPassword() {
        mAuth.signInWithEmailAndPassword(
            view.loginInputField.text.toString(),
            view.passwordInputField.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signIn:success")
                    mAuth.currentUser?.reload()
                    startActivity(Intent(this, RssFeedList::class.java))
                } else {
//                    Log.d(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}